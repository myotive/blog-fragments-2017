package com.example.mosby_fragment_sample.repository;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.example.common.network.GitHubAPI;
import com.example.common.network.models.Repository;
import com.example.common.ui.ProgressBarProvider;
import com.example.common.ui.adapters.RepositoryAdapter;
import com.example.common.utilities.FragmentUtility;
import com.example.common.utilities.TransitionType;
import com.example.mosby_fragment_sample.BuildConfig;
import com.example.mosby_fragment_sample.R;
import com.example.mosby_fragment_sample.content.ContentFragment;
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
    private RepositoryAdapter.RepositoryItemClick itemClickCallback;

    private List<Repository> repositories;

    public RepositoryPresenter(GitHubAPI gitHubAPI){
        this.gitHubAPI = gitHubAPI;
    }


    @Override
    public void getRepositories(final boolean pullToRefresh) {

        getView().showLoading(pullToRefresh);

        // TODO: 2/19/2017  Need to handle cancelled network call in detach view
        gitHubAPI.GetRepos(BuildConfig.GITHUB_OWNER).enqueue(new Callback<List<Repository>>() {
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
