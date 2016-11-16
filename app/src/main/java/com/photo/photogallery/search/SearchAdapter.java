package com.photo.photogallery.search;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.photo.photogallery.R;
import com.photo.photogallery.api.entities.Photo;
import com.photo.photogallery.network.AppImageLoader;
import com.photo.photogallery.other.Utils;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;

class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    @NonNull
    private final Listener listener;

    @NonNull
    private final LayoutInflater layoutInflater;

    @NonNull
    private final AppImageLoader imageLoader;

    @NonNull
    private SearchFragment searchFragment;

    @NonNull
    private RecyclerView recyclerView;

    private float density;

    @NonNull
    private List<Photo> photos = emptyList();

    SearchAdapter(@NonNull Fragment fragment, @NonNull LayoutInflater layoutInflater, @NonNull RecyclerView recyclerView, @NonNull AppImageLoader imageLoader) {
        this.searchFragment = (SearchFragment) fragment;
        this.listener = (Listener) fragment;
        this.layoutInflater = layoutInflater;
        this.recyclerView = recyclerView;
        this.imageLoader = imageLoader;
        this.density = searchFragment.getResources().getDisplayMetrics().density;
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public void setData(@NonNull List<Photo> photos) {
        this.photos = unmodifiableList(photos);
        notifyDataSetChanged();
    }

    @Override
    @NonNull
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SearchViewHolder(layoutInflater.inflate(R.layout.photo_grid_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder viewHolder, int position) {
        viewHolder.bind(photos.get(position));
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageView;
        private final CardView cardView;

        SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view_photo);
            imageView = (ImageView) itemView.findViewById(R.id.grid_photo_image_view);
        }

        private void bind(@NonNull Photo photo) {
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.OnPhotoSelected(photo);
                }
            });
            int imageWidth = (int) (recyclerView.getWidth() / density / 1.5);
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(imageWidth, imageWidth);
            imageView.setLayoutParams(layoutParams);
            imageLoader.downloadInto(Utils.getImageUrl(photo, imageWidth), imageView);
        }
    }

    interface Listener {
        void OnPhotoSelected(@NonNull Photo photo);
    }
}
