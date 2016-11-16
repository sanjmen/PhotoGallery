package com.photo.photogallery.search;

import android.support.annotation.NonNull;

import dagger.Subcomponent;

@Subcomponent(modules = SearchModule.class)
public interface SearchComponent {
    void inject(@NonNull SearchFragment searchFragment);
}
