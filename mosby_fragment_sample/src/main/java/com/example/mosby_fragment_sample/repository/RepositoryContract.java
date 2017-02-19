package com.example.mosby_fragment_sample.repository;

import com.example.common.network.models.Repository;
import com.example.common.ui.adapters.RepositoryAdapter;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;
import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;

import java.util.List;

/**
 * Created by myotive on 2/13/2017.
 */

public interface RepositoryContract {
    interface View extends MvpLceView<List<Repository>>{
    }

    interface Presenter extends MvpPresenter<View>{
        void getRepositories(boolean pullToRefresh);
    }
}
