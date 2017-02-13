package com.example.myotive.blog_fragments_2017;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.common.BaseApplication;
import com.example.common.network.GitHubAPI;
import com.example.common.network.models.Repository;

/**
 * Created by myotive on 2/12/2017.
 */

public class ContentFragment extends Fragment {

    private static final String TAG = ContentFragment.class.getSimpleName();
    public static final String REPOSITORY_KEY = "REPOSITORY_KEY";

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
        View view = inflater.inflate(R.layout.fragment_content, container, false);

        return view;
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
}
