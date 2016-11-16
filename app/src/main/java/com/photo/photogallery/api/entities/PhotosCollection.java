package com.photo.photogallery.api.entities;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@AutoValue
public abstract class PhotosCollection {
    private static final String JSON_PROPERTY_PAGE = "page";
    private static final String JSON_PROPERTY_PAGES = "pages";
    private static final String JSON_PROPERTY_PER_PAGE = "perpage";
    private static final String JSON_PROPERTY_TOTAL = "total";
    private static final String JSON_PROPERTY_STAT = "stat";
    private static final String JSON_PROPERTY_PHOTO = "photo";

    @NonNull
    public static Builder builder() {
        return new AutoValue_PhotosCollection.Builder();
    }

    @NonNull
    public static TypeAdapter<PhotosCollection> typeAdapter(Gson gson) {
        return new AutoValue_PhotosCollection.GsonTypeAdapter(gson);
    }

    @NonNull
    @SerializedName(JSON_PROPERTY_PAGE)
    public abstract Integer page();

    @NonNull
    @SerializedName(JSON_PROPERTY_PAGES)
    public abstract Integer pages();

    @NonNull
    @SerializedName(JSON_PROPERTY_PER_PAGE)
    public abstract Integer perPage();

    @NonNull
    @SerializedName(JSON_PROPERTY_TOTAL)
    public abstract Integer total();

    @Nullable
    @SerializedName(JSON_PROPERTY_STAT)
    public abstract String stat();

    @NonNull
    @SerializedName(JSON_PROPERTY_PHOTO)
    public abstract List<Photo> photos();

    @AutoValue.Builder
    public static abstract class Builder {

        @NonNull
        public abstract Builder page(@NonNull Integer page);

        @NonNull
        public abstract Builder pages(@NonNull Integer pages);

        @NonNull
        public abstract Builder perPage(@NonNull Integer perPage);

        @NonNull
        public abstract Builder total(@NonNull Integer total);

        @NonNull
        public abstract Builder stat(@Nullable String stat);

        @NonNull
        public abstract Builder photos(@NonNull List<Photo> photo);

        @NonNull
        public abstract PhotosCollection build();
    }
}
