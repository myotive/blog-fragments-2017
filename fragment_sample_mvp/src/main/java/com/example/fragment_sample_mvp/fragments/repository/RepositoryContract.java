package com.example.fragment_sample_mvp.fragments.repository;

import com.example.common.network.models.Repository;
import com.example.fragment_sample_mvp.mvp.BasePresenter;
import com.example.fragment_sample_mvp.mvp.BaseView;

import java.util.List;

/**
 * Created by myotive on 2/26/2017.
 */

public class RepositoryContract {

    public interface Presenter<T extends BaseView> extends BasePresenter<View>{
        void getRepositories();
    }

    public interface View extends BaseView {
        void updateRepositoryList(List<Repository> repositories);
        void onRepositoryItemClick(Repository item);
    }
}
