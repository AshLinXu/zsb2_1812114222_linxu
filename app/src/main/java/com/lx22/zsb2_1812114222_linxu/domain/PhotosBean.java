package com.lx22.zsb2_1812114222_linxu.domain;

import java.util.ArrayList;

public class PhotosBean {

    public PhotosData data;

    public class PhotosData {
        public ArrayList<PhotoNews> news;
    }

    public class PhotoNews {
        public int id;
        public String listimage;
        public String title;
    }
}
