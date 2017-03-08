package com.example.fragment_sample_mvp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.common.network.models.Content;
import com.example.common.network.models.Repository;
import com.example.common.ui.adapters.ContentAdapter;
import com.example.fragment_sample_mvp.MainActivity;
import com.example.fragment_sample_mvp.R;
import com.example.fragment_sample_mvp.di.ActivityComponent;
import com.example.fragment_sample_mvp.fragments.content.ContentContract;
import com.example.fragment_sample_mvp.fragments.content.ContentPresenter;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by myotive on 2/12/2017.
 */

public class ContentFragment extends Fragment implements ContentContract.View {

    private static final String TAG = ContentFragment.class.getSimpleName();
    public static final String REPOSITORY_KEY = "REPOSITORY_KEY";

    private ActivityComponent activityComponent;

    @Inject
    ContentContract.Presenter presenter;

    @BindView(R.id.rv_content)
    RecyclerView contentRecyclerView;

    private ContentAdapter contentAdapter;
    private Repository repository;

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

        activityComponent = ((MainActivity)getActivity()).getActivityComponent();

        if(getArguments() != null){
            repository = (Repository) getArguments().getSerializable(REPOSITORY_KEY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);

        ButterKnife.bind(this, view);

        activityComponent.inject(this);
        presenter.setView(this);

        if(contentAdapter == null){
            contentAdapter = new ContentAdapter(getContext(), Collections.<Content>emptyList());
        }
        contentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        contentRecyclerView.setAdapter(contentAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setActionBar();

        presenter.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.stop();
    }

    private void setActionBar() {
        if(((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(repository.getName());
        }
    }

    @Override
    public void updateContentList(List<Content> contents) {
        contentAdapter.swapData(contents);
    }

    @Override
    public Repository getCurrentRepository() {
        return repository;
    }
}
