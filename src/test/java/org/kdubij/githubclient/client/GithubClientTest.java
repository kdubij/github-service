package org.kdubij.githubclient.client;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kdubij.githubclient.client.exception.client.GithubClientConnectionException;
import org.kdubij.githubclient.client.exception.client.GithubClientException;
import org.kdubij.githubclient.client.exception.client.GithubRepoNotFoundException;
import org.kdubij.githubclient.client.exception.server.GithubServerException;
import org.kdubij.githubclient.client.exception.server.GithubServiceUnavailableException;
import org.kdubij.githubclient.client.model.GithubResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.support.RestGatewaySupport;

import java.net.SocketTimeoutException;
import java.time.LocalDate;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class GithubClientTest {
    private static final String GITHUB_URL = "https://api.github.com/repos/owner/repoName";
    private static final String OWNER = "owner";
    private static final String REPO_NAME = "repoName";
    private static final String FULL_NAME = "kdubij/HeroService";
    private static final String REPOSITORY_DESCRIPTION = "repository description";
    private static final LocalDate CREATED_AT = LocalDate.parse("2018-07-30");
    private static final String CLONE_URL = "https://github.com/kdubij/HeroService.git";
    private static final int STARS = 100;
    private static final String EXCEPTION_MSG = "Connection exception";
    private MockRestServiceServer mockServer;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private GithubClient githubClient;

    @Before
    public void setUp() {
        RestGatewaySupport gateway = new RestGatewaySupport();
        gateway.setRestTemplate(restTemplate);
        mockServer = MockRestServiceServer.createServer(gateway);
    }

    @Test
    public void shouldReturnGithubDtoWhenRequestSuccess() {
        mockServer.expect(requestTo(GITHUB_URL))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(GithubClientTestHelper.getGithubJson(), MediaType.APPLICATION_JSON));

        GithubResponseDto dto = githubClient.getRepository(OWNER, REPO_NAME);

        mockServer.verify();
        GithubResponseAssertions.assertDto(dto)
                .withCloneUrl(CLONE_URL)
                .withCreatedAt(CREATED_AT)
                .withDescription(REPOSITORY_DESCRIPTION)
                .withFullName(FULL_NAME)
                .withStars(STARS);
    }

    @Test(expected = GithubServerException.class)
    public void shouldThrowGithubServerExceptionWhenServerReturnWithErrorStatus() {
        mockServer.expect(requestTo(GITHUB_URL))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withServerError());

        githubClient.getRepository(OWNER, REPO_NAME);

        mockServer.verify();
    }

    @Test(expected = GithubServiceUnavailableException.class)
    public void shouldThrowGithubServiceUnavailableExceptionWhenReturnStatusServiceUnavailable() {
        mockServer.expect(requestTo(GITHUB_URL))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.SERVICE_UNAVAILABLE));

        githubClient.getRepository(OWNER, REPO_NAME);

        mockServer.verify();
    }

    @Test(expected = GithubRepoNotFoundException.class)
    public void shouldThrowGithubRepoNotFoundExceptionWhenReturnStatusNotFound() {
        mockServer.expect(requestTo(GITHUB_URL))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.NOT_FOUND));

        githubClient.getRepository(OWNER, REPO_NAME);

        mockServer.verify();
    }

    @Test(expected = GithubClientException.class)
    public void shouldThrowGithubClientExceptionWhenReturnClientErrorStatus() {
        mockServer.expect(requestTo(GITHUB_URL))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withBadRequest());

        githubClient.getRepository(OWNER, REPO_NAME);

        mockServer.verify();
    }

    @Test(expected = GithubClientConnectionException.class)
    public void shouldThrowGithubClientConnectionExceptionWhenResourceAccessConnectionOccurred() {
        mockServer.expect(requestTo(GITHUB_URL))
                .andRespond((response) -> {
                    throw new ResourceAccessException(EXCEPTION_MSG);
                });

        githubClient.getRepository(OWNER, REPO_NAME);

        mockServer.verify();
    }

    @Test(expected = GithubClientConnectionException.class)
    public void shouldThrowGithubClientConnectionExceptionWhenSocketTimeoutExceptionOccurred() {
        mockServer.expect(requestTo(GITHUB_URL))
                .andRespond((response) -> {
                    throw new SocketTimeoutException(EXCEPTION_MSG);
                });

        githubClient.getRepository(OWNER, REPO_NAME);

        mockServer.verify();
    }

}
