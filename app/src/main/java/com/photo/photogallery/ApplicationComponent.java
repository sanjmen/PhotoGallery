package com.photo.photogallery;


import android.support.annotation.NonNull;

import com.photo.photogallery.api.ApiModule;
import com.photo.photogallery.network.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        NetworkModule.class,
        ApiModule.class
})

public interface ApplicationComponent {

    void inject(@NonNull MainActivity mainActivity);
}
