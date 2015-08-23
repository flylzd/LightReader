package com.lemon.lightreader.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lemon.lightreader.bean.NavigationEntity;
import com.lemon.library.eventbus.EventCenter;
import com.lemon.library.widgets.XViewPager;
import com.lemon.lightreader.R;
import com.lemon.lightreader.base.BaseActivity;
import com.lemon.lightreader.base.BaseFragment;
import com.lemon.lightreader.ui.adapter.NavigationAdapter;
import com.lemon.lightreader.ui.adapter.VPFragmentAdapter;
import com.lemon.lightreader.ui.fragment.ImagesContainerFragment;
import com.lemon.lightreader.ui.fragment.MusicsFragment;
import com.lemon.lightreader.ui.fragment.NewsContainerFragment;
import com.lemon.lightreader.ui.fragment.VideosContainerFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;


public class MainActivity extends BaseActivity {

    @Bind(R.id.main_drawer)
    DrawerLayout drawerLayout;

    @Bind(R.id.main_container)
    XViewPager viewPager;

    @Bind(R.id.main_navigation_list)
    ListView navListView;

    private NavigationAdapter navigationAdapter;
    private int currentMenuCheckedPos = 0;

    private ActionBarDrawerToggle actionBarDrawerToggle = null;

    @Override
    protected void initViewsAndEvents() {

        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,getToolbar(),R.string.drawer_open,R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                setTitle(getString(R.string.app_name));
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (null != navigationAdapter) {
                    setTitle(((NavigationEntity)navigationAdapter.getItem(currentMenuCheckedPos)).getName());
                }
            }
        };
        actionBarDrawerToggle.syncState();
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        navigationAdapter = new NavigationAdapter(this);
        navListView.setAdapter(navigationAdapter);
        navListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                currentMenuCheckedPos = position;
                navigationAdapter.changeTextColor(position);
                drawerLayout.closeDrawer(Gravity.LEFT);

                viewPager.setCurrentItem(currentMenuCheckedPos,false);
            }
        });

        List<BaseFragment> fragments = getPagerFragments();
        if (null != fragments && !fragments.isEmpty()) {
            viewPager.setEnableScroll(false);
            viewPager.setOffscreenPageLimit(fragments.size());
            viewPager.setAdapter(new VPFragmentAdapter(getSupportFragmentManager(), fragments));
//            fragments.get(0)
        }
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
    }

    public List<BaseFragment> getPagerFragments() {
        List<BaseFragment> fragments = new ArrayList<>();
        fragments.add(new NewsContainerFragment());
        fragments.add(new ImagesContainerFragment());
        fragments.add(new VideosContainerFragment());
        fragments.add(new MusicsFragment());

        return fragments;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
    }

    @Override
    protected boolean isBindEventBus() {
        return false;
    }

    @Override
    protected void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return true;
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
