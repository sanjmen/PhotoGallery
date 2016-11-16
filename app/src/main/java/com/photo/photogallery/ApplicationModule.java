package com.photo.photogallery;

import android.app.Application;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapterFactory;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.photo.photogallery.api.entities.EntityTypeAdapterFactory;
import com.photo.photogallery.network.AppImageLoader;
import com.photo.photogallery.network.PicassoImageLoader;
import com.squareup.picasso.Picasso;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;

@Module
public class ApplicationModule {

    @NonNull
    private final Application application;

    public ApplicationModule(@NonNull Application application) {
        this.application = application;
    }

    @Provides
    @NonNull
    @Singleton
    public Application provideOrcheederApp() {
        return application;
    }

    @Provides
    @NonNull
    @Singleton
    public TypeAdapterFactory provideTypeAdapterFactory() {
        return EntityTypeAdapterFactory.create();
    }

    @Provides
    @Singleton
    Cache provideOkHttpCache(Application application) {
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(application.getCacheDir(), cacheSize);
        return cache;
    }

    @Provides
    @NonNull
    @Singleton
    public Gson provideGson(TypeAdapterFactory typeAdapterFactory) {
        return new GsonBuilder()
                .registerTypeAdapterFactory(typeAdapterFactory)
                .create();
    }

    @Provides
    @NonNull
    @Singleton
    public Picasso providePicasso(@NonNull Application application, @NonNull @Named("OkHttpDownloader") OkHttpClient okHttpClient) {
        return new Picasso.Builder(application)
                .downloader(new OkHttp3Downloader(okHttpClient))
                .build();
    }

    @Provides
    @NonNull
    @Singleton
    public AppImageLoader provideImageLoader(@NonNull Picasso picasso) {
        return new PicassoImageLoader(picasso);
    }

}
