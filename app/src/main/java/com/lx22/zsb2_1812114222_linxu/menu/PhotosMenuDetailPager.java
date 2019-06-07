package com.lx22.zsb2_1812114222_linxu.menu;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lx22.zsb2_1812114222_linxu.R;
import com.lx22.zsb2_1812114222_linxu.base.BaseMenuDetailPager;
import com.lx22.zsb2_1812114222_linxu.domain.PhotosBean;
import com.lx22.zsb2_1812114222_linxu.global.GlobalConstanst;
import com.lx22.zsb2_1812114222_linxu.utils.CacheUtils;

import java.util.ArrayList;

public class PhotosMenuDetailPager extends BaseMenuDetailPager {

    @ViewInject(R.id.lv_photo)
    private ListView lv_photo;
    @ViewInject(R.id.gv_photo)
    private GridView gv_photo;
    private ArrayList<PhotosBean.PhotoNews> myNewsList;
    private boolean isListView;
    public ImageButton btnPhoto;

    public PhotosMenuDetailPager(Activity activity, ImageButton btn) {
        super(activity);
        this.btnPhoto = btn;
        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isListView) {
                    lv_photo.setVisibility(View.VISIBLE);
                    gv_photo.setVisibility(View.GONE);
                    btnPhoto.setImageResource(R.drawable.icon_pic_list_type);
                    isListView = false;
                } else {

                    lv_photo.setVisibility(View.GONE);
                    gv_photo.setVisibility(View.VISIBLE);
                    btnPhoto.setImageResource(R.drawable.icon_pic_grid_type);
                    isListView = true;
                }
            }
        });

    }


    public View initView() {
        View view = View.inflate(myActivity, R.layout.pager_photo_menu_detail, null);
        ViewUtils.inject(this, view);
        return view;
    }

    public void initData() {
        String cache = CacheUtils.getCache(GlobalConstanst.PHOTOS_JSON_URL, myActivity);
        if (!TextUtils.isEmpty(cache)) {
            progressData(cache);
        }
        getDataFromServer();
    }

    private void getDataFromServer() {
        HttpUtils httpUtils = new HttpUtils();
        Log.w("url", GlobalConstanst.PHOTOS_JSON_URL);
        httpUtils.send(HttpRequest.HttpMethod.GET, GlobalConstanst.PHOTOS_JSON_URL, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                progressData(result);
                CacheUtils.setCache(GlobalConstanst.PHOTOS_JSON_URL, result, myActivity);
            }


            @Override
            public void onFailure(HttpException error, String msg) {
                error.printStackTrace();
                Toast.makeText(myActivity, msg, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void progressData(String result) {
        Gson gson = new Gson();
        PhotosBean photosBean = gson.fromJson(result, PhotosBean.class);

        myNewsList = photosBean.data.news;

        lv_photo.setAdapter(new PhotoAdapter());
        gv_photo.setAdapter(new PhotoAdapter());
    }

    class PhotoAdapter extends BaseAdapter {

        private BitmapUtils myBitmapUtils;

        public PhotoAdapter() {
            myBitmapUtils = new BitmapUtils(myActivity);
            myBitmapUtils.configDefaultLoadingImage(R.drawable.pic_item_list_default);
        }

        @Override
        public int getCount() {
            return myNewsList.size();
        }

        @Override
        public PhotosBean.PhotoNews getItem(int position) {
            return myNewsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            PhotosMenuDetailPager.ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(myActivity, R.layout.list_item_photos, null);
                holder = new PhotosMenuDetailPager.ViewHolder();
                holder.ivPic = convertView.findViewById(R.id.iv_pic);
                holder.tvTitle = convertView.findViewById(R.id.tv_title);
                convertView.setTag(holder);
            } else {
                holder = (PhotosMenuDetailPager.ViewHolder) convertView.getTag();
            }

            PhotosBean.PhotoNews item = getItem(position);
            holder.tvTitle.setText(item.title);

            myBitmapUtils.display(holder.ivPic, item.listimage);
            return convertView;

        }
    }

    static class ViewHolder {
        public ImageView ivPic;
        public TextView tvTitle;
    }

}
