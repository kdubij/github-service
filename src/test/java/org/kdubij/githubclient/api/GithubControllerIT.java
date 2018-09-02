package org.kdubij.githubclient.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kdubij.githubclient.GithubAssertions;
import org.kdubij.githubclient.api.exception.ErrorCode;
import org.kdubij.githubclient.api.exception.ErrorResponseDto;
import org.kdubij.githubclient.model.GithubDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GithubControllerIT {
    private static final String FULL_NAME = "kdubij/HeroService";
    private static final LocalDate CREATED_AT = LocalDate.parse("2018-07-30");
    private static final String CLONE_URL = "https://github.com/kdubij/HeroService.git";
    private static final String REPOSITORY_NOT_FOUND_MSG = "Repository not found";

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void shouldReturnRealResponseDtoWithSuccess() {
        ResponseEntity<GithubDto> responseEntity = restTemplate.getForEntity("/repositories/kdubij/heroservice", GithubDto.class);

        GithubAssertions.assertResponse(responseEntity)
                .withCloneUrl(CLONE_URL)
                .withCreatedAt(CREATED_AT)
                .withDescription(null)
                .withFullName(FULL_NAME)
                .withStars(0);
    }

    @Test
    public void shouldReturnRealErrorResponseDtoWithStatusNotFound() {
        ResponseEntity<ErrorResponseDto> responseEntity = restTemplate.getForEntity("/repositories/aaaaaa123/324sdf", ErrorResponseDto.class);

        ErrorResponseAssertions.assertResponse(responseEntity)
                .withStatus(HttpStatus.NOT_FOUND)
                .withType(ErrorCode.REPOSITORY_NOT_FOUND)
                .withCode(ErrorCode.REPOSITORY_NOT_FOUND.ordinal())
                .withMessage(REPOSITORY_NOT_FOUND_MSG);
    }

}
