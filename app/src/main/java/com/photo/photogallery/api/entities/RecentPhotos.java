package com.photo.photogallery.api.entities;

import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class RecentPhotos {
    private static final String JSON_PROPERTY_PHOTOS = "photos";

    @NonNull
    public static RecentPhotos.Builder builder() {
        return new AutoValue_RecentPhotos.Builder();
    }

    @NonNull
    public static TypeAdapter<RecentPhotos> typeAdapter(Gson gson) {
        return new AutoValue_RecentPhotos.GsonTypeAdapter(gson);
    }

    @NonNull
    @SerializedName(JSON_PROPERTY_PHOTOS)
    public abstract PhotosCollection photosCollection();

    @AutoValue.Builder
    public static abstract class Builder {

        @NonNull
        public abstract RecentPhotos.Builder photosCollection(@NonNull PhotosCollection collection);

        @NonNull
        public abstract RecentPhotos build();
    }

}

