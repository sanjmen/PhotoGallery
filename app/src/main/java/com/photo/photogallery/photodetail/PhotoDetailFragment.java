package com.photo.photogallery.photodetail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.photo.photogallery.PhotoGalleryApp;
import com.photo.photogallery.R;
import com.photo.photogallery.api.entities.Info;
import com.photo.photogallery.api.entities.Photo;
import com.photo.photogallery.network.AppImageLoader;
import com.photo.photogallery.other.Utils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class PhotoDetailFragment extends Fragment implements PhotoDetailView, View.OnClickListener {

    @NonNull
    private static final String ARGUMENT_PHOTO = "PHOTO";

    private static Photo mPhoto;
    private static Info mInfo;

    @BindView(R.id.photo_loading_ui)
    View loadingUiView;

    @BindView(R.id.photo_loading_error_ui)
    View errorUiView;

    @BindView(R.id.photo_content_ui)
    RelativeLayout contentUiView;

    @BindView(R.id.photo_image_view)
    ImageView photoImageView;

    @BindView(R.id.photo_icon_image_view)
    CircleImageView ownerImageView;

    @BindView(R.id.photo_owner_name_text_view)
    TextView ownerNameTextView;

    @BindView(R.id.photo_title_text_view)
    TextView titleTextView;

    @BindView(R.id.photo_date_text_view)
    TextView dateTextView;

    @BindView(R.id.photo_description_text_view)
    TextView descriptionTextView;

    @BindView(R.id.photo_location_image_view)
    ImageView locationIconImageView;

    @BindView(R.id.photo_location_text_view)
    TextView locationTextView;

    @Inject
    AppImageLoader imageLoader;

    @Inject
    PhotoDetailPresenter photoDetailPresenter;

    @NonNull
    private Unbinder unbinder;

    public PhotoDetailFragment() {
    }

    public static PhotoDetailFragment newInstance(@NonNull Photo photo) {
        Bundle arguments = new Bundle();
        arguments.putParcelable(ARGUMENT_PHOTO, photo);
        PhotoDetailFragment fragment = new PhotoDetailFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPhoto = getArguments().getParcelable(ARGUMENT_PHOTO);

        PhotoGalleryApp.get(getContext()).applicationComponent()
                .plus(new PhotoDetailModule(mPhoto.id(), this))
                .inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_photo_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void setPresenter(PhotoDetailPresenter photoDetailPresenter) {
        this.photoDetailPresenter = photoDetailPresenter;
    }

    @Override
    public void onResume() {
        super.onResume();
        photoDetailPresenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        photoDetailPresenter.unsubscribe();
    }


    @Override
    public void showLoadingUi() {
        errorUiView.setVisibility(GONE);
        loadingUiView.setVisibility(VISIBLE);
        contentUiView.setVisibility(GONE);
    }

    @Override
    public void showErrorUi(@NonNull Throwable error) {
        loadingUiView.setVisibility(GONE);
        errorUiView.setVisibility(VISIBLE);
        contentUiView.setVisibility(GONE);
    }

    @Override
    public void showContentUi(@NonNull Info info) {
        loadingUiView.setVisibility(GONE);
        errorUiView.setVisibility(GONE);
        contentUiView.setVisibility(VISIBLE);

        mInfo = info;

        DisplayMetrics displayMetrics = Utils.getDisplayMetrics(getActivity());
        imageLoader.downloadInto(Utils.getImageUrl(mPhoto, displayMetrics.heightPixels), photoImageView);
        imageLoader.downloadInto(Utils.getBuddyIconUrl(mPhoto), ownerImageView);
        ownerNameTextView.setText(mPhoto.ownerName());
        titleTextView.setText(Utils.getShortTitle(mPhoto.title()));
        dateTextView.setText(Utils.getDateTaken(mPhoto.dateTaken()));

        if (info.photoInfo().description().content() != null) {
            descriptionTextView.setText(info.photoInfo().description().content());
        }

        if (info.photoInfo().location() != null) {
            locationIconImageView.setVisibility(VISIBLE);
            try {
                locationTextView.setText(info.photoInfo().location().locality().content());
            } catch (NullPointerException e) {
                locationTextView.setVisibility(GONE);
            }
        }

        photoImageView.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        FragmentManager fragmentManager = getFragmentManager();
        DialogFragment newFragment = PhotoFullDialog.newInstance(mPhoto, mInfo);
        newFragment.show(fragmentManager, "photo_full_dialog");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onDestroyView() {
        if (unbinder != null) {
            unbinder.unbind();
        }
        super.onDestroyView();
    }
}

