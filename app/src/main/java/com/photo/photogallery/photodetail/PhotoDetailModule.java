package com.photo.photogallery.photodetail;

import android.support.annotation.NonNull;

import com.photo.photogallery.api.FlickrRestApi;

import dagger.Module;
import dagger.Provides;

@Module
public class PhotoDetailModule {

    private PhotoDetailView photoDetailView;

    private String photoId;

    public PhotoDetailModule(String photoId, PhotoDetailView photoDetailView) {
        this.photoDetailView = photoDetailView;
        this.photoId = photoId;
    }

    @Provides
    @NonNull
    public PhotoDetailView providesPhotoDetailView() {
        return photoDetailView;
    }

    @Provides
    @NonNull
    String providePhotoId() {
        return photoId;
    }

    @Provides
    @NonNull
    public PhotoDetailModel providePhotoDetailModel(@NonNull FlickrRestApi flickrRestApi) {
        return new PhotoDetailModel(flickrRestApi);
    }

    @Provides
    @NonNull
    public PhotoDetailPresenter providePhotoDetailPresenter(
            @NonNull String photoId, @NonNull PhotoDetailModel photoDetailModel, @NonNull PhotoDetailView photoDetailView) {
        return new PhotoDetailPresenter(photoId, photoDetailModel, photoDetailView);
    }
}
