package com.photo.photogallery.photos;

import android.support.annotation.NonNull;

import com.photo.photogallery.BasePresenter;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class PhotosPresenter implements BasePresenter {

    @NonNull
    private final PhotosModel photosModel;

    @NonNull
    private PhotosView photosView;

    @NonNull
    private CompositeSubscription subscriptions;

    public PhotosPresenter(@NonNull PhotosModel photosModel, @NonNull PhotosView photosView) {
        this.photosModel = photosModel;
        this.photosView = photosView;

        this.subscriptions = new CompositeSubscription();
        this.photosView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        getRecent();

    }

    @Override
    public void unsubscribe() {
        subscriptions.clear();

    }

    public void getRecent() {

        photosView.showLoadingUi();

        subscriptions.clear();

        Subscription subscription = photosModel
                .getRecent(null, null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        recentPhotos -> {
                            photosView.showContentUi(recentPhotos);
                        },
                        error -> {
                            photosView.showErrorUi(error);
                        }
                );

        subscriptions.add(subscription);

    }
}
