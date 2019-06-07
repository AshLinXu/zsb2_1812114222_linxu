package com.lx22.zsb2_1812114222_linxu.base.impl;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lx22.zsb2_1812114222_linxu.MainActivity;
import com.lx22.zsb2_1812114222_linxu.base.BaseMenuDetailPager;
import com.lx22.zsb2_1812114222_linxu.base.BasePager;
import com.lx22.zsb2_1812114222_linxu.domain.NewsMenu;
import com.lx22.zsb2_1812114222_linxu.fragment.LeftMenuFragment;
import com.lx22.zsb2_1812114222_linxu.global.GlobalConstanst;
import com.lx22.zsb2_1812114222_linxu.menu.InteractMenuDetailPager;
import com.lx22.zsb2_1812114222_linxu.menu.NewsMenuDetailPager;
import com.lx22.zsb2_1812114222_linxu.menu.PhotosMenuDetailPager;
import com.lx22.zsb2_1812114222_linxu.menu.TopicsMenuDetailPager;
import com.lx22.zsb2_1812114222_linxu.utils.CacheUtils;

import java.util.ArrayList;

public class GlobPager extends BasePager {

    private ArrayList<BaseMenuDetailPager> myMenuDetailPagers;
    private NewsMenu myNewsMenuData;

    public GlobPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initDate() {

        tv_title.setText("广场");
        ib_menu.setVisibility(View.VISIBLE);

        Log.w("广场", "广场初始化==============");
//        TextView textView = new TextView(myActivity);
//        textView.setText("广场");
//        textView.setTextColor(Color.RED);
//        fl_content.addView(textView);

        //请求服务器之前看看有没有缓存，
        String cache = CacheUtils.getCache(GlobalConstanst.CATEGORIES_JSON_URL, myActivity);
        if (!TextUtils.isEmpty(cache)) {
            progressData(cache);
            Log.w("从缓存中取值", cache);
        }
//        else{
//            getDataFromServer();
//        }
        //先从缓存读取，在从服务器加载
        getDataFromServer();

    }


    public void progressData(String json) {
        Gson gson = new Gson();
        myNewsMenuData = gson.fromJson(json, NewsMenu.class);
        Log.w("解析数据", myNewsMenuData.toString());

        MainActivity activity = (MainActivity) myActivity;
        LeftMenuFragment fragmentLeftMenu = activity.getFragmentLeftMenu();

        fragmentLeftMenu.setMenuData(myNewsMenuData.data);

        myMenuDetailPagers = new ArrayList<>();
        myMenuDetailPagers.add(new NewsMenuDetailPager(myActivity,myNewsMenuData.data.get(0).children));
        myMenuDetailPagers.add(new TopicsMenuDetailPager(myActivity));
        myMenuDetailPagers.add(new PhotosMenuDetailPager(myActivity,btnPhoto));
        myMenuDetailPagers.add(new InteractMenuDetailPager(myActivity));

        //默认调用新闻详情页, hardcore
        setCurrentDetailPager(0);
    }

    public void getDataFromServer() {
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, GlobalConstanst.CATEGORIES_JSON_URL, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;

                progressData(result);
                CacheUtils.setCache(GlobalConstanst.CATEGORIES_JSON_URL, result, myActivity);

                Log.w("访问服务器", result);
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                error.printStackTrace();
                Toast.makeText(myActivity, msg, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void setCurrentDetailPager(int postition) {
        //更新freamlayout内容
        BaseMenuDetailPager pager = myMenuDetailPagers.get(postition);
        View view = pager.myRootView;
        fl_content.removeAllViews();
        fl_content.addView(view);

        pager.initData();

        //改标题
        String title = myNewsMenuData.data.get(postition).title;
        tv_title.setText(title);

        //组图页面，显示按钮
        if(pager instanceof PhotosMenuDetailPager){
            btnPhoto.setVisibility(View.VISIBLE);
        }else{
            //隐藏按钮
            btnPhoto.setVisibility(View.GONE);
        }
    }
}
