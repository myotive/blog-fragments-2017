package com.example.mosbysample.content;

import com.example.common.network.models.Content;
import com.example.common.network.models.Repository;
import com.example.common.ui.adapters.RepositoryAdapter;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.List;

/**
 * Created by myotive on 2/13/2017.
 */

public interface ContentContract {
    interface View extends MvpView{
        void updateContentList(List<Content> repositories);
    }

    interface Presenter extends MvpPresenter<View>{
        void getContent(Repository repository);
    }
}
