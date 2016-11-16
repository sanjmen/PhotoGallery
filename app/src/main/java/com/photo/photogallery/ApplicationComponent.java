package com.photo.photogallery;


import android.support.annotation.NonNull;

import com.photo.photogallery.api.ApiModule;
import com.photo.photogallery.network.NetworkModule;
import com.photo.photogallery.photos.PhotosActivity;
import com.photo.photogallery.photos.PhotosComponent;
import com.photo.photogallery.photos.PhotosModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        NetworkModule.class,
        ApiModule.class
})

public interface ApplicationComponent {

    @NonNull
    PhotosComponent plus(@NonNull PhotosModule photosModule);

    void inject(@NonNull PhotosActivity photosActivity);
}
