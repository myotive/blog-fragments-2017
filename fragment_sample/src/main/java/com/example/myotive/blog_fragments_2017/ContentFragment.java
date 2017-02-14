package com.example.myotive.blog_fragments_2017;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.common.BaseApplication;
import com.example.common.network.GitHubAPI;
import com.example.common.network.models.Content;
import com.example.common.network.models.Repository;
import com.example.common.ui.ProgressBarProvider;
import com.example.common.ui.adapters.ContentAdapter;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by myotive on 2/12/2017.
 */

public class ContentFragment extends Fragment {

    private static final String TAG = ContentFragment.class.getSimpleName();
    public static final String REPOSITORY_KEY = "REPOSITORY_KEY";

    @BindView(R.id.rv_content)
    RecyclerView contentRecyclerView;

    private ContentAdapter contentAdapter;
    private Repository repository;
    private GitHubAPI gitHubAPI;
    private ProgressBarProvider progressBarProvider;

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
        progressBarProvider = (ProgressBarProvider)getActivity();

        if(getArguments() != null){
            repository = (Repository) getArguments().getSerializable(REPOSITORY_KEY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);

        ButterKnife.bind(this, view);
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

        progressBarProvider.showProgressBar();
        gitHubAPI.GetRepoContents(repository.getOwner().getLogin(), repository.getName()).enqueue(new Callback<List<Content>>() {
            @Override
            public void onResponse(Call<List<Content>> call, Response<List<Content>> response) {
                contentAdapter.swapData(response.body());
                progressBarProvider.hideProgressBar();
            }

            @Override
            public void onFailure(Call<List<Content>> call, Throwable t) {
                Log.e(TAG, "Error calling GetRepoContents", t);
                progressBarProvider.hideProgressBar();
            }
        });
    }

    private void setActionBar() {
        if(((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(repository.getName());
        }
    }
}
