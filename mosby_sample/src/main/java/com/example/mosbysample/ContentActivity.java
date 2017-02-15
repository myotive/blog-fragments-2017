package com.example.mosbysample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.common.network.models.Repository;
import com.example.common.ui.ProgressBarProvider;
import com.example.mosbysample.repository.RepositoryProvider;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContentActivity extends AppCompatActivity implements RepositoryProvider, ProgressBarProvider {

    public static final String REPOSITORY_EXTRA = "REPOSITORY_EXTRA";

    @BindView(R.id.loading)
    ProgressBar progressBar;

    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        if(getIntent() != null && getIntent().hasExtra(REPOSITORY_EXTRA)){
            repository = (Repository)getIntent().getSerializableExtra(REPOSITORY_EXTRA);
        }

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        ButterKnife.bind(this);
    }

    @Override
    public void finish() {
        super.finish();

        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            if(repository != null){
                getSupportActionBar().setTitle(repository.getName());
            }
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
    public Repository getRepository() {
        return repository;
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }
}
