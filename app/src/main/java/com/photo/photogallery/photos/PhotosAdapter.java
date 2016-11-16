package com.photo.photogallery.photos;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.photo.photogallery.R;
import com.photo.photogallery.api.entities.Photo;
import com.photo.photogallery.network.AppImageLoader;
import com.photo.photogallery.other.Utils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;

class PhotosAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // A grid item view type.
    static final int GRID_VIEW_MODE = 0;

    // A list item view type.
    static final int LIST_VIEW_MODE = 1;

    private static boolean isGridMode;

    @NonNull
    private final Listener listener;

    @NonNull
    private final LayoutInflater layoutInflater;

    @NonNull
    private final AppImageLoader imageLoader;

    @NonNull
    private PhotosFragment photosFragment;

    @NonNull
    private RecyclerView recyclerView;

    private float density;

    @NonNull
    private List<Photo> photos = emptyList();

    PhotosAdapter(@NonNull Fragment fragment, @NonNull LayoutInflater layoutInflater, @NonNull RecyclerView recyclerView, @NonNull AppImageLoader imageLoader) {
        this.photosFragment = (PhotosFragment) fragment;
        this.listener = (Listener) fragment;
        this.layoutInflater = layoutInflater;
        this.recyclerView = recyclerView;
        this.imageLoader = imageLoader;
        this.density = photosFragment.getResources().getDisplayMetrics().density;
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    public void setData(@NonNull List<Photo> photos) {
        this.photos = unmodifiableList(Utils.filterPublicPhotos(photos));
        notifyDataSetChanged();
    }

    public void setLayoutMode(int layoutMode) {
        isGridMode = (layoutMode == GRID_VIEW_MODE);
    }

    @Override
    public int getItemViewType(int position) {
        return (isGridMode) ? GRID_VIEW_MODE : LIST_VIEW_MODE;
    }

    @Override
    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case GRID_VIEW_MODE:
                return new PhotoGridViewHolder(layoutInflater.inflate(R.layout.photo_grid_item, parent, false));
            case LIST_VIEW_MODE:
                // fall through
            default:
                return new PhotoListViewHolder(layoutInflater.inflate(R.layout.photo_list_item, parent, false));
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case GRID_VIEW_MODE:
                PhotoGridViewHolder photoGridViewHolder = (PhotoGridViewHolder) viewHolder;
                photoGridViewHolder.bind(photos.get(position));
                break;
            case LIST_VIEW_MODE:
                // fall through
            default:
                PhotoListViewHolder photoListViewHolder = (PhotoListViewHolder) viewHolder;
                photoListViewHolder.bind(photos.get(position));
                break;
        }
    }

    private class PhotoGridViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageView;
        private final CardView cardView;

        PhotoGridViewHolder(@NonNull View itemView) {
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

    private class PhotoListViewHolder extends RecyclerView.ViewHolder {

        private final CardView cardView;
        private final ImageView mainImageView;
        private final CircleImageView ownerImageView;
        private final TextView ownerNameTextView;
        private final TextView titleTextView;
        private final TextView dateTextView;

        PhotoListViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.card_view_photo);
            mainImageView = (ImageView) itemView.findViewById(R.id.card_photo_image_view);
            ownerImageView = (CircleImageView) itemView.findViewById(R.id.card_photo_icon_image_view);
            ownerNameTextView = (TextView) itemView.findViewById(R.id.card_photo_owner_name_text_view);
            titleTextView = (TextView) itemView.findViewById(R.id.card_photo_title_text_view);
            dateTextView = (TextView) itemView.findViewById(R.id.card_photo_date_text_view);
        }

        private void bind(@NonNull Photo photo) {
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.OnPhotoSelected(photo);
                }
            });

            int imageWidth = (int) (recyclerView.getWidth() / density);
            imageLoader.downloadInto(Utils.getImageUrl(photo, imageWidth), mainImageView);
            imageLoader.downloadInto(Utils.getBuddyIconUrl(photo), ownerImageView);
            ownerNameTextView.setText(photo.ownerName());
            titleTextView.setText(Utils.getShortTitle(photo.title()));
            dateTextView.setText(Utils.getDateTaken(photo.dateTaken()));
        }
    }

    interface Listener {
        void OnPhotoSelected(@NonNull Photo photo);
    }
}
