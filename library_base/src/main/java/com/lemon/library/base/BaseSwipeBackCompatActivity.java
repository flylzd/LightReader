package com.lemon.library.base;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.lemon.library.R;
import com.lemon.library.eventbus.EventCenter;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrPosition;

/**
 * 作者：lemon
 * 日期：2015-08-21
 */
public abstract class BaseSwipeBackCompatActivity extends BaseAppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initSlidrConfig();
    }

    private void initSlidrConfig() {

        // Get the status bar colors to interpolate between
        int primary = getResources().getColor(R.color.primary_dark_material_dark);
        int secondary = getResources().getColor(R.color.transparent);

        SlidrConfig config = new SlidrConfig.Builder()
                .primaryColor(primary)
                .secondaryColor(secondary)
                .scrimColor(Color.BLACK)
                .position(SlidrPosition.LEFT)
                .scrimStartAlpha(0.8f)
                .scrimEndAlpha(0f)
                .velocityThreshold(5f)
                .distanceThreshold(.25f)
                .build();

        // Attach the Slidr Mechanism to this activity
        Slidr.attach(this, config);
    }

}
