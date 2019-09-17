package com.chen.demo2019.test;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;

import com.base.utils.Logger;

import java.io.IOException;

public class LocalMp3Player {


    public enum MP3_TYPE {
        APP_START
    }


    public void mp3Play(final Context context, MP3_TYPE mp3_type) {
        Logger.d("播放本地音频 " + mp3_type);
        if (mp3_type == MP3_TYPE.APP_START) {
            mp3Play(context, "mp3/hemiaohemiao.mp3");
        }
    }

    public void mp3Play(final Context context, final String url) {
        new AsyncTask<Void, Void, Void>() {

            private MediaPlayer mp;

            @Override
            protected Void doInBackground(Void... voids) {
                Logger.d("播放本地音频21 ");
                try {
                    AssetManager am = context.getAssets();
                    AssetFileDescriptor afd = am.openFd(url);
                    mp = new MediaPlayer();
                    mp.setDataSource(afd.getFileDescriptor());
                    mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mp.start();
                            Logger.d("播放本地音频22 ");
                        }
                    });
                    mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            mp.release();
                            Logger.d("播放本地音频23 ");
                        }
                    });
                    mp.prepare();
                    Logger.d("播放本地音频24 ");
                } catch (IOException e) {
                    Logger.e(e);
                }
                return null;
            }
        }.execute();
    }


//    //TODO  开启了一个异步任务
//    class MyTask extends AsyncTask<Void, Void, Void> {
//
//        @Override //操作之前-- MainThread
//        protected void onPreExecute() {
//            super.onPreExecute();
//            instance = Voice_Utils.getInstance();
//        }
//
//        @Override //耗时操作在这里
//        protected Void doInBackground(Void... voids) {
//            //缓存
//            if (!ClassPathResource.isEmptyOrNull(urls)) {
//                HttpProxyCacheServer proxy = MyApplication.getProxy(Services.this);
//                proxyUrl = proxy.getProxyUrl(urls);
//
//
//                Share.d("proxyUrl" + proxyUrl);
//
//
//                if (urls != null && mMediaPlayer == null) {//判断执行如下操作-创建媒体播放类
//                    mMediaPlayer = MediaPlayer.create(C, Uri.parse(proxyUrl));
//                } else if (urls != null && mMediaPlayer != null) {
//                    String mp3 = (String) SharePrenfencesUtil.get(Services.this, "mp3", urls);
//                    if (mp3.equals(urls)) {
//                        Log.e("The", "Same");//同步播放进度
//                    } else {
//                        try {
//                            mMediaPlayer.reset();//重置
//                            mMediaPlayer.setDataSource(Services.this, Uri.parse(proxyUrl));
//                            try {
//                                mMediaPlayer.prepare();
//                            } catch (IOException e) {
//                                Log.e("media_player", "-prepare-");
//                            } catch (IllegalStateException e) {
//                                e.printStackTrace();
//                            }
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }
//            return null;
//        }
//
//
//        //此方法为随时更新函数，使用需在上面调用 publicProgress();
//        @Override
//        protected void onProgressUpdate(Void... values) {
//            super.onProgressUpdate(values);
//        }
//
//        @Override //操作完成-- MainThread
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//
//
//            if (mMediaPlayer != null) {
//                //如果他ture说明没有创建播放器  让手动点击创建播放
//                if (type.equals("auto")) {
//                    instance.play(mMediaPlayer);//播放响应1
//                    sendBroadcast(new Intent("playing"));
//                }
//
//
//            }
//            if (urls != null) {
//                SharePrenfencesUtil.put(Services.this, "mp3", urls);
//            }
//            try { //回调
//                Share_utils.getInstance().getMedia_playerCallBack().ComingPlayer(mMediaPlayer);
//            } catch (Exception e) {
//                Log.e("TAG", "ZhiXin ComingPlayer is null");
//            }
//        }
//    }

}
