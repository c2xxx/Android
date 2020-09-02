package demo.playerlib;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ksyun.media.player.IMediaPlayer;
import com.ksyun.media.player.KSYMediaPlayer;
import com.ksyun.media.player.KSYTextureView;

import demo.playerlib.view.VideoProgressBar;

/**
 * Created by ChenHui on 2017/4/25.
 */

public class DemoPlayActivity extends Activity {
    private static final long HIDE_SEEKBAR_TIME = 5000;//隐藏进度条的时间，毫秒

    public static final int UPDATE_SEEKBAR = 0;
    public static final int HIDDEN_SEEKBAR = 1;
    public static final int HANDLE_SEEK = 2;//执行seek

    private KSYTextureView textureView;
    private ProgressBar pbPlayerLoading;
    private ImageView ivPlayerPlay;
    private RelativeLayout llPlayerControl;
    private TextView tvPlayerTitle;
    private TextView tvPlayerSubtitle;
    private VideoProgressBar vpbPlayerProgress;

    private void assignViews() {
        textureView = (KSYTextureView) findViewById(R.id.texture_view);
        pbPlayerLoading = (ProgressBar) findViewById(R.id.pb_player_loading);
        ivPlayerPlay = (ImageView) findViewById(R.id.iv_player_play);
        llPlayerControl = (RelativeLayout) findViewById(R.id.ll_player_control);
        tvPlayerTitle = (TextView) findViewById(R.id.tv_player_title);
        tvPlayerSubtitle = (TextView) findViewById(R.id.tv_player_subtitle);
        vpbPlayerProgress = (VideoProgressBar) findViewById(R.id.vpb_player_progress);
    }


    private KSYTextureView mVideoView;
    private String mDataSource;
    private boolean isBuffering = false;//是否缓冲中
    private int errorRetryCount = 0;//错误重试次数
    private Handler mHandler;
    private boolean isExit;//退出
    private String title;
    private String subTitle;
    private Handler handler = new Handler();

    /**
     * 调用DEMO
     */
    private static void startActivity(Activity context, String url, String title, String subTitle) {
        //http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4
//        String uri_MP4 = "http://121.201.105.254/boxvideo.xl.ptxl.gitv.tv/65/50/6550535642C4E9EF54E0B1C0451FE196.mp4?timestamp=1487749541&sign=89b1616755fac4ba114fc82783ef260c";
//        Intent intent = new Intent(this, SimplePlayActivity.class);
//        intent.putExtra("url", uri_MP4);
//        intent.putExtra("title", "大标题");
//        intent.putExtra("subTitle", "副标题");
//        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_demo);
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        assignViews();
        initHandler();
        initVideoView();
        initData();
        nextURL();
    }


    private void initData() {
        Intent intent = getIntent();
        initByIntent(intent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        initByIntent(intent);
    }

    /**
     * 根据intent初始化内容
     *
     * @param intent
     */
    private void initByIntent(Intent intent) {
        title = intent.getStringExtra("title");
        mDataSource = intent.getStringExtra("url");
        subTitle = intent.getStringExtra("subTitle");
        tvPlayerTitle.setText(title);
        tvPlayerSubtitle.setText(subTitle);
        if (TextUtils.isEmpty(mDataSource)) {
            show("播放地址错误！");
            //TODO 播放地址错误finish
        }
    }


    /**
     * 播放下一个地址
     */
    private void nextURL() {
        errorRetryCount = 0;
        Logger.d("播放下一集");
        try {
            mVideoView.setDataSource(mDataSource);
            mVideoView.prepareAsync();
        } catch (Exception e) {
            Log.e("Logger", Log.getStackTraceString(e));
        }
        Logger.d("播放URL:" + mDataSource);
        setProgressBar(true);
    }


    private void initVideoView() {
        mVideoView = (KSYTextureView) findViewById(R.id.texture_view);
        mVideoView.setFocusable(false);
        mVideoView.setKeepScreenOn(true);
        mVideoView.setVideoScalingMode(KSYMediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT);


        mVideoView.setOnBufferingUpdateListener(mOnBufferingUpdateListener);
        mVideoView.setOnCompletionListener(mOnCompletionListener);
        mVideoView.setOnPreparedListener(mOnPreparedListener);
        mVideoView.setOnInfoListener(mOnInfoListener);
        mVideoView.setOnVideoSizeChangedListener(mOnVideoSizeChangeListener);
        mVideoView.setOnErrorListener(mOnErrorListener);
        mVideoView.setOnSeekCompleteListener(mOnSeekCompletedListener);
        mVideoView.setScreenOnWhilePlaying(true);
        mVideoView.setBufferTimeMax(3.0f);
        mVideoView.setTimeout(5, 30);
        if (false) {
            //硬解264&265
            Logger.e("Hardware !!!!!!!!");
            mVideoView.setDecodeMode(KSYMediaPlayer.KSYDecodeMode.KSY_DECODE_MODE_AUTO);
        }
    }

    private void initHandler() {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case HANDLE_SEEK:
                        executeSeek();
                        break;
                    case UPDATE_SEEKBAR:
                        setVideoProgress(0);
                        break;
                    case HIDDEN_SEEKBAR:
                        if (ivPlayerPlay.getVisibility() != View.VISIBLE) {
                            llPlayerControl.setVisibility(View.GONE);
                        }
                        break;
                }
            }
        };
    }


    /**
     * 显示进度
     *
     * @param currentProgress
     * @return
     */
    private void setVideoProgress(long currentProgress) {
        if (mVideoView == null) {
            return;
        }
//        Logger.d("当前时间：" + TimeUtils.millisToString(System.currentTimeMillis()) + "--" + (System.currentTimeMillis() % 10000));
        long time = currentProgress > 0 ? currentProgress : mVideoView.getCurrentPosition();
        long duration = mVideoView.getDuration();
//        // Update all view elements
//        mPlayerSeekbar.setMax((int) length);
//        mPlayerSeekbar.setProgress((int) time);
//
        if (time >= 0) {
//            String progress = TimeUtils.millisToString(time) + "/" + TimeUtils.millisToString(duration);
//            tvPlayerProgress.setText("播放进度：" + progress);
        }
        if (targetSeekTime == -1) {
            vpbPlayerProgress.setProgress(duration, time);
        }
//
        mHandler.removeMessages(UPDATE_SEEKBAR);
        Message msg = mHandler.obtainMessage();
        msg.what = UPDATE_SEEKBAR;
        if (mHandler != null)
            mHandler.sendMessageDelayed(msg, 1000);
        return;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mVideoView != null) {
            mVideoView.pause();
            mVideoView.runInBackground(true);
        }
    }

    private void setProgressBar(boolean visible) {
        pbPlayerLoading.setVisibility(visible ? View.VISIBLE : View.GONE);
        if (visible) {
            ivPlayerPlay.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mVideoView != null) {
            mVideoView.start();
            mVideoView.runInForeground();
        }
//        boolean isWifiOk = Utils.isWiFiOK(this);
//        if (!isWifiOk) {
//            show(getString(R.string.check_network_plice));
//        }
        //TODO 检测网络
        targetSeekTime = -1;
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


    private IMediaPlayer.OnBufferingUpdateListener mOnBufferingUpdateListener = new IMediaPlayer.OnBufferingUpdateListener() {
        int prePercent = -1;

        @Override
        public void onBufferingUpdate(IMediaPlayer mp, int percent) {
//            long duration = mVideoView.getDuration();
//            long progress = duration * percent / 100;
////            Logger.d("progress/duration " + "==" + progress + "/" + duration);
////            if (prePercent != percent) {
////                prePercent = percent;
////                Logger.d("onBufferingUpdate：" + percent);
////            }
////            tvPlayerBufferProgress.setText("缓冲进度：" + TimeUtils.millisToString(progress)
////                    + "/" + TimeUtils.millisToString(duration));
//
            if (targetSeekTime == -1) {
                vpbPlayerProgress.setProgressBuffer(percent);
            }

        }
    };

    private IMediaPlayer.OnVideoSizeChangedListener mOnVideoSizeChangeListener = new IMediaPlayer.OnVideoSizeChangedListener() {
        @Override
        public void onVideoSizeChanged(IMediaPlayer mp, int width, int height, int sarNum, int sarDen) {
//            Logger.d(String.format("onVideoSizeChanged mp(%s),width(%s),height(%s),sarNum(%s),sarDen(%s)", mp, width, height, sarNum, sarDen));
        }
    };

    private IMediaPlayer.OnSeekCompleteListener mOnSeekCompletedListener = new IMediaPlayer.OnSeekCompleteListener() {
        @Override
        public void onSeekComplete(IMediaPlayer mp) {
            Logger.d("onSeekComplete...............");
        }
    };

    private IMediaPlayer.OnCompletionListener mOnCompletionListener = new IMediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(IMediaPlayer mp) {
            doOnPlayCompletion();
        }
    };

    /**
     * 播放结束
     */
    private void doOnPlayCompletion() {
        Logger.d("播放结束！！");
        show("播放结束！！");
//        mVideoView.release();
//        nextURL();
    }

    private IMediaPlayer.OnErrorListener mOnErrorListener = new IMediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(IMediaPlayer mp, int what, int extra) {
            Logger.e("OnErrorListener, Error:" + what + ",extra:" + extra);
            long duration = mVideoView.getDuration();
            long current = mVideoView.getCurrentPosition();

            Logger.d("duration=" + duration + " current=" + current);
            boolean hasEnd = (duration > 5000 && duration - current < 800);//剩余800毫秒以下
            if (hasEnd) {
                Logger.d("判断为播放结束");
                doOnPlayCompletion();
                return false;
            }
            switch (what) {
                case -1004:
                case -10011:
                default:
                    if (errorRetryCount < 3) {
                        doRetry();
                    } else {
                        Logger.d("网络错误请检查您的网络");
                        show("网络错误请检查您的网络！");
                    }
                    break;
            }
            return false;
        }
    };

    /**
     * 重试
     */
    private void doRetry() {
        errorRetryCount++;
        Logger.d("errorRetryCount=" + errorRetryCount);
        reLoad();
    }

    private void reLoad() {
        if (mVideoView != null)
            mVideoView.reload(mDataSource, false, KSYMediaPlayer.KSYReloadMode.KSY_RELOAD_MODE_ACCURATE);
        setProgressBar(true);
    }

    public IMediaPlayer.OnInfoListener mOnInfoListener = new IMediaPlayer.OnInfoListener() {
        @Override
        public boolean onInfo(IMediaPlayer iMediaPlayer, int i, int i1) {
            switch (i) {
                case KSYMediaPlayer.MEDIA_INFO_BUFFERING_START:
                    Logger.d("Buffering Start.");
                    isBuffering = true;
                    setProgressBar(true);
                    break;
                case KSYMediaPlayer.MEDIA_INFO_BUFFERING_END:
                    Logger.d("Buffering End.");
                    setProgressBar(false);
                    setVideoProgress(0);
                    isBuffering = false;
                    break;
                case KSYMediaPlayer.MEDIA_INFO_AUDIO_RENDERING_START:
                    Logger.d("Audio Rendering Start");
                    break;
                case KSYMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:
                    Logger.d("Video Rendering Start");
                    setProgressBar(false);
                    break;
                case KSYMediaPlayer.MEDIA_INFO_SUGGEST_RELOAD:
                    // Player find a new stream(video or audio), and we could reload the video.
                    reLoad();
                    break;
                case KSYMediaPlayer.MEDIA_INFO_RELOADED:
                    Logger.d("Succeed to reload video.");
                    setProgressBar(false);
                    return false;
            }
            return false;
        }
    };

    private IMediaPlayer.OnPreparedListener mOnPreparedListener = new IMediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(IMediaPlayer mp) {
            Logger.d("VideoPlayer——OnPrepared");
            setProgressBar(false);
//            mVideoWidth = mVideoView.getVideoWidth();
//            mVideoHeight = mVideoView.getVideoHeight();
//
//            // Set Video Scaling Mode
//            mVideoView.setVideoScalingMode(KSYMediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
//
//            //start player
//            mVideoView.start();
//
            setVideoProgress(0);
//
//            boolean debug = true;
//            if (debug) {
//                Log.e("VideoPlayer", "关闭");
//                mPlayerPosition.setVisibility(View.GONE);
//                mLoadText.setVisibility(View.GONE);
//            } else {
//                Log.e("VideoPlayer", "开启");
//                mPlayerPosition.setVisibility(View.VISIBLE);
//                mLoadText.setVisibility(View.VISIBLE);
//            }
        }
    };


    /**
     * 播放或暂停
     */
//    @OnClick(R.id.btn_player_start_or_pause)
    public void startOrPause() {
        if (mVideoView == null) {
            return;
        }
        if (!mVideoView.canPause()) {
            Logger.d("不可以暂停！");
            return;
        }
        boolean isPlaying = mVideoView.isPlaying();
        startOrPause(isPlaying);
    }

    public void startOrPause(boolean isPause) {
        if (isPause) {
            mVideoView.pause();
            ivPlayerPlay.setVisibility(View.VISIBLE);
        } else {
            mVideoView.start();
            ivPlayerPlay.setVisibility(View.GONE);
        }
    }


    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
            if (targetSeekTime != -1) {
                preExecuteSeek();
            }
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
            if (targetSeekTime != -1) {
                preExecuteSeek();
            }
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        View currView = getCurrentFocus();
        if (currView != null && currView == mVideoView) {
            mVideoView.clearFocus();
        }
//        Logger.d("keyCode=" + keyCode);
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isExit) {
                videoPlayEnd();
                finish();
            } else {
                isExit = true;
                if (!mVideoView.isPlayable()) {
                    finish();
                } else {
                    show("再次点击返回键退出播放");
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            isExit = false;
                        }
                    }, 2000);
                }
            }
            return true;
        }

        if (keyCode == KeyEvent.KEYCODE_ENTER
                || keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {//确定键
            {
                startOrPause();
                showControlView();
            }
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
            doPreSeekToForward();
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
            doPreSeekToBackward();
        }
        showControlView();

        return super.onKeyDown(keyCode, event);
    }

    /**
     * 快进
     */
    private void doPreSeekToForward() {
        boolean seekForward = mVideoView.canSeekForward();
        if (seekForward) {
            doPreSeek(1);
        }
    }

    /**
     * 快退
     */
    private void doPreSeekToBackward() {
        boolean seekForward = mVideoView.canSeekBackward();
        if (seekForward) {
            doPreSeek(-1);
        }
    }

    /**
     * @param
     */
    private void executeSeek() {
        if (targetSeekTime != -1) {
            mVideoView.seekTo(targetSeekTime);
            mVideoView.start();
        }
        targetSeekTime = -1;
    }

    /**
     * 发送执行快进的指令，加入限制间隔时间的逻辑
     */
    private void preExecuteSeek() {
        long preSeek = System.currentTimeMillis() - lastSeekTime;
        long minSeekTime = (preSeek > 1000) ? 0 : 500;
        mHandler.removeMessages(HANDLE_SEEK);
        Message msg = handler.obtainMessage();
        msg.what = HANDLE_SEEK;
        mHandler.sendMessageDelayed(msg, minSeekTime);
        lastSeekTime = System.currentTimeMillis();
    }

    long lastSeekTime;//最后执行快进、快退的时间
    long targetSeekTime = -1;//快进、快退的目标时间（根据是否-1判断是否要进行快进快退）

    /**
     * @param xiShu 系数； 前进1，后退-1
     */
    private void doPreSeek(int xiShu) {
        long currTime = targetSeekTime;
        if (currTime == -1) {
            mVideoView.pause();
            currTime = mVideoView.getCurrentPosition();
        }
        long length = mVideoView.getDuration();
        if (length == 0) {
            return;
        }
        long seekCount = (long) (length * 0.01);
        seekCount = Math.max(seekCount, 500);//每次变化最少0.5秒
        long target = currTime + xiShu * seekCount;
        if ((target >= length) || target < 1) {
            return;
        }
        vpbPlayerProgress.setProgress(length, target);
        if (targetSeekTime == -1) {//第一次设置目标时间，暂停播放
            mVideoView.pause();
        }
        targetSeekTime = target;
    }


    private void showControlView() {
        llPlayerControl.setVisibility(View.VISIBLE);
        dismissControlViewLater();
    }

    private void videoPlayEnd() {
        if (mVideoView != null) {
            mVideoView.release();
            mVideoView = null;
        }
    }

    private void dismissControlViewLater() {
        mHandler.removeMessages(HIDDEN_SEEKBAR);
        Message msg = mHandler.obtainMessage();
        msg.what = HIDDEN_SEEKBAR;
        mHandler.sendMessageDelayed(msg, HIDE_SEEKBAR_TIME);
    }


    public void show(String msg) {
        Toast.makeText(DemoPlayActivity.this, msg, Toast.LENGTH_LONG).show();
    }
}
