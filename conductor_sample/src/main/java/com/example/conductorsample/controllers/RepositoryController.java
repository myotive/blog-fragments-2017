package com.example.conductorsample.controllers;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.HorizontalChangeHandler;
import com.example.common.network.GitHubAPI;
import com.example.common.network.models.Repository;
import com.example.common.ui.ProgressBarProvider;
import com.example.common.ui.adapters.RepositoryAdapter;
import com.example.conductorsample.BuildConfig;
import com.example.conductorsample.R;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by myotive on 2/13/2017.
 */

public class RepositoryController extends BaseController implements RepositoryAdapter.RepositoryItemClick  {

    public static final String TAG = RepositoryController.class.getSimpleName();

    @BindView(R.id.rv_repository)
    RecyclerView repositoryRecyclerView;

    private GitHubAPI gitHubAPI;
    private RepositoryAdapter repositoryAdapter;
    private Call<List<Repository>> repositoryCall;

    // Required constructor to restore instance state
    public RepositoryController(Bundle args){
        super(args);
    }

    // Constructor Injection allowed with Conductor as long as you have
    // default constructor
    public RepositoryController(GitHubAPI gitHubAPI){
        this.gitHubAPI = gitHubAPI;
    }

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        View view = inflater.inflate(R.layout.controller_repository, container, false);

        ButterKnife.bind(this, view);

        if(repositoryAdapter == null){
            repositoryAdapter = new RepositoryAdapter(getApplicationContext(), Collections.<Repository>emptyList(), this);
        }
        repositoryRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        repositoryRecyclerView.setAdapter(repositoryAdapter);

        return view;
    }

    @Override
    protected void onAttach(@NonNull View view) {
        super.onAttach(view);

        setActionBarTitle(getResources().getString(R.string.app_name));
        setDisplayHomeAsUpEnabled(false);


        if(repositoryAdapter.getItemCount() == 0) {

            showProgressBar();
            repositoryCall = gitHubAPI.GetRepos(BuildConfig.GITHUB_OWNER);

            repositoryCall.enqueue(new Callback<List<Repository>>() {
                @Override
                public void onResponse(Call<List<Repository>> call, Response<List<Repository>> response) {
                    repositoryAdapter.swapData(response.body());
                    hideProgressBar();
                }

                @Override
                public void onFailure(Call<List<Repository>> call, Throwable t) {
                    Log.e(TAG, "Error getting repos", t);
                    hideProgressBar();
                }
            });
        }
    }

    @Override
    protected void onDetach(@NonNull View view) {
        super.onDetach(view);

        if(repositoryCall != null){
            repositoryCall.cancel();
        }
    }

    @Override
    public void OnRepositoryItemClick(View view, Repository item) {
        ContentController contentController = new ContentController(item, gitHubAPI);

        RouterTransaction transaction
                = RouterTransaction.with(contentController)
                .pushChangeHandler(new HorizontalChangeHandler(100))
                .popChangeHandler(new HorizontalChangeHandler());
        getRouter().pushController(transaction);
    }
}
