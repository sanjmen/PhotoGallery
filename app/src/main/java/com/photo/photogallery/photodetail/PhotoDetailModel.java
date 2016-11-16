package com.photo.photogallery.photodetail;

import android.support.annotation.NonNull;

import com.photo.photogallery.api.FlickrRestApi;
import com.photo.photogallery.api.entities.Info;

import rx.Single;

public class PhotoDetailModel {

    @NonNull
    private final FlickrRestApi flickrRestApi;

    public PhotoDetailModel(@NonNull FlickrRestApi flickrRestApi) {
        this.flickrRestApi = flickrRestApi;
    }

    @NonNull
    public Single<Info> getInfo(String photoId) {
        return flickrRestApi.getInfo(photoId);
    }
}
