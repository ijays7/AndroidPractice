package com.ijays.apolo.util;

import android.content.Context;
import android.util.TypedValue;

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
}
