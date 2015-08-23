package com.lemon.lightreader.base;

import com.lemon.library.base.BaseLazyFragment;
import com.umeng.analytics.MobclickAgent;

/**
 * 作者：lemon
 * 日期：2015-08-21
 */
public abstract class BaseFragment extends BaseLazyFragment {

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(TAG);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(TAG);
    }


    public void showError(String msg) {
        toggleShowError(true, msg, null);
    }

    public void showException(String msg) {
        toggleShowError(true, msg, null);
    }

    public void showNetError() {
        toggleNetworkError(true, null);
    }


    public void showLoading(String msg) {
        toggleShowLoading(true, msg);
    }

    public void showLoading() {
        toggleShowLoading(true, null);
    }

    public void hideLoading() {
        toggleShowLoading(false, null);
    }
}
