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
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lx22.zsb2_1812114222_linxu.R;

public class PullToRefrashRecyclerView extends RecyclerView {

    private static final int PULL_TO_REFRASH = 1;
    private static final int RELEASE_TO_REFRASH = 2;
    private static final int REFRASHING = 3;

    private int myCurrentStat = PULL_TO_REFRASH;

    private ImageView refrashImage;
    private ProgressBar progressBar;
    private TextView refrashTime;
    private TextView refrashState;

    private int measuredHeight;
    private int startY = -1;
    private View myHeaderView;

    private RotateAnimation animationUp;
    private RotateAnimation animationDown;

    private LinearLayoutManager linearLayoutManager;


    public PullToRefrashRecyclerView(@NonNull Context context) {
        super(context);
        initHeaderView();
    }

    public PullToRefrashRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initHeaderView();

    }

    public PullToRefrashRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initHeaderView();
    }

    public void initHeaderView() {
        myHeaderView = View.inflate(getContext(), R.layout.pull_to_refresh_header, null);

        //拿到组件
        refrashImage = myHeaderView.findViewById(R.id.img_refrash);
        progressBar = myHeaderView.findViewById(R.id.pb_refrash);
        refrashTime = myHeaderView.findViewById(R.id.tv_refrash_time);
        refrashState = myHeaderView.findViewById(R.id.tv_refrash_state);

        Log.w("看看能不能拿到值",refrashState.getText().toString());



        //测量高度,获得高度
        myHeaderView.measure(0, 0);
        measuredHeight = myHeaderView.getMeasuredHeight();
        Log.w("测量高度", String.valueOf(measuredHeight));
        //隐藏它
//        myHeaderView.setPadding(0, -measuredHeight, 0, 0);

        //初始化动画
        initAnimation();
//
//        this.setLayoutManager(new LinearLayoutManager(getContext()));
//        this.addView(myHeaderView,0);


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


//
//                    addView(myHeaderView,0);
//                    getAdapter().notifyItemInserted(0);

                    int padding = dy - measuredHeight;
                    myHeaderView.setPadding(0, padding, 0, 0);
                    Log.w("此时的值",refrashState.getText().toString());
                    Log.w("padding", String.valueOf(padding));
                    //按照视频方法，在我的布局里，会搞乱掉。  padding

                    if (padding > 0 && myCurrentStat != RELEASE_TO_REFRASH) {
                        myCurrentStat = RELEASE_TO_REFRASH;
                        refrashState();
                    } else if (padding < 0 && myCurrentStat != PULL_TO_REFRASH) {
                        myCurrentStat = PULL_TO_REFRASH;
                        refrashState();
                    }
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                startY=-1;
                if(myCurrentStat == RELEASE_TO_REFRASH){
                    myCurrentStat = REFRASHING;
                    refrashState();
                    myHeaderView.setPadding(0,0,0,0);
                }else if(myCurrentStat == PULL_TO_REFRASH){
                    //隐藏头布局
                    myHeaderView.setPadding(0,-measuredHeight,0,0);
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(e);
    }


    public void initAnimation() {
        animationUp = new RotateAnimation(0, -180,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animationUp.setDuration(200);
        animationUp.setFillAfter(true);

        animationDown = new RotateAnimation(-180, 0,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animationUp.setDuration(200);
        animationUp.setFillAfter(true);
    }

    public void refrashState() {
        switch (myCurrentStat) {
            case PULL_TO_REFRASH:
                Log.w("改变字？？","执行到------------了");
                refrashState.setText("下拉刷新");
                progressBar.setVisibility(View.INVISIBLE);
                refrashImage.setVisibility(View.VISIBLE);
                refrashImage.startAnimation(animationDown);
                Log.w("是不是有改变",refrashState.getText().toString());


                break;
            case RELEASE_TO_REFRASH:
                Log.w("改变字？？","执行到------------了");
                refrashState.setText("松开刷新");
                progressBar.setVisibility(View.VISIBLE);
                refrashImage.setVisibility(View.INVISIBLE);
                refrashImage.startAnimation(animationUp);
                Log.w("是不是有改变",refrashState.getText().toString());
                break;
            case REFRASHING:
                Log.w("改变字？？","执行到------------了");
                refrashState.setText("正在刷新..");
                refrashImage.clearAnimation();//清除动画，才能隐藏
                progressBar.setVisibility(View.VISIBLE);
                refrashImage.setVisibility(View.INVISIBLE);
                break;
            default:
                break;
        }
    }

}
