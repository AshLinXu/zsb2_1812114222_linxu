package com.lx22.zsb2_1812114222_linxu.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class TopNewsViewPager extends ViewPager {

    private int startX;
    private int startY;

    public TopNewsViewPager(@NonNull Context context) {
        super(context);
    }

    public TopNewsViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = (int) ev.getX();
                startY = (int) ev.getY();
                break;

            case MotionEvent.ACTION_MOVE:
                int endX = (int) ev.getX();
                int endY = (int) ev.getY();

                int dx =endX - startX ;
                int dy =endY - startY;

                if (Math.abs(dy) > Math.abs(dx)) {
                    //上下滑
                    getParent().requestDisallowInterceptTouchEvent(false);
                } else {
                    //左右滑
                    int currentItem = getCurrentItem();
                    if(dx>0){
                        //往左走
                        if(currentItem==0){
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }
                    }else{
                        //往右走
                        int lastPage = getAdapter().getCount() -1;
                        if(currentItem == lastPage){
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }
                    }
                }
                break;
        }


        return super.dispatchTouchEvent(ev);
    }
}
