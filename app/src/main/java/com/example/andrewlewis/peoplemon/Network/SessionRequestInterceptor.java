package com.example.andrewlewis.peoplemon.Network;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by andrewlewis on 11/6/16.
 */

public class SessionRequestInterceptor implements Interceptor {


    //Calling user store to get authorization token. This is so that when we start our app the it the server will recognize us as a valid user.

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();

        if (UserStore.getInstance().getToken() != null) {

            Request.Builder builder = request.newBuilder();
            builder.header("Authorization", "Bearer " + UserStore.getInstance().getToken());
            request = builder.build();
        }

        return chain.proceed(request);

    }
}
