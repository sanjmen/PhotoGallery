package com.photo.photogallery;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.photo.photogallery.api.ApiModule;

import timber.log.Timber;

public class PhotoGalleryApp extends Application {

    private ApplicationComponent applicationComponent;

    @NonNull
    public static PhotoGalleryApp get(@NonNull Context context) {
        return (PhotoGalleryApp) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = prepareApplicationComponent().build();


        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    @NonNull
    protected DaggerApplicationComponent.Builder prepareApplicationComponent() {
        return DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .apiModule(new ApiModule(Config.API_BASE_URL));
    }

    @NonNull
    public ApplicationComponent applicationComponent() {
        return applicationComponent;
    }
}
