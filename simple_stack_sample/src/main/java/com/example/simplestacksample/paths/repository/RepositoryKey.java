package com.example.simplestacksample.paths.repository;

import android.support.annotation.NonNull;

import com.example.simplestacksample.R;
import com.example.simplestacksample.common.BaseKey;
import com.google.auto.value.AutoValue;
import com.zhuinden.simplestack.navigator.ViewChangeHandler;
import com.zhuinden.simplestack.navigator.changehandlers.NoOpViewChangeHandler;
import com.zhuinden.simplestack.navigator.changehandlers.SegueViewChangeHandler;

/**
 * Created by Owner on 2017. 04. 07..
 */

@AutoValue
public abstract class RepositoryKey extends BaseKey {
    @Override
    public int layout() {
        return R.layout.path_repository;
    }

    @Override
    public boolean shouldDisplayHomeAsUp() {
        return false;
    }

    public static RepositoryKey create(String actionBarTitle) {
        return new AutoValue_RepositoryKey(actionBarTitle);
    }
}
