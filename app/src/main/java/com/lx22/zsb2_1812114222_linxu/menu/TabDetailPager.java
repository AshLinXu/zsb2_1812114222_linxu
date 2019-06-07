package com.lx22.zsb2_1812114222_linxu.menu;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
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
import com.lx22.zsb2_1812114222_linxu.domain.NewsMenu;
import com.lx22.zsb2_1812114222_linxu.domain.NewsTabBean;
import com.lx22.zsb2_1812114222_linxu.global.GlobalConstanst;
import com.lx22.zsb2_1812114222_linxu.selfdefind.adapter.NewsAdapter;
import com.lx22.zsb2_1812114222_linxu.utils.CacheUtils;
import com.lx22.zsb2_1812114222_linxu.view.TopNewsViewPager;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

//import com.lx22.zsb2_1812114222_linxu.selfdefind.adapter.TopNewsPagerAdapter;

public class TabDetailPager extends BaseMenuDetailPager {

    @ViewInject(R.id.vp_top_news)
    private TopNewsViewPager myViewPager;

    private NewsMenu.NewsTabData myTabData;
    private TextView textView;
    private String myUrl;
    private ArrayList<NewsTabBean.TopNews> myTopNews;
    private BitmapUtils myBitmapUtils;
    private LinearLayoutManager linearLayoutManager;
    private NewsAdapter newsAdapter;
    private NewsTabBean newsTabBean;
    private String nextUrl;
    private android.os.Handler myHandler;


    @ViewInject(R.id.tx_topnews_title)
    private TextView tv_topnews_title;

    @ViewInject(R.id.ci_topnews)
    private CirclePageIndicator myCirclePageIndicator;

    @ViewInject(R.id.recyclerView_news)
    private RecyclerView recyclerView_news;

    @ViewInject(R.id.refrash)
    private SwipeRefreshLayout swipeRefreshLayout;

    private ArrayList<NewsTabBean.NewsData> newsData;


    public TabDetailPager(Activity activity, NewsMenu.NewsTabData newsTabData) {
        super(activity);
        myTabData = newsTabData;

        myUrl = GlobalConstanst.SERVERURL + myTabData.url;
    }

    @Override
    public View initView() {
//        textView = new TextView(myActivity);
//        textView.setTextColor(Color.RED);
        View view = View.inflate(myActivity, R.layout.test_layout, null);
        ViewUtils.inject(this, view);
        View headerView = View.inflate(myActivity, R.layout.news_detail_header, null);
        ViewUtils.inject(this, headerView);

        return view;
    }

    @Override
    public void initData() {
//        super.initData();
//        textView.setText(myTabData.title);

        //读缓存
        String cache = CacheUtils.getCache(myUrl, myActivity);
        if (!TextUtils.isEmpty(cache)) {
            progressData(cache, false);
        }

        getDataFromServer();


    }

    public void getDataFromServer() {
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, myUrl, new RequestCallBack<String>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                progressData(result, false);

                CacheUtils.setCache(myUrl, result, myActivity);
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                error.printStackTrace();
                Log.e("获取失败", msg);
            }
        });
    }

    public void getMoreDataFromServer(String moreUrl) {
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.GET, moreUrl, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                progressData(result, true);
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                error.printStackTrace();
                Log.e("获取失败", msg);
            }
        });
    }


    protected void progressData(String result, boolean isMore) {
        Gson gson = new Gson();
        newsTabBean = gson.fromJson(result, NewsTabBean.class);

        if (!isMore) {
            //原本 头部的适配器和 小圆点
//            myViewPager.setAdapter(new TopNewsPagerAdapter());
//            myCirclePageIndicator.setViewPager(myViewPager);
//            myCirclePageIndicator.setSnap(true);
//
//            myCirclePageIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//                @Override
//                public void onPageScrolled(int i, float v, int i1) {
//                }
//
//                @Override
//                public void onPageSelected(int i) {
//                    //select的时候改标题
//                    NewsTabBean.TopNews topNews = myTopNews.get(i);
//                    tv_topnews_title.setText(topNews.title);
//                }
//
//                @Override
//                public void onPageScrollStateChanged(int i) {
//                }
//            });

            //更新第一个topnews标题
//            NewsTabBean.TopNews topNews = myTopNews.get(0);
//            tv_topnews_title.setText(topNews.title);

            //切换初始化第一页
//            myCirclePageIndicator.onPageSelected(0);
            //修改newsDetailPAger，  使用recyclerView 结合一个头部，实现新闻详情页，数据传进去了，但是好像有点慢。

            myTopNews = newsTabBean.data.topnews;
            newsData = newsTabBean.data.news;
            //如果头条新闻， 和新闻列表都不为空， 就设置适配器和布局管理器生成布局

            if (myTopNews != null && newsData != null) {
                newsAdapter = new NewsAdapter(newsData, myTopNews, myActivity);
                recyclerView_news.setAdapter(newsAdapter);
                linearLayoutManager = new LinearLayoutManager(myActivity);
                recyclerView_news.setLayoutManager(linearLayoutManager);
            }
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    getDataFromServer();
                    swipeRefreshLayout.setRefreshing(false);
                }
            });

            recyclerView_news.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
                    if (lastVisibleItemPosition + 1 == newsAdapter.getItemCount()) {
                        //如果是最后一项，滑动状态改变就请求数据。
                        String moreUrl = newsTabBean.data.more;
                        if (!TextUtils.isEmpty(moreUrl)) {
                            requestData(moreUrl);
                        } else {
                            Toast.makeText(myActivity, "没有更多数据了", Toast.LENGTH_SHORT).show();
                            newsAdapter.notifyItemRemoved(newsAdapter.getItemCount());
                        }
                    }
//                int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
//                if(firstVisibleItemPosition == 0){
//                    refrash();
//                }
                }

                private void requestData(String url) {
                    String nextUrl = GlobalConstanst.SERVERURL + url;
                    getMoreDataFromServer(nextUrl);
                }
            });

//            if (myHandler != null) {
//                myHandler = new Handler() {
//                    public void handleMessage(Message msg) {
//                        int currentItem = myViewPager.getCurrentItem();
//                        currentItem++;
//                        if (currentItem > myTopNews.size() - 1) {
//                            currentItem = 0;
//                        }
//                        myViewPager.setCurrentItem(currentItem);
//                        myHandler.sendEmptyMessageDelayed(0, 3000);
//                    }
//                };
//                myHandler.sendEmptyMessageDelayed(0, 3000);
//            }
        } else {
            ArrayList<NewsTabBean.NewsData> moreNews = newsTabBean.data.news;
            newsData.addAll(moreNews);
            newsAdapter.notifyDataSetChanged();
        }

    }

    //此处为原来实现，新闻详情头部新闻的适配器。  更改后这里就不需要了。
//    class TopNewsPagerAdapter extends PagerAdapter {
//
//        public TopNewsPagerAdapter() {
//            myBitmapUtils = new BitmapUtils(myActivity);
//            myBitmapUtils.configDefaultLoadingImage(R.drawable.topnews_item_default);//加载中默认图片
//        }
//
//        @Override
//        public int getCount() {
//            return myTopNews.size();
//        }
//
//        @Override
//        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
//            return view == o;
//        }
//
//        @NonNull
//        @Override
//        public Object instantiateItem(@NonNull ViewGroup container, int position) {
//            ImageView imageView = new ImageView(myActivity);
//            imageView.setImageResource(R.drawable.topnews_item_default);
//            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//            String imageUrl = myTopNews.get(position).topimage;
//
//            myBitmapUtils.display(imageView, imageUrl);
//
//            //下载图片， 缓存
//            container.addView(imageView);
//            return imageView;
//        }
//
//        @Override
//        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//            container.removeView((View) object);
//        }
//    }


}
