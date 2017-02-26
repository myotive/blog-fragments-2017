package com.example.fragment_sample_mvp.fragments.repository;

import android.util.Log;

import com.example.common.network.GitHubAPI;
import com.example.common.network.models.Repository;
import com.example.common.ui.ProgressBarProvider;
import com.example.fragment_sample_mvp.BuildConfig;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by myotive on 2/26/2017.
 */

public class RepositoryPresenter implements
        RepositoryContract.Presenter<RepositoryContract.View> {

    private static final String TAG = RepositoryPresenter.class.getSimpleName();

    private ProgressBarProvider progressBarProvider;
    private GitHubAPI gitHubAPI;
    private RepositoryContract.View view;

    private Call<List<Repository>> listCall;

    @Inject
    public RepositoryPresenter(GitHubAPI gitHubAPI,
                               ProgressBarProvider progressBarProvider){
        this.gitHubAPI = gitHubAPI;
        this.progressBarProvider = progressBarProvider;
    }

    @Override
    public void getRepositories() {
        this.progressBarProvider.showProgressBar();

        listCall = gitHubAPI.GetRepos(BuildConfig.GITHUB_OWNER);

        listCall.enqueue(new Callback<List<Repository>>() {
            @Override
            public void onResponse(Call<List<Repository>> call, Response<List<Repository>> response) {
                if(response.isSuccessful()){
                    view.updateRepositoryList(response.body());
                }

                progressBarProvider.hideProgressBar();
            }

            @Override
            public void onFailure(Call<List<Repository>> call, Throwable t) {
                Log.e(TAG, "Error calling GetRepos", t);

                progressBarProvider.hideProgressBar();
            }
        });
    }

    @Override
    public void start() {
        getRepositories();
    }

    @Override
    public void stop() {
        if(listCall != null){
            listCall.cancel();
        }
    }

    @Override
    public void setView(RepositoryContract.View view) {
        this.view = view;
    }
}
