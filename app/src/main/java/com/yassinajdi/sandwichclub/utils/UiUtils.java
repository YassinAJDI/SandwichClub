package com.yassinajdi.sandwichclub.utils;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

public class UiUtils {

    public static void setTextViewDrawableColor(Context context, TextView textView, int color) {
        for (Drawable drawable : textView.getCompoundDrawables()) {
            if (drawable != null) {
                drawable.setColorFilter(new PorterDuffColorFilter(
                        ContextCompat.getColor(context, color), PorterDuff.Mode.SRC_IN));
            }
        }
    }
}
