package com.chen.keepsort;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class FirstFragmentBackup extends Fragment {

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
        initViewS(view);
        initListener();
    }

    SoundPoolUtils soundPoolUtils;

    private void initListener() {
        sport.setListener(new Sport.ITimeChaneListener() {
            @Override
            public void notifyTime(int seconds, String timeDuring) {
                if (seconds % 30 == 0) {
                    soundPoolUtils.startVideoAndVibrator(R.raw.notice_b, 1000);
                }
                tvTimeDuring.post(new Runnable() {
                    @Override
                    public void run() {
                        tvTimeDuring.setText(timeDuring);
                    }
                });
            }
        });
    }

    Sport sport = new Sport();

    TextView tvTimeDuring;
    Button btnStart;
    Button btnStop;

    private void initViewS(View view) {
        tvTimeDuring = view.findViewById(R.id.tv_time_during);
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
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sport.stop();
    }
}