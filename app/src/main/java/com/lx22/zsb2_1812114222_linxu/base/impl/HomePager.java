package com.lx22.zsb2_1812114222_linxu.base.impl;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.lx22.zsb2_1812114222_linxu.base.BasePager;

public class HomePager extends BasePager {

    public HomePager(Activity activity) {
        super(activity);
    }

    @Override
    public void initDate() {

        tv_title.setText("主页");

        ib_menu.setVisibility(View.INVISIBLE);

        Log.w("主页","主页初始化==============");
        TextView textView = new TextView(myActivity);
        textView.setText("首页");
        textView.setTextColor(Color.RED);
        textView.setGravity(Gravity.CENTER);
        fl_content.addView(textView);
    }
}
