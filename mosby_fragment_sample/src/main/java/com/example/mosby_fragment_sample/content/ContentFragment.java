package com.example.mosby_fragment_sample.content;

import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.example.common.network.models.Content;
import com.example.common.network.models.Repository;
import com.example.common.ui.adapters.ContentAdapter;
import com.example.mosby_fragment_sample.R;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.LceViewState;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.MvpLceViewStateFragment;
import com.hannesdorfmann.mosby.mvp.viewstate.lce.data.RetainingLceViewState;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by myotive on 2/19/2017.
 */

public class ContentFragment extends MvpLceViewStateFragment<SwipeRefreshLayout, List<Content>, ContentContract.View, ContentContract.Presenter>
    implements SwipeRefreshLayout.OnRefreshListener,
        ContentContract.View{

    private static final String TAG = ContentFragment.class.getSimpleName();
    public static final String REPOSITORY_KEY = "REPOSITORY_KEY";

    @BindView(R.id.rv_content)
    RecyclerView contentRecyclerView;

    private ContentAdapter contentAdapter;
    private Repository repository;
    private GitHubAPI gitHubAPI;

    public static ContentFragment newInstance(Repository repository){
        ContentFragment fragment = new ContentFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(REPOSITORY_KEY, repository);

        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gitHubAPI = BaseApplication.getApplication(getActivity().getApplicationContext()).getApplicationComponent().githubapi();

        if(getArguments() != null){
            repository = (Repository) getArguments().getSerializable(REPOSITORY_KEY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_content, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        contentView.setOnRefreshListener(this);

        if(contentAdapter == null){
            contentAdapter = new ContentAdapter(getContext(), Collections.<Content>emptyList());
        }
        contentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        contentRecyclerView.setAdapter(contentAdapter);

        loadData(false);
    }


    @Override
    public void onResume() {
        super.onResume();
        setActionBar();
    }

    private void setActionBar() {
        if(((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(repository.getName());
        }
    }

    @Override
    public void onRefresh() {
        loadData(true);
    }

    @Override
    public ContentContract.Presenter createPresenter() {
        return new ContentPresenter(gitHubAPI, repository);
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        //// TODO: 2/19/2017  
        return "Error loading content.";
    }

    @Override
    public LceViewState<List<Content>, ContentContract.View> createViewState() {
        setRetainInstance(true);
        return new RetainingLceViewState<>();
    }

    @Override
    public List<Content> getData() {
        return contentAdapter != null ? contentAdapter.getContents() : null;
    }

    @Override
    public void setData(List<Content> data) {
        contentAdapter.swapData(data);
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        presenter.getContent(repository, pullToRefresh);
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
}
