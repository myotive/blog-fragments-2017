package com.example.fragment_sample_mvp.mvp;

/**
 * Created by myotive on 2/26/2017.
 */

public interface BasePresenter<T extends BaseView> {
    void start();
    void stop();
    void setView(T view);
}
