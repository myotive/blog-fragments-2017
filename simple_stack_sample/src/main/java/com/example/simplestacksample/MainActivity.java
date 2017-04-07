package com.example.simplestacksample;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.example.common.BaseApplication;
import com.example.common.network.GitHubAPI;
import com.example.common.ui.ProgressBarProvider;
import com.example.simplestacksample.common.BaseKey;
import com.example.simplestacksample.paths.repository.RepositoryKey;
import com.zhuinden.simplestack.HistoryBuilder;
import com.zhuinden.simplestack.StateChange;
import com.zhuinden.simplestack.StateChanger;
import com.zhuinden.simplestack.navigator.DefaultStateChanger;
import com.zhuinden.simplestack.navigator.Navigator;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity
        extends AppCompatActivity
        implements ProgressBarProvider, StateChanger {
    private static final String TAG = "MainActivity";

    @BindView(R.id.loading)
    ProgressBar progressBar;
    @BindView(R.id.main_content)
    FrameLayout mainContent;

    GitHubAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        api = BaseApplication.getApplication(getApplicationContext()).getApplicationComponent().githubapi();

        Navigator.configure()
                .setStateChanger(DefaultStateChanger.configure().setExternalStateChanger(this).create(this, mainContent))
                .install(this, mainContent, HistoryBuilder.single(RepositoryKey.create(getResources().getString(R.string.app_name))));
    }


    @Override
    public void handleStateChange(StateChange stateChange, Callback completionCallback) {
        if(stateChange.topNewState().equals(stateChange.topPreviousState())) {
            completionCallback.stateChangeComplete();
            return;
        }
        BaseKey key = stateChange.topNewState();
        setActionBarTitle(key.actionBarTitle());
        setDisplayHomeAsUpEnabled(key.shouldDisplayHomeAsUp());
        completionCallback.stateChangeComplete();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(!Navigator.onBackPressed(this)) {
            super.onBackPressed();
        }
    }

    @Override
    public void showProgressBar() {
        mainContent.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mainContent.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    void setActionBarTitle(String title) {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    void setDisplayHomeAsUpEnabled(boolean enabled) {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(enabled);
        }
    }

    // expose activity-scoped dependencies through context
    @Override
    public Object getSystemService(@NonNull String name) {
        if(name.equals(TAG)) {
            return this;
        }
        if(name.equals("GITHUB_API")) {
            return api;
        }
        return super.getSystemService(name);
    }

    public static MainActivity get(Context context) {
        //noinspection ResourceType
        return (MainActivity) context.getSystemService(TAG);
    }

    public static GitHubAPI getApi(Context context) {
        // noinspection ResourceType
        return (GitHubAPI) context.getSystemService("GITHUB_API");
    }
}
