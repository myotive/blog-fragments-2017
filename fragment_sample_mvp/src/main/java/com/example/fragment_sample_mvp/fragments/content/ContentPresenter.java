package com.example.fragment_sample_mvp.fragments.content;

import android.util.Log;

import com.example.common.network.GitHubAPI;
import com.example.common.network.models.Content;
import com.example.common.network.models.Repository;
import com.example.common.ui.ProgressBarProvider;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by myotive on 2/26/2017.
 */

public class ContentPresenter implements ContentContract.Presenter<ContentContract.View> {

    private static final String TAG = ContentPresenter.class.getSimpleName();

    private ContentContract.View view;

    private GitHubAPI gitHubAPI;
    private ProgressBarProvider progressBarProvider;

    private Call<List<Content>> contentCall;

    @Inject
    public ContentPresenter(GitHubAPI gitHubAPI, ProgressBarProvider progressBarProvider){
        this.gitHubAPI = gitHubAPI;
        this.progressBarProvider = progressBarProvider;
    }

    @Override
    public void getContent() {

        Repository repository = view.getCurrentRepository();

        if(repository != null) {

            progressBarProvider.showProgressBar();

            contentCall = gitHubAPI.GetRepoContents(repository.getOwner().getLogin(), repository.getName());

            contentCall.enqueue(new Callback<List<Content>>() {
                @Override
                public void onResponse(Call<List<Content>> call, Response<List<Content>> response) {
                    if(response.isSuccessful()) {
                        view.updateContentList(response.body());
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

    @Override
    public void start() {
        getContent();
    }

    @Override
    public void stop() {
        if(contentCall != null){
            contentCall.cancel();
        }
    }

    @Override
    public void setView(ContentContract.View view) {
        this.view = view;
    }
}
