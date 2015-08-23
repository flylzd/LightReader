package com.lemon.lightreader.ui.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lemon.lightreader.bean.NavigationEntity;
import com.lemon.lightreader.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NavigationAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater layoutInflater;
    private List<NavigationEntity> list;
    private int changeTextColorIndex=0;
    private int mCheckedListItemColorResIds[] = {
            R.color.navigation_checked_news_text_color,
            R.color.navigation_checked_picture_text_color,
            R.color.navigation_checked_video_text_color,
            R.color.navigation_checked_music_text_color
    };

    public NavigationAdapter(Context context) {
        this.mContext = context;
        layoutInflater = LayoutInflater.from(context);
        list = getNavigationListData(context);
    }

    public void changeTextColor(int position) {
        changeTextColorIndex = position;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

//        View convertView = layoutInflater.inflate(R.layout.list_item_navigation,null);
//        ImageView itemIcon = ButterKnife.findById(convertView,R.id.list_item_navigation_icon);
//        TextView itemName = ButterKnife.findById(convertView,R.id.list_item_navigation_name);
//
//        itemIcon.setImageResource(list.get(position).getIconResId());
//        itemName.setText(list.get(position).getName());

        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_item_navigation, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.itemIcon.setImageResource(list.get(position).getIconResId());
        holder.itemName.setText(list.get(position).getName());

        if (changeTextColorIndex == position) {
            // checked
            holder.itemName.setTextColor(mContext.getResources().getColor(mCheckedListItemColorResIds[position]));
        } else {
            // unchecked
            holder.itemName.setTextColor(mContext.getResources().getColor(android.R.color.black));
        }

        return convertView;
    }

    class ViewHolder {

        @Bind(R.id.list_item_navigation_icon)
        ImageView itemIcon;

        @Bind(R.id.list_item_navigation_name)
        TextView itemName;

        public ViewHolder(View view) {
            ButterKnife.bind(this,view);
        }
    }


    private List<NavigationEntity> getNavigationListData(Context context) {
        List<NavigationEntity> navigationEntities = new ArrayList<>();
        String[] navigationArrays = context.getResources().getStringArray(R.array.navigation_list);
        navigationEntities.add(new NavigationEntity("", navigationArrays[0], R.drawable.ic_nav_news));
        navigationEntities.add(new NavigationEntity("", navigationArrays[1], R.drawable.ic_nav_picture));
        navigationEntities.add(new NavigationEntity("", navigationArrays[2], R.drawable.ic_nav_video));
        navigationEntities.add(new NavigationEntity("", navigationArrays[3], R.drawable.ic_nav_music));
        return navigationEntities;
    }

}
