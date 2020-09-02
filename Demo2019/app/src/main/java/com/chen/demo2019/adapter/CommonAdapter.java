package com.chen.demo2019.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.bean.ContentItem;
import com.chen.demo2019.R;
import com.chen.demo2019.utils.ImgLoader;

import java.util.List;

public class CommonAdapter extends RecyclerView.Adapter<CommonAdapter.VH> {

    private List<ContentItem> list;
    private IOnItemClick onItemClick;

    public CommonAdapter(List<ContentItem> list) {
        this.list = list;
    }

    public CommonAdapter(List<ContentItem> list, IOnItemClick onItemClick) {
        this.list = list;
        this.onItemClick = onItemClick;
    }

    public List<ContentItem> getList() {
        return list;
    }

    public void setList(List<ContentItem> list) {
        this.list = list;
    }

    public IOnItemClick getOnItemClick() {
        return onItemClick;
    }

    public void setOnItemClick(IOnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int type) {
        return new VH(View.inflate(viewGroup.getContext(), getLayoutId(type), null));
    }

    protected int getLayoutId(int type) {
        return R.layout.item_reacycle_demo_01;
    }

    @Override
    public void onBindViewHolder(@NonNull VH vh, int position) {
        ContentItem content = list.get(position);
        vh.onBind(content, position, onItemClick);
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }


    public static class VH extends RecyclerView.ViewHolder {

        public VH(@NonNull View itemView) {
            super(itemView);
            initViews(itemView);
        }

        protected void initViews(@NonNull View itemView) {
            itemImg = itemView.findViewById(R.id.item_img);
            itemTitle = itemView.findViewById(R.id.item_title);
            itemSubTitle = itemView.findViewById(R.id.item_subtitle);
            item_border = itemView.findViewById(R.id.item_border);
        }

        protected void onBind(ContentItem content, int position, IOnItemClick onItemClick) {
            if (itemTitle != null) {
                itemTitle.setText(content.getTitle());
            }
            if (itemSubTitle != null) {
                itemSubTitle.setText(content.getSubTitle());
            }
            if (itemImg != null) {
                if (TextUtils.isEmpty(content.getPoster())) {
                    ImgLoader.show(itemImg, content.getImgRes());
                } else {
                    ImgLoader.show(itemImg, content.getPoster());
                }
            }
            if (item_border != null && onItemClick != null) {
                item_border.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClick.onClick(content, position);
                    }
                });
            }
        }


        protected ImageView itemImg;
        protected TextView itemTitle;
        protected TextView itemSubTitle;
        protected View item_border;

    }

    public interface IOnItemClick {
        void onClick(ContentItem item, int position);
    }
}
