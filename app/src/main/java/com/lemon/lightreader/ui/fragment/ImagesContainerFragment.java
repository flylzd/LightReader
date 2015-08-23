package com.lemon.lightreader.ui.fragment;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.lemon.lightreader.bean.BaseEntity;
import com.lemon.library.eventbus.EventCenter;
import com.lemon.library.widgets.XViewPager;
import com.lemon.lightreader.R;
import com.lemon.lightreader.base.BaseFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class ImagesContainerFragment extends BaseFragment {

    @Bind(R.id.fragment_images_pager)
    XViewPager viewPager;

    @Bind(R.id.fragment_images_tab_smart)
    SmartTabLayout smartTabLayout;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_images;
    }

    @Override
    protected void onFirstUserVisible() {

        final List<BaseEntity> categoryList = getCommonCategoryList(getActivity());
        if (null != categoryList && !categoryList.isEmpty()) {
            FragmentPagerItems.Creator creator = FragmentPagerItems.with(getActivity());
            for (BaseEntity entity : categoryList) {
                creator.add(entity.getName(), ImagesListFragment.class);
            }
            viewPager.setOffscreenPageLimit(categoryList.size());
            final FragmentPagerItemAdapter fragmentPagerItemAdapter = new FragmentPagerItemAdapter(getSupportFragmentManager(), creator.create());
            viewPager.setAdapter(fragmentPagerItemAdapter);
            smartTabLayout.setViewPager(viewPager);
            smartTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    ImagesListFragment fragment = (ImagesListFragment)fragmentPagerItemAdapter.getItem(position);
                    fragment.onPageSelected(position, categoryList.get(position).getId());
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }
    }

    public List<BaseEntity> getCommonCategoryList(Context context) {
        List<BaseEntity> resultData = new ArrayList<>();
        String[] imagesCategoryArray = context.getResources().getStringArray(R.array.images_category_list);
        int size = imagesCategoryArray.length;
        for (int i = 0; i < size; i++) {
            resultData.add(new BaseEntity(imagesCategoryArray[i], imagesCategoryArray[i]));
        }
//        resultData.add(new BaseEntity(imagesCategoryArray[0], imagesCategoryArray[0]));
//        resultData.add(new BaseEntity(imagesCategoryArray[1], imagesCategoryArray[1]));
//        resultData.add(new BaseEntity(imagesCategoryArray[2], imagesCategoryArray[2]));
//        resultData.add(new BaseEntity(imagesCategoryArray[3], imagesCategoryArray[3]));
//        resultData.add(new BaseEntity(imagesCategoryArray[4], imagesCategoryArray[4]));
//        resultData.add(new BaseEntity(imagesCategoryArray[5], imagesCategoryArray[5]));
        return resultData;
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
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected boolean isBindEventBus() {
        return false;
    }
}

