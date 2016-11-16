package com.photo.photogallery.photos;

import android.support.annotation.NonNull;

import dagger.Subcomponent;

@Subcomponent(modules = PhotosModule.class)
public interface PhotosComponent {
    void inject(@NonNull PhotosFragment photosFragment);
}
