package com.lx22.zsb2_1812114222_linxu.base;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lx22.zsb2_1812114222_linxu.MainActivity;
import com.lx22.zsb2_1812114222_linxu.R;

public class BasePager {

    public Activity myActivity;
    public View myRootView;
    public TextView tv_title;
    public ImageButton ib_menu;
    public FrameLayout fl_content;
    public ImageButton btnPhoto;


    public BasePager(Activity activity) {
        myActivity = activity;
        myRootView = initView();
    }

    public void toggle() {
        MainActivity activity = (MainActivity) myActivity;
        SlidingMenu slidingMenu = activity.getSlidingMenu();
        slidingMenu.toggle();
    }

    public View initView() {
        View view = View.inflate(myActivity, R.layout.base_pager, null);
        tv_title = view.findViewById(R.id.tv_title);
        ib_menu = view.findViewById(R.id.ib_menu);
        fl_content = view.findViewById(R.id.fl_content);
        btnPhoto = view.findViewById(R.id.btn_photo);

        ib_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle();
            }
        });

        return view;
    }

    public void initDate() {

    }
}
