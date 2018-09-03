package org.kdubij.githubclient.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kdubij.githubclient.client.GithubClient;
import org.kdubij.githubclient.client.model.GithubResponseDto;
import org.kdubij.githubclient.model.GithubDto;
import org.kdubij.githubclient.service.GithubService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class GithubServiceImpl implements GithubService {
    private static final Logger logger = LogManager.getLogger(GithubServiceImpl.class);

    private final GithubClient githubClient;
    private final ModelMapper modelMapper;

    public GithubServiceImpl(GithubClient githubClient, ModelMapper modelMapper) {
        this.githubClient = githubClient;
        this.modelMapper = modelMapper;
    }

    @Override
    public GithubDto getRepository(String owner, String repositoryName) {
        logger.info("Get repository: {} with owner: {}", owner, repositoryName);
        GithubResponseDto response = githubClient.getRepository(owner, repositoryName);
        return modelMapper.map(response, GithubDto.class);
    }


}
