package com.chen.demo2019.test;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;

import com.base.utils.GetIPv6Address;
import com.base.utils.Logger;
import com.base.utils.NetWorkUtil;
import com.chen.demo2019.AzeroSDK;
import com.chen.demo2019.R;
import com.soundai.azero_sdk.constant.Cmd;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

public class Test {
    public void test(Context context) {
//        testIpV6(context);
        testAzeroSDK(context);
    }

    private AzeroSDK azeroSDK;

    public void testPlayMp3(Context context) {

        Logger.d("播放MP3");
        Logger.time1();
        setVoiceMin50(context);

        new LocalMp3Player().mp3Play(context, LocalMp3Player.MP3_TYPE.APP_START);
        Logger.time2("xxxxA");
        Logger.time1();
        MediaPlayer mMediaPlayer = MediaPlayer.create(context, R.raw.hemiaohemiao);
        mMediaPlayer.start();
        Logger.time2("xxxxB");
    }

    /**
     * 声音最小一半
     *
     * @param context
     */
    private void setVoiceMin50(Context context) {
        AudioManager audioManager = (AudioManager) context.getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        int max = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int current = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
//        Logger.d("声音max：" + max);
//        Logger.d("声音current：" + current);
        if (current * 2 < max) {
//            Logger.d("声音set：" + max / 2);
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, max / 2, AudioManager.FLAG_PLAY_SOUND);
        }
    }

    public void testIpV6(Context context) {
        NetWorkUtil netWorkUtil = new NetWorkUtil();

        Logger.json("ipList", netWorkUtil.getIpList());
        Logger.json("网络是否可用", netWorkUtil.checkNetEnable(context));
        Logger.json("ip", netWorkUtil.getLocalIpAddress(context));
        Logger.json("currentWifiName", netWorkUtil.getCurrentWifiName(context));


        GetIPv6Address getAddress = new GetIPv6Address();
//        Logger.json("ipv6", getAddress.getIpv6AddrString());
        Logger.json("ipv6List", getAddress.getIpv6AddrStringList());


        Logger.jsonFormat("ipv6List", getAddress.getIpv6AddrStringList());

//        [
//                "::1/128",
//                "fe80::4c85:bbff:fefd:358d/64",
//                "fe80::e772:6cf7:a747:a319/64",
//                "2409:8809:8572:fd9b:befc:5d0e:feab:170e/64",
//                "fe80::befc:5d0e:feab:170e/64",
//                "fe80::72bb:e9ff:fea7:6591/64"
//    ]
    }


    public void testAzeroSDK(Context context) {
        Logger.d("语音SDK");
        azeroSDK = new AzeroSDK();
        azeroSDK.init(context);

        StringCallback callback = new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Logger.e("错误");
                Logger.jsonFormat("call", call);
                Logger.jsonFormat("call", call.request());
                Logger.e("call", "" + call.request().url());
                Logger.e(e);
            }

            @Override
            public void onResponse(String response, int id) {
                Logger.d("正确" + response);
                callStep2();
            }
        };
        String args = "{\"userKey\": \"10086\"}";
        azeroSDK.post(Cmd.association, args, callback);
    }

    private void callStep2() {
//        10086=>946a95e8c6bec6d21214a739978ef93c
        String args = "{\n" +
                "   \"deviceSN\": \"110003801000016\",\n" +
                "   \"productId\": \"rXuQj2gi\",\n" +
                "  \"userId\": \"946a95e8c6bec6d21214a739978ef93c\",\n" +
                "  \"token\":\"DA1A5484-EF81-44EA-90A5-13C68AA84285\"\n" +
//                "  \"deviceKey\": \"\",\n" +
//                "  \"name\":\"\"\n" +
                "}";

        args = "{\"deviceSN\":\"110003801000016\",\"productId\":\"rXuQj2gi\",\"userId\":\"946a95e8c6bec6d21214a739978ef93c\",\"token\":\"DA1A5484-EF81-44EA-90A5-13C68AA84285\"}";
        Logger.d("参数:" + args);
        StringCallback callback = new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

                Logger.e("onError2");
                Logger.jsonFormat("call", call);
                Logger.e("call", "" + call.request().url());
                Logger.e(e);
            }

            @Override
            public void onResponse(String response, int id) {
                Logger.d("onResponse2" + response);
            }
        };
        azeroSDK.post(Cmd.bind, args, callback);

    }
}
