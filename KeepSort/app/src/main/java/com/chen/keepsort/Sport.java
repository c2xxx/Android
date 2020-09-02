package com.chen.keepsort;

public class Sport {

    public interface STATUS {
        int NULL = 0;
        int sporing = 1;
        int pause = 3;
        int finish = 4;
    }

    public long startTime;
    public long stopTime;
    public int sportSeconds = 0;
    public int status;//0未开始，1正在。2.暂停，4，结束

    public void start() {
        if (status == STATUS.NULL || status == STATUS.finish) {
            sportSeconds = 0;
            doStart();
            startTime = System.currentTimeMillis();
            stopTime = 0;
        }
        status = STATUS.sporing;
        notifyTimeChange();
    }

    public void pause() {
        status = STATUS.pause;
    }

    private void doStart() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (status != STATUS.finish) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (status == STATUS.sporing) {
                        sportSeconds++;
                        notifyTimeChange();
                    }
                }
            }
        }).start();
    }

    ITimeChaneListener listener;

    public ITimeChaneListener getListener() {
        return listener;
    }

    public void setListener(ITimeChaneListener listener) {
        this.listener = listener;
    }

    private void notifyTimeChange() {
        if (listener != null) {
            listener.notifyTime(sportSeconds, timeFormat(sportSeconds * 1000));
        }
    }

    public void stop() {
        stopTime = System.currentTimeMillis();
        status = STATUS.finish;
    }

    public static String timeFormat(long mills) {
        int seconds = (int) (mills / 1000);
        int showSeconds = seconds % 60;
        int minutes = seconds / 60;
        int showMinutes = minutes % 60;
        int hours = minutes / 60;
        if (hours == 0) {
            return String.format("%02d:%02d", showMinutes, showSeconds);
        }
        return String.format("%02d:%02d:%02d", hours, showMinutes, showSeconds);
    }

    public interface ITimeChaneListener {
        void notifyTime(int seconds, String timeDuring);
    }
}
