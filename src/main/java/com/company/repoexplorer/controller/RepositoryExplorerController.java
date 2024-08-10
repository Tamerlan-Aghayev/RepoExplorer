package com.company.repoexplorer.controller;

import com.company.repoexplorer.model.ApiResponse;
import com.company.repoexplorer.model.RepositoryInfo;
import com.company.repoexplorer.service.CodeRepositoryExplorer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/repos")
public class RepositoryExplorerController {
    private final CodeRepositoryExplorer explorer;

    @GetMapping("/{username}")
    public ResponseEntity<ApiResponse<List<RepositoryInfo>>> exploreRepositories(
            @PathVariable("username") String username) {
        return ResponseEntity.ok(ApiResponse.success(explorer.exploreRepositories(username)));
    }
}