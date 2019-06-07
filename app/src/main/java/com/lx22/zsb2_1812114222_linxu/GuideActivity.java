package com.lx22.zsb2_1812114222_linxu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.lx22.zsb2_1812114222_linxu.utils.PreUtils;

import java.util.ArrayList;

public class GuideActivity extends Activity {

    private ViewPager viewPager;
    private int[] imageData = {R.drawable.guide_1, R.drawable.guide_2, R.drawable.guide_3};
    private ArrayList<ImageView> imageViews;
    LinearLayout llContainer;
    private ImageView ivRedPoint;
    private Button btStart;
    private int positDis;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_guide);

        llContainer = findViewById(R.id.ll_container);
        ivRedPoint = findViewById(R.id.iv_red_point);
        btStart = findViewById(R.id.btn_start);
        viewPager = findViewById(R.id.vp_guide);

        initData();
        viewPager.setAdapter(new GuideAdapter());

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                int dis = (int) (positDis * v) + i * positDis;
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ivRedPoint.getLayoutParams();
                params.leftMargin = dis;
                ivRedPoint.setLayoutParams(params);
            }

            @Override
            public void onPageSelected(int i) {
                if (i == imageViews.size() - 1) {
                    btStart.setVisibility(View.VISIBLE);
                } else {
                    btStart.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        //获取point位置，在layout执行结束时
        ivRedPoint.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        ivRedPoint.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        positDis = llContainer.getChildAt(1).getLeft() -
                                llContainer.getChildAt(0).getLeft();
                    }
                });

        //点按钮
        btStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreUtils.settBoolean(getApplicationContext(), "is_first_enter", false);
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });

    }

    private void initData() {
        imageViews = new ArrayList<ImageView>();
        for (int i = 0; i < imageData.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(imageData[i]);
            imageViews.add(imageView);
            ImageView point = new ImageView(this);
            point.setImageResource(R.drawable.shape_point_gray);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
            );
            if (i > 0) {
                params.leftMargin = 10;
            }
            point.setLayoutParams(params);
            llContainer.addView(point);
        }
    }

    class GuideAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imageViews.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ImageView imageView = imageViews.get(position);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }
}
