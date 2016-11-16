package com.photo.photogallery.api.entities;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class Locality implements Parcelable {
    private static final String JSON_PROPERTY_CONTENT = "_content";

    @NonNull
    public static Locality.Builder builder() {
        return new AutoValue_Locality.Builder();
    }

    @NonNull
    public static TypeAdapter<Locality> typeAdapter(Gson gson) {
        return new AutoValue_Locality.GsonTypeAdapter(gson);
    }

    @NonNull
    @SerializedName(JSON_PROPERTY_CONTENT)
    public abstract String content();

    @AutoValue.Builder
    public static abstract class Builder {

        @NonNull
        public abstract Locality.Builder content(@NonNull String content);

        @NonNull
        public abstract Locality build();
    }
}

