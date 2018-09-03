package org.kdubij.githubclient.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kdubij.githubclient.client.exception.client.GithubClientConnectionException;
import org.kdubij.githubclient.client.exception.client.GithubClientException;
import org.kdubij.githubclient.client.exception.client.GithubRepoNotFoundException;
import org.kdubij.githubclient.client.exception.server.GithubServerException;
import org.kdubij.githubclient.client.exception.server.GithubServiceUnavailableException;
import org.kdubij.githubclient.client.model.GithubResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Github repository client
 */
@Service
public class GithubClient {
    private static final Logger logger = LogManager.getLogger(GithubClient.class);
    private final RestTemplate restTemplate;

    @Value("${github.service.url}")
    private String githubUrl;

    public GithubClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * @param owner          owner of repository
     * @param repositoryName name of repository
     * @return found github repository {@link GithubResponseDto}
     * @throws GithubClientConnectionException   if cannot connect with resource
     * @throws GithubRepoNotFoundException       if repository not found
     * @throws GithubClientException             if others clients exceptions occurs
     * @throws GithubServiceUnavailableException if service is unavailable
     * @throws GithubServerException             if other server errors occurs
     */
    public GithubResponseDto getRepository(String owner, String repositoryName) {
        GithubResponseDto result = null;
        String requestUrl = createRequestUrl(owner, repositoryName);
        logger.info("Sending request to github api for repository: {}, owned by: {} , url: {}", repositoryName, owner, requestUrl);
        try {
            result = restTemplate.getForEntity(requestUrl, GithubResponseDto.class).getBody();
        } catch (ResourceAccessException e) {
            handleResourceAccessException(e);
        } catch (HttpClientErrorException e) {
            handleClientException(e);
        } catch (HttpServerErrorException e) {
            handleServerException(e);
        }
        return result;
    }

    private void handleResourceAccessException(ResourceAccessException e) {
        logger.error("Resource access exception occurred: {}", e.getCause());
        throw new GithubClientConnectionException(e.getLocalizedMessage());
    }

    private void handleClientException(HttpClientErrorException e) {
        logger.error("Client error occurred with status: {}", e.getLocalizedMessage());
        if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new GithubRepoNotFoundException();
        } else {
            throw new GithubClientException(e.getLocalizedMessage());
        }
    }

    private void handleServerException(HttpServerErrorException e) {
        logger.error("Server error occurred with status: {}", e.getLocalizedMessage());
        if (e.getStatusCode() == HttpStatus.SERVICE_UNAVAILABLE) {
            throw new GithubServiceUnavailableException();
        } else {
            throw new GithubServerException(e.getLocalizedMessage());
        }
    }

    private String createRequestUrl(String owner, String repositoryName) {
        return UriComponentsBuilder.fromUriString(githubUrl).build()
                .expand(owner, repositoryName)
                .encode()
                .toString();
    }
}
