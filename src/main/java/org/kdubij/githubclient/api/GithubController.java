package org.kdubij.githubclient.api;

import org.kdubij.githubclient.model.GithubDto;
import org.kdubij.githubclient.service.GithubService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Github service controller
 */
@RestController
public class GithubController {
    private final GithubService githubService;

    public GithubController(GithubService githubService) {
        this.githubService = githubService;
    }

    @GetMapping("/repositories/{owner}/{repository-name}")
    public GithubDto getRepository(@PathVariable("owner") String owner, @PathVariable("repository-name") String repoName) {
        return githubService.getRepository(owner, repoName);

    }
}
