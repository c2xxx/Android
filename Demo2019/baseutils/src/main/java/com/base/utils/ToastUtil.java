package com.base.utils;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;
import android.widget.Toast;

import com.base.AppContext;
import com.base.R;


public class ToastUtil {
    private static Toast toast;

    public static void show(String msg) {
        show(AppContext.getContext(), msg);
    }

    public static void show(Context context, String msg) {
        Logger.d("toast", msg);
        if (toast != null) {
            try {
                toast.cancel();
            } catch (Exception e) {
            }
        }
        if (context == null) {
            Logger.e("toast", "context is null");
            return;
        }
        toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        TextView view = new TextView(context);
        view.setText(msg);
        view.setTextColor(Color.WHITE);
        view.setTextSize(14);
        view.setBackgroundResource(R.drawable.toast_bg);
        toast.setView(view);
        toast.show();
    }
}
