package com.photo.photogallery.search;

import android.support.annotation.NonNull;

import com.photo.photogallery.api.FlickrRestApi;

import dagger.Module;
import dagger.Provides;

@Module
public class SearchModule {

    private SearchView searchView;

    public SearchModule(SearchView searchView) {
        this.searchView = searchView;
    }

    @Provides
    @NonNull
    public SearchView providesSearchView() {
        return searchView;
    }

    @Provides
    @NonNull
    public SearchModel provideSearchModel(@NonNull FlickrRestApi flickrRestApi) {
        return new SearchModel(flickrRestApi);
    }

    @Provides
    @NonNull
    public SearchPresenter provideSearchPresenter(@NonNull SearchModel searchModel, @NonNull SearchView searchView) {
        return new SearchPresenter(searchModel, searchView);
    }
}
