package org.kdubij.githubclient;

import org.kdubij.githubclient.model.GithubDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class GithubAssertions {
    private GithubDto dto;

    private GithubAssertions(GithubDto dto) {
        this.dto = dto;
    }

    public static GithubAssertions assertDto(GithubDto dto) {
        assertThat(dto).isNotNull();
        return new GithubAssertions(dto);
    }

    public static GithubAssertions assertResponse(ResponseEntity<GithubDto> responseEntity) {
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        return new GithubAssertions(responseEntity.getBody());
    }

    public GithubAssertions withFullName(String fullName) {
        assertThat(dto.getFullName()).isEqualTo(fullName);
        return this;
    }

    public GithubAssertions withDescription(String description) {
        assertThat(dto.getDescription()).isEqualTo(description);
        return this;
    }

    public GithubAssertions withCloneUrl(String cloneUrl) {
        assertThat(dto.getCloneUrl()).isEqualTo(cloneUrl);
        return this;
    }

    public GithubAssertions withStars(int stars) {
        assertThat(dto.getStars()).isEqualTo(stars);
        return this;
    }

    public GithubAssertions withCreatedAt(LocalDate createdAt) {
        assertThat(dto.getCreatedAt()).isEqualTo(createdAt);
        return this;
    }
}
