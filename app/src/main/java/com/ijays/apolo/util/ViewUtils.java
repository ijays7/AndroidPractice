package com.ijays.apolo.util;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;

/**
 * Created by ijaysdev on 28/05/2017.
 */

public class ViewUtils {
    private static int widthPixels = -1;
    private static int heightPixels = -1;

    public static int dp2Px(Context context, int dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int sp2px(Context context, int spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, context.getResources().getDisplayMetrics());

    }

    public static int resolveGravity(int gravity) {
        return gravity == Gravity.NO_GRAVITY ? Gravity.START | Gravity.TOP : gravity;
    }

    public static int getScreenWidth(Context context) {
        if (widthPixels <= 0) {
            widthPixels = context.getResources().getDisplayMetrics().widthPixels;
        }
        return widthPixels;
    }


    public static int getScreenHeight(Context context) {
        if (heightPixels <= 0) {
            heightPixels = context.getResources().getDisplayMetrics().heightPixels;
        }
        return heightPixels;
    }
}
