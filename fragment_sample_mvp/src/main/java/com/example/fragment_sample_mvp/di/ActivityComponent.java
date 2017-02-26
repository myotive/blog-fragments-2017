package com.example.fragment_sample_mvp.di;

import com.example.common.di.ApplicationComponent;
import com.example.fragment_sample_mvp.di.modules.PresenterModule;
import com.example.fragment_sample_mvp.di.scopes.ActivityScope;
import com.example.fragment_sample_mvp.fragments.ContentFragment;
import com.example.fragment_sample_mvp.fragments.RepositoryFragment;

import dagger.Component;

/**
 * Created by myotive on 2/26/2017.
 */
@ActivityScope
@Component(modules = { PresenterModule.class },
        dependencies = {ApplicationComponent.class})
public interface ActivityComponent {
    void inject(RepositoryFragment fragment);
    void inject(ContentFragment fragment);
}
