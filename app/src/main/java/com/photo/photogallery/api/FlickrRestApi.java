package com.photo.photogallery.api;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.photo.photogallery.api.entities.Info;
import com.photo.photogallery.api.entities.RecentPhotos;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Single;

public interface FlickrRestApi {

    /**
     * @param perPage (optional) Number of photosList to return per page. If this argument is omitted, it defaults to 100. The maximum allowed value is 500.
     * @param page    (optional) The page of results to return. If this argument is omitted, it defaults to 1.
     * @return a list of the latest public photosList uploaded to flickr
     */
    @GET("?method=flickr.photos.getRecent")
    Single<RecentPhotos> getRecent(
            @Nullable @Query("per_page") Integer perPage,
            @Nullable @Query("page") Integer page
    );

    /**
     * @param photoId (Required) The id of the photo to get information for.
     * @return Get information about a photo. The calling user must have permission to view the photo.
     */
    @GET("?method=flickr.photos.getInfo")
    Single<Info> getInfo(
            @NonNull @Query("photo_id") String photoId
    );

    /**
     *
     * @param text A free text search. Photos who's title, description or tags contain the text will be returned. You can exclude results that match a term by prepending it with a - character.
     * @param perPage (optional) Number of photosList to return per page. If this argument is omitted, it defaults to 100. The maximum allowed value is 500.
     * @param page    (optional) The page of results to return. If this argument is omitted, it defaults to 1.
     * @return a list of photos matching some criteria. Only photos visible to the calling user will be returned.
     */
    @GET("?method=flickr.photos.search")
    Single<RecentPhotos> search(
            @NonNull @Query("text") String text,
            @Nullable @Query("per_page") Integer perPage,
            @Nullable @Query("page") Integer page
    );
}
