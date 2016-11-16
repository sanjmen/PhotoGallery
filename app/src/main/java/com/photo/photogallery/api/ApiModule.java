package com.photo.photogallery.api;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.photo.photogallery.BuildConfig;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {

    @NonNull
    private final HttpUrl baseUrl;

    public ApiModule(@NonNull String baseUrl) {
        this.baseUrl = HttpUrl.parse(baseUrl);
    }

    @Provides @NonNull @Singleton
    public HttpUrl provideBaseUrl() {
        return baseUrl;
    }

    @Provides @NonNull
    public FlickrRestApi provideFlickrApi(@NonNull OkHttpClient okHttpClient, @NonNull Gson gson, @NonNull HttpUrl baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .validateEagerly(BuildConfig.DEBUG)
                .build()
                .create(FlickrRestApi.class);
    }
}