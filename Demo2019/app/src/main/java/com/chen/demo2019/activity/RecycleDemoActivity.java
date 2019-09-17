package com.chen.demo2019.activity;

import android.content.Context;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.TypedValue;
import android.view.View;

import com.base.bean.ContentItem;
import com.base.utils.Logger;
import com.base.utils.ToastUtil;
import com.chen.demo2019.R;
import com.chen.demo2019.adapter.CommonAdapter;
import com.chen.demo2019.adapter.SimpleAdapter;
import com.chen.demo2019.test.LocalMp3Player;
import com.chen.demo2019.test.Test;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecycleDemoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CommonAdapter recycleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_demo);
        ButterKnife.bind(this);
        initViews();
        initData();
    }

    private void initViews() {
        recyclerView = findViewById(R.id.rv_list);
    }

    List<ContentItem> list = new ArrayList<>();

    private void initData() {
        for (int i = 0; i < 10; i++) {
            ContentItem item = new ContentItem();
            item.setTitle(String.format("标题%s", i + 1));
            item.setSubTitle(String.format("副标题%s", i + 1));
            item.setObj(3);
            if (i % 2 == 0) {
                item.setPoster("http://img.ziyuanku.com/upload/article/2017/201709/6364080700313836098944984.png");
            } else {
                item.setPoster("http://img.ziyuanku.com/upload/article/2017/201709/6364080700315398497472402.png");
            }
            list.add(item);
        }
        showData(list);
    }

    private void showData(List<ContentItem> list) {
        CommonAdapter.IOnItemClick onClick = new CommonAdapter.IOnItemClick() {
            @Override
            public void onClick(ContentItem item, int position) {
                ToastUtil.show(item.getTitle());
            }
        };
        recycleAdapter = new SimpleAdapter(list, onClick);

        setManager();
    }

    private void setManager() {
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
//设置布局管理器
        recyclerView.setLayoutManager(manager);
//设置Adapter
        recyclerView.setAdapter(recycleAdapter);
//设置增加或删除条目的动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addItemDecoration(new StaggeredDividerItemDecoration(this, 50));

    }

    public class StaggeredDividerItemDecoration extends RecyclerView.ItemDecoration {
        private Context context;
        private int interval;

        public StaggeredDividerItemDecoration(Context context, int interval) {
            this.context = context;
            this.interval = interval;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view);
            int interval = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    this.interval, context.getResources().getDisplayMetrics());
            // 中间间隔
            if (position % 2 == 0) {
                outRect.top = 0;
            } else {
                // item为奇数位，设置其左间隔为5dp
                outRect.top = interval;
            }
            // 下方间隔
            outRect.bottom = interval;
        }
    }

    private void setManager1() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
//设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
//设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
//设置Adapter
        recyclerView.setAdapter(recycleAdapter);
//设置增加或删除条目的动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }


    @OnClick(R.id.btn_click2)
    public void addItem2() {
        Test test = new Test();
        test.test(this);




    }



    @OnClick(R.id.btn_click)
    public void addItem() {
        Logger.d("addItem");
        ToastUtil.show("点击");
        ContentItem item = new ContentItem();
        item.setTitle(String.format("标题%s", "新"));
        item.setSubTitle(String.format("副标题%s", "新"));
        item.setObj(3);
        item.setPoster("http://img.ziyuanku.com/upload/article/2017/201709/6364080700313836098944984.png");
        list.add(3, item);

        recycleAdapter.notifyDataSetChanged();
    }
}
