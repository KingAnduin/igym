package com.example.thinkpad.icompetition.view.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created By hjg on 2018/12/15
 * 分享
 */
public class Share {
    private Context context;

    public Share(Context context) {
        this.context = context;
    }


    /**
     * 不携带屏幕截图
     */
    public void share() {
        share("我正在使用i竞赛，感觉非常好用，小伙伴们快来试试吧！", null);
    }



    /**
     * 携带屏幕截图
     * @param content 分享时携带的文本
     */

    public void share(String content) {
        // 截取当前屏幕
        View view = ((Activity) context).getWindow().getDecorView();
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        view.layout(0, 0, dm.widthPixels, dm.heightPixels);

        // 允许当前窗口保存缓存信息，这样getDrawingCache()方法才会返回一个Bitmap
        view.setDrawingCacheEnabled(true);
        Bitmap bmp = Bitmap.createBitmap(view.getDrawingCache(true));
        share(content, bmp);
    }

    /**
     * 分享时传入文字和截图
     *
     * @param content 分享时携带的文字
     * @param bmp     分享时携带的图片
     */
    public void share(String content, Bitmap bmp) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/*");

//        if (bmp != null) {
//            File file = new File(context.getExternalFilesDir(null) + "share.png");
//            Uri uri = null;
//            try {
//                FileOutputStream outputStream = new FileOutputStream(file);
//                bmp.compress(Bitmap.CompressFormat.PNG, 90, outputStream);
//                outputStream.close();
//                uri = Uri.fromFile(file);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            intent.putExtra(Intent.EXTRA_STREAM, uri);
//            intent.setType("image/*");
//        }

        intent.putExtra(Intent.EXTRA_TEXT, content);
        context.startActivity(intent);
    }

}
