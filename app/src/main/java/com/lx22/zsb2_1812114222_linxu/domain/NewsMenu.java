package com.lx22.zsb2_1812114222_linxu.domain;

import java.util.ArrayList;

public class NewsMenu {

    public int retcode;
    public ArrayList<Integer> extend;
    public ArrayList<NewsMenuData> data;

    public class NewsMenuData {
        public int id;
        public String title;
        public int type;
        public ArrayList<NewsTabData> children;
    }

    public class NewsTabData {
        public int id;
        public String title;
        public int type;
        public String url;
    }
}
