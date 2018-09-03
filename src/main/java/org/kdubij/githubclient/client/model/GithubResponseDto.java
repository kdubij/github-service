package org.kdubij.githubclient.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

/**
 * Response from Github API
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GithubResponseDto {
    @JsonProperty("full_name")
    private String fullName;

    private String description;

    @JsonProperty("clone_url")
    private String cloneUrl;

    @JsonProperty("stargazers_count")
    private int stars;

    @JsonProperty("created_at")
    private LocalDate createdAt;
}
