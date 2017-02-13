package com.example.common.di.modules;

import com.example.common.di.scopes.ApplicationScope;
import com.example.common.network.GitHubAPI;
import com.fatboyindustrial.gsonjodatime.DateTimeConverter;
import com.google.gson.GsonBuilder;

import org.joda.time.DateTime;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by myotive on 2/12/2017.
 */

@Module
public class NetworkModule {

    @Provides
    @ApplicationScope
    Retrofit provideRetrofit(OkHttpClient client){

        GsonBuilder builder = new GsonBuilder(); // Set up the custom GSON converters
        builder.registerTypeAdapter(DateTime.class, new DateTimeConverter());

        return new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(builder.create()))
                .build();
    }

    @Provides
    @ApplicationScope
    OkHttpClient provideHttpClient(HttpLoggingInterceptor logging){

        return new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();
    }

    @Provides
    @ApplicationScope
    HttpLoggingInterceptor provideInterceptor(){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);

        return logging;
    }

    @Provides
    @ApplicationScope
    GitHubAPI provideGithubAPI(Retrofit retrofit){
        return retrofit.create(GitHubAPI.class);
    }
}
