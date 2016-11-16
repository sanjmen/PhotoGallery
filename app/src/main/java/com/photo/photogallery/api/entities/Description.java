package com.photo.photogallery.api.entities;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class Description implements Parcelable {
    private static final String JSON_PROPERTY_CONTENT = "_content";

    @NonNull
    public static Description.Builder builder() {
        return new AutoValue_Description.Builder();
    }

    @NonNull
    public static TypeAdapter<Description> typeAdapter(Gson gson) {
        return new AutoValue_Description.GsonTypeAdapter(gson);
    }

    @Nullable
    @SerializedName(JSON_PROPERTY_CONTENT)
    public abstract String content();

    @AutoValue.Builder
    public static abstract class Builder {

        @Nullable
        public abstract Description.Builder content(@Nullable String content);

        @NonNull
        public abstract Description build();
    }
}

