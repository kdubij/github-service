package org.kdubij.githubclient.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kdubij.githubclient.api.GithubAssertions;
import org.kdubij.githubclient.client.GithubClient;
import org.kdubij.githubclient.client.model.GithubResponseDto;
import org.kdubij.githubclient.model.GithubDto;
import org.kdubij.githubclient.service.GithubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GithubServiceImplTest {
    private static final String OWNER = "owner";
    private static final String REPO_NAME = "repoName";
    private static final int STARS = 999;
    private static final LocalDate CREATED_AT = LocalDate.parse("2018-07-30");
    private static final String DESCRIPTION = "Description";
    private static final String FULL_NAME = "fullName";
    private static final String CLONE_URL = "http://github.com";

    @MockBean
    private GithubClient githubClient;
    @Autowired
    private GithubService githubService;

    @Test
    public void shouldReturnRepositoryFromClientResponse() {
        when(githubClient.getRepository(OWNER, REPO_NAME)).thenReturn(createResponseDto());

        GithubDto githubDto = githubService.getRepository(OWNER, REPO_NAME);

        GithubAssertions.assertDto(githubDto)
                .withStars(STARS)
                .withCloneUrl(CLONE_URL)
                .withCreatedAt(CREATED_AT)
                .withDescription(DESCRIPTION)
                .withFullName(FULL_NAME);
    }

    private GithubResponseDto createResponseDto() {
        GithubResponseDto dto = new GithubResponseDto();
        dto.setStars(STARS);
        dto.setCreatedAt(CREATED_AT);
        dto.setDescription(DESCRIPTION);
        dto.setFullName(FULL_NAME);
        dto.setCloneUrl(CLONE_URL);
        return dto;
    }
}
