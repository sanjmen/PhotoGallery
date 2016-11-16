package com.photo.photogallery.network;

import android.support.annotation.NonNull;
import android.widget.ImageView;

public interface AppImageLoader {
    void downloadInto(@NonNull String url, @NonNull ImageView imageView);
    void downloadIntoAndResize(@NonNull String url, @NonNull ImageView imageView, @NonNull Integer width, @NonNull Integer height);
}
