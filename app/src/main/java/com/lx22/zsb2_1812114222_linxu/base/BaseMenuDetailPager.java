package com.lx22.zsb2_1812114222_linxu.base;

import android.app.Activity;
import android.view.View;

public abstract class BaseMenuDetailPager {

    public Activity myActivity;
    public View myRootView;


    public BaseMenuDetailPager(Activity activity) {
        this.myActivity = activity;
        myRootView = initView();
    }

    public abstract View initView();

    public void initData() {

    }
}
