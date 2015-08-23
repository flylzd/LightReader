package com.lemon.lightreader.api.remote;


import com.lemon.lightreader.api.ApiHttpClient;
import com.lemon.lightreader.api.UriHelper;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.orhanobut.logger.Logger;

public class ImagesApi {

    public static void getImageList(String category, int page,
                                    AsyncHttpResponseHandler handler) {

        String url = UriHelper.getInstance().getImagesListUrl(category, page);
        Logger.d(url);
        ApiHttpClient.getDirect(url, handler);
    }
}
