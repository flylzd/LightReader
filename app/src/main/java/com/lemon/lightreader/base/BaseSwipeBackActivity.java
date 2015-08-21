package com.lemon.lightreader.base;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.lemon.library.base.BaseSwipeBackCompatActivity;
import com.lemon.lightreader.R;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;

/**
 * 作者：lemon
 * 日期：2015-08-21
 */
public abstract class BaseSwipeBackActivity extends BaseSwipeBackCompatActivity {


    protected Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isApplyKitKatTranslucency()) {
            setSystemBarTintDrawable(getResources().getDrawable(R.drawable.primary_dark));
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        mToolbar = ButterKnife.findById(this, R.id.common_toolbar);
        if (null != mToolbar) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
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
        toggleShowLoading(true, null);
    }

    public void hideLoading() {
        toggleShowLoading(false, null);
    }

    protected boolean isApplyKitKatTranslucency() {
        return true;
    }

}
