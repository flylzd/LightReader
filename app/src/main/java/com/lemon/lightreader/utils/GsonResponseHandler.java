package com.lemon.lightreader.utils;


import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

public class GsonResponseHandler extends AsyncHttpResponseHandler {


    private Gson gson = new Gson();
    private Class<?> clazz;

    public GsonResponseHandler(Class<?> clazz ) {
        this.clazz = clazz;
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

        gson.fromJson(new String(responseBody),clazz);
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

    }
}
