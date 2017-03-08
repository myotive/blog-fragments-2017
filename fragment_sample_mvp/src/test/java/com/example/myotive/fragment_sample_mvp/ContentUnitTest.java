package com.example.myotive.fragment_sample_mvp;

import com.example.common.network.GitHubAPI;
import com.example.common.network.models.Content;
import com.example.common.network.models.Owner;
import com.example.common.network.models.Repository;
import com.example.common.ui.ProgressBarProvider;
import com.example.fragment_sample_mvp.BuildConfig;
import com.example.fragment_sample_mvp.fragments.content.ContentContract;
import com.example.fragment_sample_mvp.fragments.content.ContentPresenter;
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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ContentUnitTest {

    @Mock
    private ContentContract.View view;

    @Mock
    private ProgressBarProvider progressBarProvider;

    @Mock
    private GitHubAPI gitHubAPI;

    @Mock
    private Call<List<Content>> mockContentCall;

    @Captor
    private ArgumentCaptor<Callback<List<Content>>> captor;

    private ContentPresenter presenter;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);

        presenter = new ContentPresenter(gitHubAPI, progressBarProvider);
        presenter.setView(view);
    }

    @Test
    public void test_GetContents() {
        // arrange
        String user = BuildConfig.GITHUB_OWNER;
        String repoName = "fakeRepo";

        Owner owner = new Owner();
        owner.setLogin(user);

        Repository repository = new Repository();
        repository.setName(repoName);
        repository.setOwner(owner);

        List<Content> contents = new ArrayList<>();
        contents.add(new Content());

        when(view.getCurrentRepository()).thenReturn(repository);
        when(gitHubAPI.GetRepoContents(user, repoName)).thenReturn(mockContentCall);
        Response<List<Content>> response = Response.success(contents);

        // act
        presenter.start();

        // assert
        verify(mockContentCall).enqueue(captor.capture());
        captor.getValue().onResponse(null, response);

        verify(progressBarProvider).showProgressBar();
        verify(progressBarProvider).hideProgressBar();

        verify(view).getCurrentRepository();
        verify(view).updateContentList(contents);
    }
}