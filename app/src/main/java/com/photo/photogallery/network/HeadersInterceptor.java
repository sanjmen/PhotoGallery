package com.photo.photogallery.network;

import com.photo.photogallery.Config;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeadersInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        Request request = original.newBuilder()
                .header("Accept", "application/json")
                .header("User-Agent", Config.USER_AGENT)
                .method(original.method(), original.body())
                .build();

        return chain.proceed(request);
    }
}
