package com.photo.photogallery.photos;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.photo.photogallery.BaseActivity;
import com.photo.photogallery.PhotoGalleryApp;
import com.photo.photogallery.R;

public class PhotosActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PhotoGalleryApp.get(this).applicationComponent().inject(this);

        setContentView(getLayoutInflater().inflate(R.layout.activity_photos, null));

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_frame_layout, new PhotosFragment())
                    .commit();
        }
    }
}
