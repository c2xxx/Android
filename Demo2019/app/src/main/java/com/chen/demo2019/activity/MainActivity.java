package com.chen.demo2019.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import com.chen.demo2019.R;
import com.chen.demo2019.bean.UserInfo;
import com.chen.demo2019.net.BaseRequest;
import com.chen.demo2019.net.NetCallback;
import com.chen.demo2019.utils.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_click)
    Button btnClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_click)
    public void doClick() {
        Toast.makeText(this, "哈哈", Toast.LENGTH_LONG).show();
        String url = "https://suggest.taobao.com/sug?code=utf-8&q=%E9%A3%9E%E6%9C%BA";
        url = "http://api.androidhive.info/volley/person_object.json";
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.get(url, null, new NetCallback<String>() {
            @Override
            public void onResponse(String response) {
                Logger.d("哈哈哈，得到返回值：", response);
            }
        });
        baseRequest.get(url, null, new NetCallback<UserInfo>() {
            @Override
            public void onResponse(UserInfo userInfo) {
                Logger.d("哈哈哈，得到返回值：", userInfo.getName());
            }
        });
    }
}
