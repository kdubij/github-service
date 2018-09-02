package org.kdubij.githubclient.client.exception.client;

public class GithubRepoNotFoundException extends GithubClientException {
    public GithubRepoNotFoundException() {
        super("Repository not found");
    }
}
