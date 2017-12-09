package com.ijays.apolo.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.View;


import java.io.File;
import java.io.FileOutputStream;

/**
 * ScrollView截图的工具类
 * Created by ijaysdev on 25/11/2017.
 */

public class ScrollUtil {

    public static Bitmap shotScrollView(NestedScrollView scrollView) {
        int h = 0;
        Bitmap bitmap = null;
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
            scrollView.getChildAt(i).setBackgroundColor(Color.parseColor("#ffffff"));
        }
        bitmap = Bitmap.createBitmap(scrollView.getWidth(), h, Bitmap.Config.RGB_565);
        final Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);
        return bitmap;
    }

    public static String saveImageToFile(String path, Bitmap bitmap, int compressQuolity) {
        return saveImageToFile(path, bitmap, compressQuolity, Bitmap.CompressFormat.JPEG);
    }

    public static String saveImageToFile(String path, Bitmap bitmap, int compressQuolity,
                                         Bitmap.CompressFormat compressFormat) {
        try {
            File jpgFile = getFile(path);
            Log.e("SrollUtil", "jpg save path -->" + jpgFile.getAbsolutePath());
            FileOutputStream outputStream = new FileOutputStream(jpgFile); // 文件输出流
            bitmap.compress(compressFormat, compressQuolity, outputStream);
            outputStream.close();
            return jpgFile.getAbsolutePath();
        } catch (Exception e) {
            Log.e("SrollUtil", "e-->" + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public static File getFile(String path) {
        File file = new File(path);
        File dir = new File(file.getParent());
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return file;
    }

    /**
     * @param first
     * @param second 拼接三个Bitmap 成一个Bitmap
     * @param third
     * @return
     */
    public static Bitmap addBitmap(Bitmap first, Bitmap second, Bitmap third) {
        int width = second.getWidth();
        int height = first.getHeight() + second.getHeight() + third.getHeight();
        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(first, 0, 0, null);
        canvas.drawBitmap(second, 0, first.getHeight(), null);
        canvas.drawBitmap(third, 0, first.getHeight() + second.getHeight(), null);
        return result;
    }

    /**
     * @param addViewContent 将View转换成Bitmap
     * @return
     */
    public static Bitmap getViewBitmap(View addViewContent) {

        addViewContent.setDrawingCacheEnabled(true);

        addViewContent.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        addViewContent.layout(0, 0,
                ViewUtils.getScreenWidth(addViewContent.getContext()),
                addViewContent.getMeasuredHeight());

        addViewContent.buildDrawingCache();
        Bitmap cacheBitmap = addViewContent.getDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);

        return bitmap;
    }
}
