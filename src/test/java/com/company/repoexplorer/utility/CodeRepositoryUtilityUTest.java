package com.company.repoexplorer.utility;

import com.company.repoexplorer.client.CodeRepositoryClient;
import com.company.repoexplorer.constants.TestConstants;
import com.company.repoexplorer.exception.UserNotFoundException;
import com.company.repoexplorer.model.BranchInfo;
import com.company.repoexplorer.model.RepositoryInfo;
import com.company.repoexplorer.model.client.ClientDTO;
import feign.Request;
import feign.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

class CodeRepositoryUtilityUTest {

    @Mock
    private CodeRepositoryClient client;

    @Spy
    @InjectMocks
    private CodeRepositoryUtility utility;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void validateUser_userNotFound() {
        Response response = createMockResponse(404);

        when(client.validateUser(TestConstants.INVALID_USERNAME)).thenReturn(response);

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> utility.validateUser(TestConstants.INVALID_USERNAME));
        assertEquals("User "+TestConstants.INVALID_USERNAME+" not found", exception.getMessage());
    }


    @Test
    void validateUser_success() {
        when(client.validateUser("validUser")).thenReturn(createMockResponse(200));

        assertDoesNotThrow(() -> utility.validateUser("validUser"));
    }


    @Test
    void processRepositories_success() {
        ClientDTO.Repository repo1 = new ClientDTO.Repository("repo1",false, new ClientDTO.Owner("owner1"), List.of());
        ClientDTO.Repository repo2 = new ClientDTO.Repository("repo2",false, new ClientDTO.Owner("owner2"), List.of());

        doReturn(new RepositoryInfo("repo1", "owner1", List.of()))
                .when(utility).processRepository("validUser", repo1);
        doReturn(new RepositoryInfo("repo2", "owner2", List.of()))
                .when(utility).processRepository("validUser", repo2);

        List<RepositoryInfo> repositoryInfos = utility.processRepositories("validUser", List.of(repo1, repo2));

        assertEquals(2, repositoryInfos.size());
        assertEquals("repo1", repositoryInfos.get(0).name());
        assertEquals("repo2", repositoryInfos.get(1).name());
    }

    @Test
    void fetchBranches_success() {
        ClientDTO.Branch branch1 = new ClientDTO.Branch("branch1", new ClientDTO.Commit("sha1"));
        ClientDTO.Branch branch2 = new ClientDTO.Branch("branch2", new ClientDTO.Commit("sha2"));

        when(client.getBranches("owner1", "repo1")).thenReturn(List.of(branch1, branch2));

        List<BranchInfo> branches = utility.fetchBranches("owner1", "repo1");

        assertEquals(2, branches.size());
        assertEquals("branch1", branches.get(0).name());
        assertEquals("sha1", branches.get(0).lastCommitSha());
    }
    private Response createMockResponse(int status) {
        Request request = Request.create(Request.HttpMethod.GET, "/test", new HashMap<>(), Request.Body.empty(), null);

        return Response.builder()
                .status(status)
                .reason("Test Reason")
                .headers(new HashMap<>())
                .request(request)
                .body(new byte[0])
                .build();
    }

}
