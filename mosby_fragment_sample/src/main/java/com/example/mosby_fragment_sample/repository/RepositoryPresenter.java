package com.example.mosby_fragment_sample.repository;

import android.util.Log;

import com.example.common.network.GitHubAPI;
import com.example.common.network.models.Repository;
import com.example.mosby_fragment_sample.BuildConfig;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by myotive on 2/14/2017.
 */

public class RepositoryPresenter extends MvpBasePresenter<RepositoryContract.View>
        implements RepositoryContract.Presenter{

    public static final String TAG = RepositoryPresenter.class.getSimpleName();

    // Dependencies
    private GitHubAPI gitHubAPI;

    private List<Repository> repositories;
    private Call<List<Repository>> reposCall;

    public RepositoryPresenter(GitHubAPI gitHubAPI){
        this.gitHubAPI = gitHubAPI;
    }


    @Override
    public void getRepositories(final boolean pullToRefresh) {

        getView().showLoading(pullToRefresh);

        reposCall = gitHubAPI.GetRepos(BuildConfig.GITHUB_OWNER);

        reposCall.enqueue(new Callback<List<Repository>>() {
            @Override
            public void onResponse(Call<List<Repository>> call, Response<List<Repository>> response) {
                if(response.isSuccessful() && isViewAttached()){
                    repositories = response.body();
                    getView().setData(repositories);
                    getView().showContent();
                }
                else{
                    // Call was not successful
                    Log.e(TAG, "Error calling GitHub API.");
                    getView().showError(null, pullToRefresh);
                }
            }

            @Override
            public void onFailure(Call<List<Repository>> call, Throwable t) {
                Log.e(TAG, "Error calling GetRepos", t);

                getView().showError(t, pullToRefresh);
            }
        });
    }
}
