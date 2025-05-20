package com.company.repoexplorer.service;

import com.company.repoexplorer.RepoExplorerApplication;
import com.company.repoexplorer.constants.TestConstants;
import com.company.repoexplorer.exception.UserNotFoundException;
import com.company.repoexplorer.model.RepositoryInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(classes = RepoExplorerApplication.class)
public class CodeRepositoryExplorerITest {

    @Autowired
    private CodeRepositoryExplorerService explorer;

    @Test
    @DisplayName("Explore repositories-success")
    void explore_repositories_success(){
        List<RepositoryInfo> repos=explorer.exploreRepositories(TestConstants.VALID_USERNAME);
        Assertions.assertEquals(TestConstants.NON_FORK_REPO_COUNT, repos.size());
    }

    @Test
    @DisplayName("Explore repositories - user not found")
    void explore_repositories_not_found() {
        UserNotFoundException exception = Assertions.assertThrows(UserNotFoundException.class, () -> explorer.exploreRepositories(TestConstants.INVALID_USERNAME));
        Assertions.assertEquals("User nonGithubUser not found", exception.getMessage());
    }

}
