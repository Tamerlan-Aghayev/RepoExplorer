package com.company.repoexplorer.client;

import com.company.repoexplorer.model.client.ClientDTO;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name="CodeRepositoryClient", url="https://api.github.com/")
public interface CodeRepositoryClient {
    @GetMapping("/users/{username}")
    Response validateUser(@PathVariable("username") String username);

    @GetMapping("/users/{username}/repos")
    List<ClientDTO.Repository> getRepositories(
            @PathVariable("username") String username,
            @RequestParam(name = "per_page") int perPage,
            @RequestParam(name = "page") int page
    );

    @GetMapping("/repos/{owner}/{repo}/branches")
    List<ClientDTO.Branch> getBranches(
            @PathVariable("owner") String owner,
            @PathVariable("repo") String repo
    );
}