package com.photo.photogallery.network;

import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class PicassoImageLoader implements AppImageLoader {

    @NonNull
    private final Picasso picasso;

    public PicassoImageLoader(@NonNull final Picasso picasso) {
        this.picasso = picasso;
    }

    @Override
    public void downloadInto(@NonNull String url, @NonNull ImageView imageView) {
        picasso.load(url).fit().centerCrop().into(imageView);
    }

    @Override
    public void downloadIntoAndResize(@NonNull String url, @NonNull ImageView imageView, @NonNull Integer width, @NonNull Integer height) {
        picasso.load(url).resize(width, height).onlyScaleDown().into(imageView);
    }
}