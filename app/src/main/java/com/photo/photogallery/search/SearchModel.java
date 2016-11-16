package com.photo.photogallery.search;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.photo.photogallery.api.FlickrRestApi;
import com.photo.photogallery.api.entities.RecentPhotos;

import rx.Single;

public class SearchModel {

    @NonNull
    private final FlickrRestApi flickrRestApi;

    public SearchModel(@NonNull FlickrRestApi flickrRestApi) {
        this.flickrRestApi = flickrRestApi;
    }

    @NonNull
    public Single<RecentPhotos> search(@NonNull String text, @Nullable Integer perPage, @Nullable Integer page) {
        return flickrRestApi.search(text, perPage, page);
    }

    @NonNull
    public Single<RecentPhotos> getRecent(@Nullable Integer perPage, @Nullable Integer page) {
        return flickrRestApi.getRecent(perPage, page);
    }
}
