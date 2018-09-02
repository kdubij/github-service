package org.kdubij.githubclient.client.exception.server;

public class GithubServiceUnavailableException extends GithubServerException {
    public GithubServiceUnavailableException() {
        super("Github server is unavailable");
    }
}
