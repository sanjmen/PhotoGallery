package com.photo.photogallery.api.entities;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class Location implements Parcelable {
    private static final String JSON_PROPERTY_LOCALITY = "locality";

    @NonNull
    public static Location.Builder builder() {
        return new AutoValue_Location.Builder();
    }

    @NonNull
    public static TypeAdapter<Location> typeAdapter(Gson gson) {
        return new AutoValue_Location.GsonTypeAdapter(gson);
    }

    @Nullable
    @SerializedName(JSON_PROPERTY_LOCALITY)
    public abstract Locality locality();

    @AutoValue.Builder
    public static abstract class Builder {

        @Nullable
        public abstract Location.Builder locality(@Nullable Locality locality);

        @NonNull
        public abstract Location build();
    }
}

