package com.example.myotive.fragment_sample_mvp.rules;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;

import com.jakewharton.espresso.OkHttp3IdlingResource;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import okhttp3.OkHttpClient;

/**
 * Created by myotive on 3/7/2017.
 */

public class IdlingResourceRule implements TestRule {
    private IdlingResource resource;

    public IdlingResourceRule(OkHttpClient client) {
        resource = OkHttp3IdlingResource.create("OkHttp", client);
    }

    @Override
    public Statement apply(final Statement base, final Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                Espresso.registerIdlingResources(resource);
                base.evaluate();
                Espresso.unregisterIdlingResources(resource);
            }
        };
    }
}
