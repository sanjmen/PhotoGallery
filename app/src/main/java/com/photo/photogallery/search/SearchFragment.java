package com.photo.photogallery.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.photo.photogallery.PhotoGalleryApp;
import com.photo.photogallery.R;
import com.photo.photogallery.api.entities.Photo;
import com.photo.photogallery.api.entities.RecentPhotos;
import com.photo.photogallery.network.AppImageLoader;
import com.photo.photogallery.other.CustomSearchView;
import com.photo.photogallery.other.SpacesItemDecoration;
import com.photo.photogallery.photodetail.PhotoDetailActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscription;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class SearchFragment extends Fragment implements
        SearchView,
        CustomSearchView.OnQueryTextListener,
        CustomSearchView.SearchViewListener,
        SearchAdapter.Listener {

    @BindView(R.id.photos_loading_ui)
    View loadingUiView;

    @BindView(R.id.photos_loading_error_try_again_button)
    Button errorUiView;

    @BindView(R.id.photos_content_ui)
    RecyclerView contentUiRecyclerView;

    @BindView(R.id.search_view)
    CustomSearchView searchView;

    SearchAdapter searchAdapter;

    @Inject
    AppImageLoader imageLoader;

    @Inject
    SearchPresenter searchPresenter;

    @NonNull
    private Unbinder unbinder;

    private Subscription subscription;

    public static String CURRENT_QUERY = "current_query";
    private static String currentQuery;

    public SearchFragment() {
    }

    public static SearchFragment newInstance() {

        return new SearchFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PhotoGalleryApp.get(getContext()).applicationComponent().plus(new SearchModule(this)).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, view);
        contentUiRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        SpacesItemDecoration decoration = new SpacesItemDecoration(4);
        contentUiRecyclerView.addItemDecoration(decoration);
        searchAdapter = new SearchAdapter(this, getActivity().getLayoutInflater(), contentUiRecyclerView, imageLoader);
        contentUiRecyclerView.setAdapter(searchAdapter);
        setupSearchView();
    }

    @Override
    public void setPresenter(SearchPresenter searchPresenter) {
        this.searchPresenter = searchPresenter;
    }

    @Override
    public void onResume() {
        super.onResume();
        searchPresenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        searchPresenter.unsubscribe();
    }


    @Override
    public void showLoadingUi() {
        errorUiView.setVisibility(GONE);
        loadingUiView.setVisibility(VISIBLE);
        contentUiRecyclerView.setVisibility(GONE);
    }

    @Override
    public void showErrorUi(@NonNull Throwable error) {
        loadingUiView.setVisibility(GONE);
        errorUiView.setVisibility(VISIBLE);
        contentUiRecyclerView.setVisibility(GONE);
    }

    @Override
    public void showContentUi(@NonNull RecentPhotos recentPhotos) {
        loadingUiView.setVisibility(GONE);
        errorUiView.setVisibility(GONE);
        contentUiRecyclerView.setVisibility(VISIBLE);

        if (searchAdapter != null) {
            searchAdapter.setData(recentPhotos.photosCollection().photos());
        }
    }

    @Override
    public void OnPhotoSelected(@NonNull Photo photo) {
        Intent photoDetailIntent = PhotoDetailActivity.getIntent(getContext());
        photoDetailIntent.putExtra(PhotoDetailActivity.ARGUMENT_PHOTO, photo);
        startActivity(photoDetailIntent);
    }

    @Override
    public void onDestroyView() {
        if (unbinder != null) {
            unbinder.unbind();
        }
        super.onDestroyView();
    }

    private void setupSearchView() {
        searchView.setCursorDrawable(R.drawable.custom_cursor);
        searchView.setOnQueryTextListener(this);
        searchView.setOnSearchViewListener(this);
        subscription = searchView.getSubscription();
        searchView.showSearch(false);
        searchView.showKeyboard(getActivity());
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        doSearch(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        doSearch(newText);
        return true;
    }

    @Override
    public void onSearchViewShown() {
    }

    @Override
    public void onSearchViewClosed() {
    }

    @Override
    public void onBackButtonPressed() {
        searchView.hideKeyboard(getActivity());
        getActivity().onBackPressed();
    }

    private void doSearch(String query) {
        currentQuery = query;
        searchPresenter.search(query, null);
    }
}
