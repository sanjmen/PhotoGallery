package com.photo.photogallery.network;

import android.support.annotation.NonNull;


import com.photo.photogallery.Config;

import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;

@Module
public class NetworkModule {

    @Provides
    @NonNull
    @Singleton
    public HeadersInterceptor provideTokenInterceptor() {
        return new HeadersInterceptor();
    }

    @Provides
    @NonNull
    @Singleton
    public QueryInterceptor provideQueryParamsInterceptor() { return new QueryInterceptor(); }

    @Provides
    @NonNull
    @Singleton
    public OkHttpClient provideOkHttpClient(
            @NonNull HeadersInterceptor headersInterceptor,
            @NonNull QueryInterceptor queryInterceptor, @NonNull Cache cache) {

        final OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder()
                .connectTimeout(Config.API_CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(Config.API_WRITE_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(Config.API_READ_TIMEOUT, TimeUnit.SECONDS);

        okHttpBuilder.cache(cache);

        okHttpBuilder.addInterceptor(headersInterceptor);
        okHttpBuilder.addInterceptor(queryInterceptor);

        return okHttpBuilder.build();
    }

    @Provides
    @NonNull
    @Singleton
    @Named("OkHttpDownloader")
    public OkHttpClient provideOkHttpDownloader(@NonNull Cache cache) {

        final OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder()
                .connectTimeout(Config.API_CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(Config.API_WRITE_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(Config.API_READ_TIMEOUT, TimeUnit.SECONDS);

        okHttpBuilder.cache(cache);

        return okHttpBuilder.build();
    }
}
