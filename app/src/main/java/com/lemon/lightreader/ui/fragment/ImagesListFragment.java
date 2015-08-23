package com.lemon.lightreader.ui.fragment;


import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.lemon.library.eventbus.EventCenter;
import com.lemon.library.netstatus.NetUtils;
import com.lemon.library.recyclerview.XRecyclerView;
import com.lemon.lightreader.R;
import com.lemon.lightreader.api.remote.ImagesApi;
import com.lemon.lightreader.base.BaseFragment;
import com.lemon.lightreader.bean.ImagesListEntity;
import com.lemon.lightreader.bean.ResponseImagesListEntity;
import com.lemon.lightreader.ui.adapter.ImageListAdapter;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.orhanobut.logger.Logger;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;


public class ImagesListFragment extends BaseFragment implements XRecyclerView.PullLoadMoreListener {

    @Bind(R.id.xRecyclerView)
    XRecyclerView xRecyclerView;

    private ImageListAdapter adapter;
    private List<ImagesListEntity> imagesList = new ArrayList<ImagesListEntity>();

    private static String currentImagesCategory = null;
    private int currentPage = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentImagesCategory = getResources().getStringArray(R.array.images_category_list)[0];
    }

    public void onPageSelected(int position, String keywords) {
        currentImagesCategory = keywords;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_images_list;
    }


    @Override
    protected void initViewsAndEvents() {

        adapter = new ImageListAdapter(imagesList);
//        xRecyclerView.setStaggeredGridLayoutManager();
        xRecyclerView.setLinearLayoutManager();
        xRecyclerView.setAdapter(adapter);
        xRecyclerView.setPullLoadMoreListener(this);

//        xRecyclerView.set
    }

    @Override
    protected void onFirstUserVisible() {
        currentPage = 0;
        if (!NetUtils.isNetworkConnected(mContext)) {
            showNetError();
        }
//        showLoading();
        ImagesApi.getImageList(currentImagesCategory, currentPage, new GsonResponseHandler());

    }

    @Override
    public void onRefresh() {
        currentPage = 0;
        ImagesApi.getImageList(currentImagesCategory, currentPage, new GsonResponseHandler());
    }

    @Override
    public void onLoadMore() {
        currentPage++;
        ImagesApi.getImageList(currentImagesCategory, currentPage, new GsonResponseHandler());
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void onUserVisible() {
    }

    @Override
    protected void onUserInvisible() {
    }


    @Override
    protected void onEventComming(EventCenter eventCenter) {
    }

    @Override
    protected boolean isBindEventBus() {
        return false;
    }

    private class GsonResponseHandler extends AsyncHttpResponseHandler {

        @Override
        public void onStart() {
            super.onStart();

        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            Logger.d(new String(responseBody));
            Gson gson = new Gson();
            ResponseImagesListEntity responseImagesListEntity = gson.fromJson(new String(responseBody), ResponseImagesListEntity.class);

            if (currentPage == 0){
                adapter.add(responseImagesListEntity.getImgs());
            } else {
                adapter.addMore(responseImagesListEntity.getImgs());
            }

            Logger.d(String.valueOf(responseImagesListEntity.getImgs().size()));
//            hideLoading();
            xRecyclerView.setCompleted();
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            showNetError();
        }
    }


}
