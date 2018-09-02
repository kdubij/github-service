package org.kdubij.githubclient.service.impl;

import org.kdubij.githubclient.client.GithubClient;
import org.kdubij.githubclient.model.GithubDto;
import org.kdubij.githubclient.service.GithubService;
import org.springframework.stereotype.Service;

@Service
public class GithubServiceImpl implements GithubService {
    private final GithubClient githubClient;

    public GithubServiceImpl(GithubClient githubClient) {
        this.githubClient = githubClient;
    }

    @Override
    public GithubDto getRepository(String owner, String repositoryName) {
        return githubClient.getRepository(owner, repositoryName);
    }


}
