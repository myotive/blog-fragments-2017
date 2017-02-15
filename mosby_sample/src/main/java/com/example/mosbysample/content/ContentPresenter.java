package com.example.mosbysample.content;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.example.common.network.GitHubAPI;
import com.example.common.network.models.Content;
import com.example.common.network.models.Repository;
import com.example.common.ui.ProgressBarProvider;
import com.example.common.ui.adapters.RepositoryAdapter;
import com.example.mosbysample.BuildConfig;
import com.example.mosbysample.ContentActivity;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by myotive on 2/14/2017.
 */

public class ContentPresenter extends MvpBasePresenter<ContentContract.View>
        implements ContentContract.Presenter{

    public static final String TAG = ContentPresenter.class.getSimpleName();

    private GitHubAPI gitHubAPI;
    private ProgressBarProvider progressBarProvider;
    private Repository repository;

    ContentPresenter(GitHubAPI gitHubAPI, ProgressBarProvider progressBarProvider, Repository repository){
        this.gitHubAPI = gitHubAPI;
        this.progressBarProvider = progressBarProvider;
        this.repository = repository;
    }

    @Override
    public void attachView(ContentContract.View view) {
        super.attachView(view);

        if(repository != null) {
            getContent(repository);
        }
    }

    @Override
    public void getContent(Repository repository) {
        this.progressBarProvider.showProgressBar();

        gitHubAPI.GetRepoContents(repository.getOwner().getLogin(), repository.getName()).enqueue(new Callback<List<Content>>() {
            @Override
            public void onResponse(Call<List<Content>> call, Response<List<Content>> response) {
                if(response.isSuccessful() && isViewAttached()){
                    getView().updateContentList(response.body());
                }

                progressBarProvider.hideProgressBar();
            }

            @Override
            public void onFailure(Call<List<Content>> call, Throwable t) {
                Log.e(TAG, "Error calling GetRepoContents", t);

                progressBarProvider.hideProgressBar();
            }
        });
    }
}
