package com.lx22.zsb2_1812114222_linxu.base.impl;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.lx22.zsb2_1812114222_linxu.base.BasePager;

public class SettingPager extends BasePager {
    public SettingPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initDate() {

        tv_title.setText("个人");

        ib_menu.setVisibility(View.INVISIBLE);

        Log.w("个人页面","个人页面初始化==============");
        TextView textView = new TextView(myActivity);
        textView.setText("设置");
        textView.setTextColor(Color.RED);
        fl_content.addView(textView);
    }
}
