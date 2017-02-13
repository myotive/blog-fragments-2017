package com.example.common.di;

import com.example.common.di.modules.NetworkModule;
import com.example.common.di.scopes.ApplicationScope;
import com.example.common.network.GitHubAPI;

import dagger.Component;

/**
 * Created by myotive on 2/12/2017.
 */
@ApplicationScope
@Component(modules = { NetworkModule.class })
public interface ApplicationComponent {

    GitHubAPI githubapi();
}
