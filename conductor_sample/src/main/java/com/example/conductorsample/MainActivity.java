package com.example.conductorsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.bluelinelabs.conductor.Conductor;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import com.example.common.BaseApplication;
import com.example.common.network.GitHubAPI;
import com.example.common.ui.ActionBarProvider;
import com.example.common.ui.ProgressBarProvider;
import com.example.conductorsample.controllers.RepositoryController;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ProgressBarProvider, ActionBarProvider {

    @BindView(R.id.loading)
    ProgressBar progressBar;
    @BindView(R.id.main_content)
    FrameLayout mainContent;
    private Router router;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        router = Conductor.attachRouter(this, mainContent, savedInstanceState);

        GitHubAPI api = BaseApplication.getApplication(getApplicationContext()).getApplicationComponent().githubapi();

        if (!router.hasRootController()) {
            router.setRoot(RouterTransaction.with(new RepositoryController(api)));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (!router.handleBack()) {
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
}
