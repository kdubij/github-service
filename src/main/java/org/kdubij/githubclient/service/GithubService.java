package org.kdubij.githubclient.service;

import org.kdubij.githubclient.client.model.GithubResponseDto;
import org.kdubij.githubclient.model.GithubDto;

public interface GithubService {
    /**
     * @param owner          of repository
     * @param repositoryName - name of repository
     * @return {@link GithubResponseDto}
     */
    GithubDto getRepository(String owner, String repositoryName);
}
