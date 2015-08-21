package com.lemon.library.base;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.lemon.library.varyview.VaryViewHelperController;

/**
 * 作者：lemon
 * 日期：2015-08-21
 */
public class BaseLazyFragment extends Fragment {

    protected String TAG = this.getClass().getSimpleName();

    /**
     * Screen information
     */
    protected int mScreenWidth = 0;
    protected int mScreenHeight = 0;
    protected float mScreenDensity = 0.0f;

    protected Context mContext = null;
    /**
     * loading view controller
     */
    private VaryViewHelperController mVaryViewHelperController = null;

}
