package demo.playerlib.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import demo.playerlib.R;


/**
 * Created by ChenHui on 2017/3/14.
 */

public class VideoProgressBar extends RelativeLayout {
    private TextView tvVideoProgressbarCurr;
    private TextView tvVideoProgressbarTotal;
    private SeekBar sbVideoProgressbar;
    private SeekBar sbVideoProgressbarIcon;
    private View llVideoProgressbarText;

    private void assignViews(View view) {
        tvVideoProgressbarCurr = (TextView) view.findViewById(R.id.tv_video_progressbar_curr);
        llVideoProgressbarText = view.findViewById(R.id.ll_video_progressbar_text);
        tvVideoProgressbarTotal = (TextView) view.findViewById(R.id.tv_video_progressbar_total);
        sbVideoProgressbar = (SeekBar) view.findViewById(R.id.sb_video_progressbar);
        sbVideoProgressbarIcon = (SeekBar) view.findViewById(R.id.sb_video_progressbar_icon);
    }


    public VideoProgressBar(Context context) {
        this(context, null, 0);
    }

    public VideoProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VideoProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View view = View.inflate(context, R.layout.view_video_progress, null);
        assignViews(view);
        addView(view);
        setProgress(0, 0);
        setProgressBuffer(0);
    }

    /**
     * @param duration 总时长
     * @param time     当前播放
     */
    public void setProgress(long duration, long time) {
        String strProgress = millisToString(time, false);
        String strDuration = millisToString(duration, false);
        tvVideoProgressbarCurr.setText(strProgress);
        tvVideoProgressbarTotal.setText(strDuration);
        double percent = Math.ceil(time * 100.0 / duration);
        percent = Math.max(percent, 0);
        percent = Math.min(percent, 100);
//        Logger.d("进度百分比：" + percent);
        sbVideoProgressbar.setProgress((int) percent);
        sbVideoProgressbarIcon.setProgress((int) percent);
    }

    /**
     * @param percent 缓冲进度百分比
     */
    public void setProgressBuffer(int percent) {
        percent = Math.min(percent, 100);
        percent = Math.max(percent, 0);
        sbVideoProgressbar.setSecondaryProgress(percent);
    }

    static String millisToString(long millis, boolean text) {
        boolean negative = millis < 0;
        millis = Math.abs(millis);

        millis /= 1000;
        int sec = (int) (millis % 60);
        millis /= 60;
        int min = (int) (millis % 60);
        millis /= 60;
        int hours = (int) millis;

        String time;
        DecimalFormat format = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        format.applyPattern("00");
        if (text) {
            if (millis > 0)
                time = (negative ? "-" : "") + hours + "h" + format.format(min) + "min";
            else if (min > 0)
                time = (negative ? "-" : "") + min + "min";
            else
                time = (negative ? "-" : "") + sec + "s";
        } else {
            if (millis > 0)
                time = (negative ? "-" : "") + hours + ":" + format.format(min) + ":" + format.format(sec);
            else
                time = (negative ? "-" : "") + min + ":" + format.format(sec);
        }
        return time;
    }

    public String getProgressText() {
        String currText = tvVideoProgressbarCurr.getText().toString();
        String totalText = tvVideoProgressbarTotal.getText().toString();
        return String.format("%s / %s", currText, totalText);
    }

    public void setTextAreaVisible(boolean isVisible) {
        llVideoProgressbarText.setVisibility(isVisible ? VISIBLE : GONE);
    }
}
