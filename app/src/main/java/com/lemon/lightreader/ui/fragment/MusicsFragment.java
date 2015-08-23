package com.lemon.lightreader.ui.fragment;


import android.view.View;

import com.lemon.library.eventbus.EventCenter;
import com.lemon.lightreader.R;
import com.lemon.lightreader.base.BaseFragment;

public class MusicsFragment extends BaseFragment {

    @Override
    protected void onFirstUserVisible() {

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_musics;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected boolean isBindEventBus() {
        return false;
    }
}

