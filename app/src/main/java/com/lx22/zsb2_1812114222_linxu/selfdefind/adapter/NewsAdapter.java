package com.lx22.zsb2_1812114222_linxu.selfdefind.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.lx22.zsb2_1812114222_linxu.NewsDetailActivity;
import com.lx22.zsb2_1812114222_linxu.R;
import com.lx22.zsb2_1812114222_linxu.domain.NewsTabBean;
import com.lx22.zsb2_1812114222_linxu.utils.PreUtils;
import com.lx22.zsb2_1812114222_linxu.view.TopNewsViewPager;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter {

    //内容
    private ArrayList<NewsTabBean.NewsData> data;
    private ArrayList<NewsTabBean.TopNews> topData;
    private BitmapUtils bitmapUtils;
    private Activity myActivity;

    TopNewsViewPager myViewPager;
    CirclePageIndicator circlePageIndicator;
    TextView tv_topnews_title;


    //    private static final int HEAD_REFRASH = 0;
    private static final int HEAD = 1;
    private static final int BODY = 2;
    private static final int FOOT = 3;


    public NewsAdapter(ArrayList<NewsTabBean.NewsData> data, ArrayList<NewsTabBean.TopNews> topData, Activity myActivity) {
        this.data = data;
        this.topData = topData;
        this.myActivity = myActivity;

        bitmapUtils = new BitmapUtils(myActivity);
        bitmapUtils.configDefaultLoadingImage(R.drawable.news_pic_default);
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;
        TextView textView2;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            //列表新闻组件
            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.news_tv_title);
            textView2 = itemView.findViewById(R.id.news_tv_date);
        }
    }

    class HeadViewHolder extends RecyclerView.ViewHolder {
        public HeadViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

//    class HeadRefrashViewHolder extends RecyclerView.ViewHolder {
//
//        ImageView refrashImage;
//        TextView refrashTime;
//        ProgressBar pb_refrash;
//        TextView refrashState;
//
//        public HeadRefrashViewHolder(@NonNull View itemView) {
//            super(itemView);
//            refrashImage = itemView.findViewById(R.id.img_refrash);
//            refrashTime = itemView.findViewById(R.id.tv_refrash_time);
//            pb_refrash = itemView.findViewById(R.id.pb_refrash);
//            refrashState = itemView.findViewById(R.id.tv_refrash_state);
//        }
//    }

    class FootViewHolder extends RecyclerView.ViewHolder {

        public FootViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEAD;
        } else if (position == getItemCount() - 1) {
            return FOOT;
        } else {
            return BODY;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

//        int measuredHeight;
//        if (i == HEAD_REFRASH) {
////            //初始化布局
//            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pull_to_refresh_header, viewGroup, false);
////
////
////            //测量高度,获得高度
////            view.measure(0, 0);
////            measuredHeight = view.getMeasuredHeight();
////
////            //设置高度为 0；
//            LayoutParams non_height_header = new LayoutParams(LayoutParams.MATCH_PARENT, 0);
//            view.setLayoutParams(non_height_header);
////            //emm  不对，跟着视频设padding试试
//////            view.setPadding(0, -measuredHeight, 0, 0);
////            //这样隐藏更好些吧。
////            Log.w("视图高度", String.valueOf(measuredHeight));
////
//
//            return new HeadRefrashViewHolder(view);
//        } else

        if (i == HEAD) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.news_detail_header, viewGroup,
                    false);
            myViewPager = view.findViewById(R.id.vp_top_news);
            circlePageIndicator = view.findViewById(R.id.ci_topnews);
            tv_topnews_title = view.findViewById(R.id.tx_topnews_title);

            myViewPager.setAdapter(new TopNewsPagerAdapter(myActivity, topData));


            circlePageIndicator.setViewPager(myViewPager);
            circlePageIndicator.setSnap(true);

            circlePageIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int i, float v, int i1) {
                }

                @Override
                public void onPageSelected(int i) {
                    //select的时候改标题
                    NewsTabBean.TopNews topNews = topData.get(i);
                    tv_topnews_title.setText(topNews.title);
                }

                @Override
                public void onPageScrollStateChanged(int i) {
                }
            });

            tv_topnews_title.setText(topData.get(0).title);//设置标题
            circlePageIndicator.onPageSelected(0);//页面切换，默认到第一页

            return new HeadViewHolder(view);
        } else if (i == FOOT) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.test_foot, viewGroup,
                    false);
            return new FootViewHolder(view);
        }
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.test_item, viewGroup,
                false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {

        int type = getItemViewType(i);
//        if (type == HEAD_REFRASH) {
////            HeadRefrashViewHolder holder = (HeadRefrashViewHolder) viewHolder;
////            holder.refrashImage.setImageResource(R.drawable.common_listview_headview_red_arrow);
//        }

        if (type == HEAD) {
            final HeadViewHolder holder = (HeadViewHolder) viewHolder;

        }

        if (type == BODY) {
            final NewsViewHolder holder = (NewsViewHolder) viewHolder;
            final NewsTabBean.NewsData newsTabBean = data.get(i);

            holder.textView.setText(newsTabBean.title);
            holder.textView2.setText(newsTabBean.pubdate);

            bitmapUtils.display(holder.imageView, newsTabBean.listimage);


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.w("onclick", "第" + i + "个？");
                    int newsId = newsTabBean.id;

                    String read_ids = PreUtils.getString(myActivity, "read_ids", "");
                    if (!read_ids.contains(newsId + ",")) {
                        read_ids = read_ids + newsId + ",";
                        PreUtils.setString(myActivity, "read_ids", read_ids);
                    }
                    Log.w("已读列表", PreUtils.getString(myActivity, "read_ids", ""));

                    holder.textView.setTextColor(Color.GRAY);

                    Intent intent = new Intent(myActivity, NewsDetailActivity.class);
                    intent.putExtra("url",newsTabBean.url);
                    myActivity.startActivity(intent);
                }
            });
        }

//        holder.imageView.setImageResource(newsTabBean.listimage);
    }

    @Override
    public int getItemCount() {
        return data.size() + 1;
    }
}
