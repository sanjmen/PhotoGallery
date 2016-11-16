package com.photo.photogallery.photodetail;

import android.support.annotation.NonNull;

import com.photo.photogallery.BasePresenter;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class PhotoDetailPresenter implements BasePresenter {

    @NonNull
    private final PhotoDetailModel photoDetailModel;

    @NonNull
    private PhotoDetailView photoDetailView;

    @NonNull
    private CompositeSubscription subscriptions;

    @NonNull
    private String photoId;

    public PhotoDetailPresenter(@NonNull String photoId, @NonNull PhotoDetailModel photoDetailModel, @NonNull PhotoDetailView photoDetailView) {
        this.photoId = photoId;
        this.photoDetailModel = photoDetailModel;
        this.photoDetailView = photoDetailView;

        this.subscriptions = new CompositeSubscription();
        this.photoDetailView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        reloadData();

    }

    @Override
    public void unsubscribe() {
        subscriptions.clear();

    }

    public void reloadData() {

        photoDetailView.showLoadingUi();

        subscriptions.clear();

        Subscription subscription = photoDetailModel
                .getInfo(photoId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        info -> {
                            photoDetailView.showContentUi(info);
                        },
                        error -> {
                            photoDetailView.showErrorUi(error);
                        }
                );

        subscriptions.add(subscription);

    }
}
