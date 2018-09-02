package org.kdubij.githubclient.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kdubij.githubclient.GithubAssertions;
import org.kdubij.githubclient.api.exception.ErrorCode;
import org.kdubij.githubclient.api.exception.ErrorResponseDto;
import org.kdubij.githubclient.client.exception.client.GithubClientConnectionException;
import org.kdubij.githubclient.client.exception.client.GithubClientException;
import org.kdubij.githubclient.client.exception.client.GithubRepoNotFoundException;
import org.kdubij.githubclient.client.exception.server.GithubServerException;
import org.kdubij.githubclient.client.exception.server.GithubServiceUnavailableException;
import org.kdubij.githubclient.model.GithubDto;
import org.kdubij.githubclient.service.GithubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GithubControllerTest {
    private static final int STARS = 999;
    private static final LocalDate CREATED_AT = LocalDate.parse("2018-07-30");
    private static final String DESCRIPTION = "Description";
    private static final String FULL_NAME = "fullName";
    private static final String CLONE_URL = "http://github.com";
    private static final String SERVICE_URL = "/repositories/owner/repoName";
    private static final String OWNER = "owner";
    private static final String REPO_NAME = "repoName";
    private static final String CLIENT_EXCEPTION_MSG = "Client exception";
    private static final String REPOSITORY_NOT_FOUND_MSG = "Repository not found";
    private static final String CLIENT_CONNECTION_EXCEPTION_MSG = "Client connection exception";
    private static final String GITHUB_SERVER_IS_UNAVAILABLE_MSG = "Github server is unavailable";
    private static final String GITHUB_SERVER_EXCEPTION_MSG = "Github server exception";
    private static final String UNEXPECTED_MSG = "Unexpected";

    @MockBean
    private GithubService githubService;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void shouldReturnGithubDtoWithStatusSuccess() {
        GithubDto dto = createDto();
        when(githubService.getRepository(OWNER, REPO_NAME)).thenReturn(dto);
        ResponseEntity<GithubDto> responseEntity = restTemplate.getForEntity(SERVICE_URL, GithubDto.class);

        GithubAssertions.assertResponse(responseEntity)
                .withStars(STARS)
                .withFullName(FULL_NAME)
                .withDescription(DESCRIPTION)
                .withCreatedAt(CREATED_AT)
                .withCloneUrl(CLONE_URL);
    }

    @Test
    public void shouldReturnClientErrorWhenGithubClientExceptionOccurred() {
        when(githubService.getRepository(OWNER, REPO_NAME)).thenThrow(new GithubClientException(CLIENT_EXCEPTION_MSG));
        ResponseEntity<ErrorResponseDto> responseEntity = restTemplate.getForEntity(SERVICE_URL, ErrorResponseDto.class);

        ErrorResponseAssertions.assertResponse(responseEntity)
                .withStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .withType(ErrorCode.CLIENT_ERROR)
                .withCode(ErrorCode.CLIENT_ERROR.ordinal())
                .withMessage(CLIENT_EXCEPTION_MSG);
    }

    @Test
    public void shouldReturnNotFoundWhenGithubRepoNotFoundExceptionOccurred() {
        when(githubService.getRepository(OWNER, REPO_NAME)).thenThrow(new GithubRepoNotFoundException());
        ResponseEntity<ErrorResponseDto> responseEntity = restTemplate.getForEntity(SERVICE_URL, ErrorResponseDto.class);

        ErrorResponseAssertions.assertResponse(responseEntity)
                .withStatus(HttpStatus.NOT_FOUND)
                .withType(ErrorCode.REPOSITORY_NOT_FOUND)
                .withCode(ErrorCode.REPOSITORY_NOT_FOUND.ordinal())
                .withMessage(REPOSITORY_NOT_FOUND_MSG);
    }

    @Test
    public void shouldReturnErrorResponseWhenGithubClientConnectionExceptionOccurred() {
        when(githubService.getRepository(OWNER, REPO_NAME)).thenThrow(new GithubClientConnectionException(CLIENT_CONNECTION_EXCEPTION_MSG));
        ResponseEntity<ErrorResponseDto> responseEntity = restTemplate.getForEntity(SERVICE_URL, ErrorResponseDto.class);

        ErrorResponseAssertions.assertResponse(responseEntity)
                .withStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .withType(ErrorCode.CLIENT_CONNECTION_ERROR)
                .withCode(ErrorCode.CLIENT_CONNECTION_ERROR.ordinal())
                .withMessage(CLIENT_CONNECTION_EXCEPTION_MSG);
    }

    @Test
    public void shouldReturnErrorResponseWheGithubServiceUnavailableExceptionOccurred() {
        when(githubService.getRepository(OWNER, REPO_NAME)).thenThrow(new GithubServiceUnavailableException());
        ResponseEntity<ErrorResponseDto> responseEntity = restTemplate.getForEntity(SERVICE_URL, ErrorResponseDto.class);

        ErrorResponseAssertions.assertResponse(responseEntity)
                .withStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .withType(ErrorCode.GITHUB_SERVICE_UNAVAILABLE)
                .withCode(ErrorCode.GITHUB_SERVICE_UNAVAILABLE.ordinal())
                .withMessage(GITHUB_SERVER_IS_UNAVAILABLE_MSG);
    }

    @Test
    public void shouldReturnErrorResponseWhenGithubServerExceptionOccurred() {
        when(githubService.getRepository(OWNER, REPO_NAME)).thenThrow(new GithubServerException(GITHUB_SERVER_EXCEPTION_MSG));
        ResponseEntity<ErrorResponseDto> responseEntity = restTemplate.getForEntity(SERVICE_URL, ErrorResponseDto.class);

        ErrorResponseAssertions.assertResponse(responseEntity)
                .withStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .withType(ErrorCode.GITHUB_SERVER_ERROR)
                .withCode(ErrorCode.GITHUB_SERVER_ERROR.ordinal())
                .withMessage(GITHUB_SERVER_EXCEPTION_MSG);
    }

    @Test
    public void shouldReturnErrorResponseWhenUnexpectedExceptionOccurred() {
        when(githubService.getRepository(OWNER, REPO_NAME)).thenThrow(new RuntimeException(UNEXPECTED_MSG));
        ResponseEntity<ErrorResponseDto> responseEntity = restTemplate.getForEntity(SERVICE_URL, ErrorResponseDto.class);

        ErrorResponseAssertions.assertResponse(responseEntity)
                .withStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .withType(ErrorCode.UNEXPECTED_ERROR)
                .withCode(ErrorCode.UNEXPECTED_ERROR.ordinal())
                .withMessage(UNEXPECTED_MSG);
    }

    private GithubDto createDto() {
        GithubDto dto = new GithubDto();
        dto.setStars(STARS);
        dto.setCreatedAt(CREATED_AT);
        dto.setDescription(DESCRIPTION);
        dto.setFullName(FULL_NAME);
        dto.setCloneUrl(CLONE_URL);
        return dto;
    }

}
