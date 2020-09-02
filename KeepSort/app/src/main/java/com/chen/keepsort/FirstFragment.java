package com.chen.keepsort;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.alibaba.fastjson.JSON;
import com.chen.keepsort.bean.SportGroup;
import com.chen.keepsort.bean.SportItem;
import com.chen.keepsort.xunfei.XunFeiVoiceReadHelper;

import java.util.ArrayList;
import java.util.List;

public class FirstFragment extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        soundPoolUtils = SoundPoolUtils.getInstance(getContext());
        initData();
        initViewS(view);
        initListener();
    }

    SportGroup sportGroup;

    private void initData() {
        voiceHelper = new XunFeiVoiceReadHelper(getContext());
        sportGroup = SportGroupManager.getInstance().loadCurrentGroup();
        if (sportGroup == null) {
            Toast.makeText(getContext(), "列表为空，请新建运动组", Toast.LENGTH_SHORT).show();
        }
    }

    SoundPoolUtils soundPoolUtils;

    private void initListener() {
        sport.setListener(new Sport.ITimeChaneListener() {
            @Override
            public void notifyTime(int seconds, String timeDuring) {

                tvTimeDuring.post(new Runnable() {
                    @Override
                    public void run() {

                        tvTimeDuring.setText(timeDuring);
                        if (sportGroup == null) {
                            return;
                        }
                        int colorRest = Color.parseColor("#00FA9A");
                        int colorSport = Color.parseColor("#EE2C2C");

                        boolean isStartSport = false;
                        boolean isStartRest = false;
                        List<SportItem> list = sportGroup.getList();
                        int time = 0;
                        String name = "";
                        boolean isSporing = true;
                        //判断当前时间是哪个项，运动还是休息
                        for (SportItem item : list) {
                            int itemStart = time;
                            time += item.getDuring();
                            time += item.getRestTime();
                            int itemEnd = time;
                            if (seconds >= itemStart && seconds < itemEnd) {
                                name = item.getName();
                                int x = seconds - itemStart;
                                if (x == 0) {
                                    isStartSport = true;
                                } else if (x == item.getDuring()) {
                                    isStartRest = true;
                                }
                                if (x < item.getDuring()) {
                                    isSporing = true;
                                } else {
                                    isSporing = false;
                                }
                                //不能break,要计算总时间
                            }
                        }

                        Logger.d("time=" + seconds);

                        if (time == seconds) {
                            btnStop.callOnClick();
                            Logger.d("恭喜你，游戏结束");
                            tvTimeDuring.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    voiceHelper.readText("太棒了，你完成了一组动作");
                                }
                            }, 2000);
                        } else if (isStartSport) {
                            Logger.d("开始运动");
                            String finalName = name;
                            tvTimeDuring.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    voiceHelper.readText(finalName);
                                }
                            }, 1000);
                            soundPoolUtils.startVideoAndVibrator(R.raw.notice_b, 1000);
                        } else if (isStartRest) {
                            Logger.d("开始休息");
                            soundPoolUtils.playVideo(R.raw.notice_b);
                        } else {
                            if (seconds % 30 == 0) {
                                Logger.d("30s提示");
                                soundPoolUtils.playVideo(R.raw.notice);
                            }
                        }
                        tv_sub_title.setText(name);
                        tv_sub_title.setBackgroundColor(isSporing ? colorSport : colorRest);
                    }
                });
            }
        });
    }

    XunFeiVoiceReadHelper voiceHelper;
    Sport sport = new Sport();

    TextView tvTimeDuring;
    TextView tv_sport_describe;
    TextView tv_title;
    TextView tv_sub_title;
    Button btnStart;
    Button btnStop;

    private void initViewS(View view) {
        tvTimeDuring = view.findViewById(R.id.tv_time_during);
        tv_sport_describe = view.findViewById(R.id.tv_sport_describe);
        tv_title = view.findViewById(R.id.tv_title);
        tv_sub_title = view.findViewById(R.id.tv_sub_title);
        btnStart = view.findViewById(R.id.button_start);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sport.status == Sport.STATUS.sporing) {
                    sport.pause();
                    btnStart.setText("继续");
                    btnStop.setVisibility(View.VISIBLE);
                } else {
                    if (sport.status == Sport.STATUS.NULL) {
                        soundPoolUtils.playVideo(R.raw.end);
                    }
                    sport.start();
                    btnStart.setText("暂停");
                    btnStop.setVisibility(View.GONE);
                }
            }
        });
        btnStop = view.findViewById(R.id.button_stop);
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sport.stop();
                btnStart.setText("重新开始");
                btnStop.setVisibility(View.GONE);
                soundPoolUtils.playVideo(R.raw.end);
            }
        });
        tvTimeDuring.setText("00:00");
        btnStart.setText("开始");
        btnStop.setVisibility(View.GONE);
        reShowContent();
    }

    private void reShowContent() {
        if (sportGroup == null) {
            return;
        }
        StringBuffer sb = new StringBuffer();
        List<SportItem> list = sportGroup.getList();
        sb.append("运动：" + sportGroup.getName() + "\n");
        int timeDuring = 0;
        int timeRest = 0;

        for (SportItem item : list) {
            timeDuring += item.getDuring();
            timeRest += item.getRestTime();
        }
        int timeTotal = timeDuring + timeRest;
        sb.append("时长：" + Sport.timeFormat(timeTotal * 1000));
        sb.append("  运动" + Sport.timeFormat(timeDuring * 1000));
        sb.append("  休息" + Sport.timeFormat(timeRest * 1000) + "\n");
        sb.append("运动项目：\n");
        int x = 1;
        for (SportItem item : list) {
            sb.append((x++) + ":" + item.getName() + String.format("(%s+%s)", item.getDuring(), item.getRestTime()) + "\n");
        }
        tv_title.setText(sportGroup.getName());
        if (!list.isEmpty()) {
            tv_sub_title.setText(list.get(0).getName());
        }
        tv_sport_describe.setText(sb);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sport.stop();
    }
}