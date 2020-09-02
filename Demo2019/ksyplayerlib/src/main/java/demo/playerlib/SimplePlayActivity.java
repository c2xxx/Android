package demo.playerlib;

import android.app.Activity;
import android.media.AudioManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.ksyun.media.player.KSYTextureView;

/**
 * 最简单的播放DEMO
 * Created by ChenHui on 2017/4/25.
 */

public class SimplePlayActivity extends Activity {
    String uri_RTMP = "rtmp://live.hkstv.hk.lxdns.com/live/hks";
    String uri_MP4 = "http://121.201.105.254/boxvideo.xl.ptxl.gitv.tv/65/50/6550535642C4E9EF54E0B1C0451FE196.mp4?timestamp=1487749541&sign=89b1616755fac4ba114fc82783ef260c";
    String uri_miniMp4 = "http://img1.music.broadin.cn/leme/media/198918f40ecc7cab0fc4231adaf67c96.mp4";

    private KSYTextureView mVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_only);

        mVideoView = (KSYTextureView) findViewById(R.id.texture_view);
        mVideoView.setKeepScreenOn(true);
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);

        String mDataSource = getIntent().getStringExtra("path");
        if (TextUtils.isEmpty(mDataSource)) {
            mDataSource = uri_miniMp4;
        }
        try {
            mVideoView.setDataSource(mDataSource);
            mVideoView.prepareAsync();
        } catch (Exception e) {
            Log.e("Logger", Log.getStackTraceString(e));
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mVideoView != null) {
            mVideoView.release();
            mVideoView = null;
        }
        mVideoView = null;
    }
}
