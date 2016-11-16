package com.photo.photogallery.photos;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.photo.photogallery.PhotoGalleryApp;
import com.photo.photogallery.R;
import com.photo.photogallery.api.entities.Photo;
import com.photo.photogallery.api.entities.RecentPhotos;
import com.photo.photogallery.network.AppImageLoader;
import com.photo.photogallery.other.SpacesItemDecoration;
import com.photo.photogallery.photodetail.PhotoDetailActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.support.v7.widget.LinearLayoutManager.VERTICAL;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class PhotosFragment extends Fragment implements
        PhotosView, PhotosAdapter.Listener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.photos_loading_ui)
    View loadingUiView;

    @BindView(R.id.photos_loading_error_ui)
    View errorUiView;

    @BindView(R.id.photos_content_ui)
    RecyclerView contentUiRecyclerView;

    PhotosAdapter photosAdapter;

    @Inject
    AppImageLoader imageLoader;

    @Inject
    PhotosPresenter photosPresenter;

    @NonNull
    private Unbinder unbinder;

    private boolean showAsGrid;

    public PhotosFragment() {
    }

    public PhotosFragment newInstance() {
        return new PhotosFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PhotoGalleryApp.get(getContext()).applicationComponent().plus(new PhotosModule(this)).inject(this);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_photos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, view);
        contentUiRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        SpacesItemDecoration decoration = new SpacesItemDecoration(4);
        contentUiRecyclerView.addItemDecoration(decoration);
        photosAdapter = new PhotosAdapter(this, getActivity().getLayoutInflater(), contentUiRecyclerView, imageLoader);
        contentUiRecyclerView.setAdapter(photosAdapter);
        setupSwipeRefreshLayout();
    }

    private void setupSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );
    }

    @Override
    public void onRefresh() {
        photosPresenter.getRecent();
    }

    @Override
    public void setPresenter(PhotosPresenter photosPresenter) {
        this.photosPresenter = photosPresenter;
    }

    @Override
    public void onResume() {
        super.onResume();
        photosPresenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        photosPresenter.unsubscribe();
    }


    @Override
    public void showLoadingUi() {
        errorUiView.setVisibility(GONE);
        if (!swipeRefreshLayout.isRefreshing()) {
            loadingUiView.setVisibility(VISIBLE);
            contentUiRecyclerView.setVisibility(GONE);
        }
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

        if (photosAdapter != null) {
            photosAdapter.setData(recentPhotos.photosCollection().photos());
        }
        swipeRefreshLayout.setRefreshing(false);
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.id.menu_show_as);
        switchLayout(true, menuItem);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void switchLayout(boolean showAsGrid, MenuItem menuItem) {
        if (showAsGrid) {
            photosAdapter.setLayoutMode(PhotosAdapter.GRID_VIEW_MODE);
            menuItem.setIcon(R.drawable.ic_list);
            menuItem.setTitle(R.string.menu_show_as_list);
            contentUiRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
            this.showAsGrid = false;
        } else {
            photosAdapter.setLayoutMode(PhotosAdapter.LIST_VIEW_MODE);
            menuItem.setIcon(R.drawable.ic_grid);
            menuItem.setTitle(R.string.menu_show_as_grid);
            contentUiRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), VERTICAL, false));
            this.showAsGrid = true;
        }
        menuItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_show_as:
                switchLayout(showAsGrid, item);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
