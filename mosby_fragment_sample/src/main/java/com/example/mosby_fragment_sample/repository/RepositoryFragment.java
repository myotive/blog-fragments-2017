package com.example.mosby_fragment_sample.repository;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.common.BaseApplication;
import com.example.common.network.GitHubAPI;
import com.example.common.network.models.Repository;
import com.example.common.ui.adapters.RepositoryAdapter;
import com.example.common.utilities.FragmentUtility;
import com.example.common.utilities.TransitionType;
import com.example.mosby_fragment_sample.R;
import com.example.mosby_fragment_sample.content.ContentFragment;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.LceViewState;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.MvpLceViewStateFragment;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.data.RetainingLceViewState;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by myotive on 2/12/2017.
 */

public class RepositoryFragment extends MvpLceViewStateFragment<SwipeRefreshLayout, List<Repository>, RepositoryContract.View, RepositoryContract.Presenter>
    implements RepositoryContract.View,
        SwipeRefreshLayout.OnRefreshListener,
        RepositoryAdapter.RepositoryItemClick
{

    private static final String TAG = RepositoryFragment.class.getSimpleName();

    // UI
    @BindView(R.id.rv_repository)
    RecyclerView repositoryRecyclerView;
    private RepositoryAdapter repositoryAdapter;

    private GitHubAPI githubAPI;

    public static RepositoryFragment newInstance(){
        return new RepositoryFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        githubAPI = BaseApplication.getApplication(getActivity().getApplicationContext()).getApplicationComponent().githubapi();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_repository, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        contentView.setOnRefreshListener(this);

        if(repositoryAdapter == null){
            repositoryAdapter = new RepositoryAdapter(getContext(), Collections.<Repository>emptyList(), this);
        }
        repositoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        repositoryRecyclerView.setAdapter(repositoryAdapter);

        loadData(false);
    }

    @Override
    public void onResume() {
        super.onResume();

        setActionBar();
    }

    private void setActionBar() {
        if(((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(false);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
        }
    }


    @Override
    public RepositoryContract.Presenter createPresenter() {
        return new RepositoryPresenter(githubAPI);
    }

    @Override
    public LceViewState<List<Repository>, RepositoryContract.View> createViewState() {
        setRetainInstance(true);
        return new RetainingLceViewState<>();
    }

    @Override
    public List<Repository> getData() {
        return repositoryAdapter == null ? null : repositoryAdapter.getRepositories();
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        //// TODO: 2/19/2017
        return "Error loading repositories.";
    }

    @Override
    public void setData(List<Repository> data) {
        repositoryAdapter.swapData(data);
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        presenter.getRepositories(pullToRefresh);
    }

    @Override
    public void onRefresh() {
        loadData(true);
    }


    @Override
    public void showContent() {
        super.showContent();
        contentView.setRefreshing(false);
    }

    @Override
    public void showError(Throwable e, boolean pullToRefresh) {
        super.showError(e, pullToRefresh);
        contentView.setRefreshing(false);
    }

    @Override
    public void OnRepositoryItemClick(View view, Repository item) {
        FragmentUtility.goToFragment(getFragmentManager(),
                ContentFragment.newInstance(item),
                R.id.main_content,
                true,
                TransitionType.SlideHorizontal);
    }
}
