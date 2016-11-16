package com.photo.photogallery.search;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.photo.photogallery.BasePresenter;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class SearchPresenter implements BasePresenter {

    @NonNull
    private final SearchModel searchModel;

    @NonNull
    private SearchView searchView;

    @NonNull
    private CompositeSubscription subscriptions;

    private static String currentQuery;

    public SearchPresenter(@NonNull SearchModel searchModel, @NonNull SearchView searchView) {
        this.searchModel = searchModel;
        this.searchView = searchView;

        this.subscriptions = new CompositeSubscription();
        this.searchView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        if (currentQuery == null) {
            getRecent();
        } else {
            search(currentQuery, null);
        }

    }

    @Override
    public void unsubscribe() {
        subscriptions.clear();

    }

    public void search(@NonNull String query, @Nullable Integer page) {
        searchView.showLoadingUi();
        subscriptions.clear();
        currentQuery = query;
        Subscription subscription = searchModel
                .search(currentQuery, null, null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        recentPhotos -> {
                            searchView.showContentUi(recentPhotos);
                        },
                        error -> {
                            searchView.showErrorUi(error);
                        }
                );

        subscriptions.add(subscription);
    }

    private void getRecent() {
        searchView.showLoadingUi();
        subscriptions.clear();
        Subscription subscription = searchModel
                .getRecent(null, null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        recentPhotos -> {
                            searchView.showContentUi(recentPhotos);
                        },
                        error -> {
                            searchView.showErrorUi(error);
                        }
                );

        subscriptions.add(subscription);
    }
}
