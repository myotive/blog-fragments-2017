package com.example.mosbysample.repository;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.example.common.BaseApplication;
import com.example.common.network.GitHubAPI;
import com.example.common.network.models.Repository;
import com.example.common.ui.ProgressBarProvider;
import com.example.common.ui.adapters.RepositoryAdapter;
import com.example.mosbysample.R;
import com.hannesdorfmann.mosby.mvp.layout.MvpFrameLayout;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by myotive on 2/14/2017.
 */

public class RepositoryView extends MvpFrameLayout<RepositoryContract.View, RepositoryContract.Presenter> implements RepositoryContract.View{

    @BindView(R.id.rv_repositories)
    RecyclerView repositoryRecyclerView;
    private RepositoryAdapter repositoryAdapter;

    public RepositoryView(Context context) {
        super(context);
        init();
    }

    public RepositoryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RepositoryView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @NonNull
    @Override
    public RepositoryContract.Presenter createPresenter() {

        GitHubAPI api = BaseApplication.getApplication(getContext()).getApplicationComponent().githubapi();
        ProgressBarProvider progressBarProvider = (ProgressBarProvider)getContext();

        return new RepositoryPresenter(api, progressBarProvider);
    }

    private void init() {
        inflate(getContext(), R.layout.view_repository, this);

        ButterKnife.bind(this);

        if(repositoryAdapter == null){
            repositoryAdapter = new RepositoryAdapter(getContext(), Collections.<Repository>emptyList(), null);
        }
        repositoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        repositoryRecyclerView.setAdapter(repositoryAdapter);
    }

    @Override
    public void updateRepositoryList(List<Repository> repositories) {
        repositoryAdapter.swapData(repositories);
    }

    @Override
    public void setOnRepositoryItemClick(RepositoryAdapter.RepositoryItemClick itemClickCallback) {
        repositoryAdapter.setRepositoryItemClick(itemClickCallback);
    }
}
