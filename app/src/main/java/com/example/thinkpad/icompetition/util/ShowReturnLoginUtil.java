package com.example.thinkpad.icompetition.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.example.thinkpad.icompetition.view.activity.impl.LoginActivity;
import com.example.thinkpad.icompetition.view.activity.impl.UserBySearchActivity;

public class ShowReturnLoginUtil {
    private AlertDialog.Builder dialog;
    private Context context;
    public ShowReturnLoginUtil(Context context){
        this.context=context;
        initDialog();
    }

    private void initDialog() {
        dialog = new AlertDialog.Builder(context)
                .setTitle("提示")
                .setMessage("你还没有登录呢，点击确认返回登陆界面~")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(context,LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
    }
    public void show(){
        if(dialog!=null){
            dialog.show();
        }
    }
}
