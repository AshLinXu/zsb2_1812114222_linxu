package com.lx22.zsb2_1812114222_linxu.selfdefind.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class RefrashRecyclerView extends RecyclerView {

    private int startY;
    private LinearLayoutManager linearLayoutManager;
    private LayoutParams layoutParams;
    private View RefrashItem;


    public RefrashRecyclerView(@NonNull Context context) {
        super(context);
    }

    public RefrashRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RefrashRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void init() {
        layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        RefrashItem = getChildAt(0);

    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {

        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = (int) e.getY();
                break;

            case MotionEvent.ACTION_MOVE:
                if (startY == -1) {
                    startY = (int) e.getY();
                }
                int endY = (int) e.getY();
                int dy = endY - startY;

                Log.w("dy", String.valueOf(dy));
                //当位置是0的时候

                //获取layoutmanager 再获取位置，
                linearLayoutManager = (LinearLayoutManager) getLayoutManager();


                int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
//                RecyclerViewPositionHelper helper = new RecyclerViewPositionHelper(this);
//                int firstVisibleItemPosition = helper.findFirstVisibleItemPosition();
                //等等

                Log.w("position", String.valueOf(firstVisibleItemPosition));
                if (dy > 0 && firstVisibleItemPosition == 1) {
                    init();
                    RefrashItem.setLayoutParams(layoutParams);
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                startY = -1;

                break;
            default:
                break;
        }
        return super.onTouchEvent(e);
    }
}
