package com.company.repoexplorer.service;

import com.company.repoexplorer.model.RepositoryInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface CodeRepositoryExplorerService {
    public List<RepositoryInfo> exploreRepositories(String username);
}
