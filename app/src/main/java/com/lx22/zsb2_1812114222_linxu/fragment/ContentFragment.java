package com.lx22.zsb2_1812114222_linxu.fragment;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lx22.zsb2_1812114222_linxu.MainActivity;
import com.lx22.zsb2_1812114222_linxu.R;
import com.lx22.zsb2_1812114222_linxu.base.BasePager;
import com.lx22.zsb2_1812114222_linxu.base.impl.GlobPager;
import com.lx22.zsb2_1812114222_linxu.base.impl.HomePager;
import com.lx22.zsb2_1812114222_linxu.base.impl.SettingPager;
import com.lx22.zsb2_1812114222_linxu.view.NoScrollViewPager;

import java.util.ArrayList;

public class ContentFragment extends BaseFragment {

    private NoScrollViewPager viewPager;
    private ArrayList<BasePager> myPagers;
    private RadioGroup radioGroup;

    @Override
    public View initView() {
        View view = View.inflate(myActivity, R.layout.fragment_content, null);
        viewPager = view.findViewById(R.id.vp_content);
        radioGroup = view.findViewById(R.id.radioGroup);
        return view;
    }

    @Override
    public void initData() {
        myPagers = new ArrayList<BasePager>();
        myPagers.add(new HomePager(myActivity));
        myPagers.add(new GlobPager(myActivity));
        myPagers.add(new SettingPager(myActivity));
        viewPager.setAdapter(new ContentAdapter());


        myPagers.get(0).initDate();
        setSlidingMenuEnable(false);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.home:
                        viewPager.setCurrentItem(0, false);
                        break;
                    case R.id.glob:
                        viewPager.setCurrentItem(1, false);
                        break;
                    case R.id.setting:
                        viewPager.setCurrentItem(2, false);
                        break;
                    default:
                        break;
                }
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                BasePager pager = myPagers.get(i);
                pager.initDate();
                if (i == 0 || i ==2) {
                   setSlidingMenuEnable(false);
                } else {
                    setSlidingMenuEnable(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    class ContentAdapter extends PagerAdapter {

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
            BasePager pager = myPagers.get(position);
            View myRootView = pager.myRootView;
//            pager.initDate();
            container.addView(myRootView);
            return myRootView;
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

    public GlobPager getNewsCenterPager(){
        GlobPager pager = (GlobPager) myPagers.get(1);
        return pager;
    }
}
