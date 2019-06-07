package com.lx22.zsb2_1812114222_linxu.menu;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lx22.zsb2_1812114222_linxu.MainActivity;
import com.lx22.zsb2_1812114222_linxu.R;
import com.lx22.zsb2_1812114222_linxu.base.BaseMenuDetailPager;
import com.lx22.zsb2_1812114222_linxu.domain.NewsMenu;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;

public class NewsMenuDetailPager extends BaseMenuDetailPager {

    @ViewInject(R.id.vp_news_menu_detail)
    private ViewPager myViewPager;
    private ArrayList<NewsMenu.NewsTabData> myTabData;
    private ArrayList<TabDetailPager> myPagers;

    @ViewInject(R.id.tabi_news)
    private TabPageIndicator myIndicator;


    public NewsMenuDetailPager(Activity activity, ArrayList<NewsMenu.NewsTabData> children) {
        super(activity);
        myTabData = children;
    }

    @Override
    public View initView() {
        View view = View.inflate(myActivity, R.layout.pager_news_menu_detail, null);
        ViewUtils.inject(this, view);
        return view;
    }

    @Override
    public void initData() {
        //初始化内部页签
        myPagers = new ArrayList<>();

        for (int i = 0; i < myTabData.size(); i++) {
            TabDetailPager tabDetailPager = new TabDetailPager(myActivity, myTabData.get(i));
            myPagers.add(tabDetailPager);
        }

        myViewPager.setAdapter(new NewsMenuDetailAdapter());
        //绑定viewpager和indicator,viewpager要setAdapter 设置数据后才能绑.
        myIndicator.setViewPager(myViewPager);
        //因为现在给indicator控制了，所以给indicator设监听
        myIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) { }

            @Override
            public void onPageSelected(int i) {
                Log.w("当前位置",i+"");
                if(i==0){
                    setSlidingMenuEnable(true);
                }else{
                    setSlidingMenuEnable(false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) { }
        });

    }


    class NewsMenuDetailAdapter extends PagerAdapter {


        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            //指定indicator的标题
            NewsMenu.NewsTabData data = myTabData.get(position);
            return data.title;
        }

        @Override
        public int getCount() {
            return myPagers.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            TabDetailPager pager = myPagers.get(position);
            View view = pager.myRootView;
            container.addView(view);
            pager.initData();
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }
    public void setSlidingMenuEnable(boolean enable) {
        MainActivity activity = (MainActivity) myActivity;
        SlidingMenu slidingMenu = activity.getSlidingMenu();
        if (enable) {
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        } else {
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        }
    }

    @OnClick(R.id.btn_next)
    public void nextPage(View view){
        int currentItem = myViewPager.getCurrentItem();
        currentItem++;
        myViewPager.setCurrentItem(currentItem);
    }
}
