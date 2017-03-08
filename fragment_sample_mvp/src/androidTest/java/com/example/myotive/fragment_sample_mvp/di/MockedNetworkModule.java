package com.example.myotive.fragment_sample_mvp.di;

import com.example.common.di.modules.NetworkModule;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by myotive on 3/7/2017.
 */

public class MockedNetworkModule extends NetworkModule {
    private MockWebServer mockWebServer;

    public MockedNetworkModule(MockWebServer mockWebServer) {
        this.mockWebServer = mockWebServer;
    }

    @Override
    public OkHttpClient provideHttpClient(HttpLoggingInterceptor logging) {
        return new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .build();
    }

    @Override
    public Retrofit provideRetrofit(OkHttpClient client) {


        return new Retrofit.Builder()
                .baseUrl(mockWebServer.url("/"))
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
