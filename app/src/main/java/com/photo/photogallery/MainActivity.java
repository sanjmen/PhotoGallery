package com.photo.photogallery;

import android.os.Bundle;
import android.support.annotation.Nullable;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PhotoGalleryApp.get(this).applicationComponent().inject(this);

        setContentView(getLayoutInflater().inflate(R.layout.activity_main, null));
    }
}
