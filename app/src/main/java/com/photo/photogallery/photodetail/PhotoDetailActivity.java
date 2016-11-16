package com.photo.photogallery.photodetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.photo.photogallery.BaseActivity;
import com.photo.photogallery.PhotoGalleryApp;
import com.photo.photogallery.R;
import com.photo.photogallery.api.entities.Photo;

public class PhotoDetailActivity extends BaseActivity {

    @NonNull
    public static final String ARGUMENT_PHOTO = "PHOTO";

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, PhotoDetailActivity.class);

        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PhotoGalleryApp.get(this).applicationComponent().inject(this);

        Photo photo = getIntent().getParcelableExtra(ARGUMENT_PHOTO);

        setContentView(getLayoutInflater().inflate(R.layout.activity_photo_detail, null));

        super.setToolbarAsUp(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_frame_layout, PhotoDetailFragment.newInstance(photo))
                    .commit();
        }
    }
}
