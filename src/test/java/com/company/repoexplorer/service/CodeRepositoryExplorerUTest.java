package com.company.repoexplorer.service;

import com.company.repoexplorer.constants.TestConstants;
import com.company.repoexplorer.exception.UserNotFoundException;
import com.company.repoexplorer.model.RepositoryInfo;
import com.company.repoexplorer.model.client.ClientDTO;
import com.company.repoexplorer.utility.CodeRepositoryUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
class CodeRepositoryExplorerUTest {

    @Mock
    private CodeRepositoryUtility utility;

    @InjectMocks
    private CodeRepositoryExplorer explorer;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void exploreRepositories_userNotFound() {
        doThrow(new UserNotFoundException("User " + TestConstants.INVALID_USERNAME + " not found"))
                .when(utility).validateUser(TestConstants.INVALID_USERNAME);

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> explorer.exploreRepositories(TestConstants.INVALID_USERNAME));
        assertEquals("User " + TestConstants.INVALID_USERNAME + " not found", exception.getMessage());
    }

    @Test
    void exploreRepositories_success() {
        ClientDTO.Repository repo1 = new ClientDTO.Repository("repo1",false, new ClientDTO.Owner("owner1"), null);
        ClientDTO.Repository repo2 = new ClientDTO.Repository("repo2",false, new ClientDTO.Owner("owner1"), null);
        RepositoryInfo repoInfo1 = new RepositoryInfo("repo1", "owner1", List.of());
        RepositoryInfo repoInfo2 = new RepositoryInfo("repo2", "owner1", List.of());

        doNothing().when(utility).validateUser(TestConstants.VALID_USERNAME);
        when(utility.fetchAllRepositories(TestConstants.VALID_USERNAME)).thenReturn(List.of(repo1, repo2));
        when(utility.processRepositories(TestConstants.VALID_USERNAME, List.of(repo1, repo2))).thenReturn(List.of(repoInfo1, repoInfo2));

        List<RepositoryInfo> result = explorer.exploreRepositories(TestConstants.VALID_USERNAME);

        assertEquals(2, result.size());
        assertEquals("repo1", result.get(0).name());
        assertEquals("repo2", result.get(1).name());
    }
}
