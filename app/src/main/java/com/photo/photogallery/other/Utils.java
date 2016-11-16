package com.photo.photogallery.other;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.photo.photogallery.api.entities.Photo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import timber.log.Timber;

public class Utils {

    public static String getBuddyIconUrl(Photo photo) {
        String buddyIconUrl = "https://www.flickr.com/images/buddyicon.gif";

        if (photo.iconServer() > 0) {

            buddyIconUrl = String.format(
                    "http://farm%s.staticflickr.com/%s/buddyicons/%s.jpg",
                    photo.iconFarm(), photo.iconServer(), photo.owner()
            );
        }

        return buddyIconUrl;
    }

    public static String getDateTaken(String dateString) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        try {
            Date newDate = format.parse(dateString);
            format = new SimpleDateFormat("d MMM", Locale.getDefault());
            return format.format(newDate);

        } catch (ParseException e) {
            Timber.e(e, "date parser error");
            return "";
        }
    }

    public static String getShortTitle(String title) {
        if (title.length() > 30) {
            return title.substring(0, 30).concat("...");
        }
        return title;
    }

    public static String getImageUrl(Photo photo, int size) {
        String imageUrl = "https://farm%s.staticflickr.com/%s/%s_%s_%s.jpg";
        if (size <= 75) {
            return String.format(imageUrl, photo.farm(), photo.server(), photo.id(), photo.secret(), "s");
        } else if (size > 75 && size <= 150) {
            return String.format(imageUrl, photo.farm(), photo.server(), photo.id(), photo.secret(), "q");
        } else if (size > 150 && size <= 240) {
            return String.format(imageUrl, photo.farm(), photo.server(), photo.id(), photo.secret(), "m");
        } else if (size > 240 && size <= 320) {
            return String.format(imageUrl, photo.farm(), photo.server(), photo.id(), photo.secret(), "n");
        } else if (size > 320 && size <= 640) {
            return String.format(imageUrl, photo.farm(), photo.server(), photo.id(), photo.secret(), "z");
        } else if (size > 640 && size <= 800) {
            return String.format(imageUrl, photo.farm(), photo.server(), photo.id(), photo.secret(), "c");
        } else if (size > 800 && size <= 1024) {
            return String.format(imageUrl, photo.farm(), photo.server(), photo.id(), photo.secret(), "b");
        } else if (size > 1024 && size <= 1600) {
            return String.format(imageUrl, photo.farm(), photo.server(), photo.id(), photo.secret(), "h");
        }

        return String.format(imageUrl, photo.farm(), photo.server(), photo.id(), photo.secret(), "n");
    }

    public static List<Photo> filterPublicPhotos(List<Photo> photos) {
        List<Photo> photoList = new ArrayList<>();
        for (Photo photo : photos) {
            if (photo.isPublic() == 1) {
                photoList.add(photo);
            }
        }
        return photoList;
    }

    public static DisplayMetrics getDisplayMetrics(Activity activity) {
        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        display.getMetrics(displaymetrics);

        return displaymetrics;
    }
}
