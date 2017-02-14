package com.example.conductorsample.controllers;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Controller;
import com.example.common.ui.ActionBarProvider;
import com.example.common.ui.ProgressBarProvider;

/**
 * Created by myotive on 2/13/2017.
 */

public class BaseController extends Controller {

    public BaseController() {

    }

    public BaseController(Bundle bundle){
        super(bundle);
    }

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        return null;
    }

    private ActionBar getActionBar() {
        ActionBarProvider actionBarProvider = ((ActionBarProvider)getActivity());
        return actionBarProvider != null ? actionBarProvider.getSupportActionBar() : null;
    }

    void setActionBarTitle(String title){
        ActionBar actionBar = getActionBar();
        if(actionBar != null){
            actionBar.setTitle(title);
        }
    }

    void setDisplayHomeAsUpEnabled(boolean enabled){
        ActionBar actionBar = getActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(enabled);
        }
    }

    private ProgressBarProvider getProgressBarProvider(){
        return ((ProgressBarProvider)getActivity());
    }

    void showProgressBar(){
        ProgressBarProvider progressBarProvider = getProgressBarProvider();
        if(progressBarProvider != null){
            progressBarProvider.showProgressBar();
        }
    }

    void hideProgressBar(){
        ProgressBarProvider progressBarProvider = getProgressBarProvider();
        if(progressBarProvider != null){
            progressBarProvider.hideProgressBar();
        }
    }
}
