package com.example.simplestacksample.paths.repository;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.example.common.network.GitHubAPI;
import com.example.common.network.models.Repository;
import com.example.common.ui.adapters.RepositoryAdapter;
import com.example.simplestacksample.BuildConfig;
import com.example.simplestacksample.MainActivity;
import com.example.simplestacksample.R;
import com.example.simplestacksample.paths.content.ContentKey;
import com.zhuinden.simplestack.navigator.Navigator;

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

public class RepositoryView
        extends FrameLayout implements RepositoryAdapter.RepositoryItemClick  {

    public static final String TAG = RepositoryView.class.getSimpleName();

    @BindView(R.id.rv_repository)
    RecyclerView repositoryRecyclerView;

    private GitHubAPI gitHubAPI;
    private RepositoryAdapter repositoryAdapter;
    private Call<List<Repository>> repositoryCall;

    public RepositoryView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public RepositoryView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RepositoryView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(21)
    public RepositoryView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        if(!isInEditMode()) {
            gitHubAPI = MainActivity.getApi(context);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        if(repositoryAdapter == null){
            repositoryAdapter = new RepositoryAdapter(getContext(), Collections.<Repository>emptyList(), this);
        }
        repositoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        repositoryRecyclerView.setAdapter(repositoryAdapter);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if(repositoryAdapter.getItemCount() == 0) {

            MainActivity.get(getContext()).showProgressBar();
            repositoryCall = gitHubAPI.GetRepos(BuildConfig.GITHUB_OWNER);

            repositoryCall.enqueue(new Callback<List<Repository>>() {
                @Override
                public void onResponse(Call<List<Repository>> call, Response<List<Repository>> response) {
                    repositoryAdapter.swapData(response.body());
                    MainActivity.get(getContext()).hideProgressBar();
                }

                @Override
                public void onFailure(Call<List<Repository>> call, Throwable t) {
                    Log.e(TAG, "Error getting repos", t);
                    MainActivity.get(getContext()).hideProgressBar();
                }
            });
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if(repositoryCall != null){
            repositoryCall.cancel();
        }
    }

    @Override
    public void OnRepositoryItemClick(View view, Repository item) {
        Navigator.getBackstack(getContext()).goTo(ContentKey.create(item));
    }
}
