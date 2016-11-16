package com.photo.photogallery;


public class Config {

    public static final String API_BASE_URL = "https://api.flickr.com/services/rest/";
    public static final String USER_AGENT = "PhotoGallery/" + BuildConfig.VERSION_NAME;
    public static final String API_PARAM_API_KEY = "YOUR_API_KEY";
    public static final String API_PARAM_RESPONSE_FORMAT = "json";
    public static final String API_PARAM_NO_JSON_CALLBACK = "1";
    public static final String API_PARAM_EXTRAS = "owner_name,icon_server,date_taken";
    public static final int RESULTS_PER_PAGE = 25;

    public static final int API_CONNECTION_TIMEOUT = 60; // seconds
    public static final int API_WRITE_TIMEOUT = 60; // seconds
    public static final int API_READ_TIMEOUT = 60;  // seconds
}
