package com.example.common.network;

import com.example.common.network.models.Content;
import com.example.common.network.models.Repository;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by myotive on 2/12/2017.
 */

public interface GitHubAPI {
    @GET("users/{user}/repos")
    Call<List<Repository>> GetRepos(@Path("user") String user);

    @GET("repos/{ownerName}/{repo}/contents")
    Call<List<Content>> GetRepoContents(@Path("ownerName") String ownerName, @Path("repo") String repo);
}
