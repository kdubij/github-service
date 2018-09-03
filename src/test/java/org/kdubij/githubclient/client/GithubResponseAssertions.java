package org.kdubij.githubclient.client;

import org.kdubij.githubclient.client.model.GithubResponseDto;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class GithubResponseAssertions {
    private GithubResponseDto dto;

    private GithubResponseAssertions(GithubResponseDto dto) {
        this.dto = dto;
    }

    public static GithubResponseAssertions assertDto(GithubResponseDto dto) {
        assertThat(dto).isNotNull();
        return new GithubResponseAssertions(dto);
    }

    public GithubResponseAssertions withFullName(String fullName) {
        assertThat(dto.getFullName()).isEqualTo(fullName);
        return this;
    }

    public GithubResponseAssertions withDescription(String description) {
        assertThat(dto.getDescription()).isEqualTo(description);
        return this;
    }

    public GithubResponseAssertions withCloneUrl(String cloneUrl) {
        assertThat(dto.getCloneUrl()).isEqualTo(cloneUrl);
        return this;
    }

    public GithubResponseAssertions withStars(int stars) {
        assertThat(dto.getStars()).isEqualTo(stars);
        return this;
    }

    public GithubResponseAssertions withCreatedAt(LocalDate createdAt) {
        assertThat(dto.getCreatedAt()).isEqualTo(createdAt);
        return this;
    }
}
