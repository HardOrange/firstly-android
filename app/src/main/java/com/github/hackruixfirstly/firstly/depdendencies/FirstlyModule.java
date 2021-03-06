package com.github.hackruixfirstly.firstly.depdendencies;

import android.content.Context;

import com.github.hackruixfirstly.firstly.FirstlyApplication;
import com.github.hackruixfirstly.firstly.network.FirstlyAPI;
import com.github.hackruixfirstly.firstly.network.LoggingInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by trevor on 10/3/15.
 */

@Module
public class FirstlyModule {

    private Context context;

    public FirstlyModule(FirstlyApplication application) {
        this.context = application;
    }

    @Provides
    @Singleton
    public Context provideApplicationContext() {
        return context;
    }

    @Provides
    @Named("endpoint")
    public String provideEndpoint() {
        return "http://shiggy.xyz/api/"; //TODO no hardcoding.
    }

    @Provides
    @Singleton
    public OkHttpClient provideOkHttp(Context context) {
        OkHttpClient client = new OkHttpClient();

        client.interceptors().add(new LoggingInterceptor());

        return client;
    }

    @Provides
    @Singleton
    public FirstlyAPI provideAPI(@Named("endpoint") String endpoint, OkHttpClient client, Gson gson) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(endpoint).client(client).addConverterFactory(GsonConverterFactory.create(gson)).build();

        return retrofit.create(FirstlyAPI.class);
    }

    @Provides
    @Singleton
    public Gson provideGson() {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
        return gson;
    }

    @Provides
    @Singleton
    public AccessTokenHolder provideTokenHolder() {
        return new AccessTokenHolder(); //TODO this is horrible design, but I need a temp workaround since backend is being weird.
    }

}
