package com.photo.photogallery;


import android.support.annotation.NonNull;

import com.photo.photogallery.api.ApiModule;
import com.photo.photogallery.network.NetworkModule;
import com.photo.photogallery.photodetail.PhotoDetailActivity;
import com.photo.photogallery.photodetail.PhotoDetailComponent;
import com.photo.photogallery.photodetail.PhotoDetailModule;
import com.photo.photogallery.photodetail.PhotoFullDialog;
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

    @NonNull
    PhotoDetailComponent plus(@NonNull PhotoDetailModule photoDetailModule);

    void inject(@NonNull PhotosActivity photosActivity);
    void inject(@NonNull PhotoDetailActivity photoDetailActivity);
    void inject(@NonNull PhotoFullDialog photoFullDialog);
}
