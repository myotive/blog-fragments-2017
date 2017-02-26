package com.example.fragment_sample_mvp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.example.common.BaseApplication;
import com.example.common.di.ApplicationComponent;
import com.example.common.ui.ProgressBarProvider;
import com.example.fragment_sample_mvp.di.ActivityComponent;
import com.example.fragment_sample_mvp.di.DaggerActivityComponent;
import com.example.fragment_sample_mvp.di.modules.PresenterModule;
import com.example.fragment_sample_mvp.fragments.RepositoryFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity  implements ProgressBarProvider{

    @BindView(R.id.loading)
    ProgressBar progressBar;
    @BindView(R.id.main_content)
    FrameLayout mainContent;

    private ActivityComponent activityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // Grab application component from BaseApplication
        ApplicationComponent applicationComponent = BaseApplication.getApplication(this)
                .getApplicationComponent();

        // Create activity component, which has a dependency on ApplicationComponent
        activityComponent = DaggerActivityComponent
                .builder()
                .applicationComponent(applicationComponent)
                .presenterModule(new PresenterModule(this))
                .build();

        if(savedInstanceState == null) {

            // Set our first Fragment to the RepositoryFragment
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.main_content, RepositoryFragment.newInstance())
                    .commitNow();
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
        super.onBackPressed();
        if(getSupportFragmentManager().getBackStackEntryCount() > 0){
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        mainContent.setVisibility(View.GONE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
        mainContent.setVisibility(View.VISIBLE);
    }

    public ActivityComponent getActivityComponent() {
        return activityComponent;
    }
}
