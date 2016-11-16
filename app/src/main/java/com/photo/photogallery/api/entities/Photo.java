package com.photo.photogallery.api.entities;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class Photo implements Parcelable {
    private static final String JSON_PROPERTY_ID = "id";
    private static final String JSON_PROPERTY_OWNER = "owner";
    private static final String JSON_PROPERTY_OWNERNAME = "ownername";
    private static final String JSON_PROPERTY_SERVER = "server";
    private static final String JSON_PROPERTY_SECRET = "secret";
    private static final String JSON_PROPERTY_ICON_SERVER = "iconserver";
    private static final String JSON_PROPERTY_FARM = "farm";
    private static final String JSON_PROPERTY_ICON_FARM = "iconfarm";
    private static final String JSON_PROPERTY_TITLE = "title";
    private static final String JSON_PROPERTY_IS_PUBLIC = "ispublic";
    private static final String JSON_PROPERTY_DATETAKEN = "datetaken";

    @NonNull
    public static Builder builder() {
        return new AutoValue_Photo.Builder();
    }

    @NonNull
    public static TypeAdapter<Photo> typeAdapter(Gson gson) {
        return new AutoValue_Photo.GsonTypeAdapter(gson);
    }

    @NonNull
    @SerializedName(JSON_PROPERTY_ID)
    public abstract String id();

    @NonNull
    @SerializedName(JSON_PROPERTY_OWNER)
    public abstract String owner();

    @NonNull
    @SerializedName(JSON_PROPERTY_OWNERNAME)
    public abstract String ownerName();

    @NonNull
    @SerializedName(JSON_PROPERTY_SERVER)
    public abstract String server();

    @NonNull
    @SerializedName(JSON_PROPERTY_ICON_SERVER)
    public abstract Integer iconServer();

    @NonNull
    @SerializedName(JSON_PROPERTY_FARM)
    public abstract String farm();

    @NonNull
    @SerializedName(JSON_PROPERTY_ICON_FARM)
    public abstract String iconFarm();

    @NonNull
    @SerializedName(JSON_PROPERTY_TITLE)
    public abstract String title();

    @NonNull
    @SerializedName(JSON_PROPERTY_IS_PUBLIC)
    public abstract Integer isPublic();

    @NonNull
    @SerializedName(JSON_PROPERTY_DATETAKEN)
    public abstract String dateTaken();

    @NonNull
    @SerializedName(JSON_PROPERTY_SECRET)
    public abstract String secret();

    @AutoValue.Builder
    public static abstract class Builder {

        @NonNull
        public abstract Builder id(@NonNull String id);

        @NonNull
        public abstract Builder owner(@NonNull String owner);

        @NonNull
        public abstract Builder ownerName(@NonNull String ownerName);

        @NonNull
        public abstract Builder server(@NonNull String server);

        @NonNull
        public abstract Builder iconServer(@NonNull Integer iconServer);

        @NonNull
        public abstract Builder farm(@NonNull String farm);

        @NonNull
        public abstract Builder iconFarm(@NonNull String iconFarm);

        @NonNull
        public abstract Builder title(@NonNull String title);

        @NonNull
        public abstract Builder isPublic(@NonNull Integer isPublic);

        @NonNull
        public abstract Builder dateTaken(@NonNull String dateTaken);

        @NonNull
        public abstract Builder secret(@NonNull String secret);

        @NonNull
        public abstract Photo build();
    }
}
