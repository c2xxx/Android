package com.chen.demo2019.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
<<<<<<< HEAD
import android.widget.EditText;
import android.widget.Toast;
=======
>>>>>>> 74ce7db71264ad81880db006ded422a1a29022c5

import com.base.net.BaseRequest;
import com.base.net.NetCallback;
import com.base.utils.Logger;
import com.base.utils.ToastUtil;
import com.chen.demo2019.R;
import com.chen.demo2019.bean.UserInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import demo.playerlib.SimplePlayActivity;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_click)
    Button btnClick;
    @BindView(R.id.et_url)
    EditText etUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        openTestActivity();
    }

    private void openTestActivity() {
        finish();
        startActivity(new Intent(this, RecycleDemoActivity.class));
    }

    @OnClick(R.id.btn_click)
<<<<<<< HEAD
    public void doClickVideo() {
        Toast.makeText(this, "看视频", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, SimplePlayActivity.class);
        String url = etUrl.getText().toString();
        url = "udp://@192.168.0.104:1234";
        intent.putExtra("path", url);
        startActivity(intent);
    }

    public void doClickNet() {
        Toast.makeText(this, "哈哈", Toast.LENGTH_LONG).show();
=======
    public void doClick() {
        ToastUtil.show("发起请求");
        Logger.trackInfo();
        Logger.d("位置");
        Logger.d("XX", "位置");
>>>>>>> 74ce7db71264ad81880db006ded422a1a29022c5
        String url = "https://suggest.taobao.com/sug?code=utf-8&q=%E9%A3%9E%E6%9C%BA";
        url = "http://api.androidhive.info/volley/person_object.json";
        BaseRequest baseRequest = new BaseRequest();
        baseRequest.get(url, null, new NetCallback<String>() {
            @Override
            public void onResponse(String response) {
                Logger.d("哈哈哈，得到返回值：", response);
                ToastUtil.show("哈哈哈，得到返回值1：" + response);
            }
        });
        baseRequest.get(url, null, new NetCallback<UserInfo>() {
            @Override
            public void onResponse(UserInfo userInfo) {
                Logger.d("哈哈哈，得到返回值：", userInfo.getName());
                Logger.jsonFormat("userInfoA:", userInfo);
                userInfo.setName(logLongStr);
                Logger.jsonFormat("userInfoB:", userInfo);
                ToastUtil.show("哈哈哈，得到返回值2：" + userInfo.getName());
            }
        });

        testLongStr();
    }

    private void testLongStr() {

        Logger.d("测试打印长文本1", logLongStr);
        Logger.logLongStr("测试打印长文本2", logLongStr);
    }

    String logLongStr = "比起java类库的惨不忍睹，android的类库可以说很对的起观众了，下面是具体的类库：\n" +
            "\n" +
            "1、Android.util 核心使用包（看名字就知道啦），包括了低级类，例如，专用的容器、字符串格式化和XML解析程序。\n" +
            "\n" +
            "2、Android.os 操作系统包，提供了基本操作系统服务的访问时间，例如，消息传递、进程间通信、始终函数和调试。\n" +
            "\n" +
            "3、Android.graphic 图形API提供了支持画布、颜色和绘画原语的低级图行类，让你可以在画布上画画。\n" +
            "\n" +
            "4、Android.text 用来显示和解析文本的文本处理工具。\n" +
            "\n" +
            "5、Android.database 当使用数据库的时候，提供处理游标（cursor）所要求的低级类。\n" +
            "\n" +
            "6、Android.content 内容API通过处理资源、内容提供器和包的服务，来管理数据访问和发布。\n" +
            "\n" +
            "7、Android.view  View是核心用户界面类。所有的用户界面元素都是使用一系列View来构造的，用来提供交互组件。\n" +
            "\n" +
            "8、Android.widget  构建在View包的基础上，Widget类是已经创建好的用户界面元素，可以直接在应用程序中使用，他们包含列表、按键和布局。\n" +
            "\n" +
            "9、Com.google.android.maps  一个高级API，提供了对本地地图控件的访问，可以再应用程序中使用这些控件，它包括MapView控件以及用来对嵌入的地图进行注释和控制的Overlay和MapController类。\n" +
            "\n" +
            "10、Android.app 一个提供了对应用程序模型进行访问的高级包。应用程序包包含活动（Acitivity）和服务（Service）API，它形成所有应用程序的基础。\n" +
            "\n" +
            "11、Android.provider 为了方便开发人员对某些标注的内容提供器进行访问，provider包提供了一些类，从而提供了对所有的android发行版中包含的标准数据库的访问。\n" +
            "\n" +
            "12、Android.telephony telephony Api允许直接与蛇鞭的电话栈进行交互，让你可以直接打电话、监控电话状态以及收发SMS消息。\n" +
            "\n" +
            "13、Android.webkit webKit 包提供了与基于Web的内容相关的API，包括一个WebView控Android.webkit webKit 包提供了与基于Web的内容相关的API，包括一个WebView控Android.webkit webKit 包提供了与基于Web的内容相关的API，包括一个WebView控Android.webkit webKit 包提供了与基于Web的内容相关的API，包括一个WebView控Android.webkit webKit 包提供了与基于Web的内容相关的API，包括一个WebView控Android.webkit webKit 包提供了与基于Web的内容相关的API，包括一个WebView控Android.webkit webKit 包提供了与基于Web的内容相关的API，包括一个WebView控Android.webkit webKit 包提供了与基于Web的内容相关的API，包括一个WebView控Android.webkit webKit 包提供了与基于Web的内容相关的API，包括一个WebView控Android.webkit webKit 包提供了与基于Web的内容相关的API，包括一个WebView控Android.webkit webKit 包提供了与基于Web的内容相关的API，包括一个WebView控Android.webkit webKit 包提供了与基于Web的内容相关的API，包括一个WebView控Android.webkit webKit 包提供了与基于Web的内容相关的API，包括一个WebView控Android.webkit webKit 包提供了与基于Web的内容相关的API，包括一个WebView控Android.webkit webKit 包提供了与基于Web的内容相关的API，包括一个WebView控Android.webkit webKit 包提供了与基于Web的内容相关的API，包括一个WebView控Android.webkit webKit 包提供了与基于Web的内容相关的API，包括一个WebView控Android.webkit webKit 包提供了与基于Web的内容相关的API，包括一个WebView控Android.webkit webKit 包提供了与基于Web的内容相关的API，包括一个WebView控Android.webkit webKit 包提供了与基于Web的内容相关的API，包括一个WebView控Android.webkit webKit 包提供了与基于Web的内容相关的API，包括一个WebView控Android.webkit webKit 包提供了与基于Web的内容相关的API，包括一个WebView控Android.webkit webKit 包提供了与基于Web的内容相关的API，包括一个WebView控件，可以再活动或许cookie管理器重嵌入一个浏览器。13、Android.webkit webKit 包提供了与基于Web的内容相关的API，包括一个WebView控件，可以再活动或许cookie管理器重嵌入一个浏览器。13、Android.webkit webKit 包提供了与基于Web的内容相关的API，包括一个WebView控件，可以再活动或许cookie管理器重嵌入一个浏览器。13、Android.webkit webKit 包提供了与基于Web的内容相关的API，包括一个WebView控件，可以再活动或许cookie管理器重嵌入一个浏览器。13、Android.webkit webKit 包提供了与基于Web的内容相关的API，包括一个WebView控件，可以再活动或许cookie管理器重嵌入一个浏览器。13、Android.webkit webKit 包提供了与基于Web的内容相关的API，包括一个WebView控件，可以再活动或许cookie管理器重嵌入一个浏览器。13、Android.webkit webKit 包提供了与基于Web的内容相关的API，包括一个WebView控件，可以再活动或许cookie管理器重嵌入一个浏览器。13、Android.webkit webKit 包提供了与基于Web的内容相关的API，包括一个WebView控件，可以再活动或许cookie管理器重嵌入一个浏览器。13、Android.webkit webKit 包提供了与基于Web的内容相关的API，包括一个WebView控件，可以再活动或许cookie管理器重嵌入一个浏览器。13、Android.webkit webKit 包提供了与基于Web的内容相关的API，包括一个WebView控件，可以再活动或许cookie管理器重嵌入一个浏览器。13、Android.webkit webKit 包提供了与基于Web的内容相关的API，包括一个WebView控件，可以再活动或许cookie管理器重嵌入一个浏览器。13、Android.webkit webKit 包提供了与基于Web的内容相关的API，包括一个WebView控件，可以再活动或许cookie管理器重嵌入一个浏览器。13、Android.webkit webKit 包提供了与基于Web的内容相关的API，包括一个WebView控件，可以再活动或许cookie管理器重嵌入一个浏览器。\n" +
            "\n" +
            "除了Android API之外，android栈还包含了一些可以提供程序框架使用的c/c++库集合：\n" +
            "\n" +
            "1、OpenGL  基于O喷GL ES ApI 的用来支持3D图形的库。\n" +
            "\n" +
            "2、Free Type  支持位图和矢量字体渲染。\n" +
            "\n" +
            "3、SGL 用来提供2D图形引擎的核心库。\n" +
            "--------------------- \n" +
            "版权声明：本文为CSDN博主「Shine_on_the_earth」的原创文章，遵循CC 4.0 by-sa版权协议，转载请附上原文出处链接及本声明。\n" +
            "原文链接：https://blog.csdn.net/weixin_40251830/article/details/82734558";

}
