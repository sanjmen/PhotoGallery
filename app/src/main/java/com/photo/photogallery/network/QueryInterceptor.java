package com.photo.photogallery.network;

import com.photo.photogallery.Config;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Add default query parameters to every request we made to flickr
 *
 * &format=json&nojsoncallback=1&extras=owner_name,geo&api_key=
 *
 */
public class QueryInterceptor implements Interceptor {
    private static final String PARAM_API_KEY = "api_key";
    private static final String PARAM_FORMAT = "format";
    private static final String PARAM_NO_JSON_CALLBACK = "nojsoncallback";
    private static final String PARAM_EXTRAS = "extras";

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request original = chain.request();
        HttpUrl originalHttpUrl = original.url();

        HttpUrl url = originalHttpUrl.newBuilder()
                .addQueryParameter(PARAM_API_KEY, Config.API_PARAM_API_KEY)
                .addQueryParameter(PARAM_FORMAT, Config.API_PARAM_RESPONSE_FORMAT)
                .addQueryParameter(PARAM_NO_JSON_CALLBACK, Config.API_PARAM_NO_JSON_CALLBACK)
                .addQueryParameter(PARAM_EXTRAS, Config.API_PARAM_EXTRAS)
                .build();

        // Request customization: add request headers
        Request.Builder requestBuilder = original.newBuilder()
                .url(url);

        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}
