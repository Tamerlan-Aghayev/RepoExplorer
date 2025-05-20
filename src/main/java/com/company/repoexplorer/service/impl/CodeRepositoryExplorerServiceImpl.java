package com.company.repoexplorer.service.impl;

import com.company.repoexplorer.exception.RateLimitException;
import com.company.repoexplorer.exception.UserNotFoundException;
import com.company.repoexplorer.model.RepositoryInfo;
import com.company.repoexplorer.model.client.ClientDTO;
import com.company.repoexplorer.service.CodeRepositoryExplorerService;
import com.company.repoexplorer.utility.CodeRepositoryUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
@Slf4j
@RequiredArgsConstructor
public class CodeRepositoryExplorerServiceImpl implements CodeRepositoryExplorerService {

    private final CodeRepositoryUtility utility;
    public List<RepositoryInfo> exploreRepositories(String username) {
        try {
            utility.validateUser(username);
            List<ClientDTO.Repository> repositories = utility.fetchAllRepositories(username);
            return utility.processRepositories(username, repositories);
        } catch (UserNotFoundException | RateLimitException e) {
            log.error("Error exploring repositories for user {}: {}", username, e.getMessage());
            throw e;
        }
    }
}
