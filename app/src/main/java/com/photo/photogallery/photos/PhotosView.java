package com.photo.photogallery.photos;

import android.support.annotation.NonNull;

import com.photo.photogallery.BaseView;
import com.photo.photogallery.api.entities.RecentPhotos;

public interface PhotosView extends BaseView<PhotosPresenter> {

    void showLoadingUi();

    void showErrorUi(@NonNull Throwable error);

    void showContentUi(@NonNull RecentPhotos photos);
}
