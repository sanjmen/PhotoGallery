package com.photo.photogallery.photodetail;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
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
import butterknife.OnClick;
import butterknife.Unbinder;

public class PhotoFullDialog extends DialogFragment {

    @NonNull
    private static final String ARGUMENT_PHOTO = "PHOTO";

    @NonNull
    private static final String ARGUMENT_INFO = "INFO";

    private static Photo photo;
    private static Info info;

    @BindView(R.id.photo_full_image_view)
    ImageView photoFullImageView;

    @BindView(R.id.photo_full_owner_name_text_view)
    TextView ownerNameTextView;

    @BindView(R.id.photo_full_title_text_view)
    TextView titleTextView;

    @BindView(R.id.photo_full_comments_text_view)
    TextView commentsTextView;

    @BindView(R.id.photo_full_comment_image_view)
    ImageView commentImageView;

    @BindView(R.id.photo_full_comment_text_view)
    TextView commentTextView;

    @NonNull
    private Unbinder unbinder;

    @Inject
    AppImageLoader imageLoader;

    public PhotoFullDialog() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PhotoGalleryApp.get(getContext()).applicationComponent().inject(this);
    }

    public static PhotoFullDialog newInstance(@NonNull Photo photo, @NonNull Info info) {
        Bundle arguments = new Bundle();
        arguments.putParcelable(ARGUMENT_PHOTO, photo);
        arguments.putParcelable(ARGUMENT_INFO, info);
        PhotoFullDialog photoFullDialog = new PhotoFullDialog();
        photoFullDialog.setArguments(arguments);

        return photoFullDialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.photo_full_screen, container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);

        photo = getArguments().getParcelable(ARGUMENT_PHOTO);
        info = getArguments().getParcelable(ARGUMENT_INFO);

        DisplayMetrics displayMetrics = Utils.getDisplayMetrics(getActivity());

        imageLoader.downloadIntoAndResize(Utils.getImageUrl(
                photo, displayMetrics.widthPixels), photoFullImageView,
                0, displayMetrics.heightPixels);

        ownerNameTextView.setText(photo.ownerName());
        titleTextView.setText(photo.title());

        if (info.photoInfo().comments().content() != null) {
            String nComments = String.format("%s %s", info.photoInfo().comments().content(), getString(R.string.comments));
            commentsTextView.setText(nComments);
            commentTextView.setVisibility(View.VISIBLE);
            commentImageView.setVisibility(View.VISIBLE);
        }

    }

    @Override

    public void onResume() {
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        super.onResume();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        unbinder.unbind();
        super.onDismiss(dialog);
    }

    @OnClick(R.id.photo_full_close_button)
    void onCloseButtonClick() {
        Dialog dialog = getDialog();
        dialog.dismiss();
    }
}
