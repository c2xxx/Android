package com.chen.demo2019.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ImgLoader {
    public static void show(ImageView imageView, String imgUrl) {
        Glide.with(imageView.getContext())
                .load(imgUrl)
                .into(imageView);
    }

    public static void show(ImageView imageView, int imgRes) {
        Glide.with(imageView.getContext())
                .load(imgRes)
                .into(imageView);
    }
}
