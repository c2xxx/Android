package com.chen.demo2019.adapter;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.base.bean.ContentItem;
import com.chen.demo2019.R;

import java.util.List;

public class SimpleAdapter extends CommonAdapter {


    public SimpleAdapter(List<ContentItem> list) {
        super(list);
    }

    public SimpleAdapter(List<ContentItem> list, IOnItemClick onItemClick) {
        super(list, onItemClick);
    }


    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int type) {
        return new VH1(View.inflate(viewGroup.getContext(), getLayoutId(type), null));
    }

    public static class VH1 extends VH {
        public VH1(@NonNull View itemView) {
            super(itemView);
        }

        TextView itemTitle111;

        @Override
        protected void onBind(ContentItem content, int position, IOnItemClick onItemClick) {
            super.onBind(content, position, onItemClick);
            itemTitle111.setText("哈哈哈");
        }

        @Override
        protected void initViews(@NonNull View itemView) {
            super.initViews(itemView);
            itemTitle111 = itemView.findViewById(R.id.item_title);
        }
    }
}
