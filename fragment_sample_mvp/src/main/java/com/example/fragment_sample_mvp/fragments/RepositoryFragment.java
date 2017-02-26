package com.example.fragment_sample_mvp.fragments;

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

import com.example.common.BaseApplication;
import com.example.common.network.GitHubAPI;
import com.example.common.network.models.Repository;
import com.example.common.ui.ProgressBarProvider;
import com.example.common.ui.adapters.RepositoryAdapter;
import com.example.common.utilities.FragmentUtility;
import com.example.common.utilities.TransitionType;
import com.example.fragment_sample_mvp.BuildConfig;
import com.example.fragment_sample_mvp.MainActivity;
import com.example.fragment_sample_mvp.R;
import com.example.fragment_sample_mvp.di.ActivityComponent;
import com.example.fragment_sample_mvp.fragments.repository.RepositoryContract;
import com.example.fragment_sample_mvp.fragments.repository.RepositoryPresenter;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by myotive on 2/12/2017.
 */

public class RepositoryFragment extends Fragment
        implements RepositoryContract.View

{
    private static final String TAG = RepositoryFragment.class.getSimpleName();

    private ActivityComponent activityComponent;

    @Inject
    RepositoryPresenter presenter;

    @BindView(R.id.rv_repository)
    RecyclerView repositoryRecyclerView;
    RepositoryAdapter repositoryAdapter;

    private RepositoryAdapter.RepositoryItemClick repositoryItemClickCallback = new RepositoryAdapter.RepositoryItemClick() {
        @Override
        public void OnRepositoryItemClick(View view, Repository item) {
            onRepositoryItemClick(item);
        }
    };

    public static RepositoryFragment newInstance(){
        return new RepositoryFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityComponent = ((MainActivity)getActivity()).getActivityComponent();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_repository, container, false);

        ButterKnife.bind(this, view);

        activityComponent.inject(this);
        presenter.setView(this);

        if(repositoryAdapter == null){
            repositoryAdapter = new RepositoryAdapter(getContext(), Collections.<Repository>emptyList(), repositoryItemClickCallback);
        }
        repositoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        repositoryRecyclerView.setAdapter(repositoryAdapter);

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
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(false);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
        }
    }

    @Override
    public void updateRepositoryList(List<Repository> repositories) {
        repositoryAdapter.swapData(repositories);
    }

    @Override
    public void onRepositoryItemClick(Repository item) {
        FragmentUtility.goToFragment(getFragmentManager(),
                ContentFragment.newInstance(item),
                R.id.main_content,
                true,
                TransitionType.SlideHorizontal);
    }
}
