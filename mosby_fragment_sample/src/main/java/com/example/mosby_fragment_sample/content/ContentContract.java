package com.example.mosby_fragment_sample.content;

import com.example.common.network.models.Content;
import com.example.common.network.models.Repository;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;
import com.hannesdorfmann.mosby.mvp.lce.MvpLceView;

import java.util.List;

/**
 * Created by myotive on 2/13/2017.
 */

public interface ContentContract {
    interface View extends MvpLceView<List<Content>>{
    }

    interface Presenter extends MvpPresenter<View>{
        void getContent(Repository repository, final boolean swipeToRefresh);
    }
}
