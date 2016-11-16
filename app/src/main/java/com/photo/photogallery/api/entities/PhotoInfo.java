package com.photo.photogallery.api.entities;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;


@AutoValue
public abstract class PhotoInfo implements Parcelable {

    private static final String JSON_PROPERTY_DESCRIPTION = "description";
    private static final String JSON_PROPERTY_COMMENTS = "comments";
    private static final String JSON_PROPERTY_LOCATION = "location";

    @NonNull
    public static Builder builder() {
        return new AutoValue_PhotoInfo.Builder();
    }

    @NonNull
    public static TypeAdapter<PhotoInfo> typeAdapter(Gson gson) {
        return new AutoValue_PhotoInfo.GsonTypeAdapter(gson);
    }

    @NonNull
    @SerializedName(JSON_PROPERTY_DESCRIPTION)
    public abstract Description description();

    @NonNull
    @SerializedName(JSON_PROPERTY_COMMENTS)
    public abstract Comment comments();

    @Nullable
    @SerializedName(JSON_PROPERTY_LOCATION)
    public abstract Location location();

    @AutoValue.Builder
    public static abstract class Builder {

        @NonNull
        public abstract Builder description(@NonNull Description description);

        @NonNull
        public abstract Builder comments(@NonNull Comment comment);

        @NonNull
        public abstract Builder location(@Nullable Location location);

        @NonNull
        public abstract PhotoInfo build();
    }
}
