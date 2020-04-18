package com.example.thinkpad.icompetition.util;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Bitmap工具类
 * created by a'su's
 */

public class BitmapUtil {
    /**
     * 压缩图片大小
     *
     * @param src       原始bitmap
     * @param newWidth  压缩后的宽度
     * @param newHeight 压缩后的高度
     * @return 压缩后的图片
     */
    public Bitmap zoomImage(Bitmap src, double newWidth, double newHeight) {
        // 获取这个图片的宽和高
        float width = src.getWidth();
        float height = src.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(src, 0, 0, (int) width, (int) height, matrix, true);
    }

    /**
     * 压缩图片
     * @param context context
     * @param uri 源图像
     * @return bitmap
     */
    public Bitmap getBitmapFromUri(Context context, Uri uri)
    {
        String[] projection = {MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null){
            return lessenPathImage(uri.getPath());
        }
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String path = cursor.getString(column_index);
        cursor.close();
        return lessenPathImage(path);
    }

    /**
     * 根据路径获取bitmap
     * @param path path
     * @return bitmap
     */
    public static Bitmap lessenPathImage(String path){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options); //此时返回 bm 为空
        options.inJustDecodeBounds = false; //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = (int) (options.outHeight / (float) 640);
        if (be <= 0) {
            be = 1;
        }
        options.inSampleSize = be; //重新读入图片，注意此时已经把 options.inJustDecodeBounds 设回 false 了
        bitmap = BitmapFactory.decodeFile(path, options);
//		int w = bitmap.getWidth();
//		int h = bitmap.getHeight();
//		System.out.println(w + " " + h); //after zoom
        return bitmap;
    }

    /**
     * 把Bitmap转Byte[]
     */
    public byte[] bitmapToBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    /**
     * bitmap的回收工具.
     *
     * @param bitmap 被回收的bitmap对象
     */
    public void bitmapRecycle(Bitmap bitmap) {
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
            bitmap = null;
        }
        System.gc();
    }

    /**
     * 把Base64字符串转bitmap
     * @param base64Data base64字符串
     * @return bitmap
     */
    public static Bitmap base64ToBitmap(String base64Data){
        byte[] bytes = Base64.decode(base64Data,Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes,0,bytes.length);
    }

}
