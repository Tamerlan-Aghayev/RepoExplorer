package com.company.repoexplorer.utility;

import com.company.repoexplorer.RepoExplorerApplication;
import com.company.repoexplorer.constants.TestConstants;
import com.company.repoexplorer.exception.UserNotFoundException;
import com.company.repoexplorer.model.BranchInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(classes = RepoExplorerApplication.class)
public class CodeRepositoryUtilityITest {

    @Autowired
    private CodeRepositoryUtility utility;

    @Test
    @DisplayName("user validation-success")
    void validateUserSuccess() {
        utility.validateUser(TestConstants.VALID_USERNAME);
    }
    @Test
    @DisplayName("user validation-failure")
    void validateUserFailure() {
        UserNotFoundException exception = Assertions.assertThrows(UserNotFoundException.class, () -> utility.validateUser(TestConstants.INVALID_USERNAME));
        Assertions.assertEquals("User nonGithubUser not found", exception.getMessage());
    }
    @Test
    @DisplayName("Fetching repos")
    void fetchAllRepositories() {
        Assertions.assertEquals(TestConstants.ALL_REPO_COUNT, utility.fetchAllRepositories(TestConstants.VALID_USERNAME).size());
    }

    @Test
    @DisplayName("Fetching branches")
    public void fetchBranches() {

        List<BranchInfo> branchInfos=utility.fetchBranches(TestConstants.VALID_USERNAME,TestConstants.REPO_NAME);
        Assertions.assertEquals(TestConstants.BRANCH_COUNT, branchInfos.size());
    }
}
