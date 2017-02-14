package com.example.conductorsample.controllers;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.common.network.GitHubAPI;
import com.example.common.network.models.Content;
import com.example.common.network.models.Repository;
import com.example.common.ui.adapters.ContentAdapter;
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

public class ContentController extends BaseController {

    private static final String TAG = ContentController.class.getSimpleName();

    @BindView(R.id.rv_content)
    RecyclerView contentRecyclerView;

    private ContentAdapter contentAdapter;
    private Repository repository;
    private GitHubAPI gitHubAPI;
    private Call<List<Content>> contentCall;

    // Required constructor to restore instance state
    public ContentController(Bundle args){
        super(args);
    }

    // Constructor Injection allowed with Conductor as long as you have
    // default constructor
    public ContentController(Repository repository, GitHubAPI gitHubAPI){
        this.repository = repository;
        this.gitHubAPI = gitHubAPI;
    }

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        View view = inflater.inflate(R.layout.controller_content, container, false);

        ButterKnife.bind(this, view);

        if(contentAdapter == null){
            contentAdapter = new ContentAdapter(getApplicationContext(), Collections.<Content>emptyList());
        }
        contentRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        contentRecyclerView.setAdapter(contentAdapter);

        return view;
    }

    @Override
    protected void onAttach(@NonNull View view) {
        super.onAttach(view);

        setActionBarTitle(repository.getName());
        setDisplayHomeAsUpEnabled(true);


        showProgressBar();
        contentCall = gitHubAPI.GetRepoContents(repository.getOwner().getLogin(), repository.getName());
        contentCall.enqueue(new Callback<List<Content>>() {
            @Override
            public void onResponse(Call<List<Content>> call, Response<List<Content>> response) {
                contentAdapter.swapData(response.body());
                hideProgressBar();
            }

            @Override
            public void onFailure(Call<List<Content>> call, Throwable t) {
                Log.e(TAG, "Error calling GetRepoContents", t);
                hideProgressBar();
            }
        });
    }

    @Override
    protected void onDetach(@NonNull View view) {
        super.onDetach(view);

        if(contentCall != null){
            contentCall.cancel();
        }
    }
}
