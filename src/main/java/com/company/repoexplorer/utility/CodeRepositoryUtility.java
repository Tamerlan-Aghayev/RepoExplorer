package com.company.repoexplorer.utility;

import com.company.repoexplorer.client.CodeRepositoryClient;
import com.company.repoexplorer.config.constants.ExplorerConfig;
import com.company.repoexplorer.exception.RateLimitException;
import com.company.repoexplorer.exception.UserNotFoundException;
import com.company.repoexplorer.model.BranchInfo;
import com.company.repoexplorer.model.RepositoryInfo;
import com.company.repoexplorer.model.client.ClientDTO;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
@Component
@RequiredArgsConstructor
@Slf4j
public class CodeRepositoryUtility {
    private final CodeRepositoryClient client;
    public void validateUser(String username) {
        var response = client.validateUser(username);
        switch (response.status()) {
            case 404 -> throw new UserNotFoundException("User " + username + " not found");
            case 403 -> throw new RateLimitException("Rate limit exceeded for " + username);
            case 200 -> log.info("User {} validated", username);
            default -> throw new RuntimeException("Unexpected error occurred");
        }
    }

    public List<ClientDTO.Repository> fetchAllRepositories(String username) {
        log.info("Fetching repositories for {}", username);
        List<ClientDTO.Repository> allRepositories = new ArrayList<>();
        int page = 1;
        List<ClientDTO.Repository> repoPage;
        do {
            repoPage = client.getRepositories(username, ExplorerConfig.MAX_PAGE_SIZE, page);
            allRepositories.addAll(repoPage);
            page++;
        } while (!repoPage.isEmpty());
        log.info("Fetched {} repositories for {}", allRepositories.size(), username);
        return allRepositories;
    }

    public List<RepositoryInfo> processRepositories(String username, List<ClientDTO.Repository> repositories) {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            List<CompletableFuture<RepositoryInfo>> futures = repositories.stream()
                    .filter(repo -> !repo.fork())
                    .map(repo -> CompletableFuture.supplyAsync(() -> processRepository(username, repo), executor))
                    .toList();

            CompletableFuture<Void> allFutures = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));

            return allFutures.thenApply(v ->
                    futures.stream()
                            .map(CompletableFuture::join)
                            .toList()
            ).join();
        }
    }

    public RepositoryInfo processRepository(String username, ClientDTO.Repository repo) {
        List<BranchInfo> branches = fetchBranches(username, repo.name());
        return new RepositoryInfo(repo.name(), repo.owner().login(), branches);
    }

    public List<BranchInfo> fetchBranches(String owner, String repo) {
        try {
            List<ClientDTO.Branch> clientBranches = client.getBranches(owner, repo);
            return clientBranches.stream()
                    .map(branch -> new BranchInfo(branch.name(), branch.commit().sha()))
                    .toList();
        } catch (FeignException.Forbidden e) {
            log.warn("Rate limit hit while fetching branches for {}/{}", owner, repo);
            return List.of();
        }
    }
}
