package com.lemon.lightreader.ui.adapter;


import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.lemon.library.widgets.ScaleImageView;
import com.lemon.lightreader.R;
import com.lemon.lightreader.bean.ImagesListEntity;
import com.lemon.lightreader.utils.StringUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ViewHolder> {

    private List<ImagesListEntity> list;

    public ImageListAdapter(List<ImagesListEntity> list) {
        this.list = list;
    }

    public void add(List<ImagesListEntity> list) {
        if (null !=list && list.size() != 0){
            this.list.clear();
            this.list.addAll(list);
            notifyDataSetChanged();
        }
    }

    public  void addMore(List<ImagesListEntity> list) {
        if (null !=list && list.size() != 0){
            this.list.addAll(list);
            notifyDataSetChanged();
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_images_list,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        ImagesListEntity itemData = list.get(position);
        int width = itemData.getThumbnailWidth();
        int height = itemData.getThumbnailHeight();
        String imageUrl = itemData.getThumbnailUrl();

        if (!StringUtils.isEmpty(imageUrl)) {
            ImageLoader.getInstance().displayImage(imageUrl, holder.itemImage);
        }
        holder.itemImage.setImageWidth(width);
        holder.itemImage.setImageHeight(height);

    }

    @Override
    public int getItemCount() {
        return null != list?list.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.list_item_images_list_image)
        ScaleImageView itemImage;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this.itemView);
        }
    }
}
