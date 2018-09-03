package org.kdubij.githubclient.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class GithubDto {
    private String fullName;
    private String description;
    private String cloneUrl;
    private int stars;
    private LocalDate createdAt;
}
