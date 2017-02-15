package com.example.mosbysample.repository;

import com.example.common.network.models.Repository;
import com.example.common.ui.adapters.RepositoryAdapter;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.List;

/**
 * Created by myotive on 2/13/2017.
 */

public interface RepositoryContract {
    interface View extends MvpView{
        void updateRepositoryList(List<Repository> repositories);
        void setOnRepositoryItemClick(RepositoryAdapter.RepositoryItemClick itemClickCallback);
    }

    interface Presenter extends MvpPresenter<View>{
        void getRepositories();
    }
}
