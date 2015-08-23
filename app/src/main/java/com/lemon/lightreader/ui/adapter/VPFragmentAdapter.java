package com.lemon.lightreader.ui.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lemon.lightreader.base.BaseFragment;

import java.util.List;

public class VPFragmentAdapter extends FragmentPagerAdapter {

    private List<BaseFragment> mListFragments = null;

    public VPFragmentAdapter(FragmentManager fm, List<BaseFragment> fragments) {
        super(fm);
        mListFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        if (mListFragments != null && position > -1 && position < mListFragments.size()) {
            return mListFragments.get(position);
        } else {
            return null;
        }
    }

    @Override
    public int getCount() {
        return null != mListFragments ? mListFragments.size() : 0;
    }
}
