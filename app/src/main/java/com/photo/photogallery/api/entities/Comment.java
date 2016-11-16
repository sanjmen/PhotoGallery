package com.photo.photogallery.api.entities;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class Comment implements Parcelable {
    private static final String JSON_PROPERTY_CONTENT = "_content";

    @NonNull
    public static Comment.Builder builder() {
        return new AutoValue_Comment.Builder();
    }

    @NonNull
    public static TypeAdapter<Comment> typeAdapter(Gson gson) {
        return new AutoValue_Comment.GsonTypeAdapter(gson);
    }

    @Nullable
    @SerializedName(JSON_PROPERTY_CONTENT)
    public abstract String content();

    @AutoValue.Builder
    public static abstract class Builder {

        @Nullable
        public abstract Comment.Builder content(@Nullable String content);

        @NonNull
        public abstract Comment build();
    }
}

