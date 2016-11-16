package com.photo.photogallery.photos;


import android.support.annotation.NonNull;

import com.photo.photogallery.api.FlickrRestApi;

import dagger.Module;
import dagger.Provides;

@Module
public class PhotosModule {

    private PhotosView photosView;

    public PhotosModule(PhotosView photosView) {
        this.photosView = photosView;
    }

    @Provides
    @NonNull
    public PhotosView providesPhotosView() {
        return photosView;
    }

    @Provides
    @NonNull
    public PhotosModel providePhotosModel(@NonNull FlickrRestApi flickrRestApi) {
        return new PhotosModel(flickrRestApi);
    }

    @Provides
    @NonNull
    public PhotosPresenter providePhotosPresenter(@NonNull PhotosModel photosModel, @NonNull PhotosView photosView) {
        return new PhotosPresenter(photosModel, photosView);
    }
}
