package com.example.fragment_sample_mvp.fragments.content;

import com.example.common.network.models.Content;
import com.example.common.network.models.Repository;
import com.example.fragment_sample_mvp.mvp.BasePresenter;
import com.example.fragment_sample_mvp.mvp.BaseView;

import java.util.List;

/**
 * Created by myotive on 2/26/2017.
 */

public class ContentContract {
    public interface Presenter<T extends BaseView> extends BasePresenter<View> {
        void getContent();
    }

    public interface View extends BaseView {
        void updateContentList(List<Content> contents);
        Repository getCurrentRepository();
    }
}
