package com.example.myotive.fragment_sample_mvp;

import com.example.common.network.GitHubAPI;
import com.example.common.network.models.Repository;
import com.example.common.ui.ProgressBarProvider;
import com.example.fragment_sample_mvp.BuildConfig;
import com.example.fragment_sample_mvp.fragments.repository.RepositoryContract;
import com.example.fragment_sample_mvp.fragments.repository.RepositoryPresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class RepositoryUnitTest {

    @Mock
    private RepositoryContract.View view;

    @Mock
    private ProgressBarProvider progressBarProvider;

    @Mock
    private GitHubAPI gitHubAPI;

    @Mock
    private Call<List<Repository>> mockCall;

    @Captor
    private ArgumentCaptor<Callback<List<Repository>>> captor;

    private RepositoryPresenter presenter;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);

        presenter = new RepositoryPresenter(gitHubAPI, progressBarProvider);
        presenter.setView(view);
    }

    @Test
    public void test_getRepositories() {
        // arrange
        String user = BuildConfig.GITHUB_OWNER;
        List<Repository> repositories = new ArrayList<>();
        repositories.add(new Repository());

        when(gitHubAPI.GetRepos(user)).thenReturn(mockCall);
        Response<List<Repository>> response = Response.success(repositories);

        // act
        presenter.start();

        // assert
        verify(mockCall).enqueue(captor.capture());
        captor.getValue().onResponse(null, response);

        verify(progressBarProvider).showProgressBar();
        verify(progressBarProvider).hideProgressBar();

        verify(view).updateRepositoryList(repositories);
    }
}