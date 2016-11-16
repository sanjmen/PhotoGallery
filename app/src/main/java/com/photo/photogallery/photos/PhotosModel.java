package com.photo.photogallery.photos;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.photo.photogallery.api.FlickrRestApi;
import com.photo.photogallery.api.entities.RecentPhotos;

import rx.Single;

public class PhotosModel {

    @NonNull
    private final FlickrRestApi flickrRestApi;

    public PhotosModel(@NonNull FlickrRestApi flickrRestApi) {
        this.flickrRestApi = flickrRestApi;
    }

    @NonNull
    public Single<RecentPhotos> getRecent(@Nullable Integer perPage, @Nullable Integer page) {
        return flickrRestApi.getRecent(perPage, page);
    }
}
