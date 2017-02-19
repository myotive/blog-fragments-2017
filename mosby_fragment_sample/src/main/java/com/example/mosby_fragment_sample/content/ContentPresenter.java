package com.example.mosby_fragment_sample.content;

import android.util.Log;

import com.example.common.network.GitHubAPI;
import com.example.common.network.models.Content;
import com.example.common.network.models.Repository;
import com.example.common.ui.ProgressBarProvider;
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
    private Repository repository;

    ContentPresenter(GitHubAPI gitHubAPI, Repository repository){
        this.gitHubAPI = gitHubAPI;
        this.repository = repository;
    }

    @Override
    public void getContent(Repository repository, final boolean swipeToRefresh) {
        getView().showLoading(swipeToRefresh);

        gitHubAPI.GetRepoContents(repository.getOwner().getLogin(), repository.getName()).enqueue(new Callback<List<Content>>() {
            @Override
            public void onResponse(Call<List<Content>> call, Response<List<Content>> response) {
                if(response.isSuccessful() && isViewAttached()){
                    getView().setData(response.body());
                    getView().showContent();
                }
                else{
                    // Call was not successful
                    Log.e(TAG, "Error calling GitHub API.");
                    getView().showError(null, swipeToRefresh);
                }
            }

            @Override
            public void onFailure(Call<List<Content>> call, Throwable t) {
                Log.e(TAG, "Error calling GetRepoContents", t);
                getView().showError(t, swipeToRefresh);
            }
        });
    }
}
