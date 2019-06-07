package com.lx22.zsb2_1812114222_linxu.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lx22.zsb2_1812114222_linxu.MainActivity;
import com.lx22.zsb2_1812114222_linxu.R;
import com.lx22.zsb2_1812114222_linxu.base.impl.GlobPager;
import com.lx22.zsb2_1812114222_linxu.domain.NewsMenu;

import java.util.ArrayList;

public class LeftMenuFragment extends BaseFragment {

    @ViewInject(R.id.menu_list)
    private ListView menu_list;
    private int myCurrentPosi;
    private LeftMenuAdapter myAdapter;

    private ArrayList<NewsMenu.NewsMenuData> myNewsMenuData;

    @Override
    public View initView() {
        View view = View.inflate(myActivity, R.layout.fragment_left_menu, null);
        ViewUtils.inject(this, view);
        return view;
    }

    @Override
    public void initData() {

    }

    public void setMenuData(ArrayList<NewsMenu.NewsMenuData> data) {
        myCurrentPosi = 0;//重新选中新闻栏，归零内容页面位置。
        myNewsMenuData = data;
        myAdapter = new LeftMenuAdapter();
        menu_list.setAdapter(myAdapter);

        menu_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                myCurrentPosi = position;
                myAdapter.notifyDataSetChanged();
                toggle();
                //选择侧边栏内的项，修改freamlayout内容

                setCurrentDetailPager(myCurrentPosi);

            }
        });

    }

    public void setCurrentDetailPager(int position) {
        MainActivity activity = (MainActivity) myActivity;
        ContentFragment contentFragment = activity.getContentFragment();
        GlobPager newsCenterPager = contentFragment.getNewsCenterPager();

        newsCenterPager.setCurrentDetailPager(position);
    }


    public void toggle() {
        MainActivity activity = (MainActivity) myActivity;
        SlidingMenu slidingMenu = activity.getSlidingMenu();
        slidingMenu.toggle();
    }


    public class LeftMenuAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return myNewsMenuData.size();
        }

        @Override
        public Object getItem(int position) {
            return myNewsMenuData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(myActivity, R.layout.list_item_left_menu, null);
            TextView textView = view.findViewById(R.id.tv_menu);

            NewsMenu.NewsMenuData item = (NewsMenu.NewsMenuData) getItem(position);
            textView.setText(item.title);

            if (position == myCurrentPosi) {
                //选中
                textView.setEnabled(true);
            } else {
                textView.setEnabled(false);
            }
            return view;
        }
    }
}
