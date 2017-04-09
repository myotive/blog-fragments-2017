package com.example.simplestacksample.paths.content;

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
import android.widget.FrameLayout;

import com.example.common.network.GitHubAPI;
import com.example.common.network.models.Content;
import com.example.common.network.models.Repository;
import com.example.common.ui.adapters.ContentAdapter;
import com.example.simplestacksample.MainActivity;
import com.example.simplestacksample.R;
import com.zhuinden.simplestack.Backstack;

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

public class ContentView
        extends FrameLayout {

    private static final String TAG = ContentView.class.getSimpleName();

    @BindView(R.id.rv_content)
    RecyclerView contentRecyclerView;

    private ContentAdapter contentAdapter;
    private Repository repository;
    private GitHubAPI gitHubAPI;
    private Call<List<Content>> contentCall;

    public ContentView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public ContentView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ContentView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(21)
    public ContentView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        if(!isInEditMode()) {
            gitHubAPI = MainActivity.getApi(context);

            ContentKey contentKey = Backstack.getKey(context);
            repository = contentKey.repository();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);

        if(contentAdapter == null){
            contentAdapter = new ContentAdapter(getContext(), Collections.<Content>emptyList());
        }
        contentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        contentRecyclerView.setAdapter(contentAdapter);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        MainActivity.get(getContext()).showProgressBar();
        contentCall = gitHubAPI.GetRepoContents(repository.getOwner().getLogin(), repository.getName());
        contentCall.enqueue(new Callback<List<Content>>() {
            @Override
            public void onResponse(Call<List<Content>> call, Response<List<Content>> response) {
                contentAdapter.swapData(response.body());
                MainActivity.get(getContext()).hideProgressBar();
            }

            @Override
            public void onFailure(Call<List<Content>> call, Throwable t) {
                Log.e(TAG, "Error calling GetRepoContents", t);
                MainActivity.get(getContext()).hideProgressBar();
            }
        });
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if(contentCall != null){
            contentCall.cancel();
        }
    }
}
