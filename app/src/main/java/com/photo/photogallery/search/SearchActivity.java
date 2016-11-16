package com.photo.photogallery.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.photo.photogallery.PhotoGalleryApp;
import com.photo.photogallery.R;

public class SearchActivity extends AppCompatActivity {
    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);

        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PhotoGalleryApp.get(this).applicationComponent().inject(this);

        setContentView(getLayoutInflater().inflate(R.layout.activity_search, null));

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_frame_layout, SearchFragment.newInstance())
                .commit();
    }

    @Override
    public void onBackPressed() {
        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
        } else {
            getFragmentManager().popBackStack();
        }
    }
}
