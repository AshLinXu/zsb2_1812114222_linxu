package com.lx22.zsb2_1812114222_linxu.selfdefind.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lidroid.xutils.BitmapUtils;
import com.lx22.zsb2_1812114222_linxu.R;
import com.lx22.zsb2_1812114222_linxu.domain.NewsTabBean;

import java.util.ArrayList;

public class TopNewsPagerAdapter extends PagerAdapter {

    private BitmapUtils myBitmapUtils;
    private Activity myActivity;
    private ArrayList<NewsTabBean.TopNews> myTopNews;

    public TopNewsPagerAdapter( Activity myActivity, ArrayList<NewsTabBean.TopNews> myTopNews) {
        this.myActivity = myActivity;
        this.myTopNews = myTopNews;
        myBitmapUtils = new BitmapUtils(myActivity);
        myBitmapUtils.configDefaultLoadingImage(R.drawable.topnews_item_default);//加载中默认图片
    }


    @Override
    public int getCount() {
        return myTopNews.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = new ImageView(myActivity);
        imageView.setImageResource(R.drawable.topnews_item_default);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        String imageUrl = myTopNews.get(position).topimage;

        myBitmapUtils.display(imageView, imageUrl);

        //下载图片， 缓存
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}


