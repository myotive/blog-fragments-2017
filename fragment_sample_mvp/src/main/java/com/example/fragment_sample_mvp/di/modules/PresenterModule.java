package com.example.fragment_sample_mvp.di.modules;

import com.example.common.network.GitHubAPI;
import com.example.common.ui.ProgressBarProvider;
import com.example.fragment_sample_mvp.di.scopes.ActivityScope;
import com.example.fragment_sample_mvp.fragments.content.ContentContract;
import com.example.fragment_sample_mvp.fragments.content.ContentPresenter;
import com.example.fragment_sample_mvp.fragments.repository.RepositoryContract;
import com.example.fragment_sample_mvp.fragments.repository.RepositoryPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by myotive on 2/26/2017.
 */
@Module
public class PresenterModule {

    private ProgressBarProvider progressBarProvider;
    public PresenterModule(ProgressBarProvider progressBarProvider){
        this.progressBarProvider = progressBarProvider;
    }

    @Provides
    @ActivityScope
    public ProgressBarProvider provideProgressBarProvider(){
        return progressBarProvider;
    }

    @Provides
    @ActivityScope
    public RepositoryContract.Presenter provideRepositoryPresenter(GitHubAPI gitHubAPI){
        return new RepositoryPresenter(gitHubAPI, progressBarProvider);
    }

    @Provides
    @ActivityScope
    public ContentContract.Presenter provideContentPresenter(GitHubAPI gitHubAPI){
        return new ContentPresenter(gitHubAPI, progressBarProvider);
    }
}
