package com.lx22.zsb2_1812114222_linxu.menu;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.lx22.zsb2_1812114222_linxu.base.BaseMenuDetailPager;

public class TopicsMenuDetailPager extends BaseMenuDetailPager {

    public TopicsMenuDetailPager(Activity activity) {
        super(activity);
    }

    @Override
    public View initView() {
        TextView textView = new TextView(myActivity);
        textView.setText("主题哟详情");
        textView.setTextColor(Color.RED);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }
}
