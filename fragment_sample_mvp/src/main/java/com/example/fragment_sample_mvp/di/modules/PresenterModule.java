package com.example.fragment_sample_mvp.di.modules;

import com.example.common.ui.ProgressBarProvider;
import com.example.fragment_sample_mvp.di.scopes.ActivityScope;

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
}
