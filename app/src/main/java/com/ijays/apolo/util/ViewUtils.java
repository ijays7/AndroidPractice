package com.ijays.apolo.util;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;

/**
 * Created by ijaysdev on 28/05/2017.
 */

public class ViewUtils {
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
}
