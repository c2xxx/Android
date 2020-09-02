package com.chen.keepsort;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.chen.keepsort.bean.SportGroup;
import com.chen.keepsort.bean.SportItem;

import java.util.ArrayList;
import java.util.List;

public class AddSportGroupActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_title;
    private EditText et_item_rest_time;
    private EditText et_item_during;
    private EditText et_item_name;
    private TextView result;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initViews();
        setTitle("添加运动项组");
    }

    private void initViews() {
        findViewById(R.id.btn_add_item).setOnClickListener(this);
        findViewById(R.id.btn_save).setOnClickListener(this);
        et_title = findViewById(R.id.et_title);
        et_item_rest_time = findViewById(R.id.et_item_rest_time);
        et_item_during = findViewById(R.id.et_item_during);
        et_item_name = findViewById(R.id.et_item_name);
        result = findViewById(R.id.result);
    }

    List<SportItem> list = new ArrayList<>();

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_save) {
            String title = et_title.getText().toString();
            if (TextUtils.isEmpty(title)) {
                toast("标题不能为空");
                return;
            }
            if (list.isEmpty()) {
                toast("运动项目不能为空");
                return;
            }
            SportGroup group = new SportGroup();
            group.setKey("group" + System.currentTimeMillis());
            group.setName(title);
            group.setList(list);
            SportGroupManager.getInstance().add(group);
            SportGroupManager.getInstance().setDefaultSportGroup(group);
            restartApp();
        } else if (view.getId() == R.id.btn_add_item) {
            String name = et_item_name.getText().toString();
            String during = et_item_during.getText().toString();
            String restTime = et_item_rest_time.getText().toString();
            if (TextUtils.isEmpty(name)) {
                toast("请输入运动项名");
                return;
            }
            if (TextUtils.isEmpty(during)) {
                toast("请输入运动时长");
                return;
            }
            if (TextUtils.isEmpty(restTime)) {
                toast("请输入休息时长");
                return;
            }
            int during1 = Integer.parseInt(during);
            int restTime1 = Integer.parseInt(restTime);
            SportItem item = new SportItem();
            item.setName(name);
            item.setDuring(during1);
            item.setRestTime(restTime1);
            list.add(item);
            et_item_name.setText("");
            et_item_during.setText("");
            et_item_rest_time.setText("");
            et_item_name.requestFocus();
            reshow();
        }
    }

    private void restartApp() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void reshow() {
        String content = JSON.toJSONString(list, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteDateUseDateFormat);
        result.setText(content);
    }


    private void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
