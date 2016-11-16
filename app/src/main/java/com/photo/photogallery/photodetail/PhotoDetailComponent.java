package com.photo.photogallery.photodetail;

import android.support.annotation.NonNull;

import dagger.Subcomponent;

@Subcomponent( modules = PhotoDetailModule.class )
public interface PhotoDetailComponent {
    void inject(@NonNull PhotoDetailFragment photoDetailFragment);
}
