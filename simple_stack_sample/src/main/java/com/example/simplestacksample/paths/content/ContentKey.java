package com.example.simplestacksample.paths.content;

import android.support.annotation.NonNull;

import com.example.common.network.models.Repository;
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
public abstract class ContentKey extends BaseKey {
    public abstract Repository repository();

    @Override
    public int layout() {
        return R.layout.path_content;
    }

    @Override
    public String actionBarTitle() {
        return repository().getName();
    }

    @Override
    public boolean shouldDisplayHomeAsUp() {
        return true;
    }

    public static ContentKey create(Repository repository) {
        return new AutoValue_ContentKey(repository);
    }

    @NonNull
    @Override
    public ViewChangeHandler viewChangeHandler() {
        return new NoOpViewChangeHandler();
    }
}
