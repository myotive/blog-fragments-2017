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

import com.example.common.BaseApplication;
import com.example.common.network.GitHubAPI;
import com.example.common.network.models.Repository;
import com.example.common.ui.ProgressBarProvider;
import com.example.common.ui.adapters.RepositoryAdapter;
import com.example.common.utilities.FragmentUtility;
import com.example.common.utilities.TransitionType;

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

public class RepositoryFragment extends Fragment implements RepositoryAdapter.RepositoryItemClick {

    private static final String TAG = RepositoryFragment.class.getSimpleName();

    @BindView(R.id.rv_repository)
    RecyclerView repositoryRecyclerView;

    private ProgressBarProvider progressBarProvider;
    private GitHubAPI githubAPI;
    private RepositoryAdapter repositoryAdapter;

    public static RepositoryFragment newInstance(){
        return new RepositoryFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        githubAPI = BaseApplication.getApplication(getActivity().getApplicationContext()).getApplicationComponent().githubapi();
        progressBarProvider = (ProgressBarProvider)getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_repository, container, false);

        ButterKnife.bind(this, view);
        if(repositoryAdapter == null){
            repositoryAdapter = new RepositoryAdapter(getContext(), Collections.<Repository>emptyList(), this);
        }
        repositoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        repositoryRecyclerView.setAdapter(repositoryAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        setActionBar();

        progressBarProvider.showProgressBar();
        githubAPI.GetRepos(BuildConfig.GITHUB_OWNER).enqueue(new Callback<List<Repository>>() {
            @Override
            public void onResponse(Call<List<Repository>> call, Response<List<Repository>> response) {
                repositoryAdapter.swapData(response.body());
                progressBarProvider.hideProgressBar();
            }

            @Override
            public void onFailure(Call<List<Repository>> call, Throwable t) {
                Log.e(TAG, "Error getting repos", t);
                progressBarProvider.hideProgressBar();
            }
        });
    }

    private void setActionBar() {
        if(((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(false);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
        }
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
