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
import okhttp3.logging.HttpLoggingInterceptor;

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

    @Provides @Singleton @NonNull
    public HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return logging;
    }

    @Provides
    @NonNull
    @Singleton
    public OkHttpClient provideOkHttpClient(
            @NonNull HttpLoggingInterceptor httpLoggingInterceptor,
            @NonNull HeadersInterceptor headersInterceptor,
            @NonNull QueryInterceptor queryInterceptor, @NonNull Cache cache) {

        final OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder()
                .connectTimeout(Config.API_CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(Config.API_WRITE_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(Config.API_READ_TIMEOUT, TimeUnit.SECONDS);

        okHttpBuilder.cache(cache);

        okHttpBuilder.addInterceptor(httpLoggingInterceptor);
        okHttpBuilder.addInterceptor(headersInterceptor);
        okHttpBuilder.addInterceptor(queryInterceptor);

        return okHttpBuilder.build();
    }

    @Provides
    @NonNull
    @Singleton
    @Named("OkHttpDownloader")
    public OkHttpClient provideOkHttpDownloader(
            @NonNull HttpLoggingInterceptor httpLoggingInterceptor,
            @NonNull Cache cache) {

        final OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder()
                .connectTimeout(Config.API_CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(Config.API_WRITE_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(Config.API_READ_TIMEOUT, TimeUnit.SECONDS);

        okHttpBuilder.cache(cache);

        okHttpBuilder.addInterceptor(httpLoggingInterceptor);

        return okHttpBuilder.build();
    }
}
