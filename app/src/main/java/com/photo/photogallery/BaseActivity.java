package com.photo.photogallery;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public abstract class BaseActivity extends AppCompatActivity {

    @Nullable
    private Toolbar toolbar;

    @Nullable
    private TextView textView;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setupToolbar();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        setupToolbar();
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        setupToolbar();
    }

    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        textView = (TextView) findViewById(R.id.toolbar_title);
        textView.setText(getString(R.string.app_name));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    protected void setToolbarAsUp(View.OnClickListener clickListener) {
        toolbar().setNavigationIcon(R.drawable.ic_up);
        toolbar().setNavigationContentDescription(R.string.go_back);
        toolbar().setNavigationOnClickListener(clickListener);
    }

    @Nullable
    protected Toolbar toolbar() {
        return toolbar;
    }
}
