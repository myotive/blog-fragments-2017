package com.example.simplestacksample.common;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.zhuinden.simplestack.navigator.StateKey;
import com.zhuinden.simplestack.navigator.ViewChangeHandler;
import com.zhuinden.simplestack.navigator.changehandlers.NoOpViewChangeHandler;
import com.zhuinden.simplestack.navigator.changehandlers.SegueViewChangeHandler;

public abstract class BaseKey
        implements StateKey, Parcelable {
    public abstract String actionBarTitle();

    public abstract boolean shouldDisplayHomeAsUp();

    @NonNull
    @Override
    public ViewChangeHandler viewChangeHandler() {
        return new NoOpViewChangeHandler();
    }
}
