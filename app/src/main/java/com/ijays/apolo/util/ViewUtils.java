package com.ijays.apolo.util;

import android.content.Context;

/**
 * Created by ijaysdev on 28/05/2017.
 */

public class ViewUtils {
    public static int  dp2Px(Context context, int dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
