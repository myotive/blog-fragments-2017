package com.example.mosbysample.content;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.example.common.BaseApplication;
import com.example.common.network.GitHubAPI;
import com.example.common.network.models.Content;
import com.example.common.network.models.Repository;
import com.example.common.ui.ProgressBarProvider;
import com.example.common.ui.adapters.ContentAdapter;
import com.example.mosbysample.R;
import com.example.mosbysample.repository.RepositoryProvider;
import com.hannesdorfmann.mosby.mvp.layout.MvpFrameLayout;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by myotive on 2/14/2017.
 */

public class ContentView extends MvpFrameLayout<ContentContract.View, ContentContract.Presenter> implements ContentContract.View{

    @BindView(R.id.rv_content)
    RecyclerView contentRecyclerView;
    private ContentAdapter contentAdapter;

    public ContentView(Context context) {
        super(context);
        init();
    }

    public ContentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ContentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @NonNull
    @Override
    public ContentContract.Presenter createPresenter() {

        GitHubAPI api = BaseApplication.getApplication(getContext()).getApplicationComponent().githubapi();
        ProgressBarProvider progressBarProvider = (ProgressBarProvider)getContext();
        Repository repository = null;
        RepositoryProvider repositoryProvider = (RepositoryProvider)getContext();

        if(repositoryProvider != null){
            repository = repositoryProvider.getRepository();
        }

        return new ContentPresenter(api, progressBarProvider, repository);
    }

    private void init() {
        inflate(getContext(), R.layout.view_content, this);

        ButterKnife.bind(this);

        if(contentAdapter == null){
            contentAdapter = new ContentAdapter(getContext(), Collections.<Content>emptyList());
        }
        contentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        contentRecyclerView.setAdapter(contentAdapter);
    }

    @Override
    public void updateContentList(List<Content> contents) {
        contentAdapter.swapData(contents);
    }
}
