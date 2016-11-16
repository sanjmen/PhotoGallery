package com.photo.photogallery.search;

import android.support.annotation.NonNull;

import com.photo.photogallery.BaseView;
import com.photo.photogallery.api.entities.RecentPhotos;

public interface SearchView extends BaseView<SearchPresenter> {

    void showLoadingUi();

    void showErrorUi(@NonNull Throwable error);

    void showContentUi(@NonNull RecentPhotos photos);
}
