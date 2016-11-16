package com.photo.photogallery.api.entities;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class Info implements Parcelable {

    private static final String JSON_PROPERTY_PHOTO = "photo";

    @NonNull
    public static Builder builder() {
        return new AutoValue_Info.Builder();
    }

    @NonNull
    public static TypeAdapter<Info> typeAdapter(Gson gson) {
        return new AutoValue_Info.GsonTypeAdapter(gson);
    }

    @NonNull
    @SerializedName(JSON_PROPERTY_PHOTO)
    public abstract PhotoInfo photoInfo();

    @AutoValue.Builder
    public static abstract class Builder {

        @NonNull
        public abstract Builder photoInfo(@NonNull PhotoInfo photoInfo);

        @NonNull
        public abstract Info build();
    }
}
