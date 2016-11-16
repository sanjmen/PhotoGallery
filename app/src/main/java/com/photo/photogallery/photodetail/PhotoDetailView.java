package com.photo.photogallery.photodetail;

import android.support.annotation.NonNull;

import com.photo.photogallery.BaseView;
import com.photo.photogallery.api.entities.Info;

public interface PhotoDetailView extends BaseView<PhotoDetailPresenter> {

    void showLoadingUi();

    void showErrorUi(@NonNull Throwable error);

    void showContentUi(@NonNull Info info);
}
