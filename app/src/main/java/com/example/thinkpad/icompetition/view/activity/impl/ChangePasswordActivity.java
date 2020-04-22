package com.example.thinkpad.icompetition.view.activity.impl;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.thinkpad.icompetition.R;
import com.example.thinkpad.icompetition.model.entity.user.ChangePasswordRoot;
import com.example.thinkpad.icompetition.presenter.impl.ChangePasswordPresenter;
import com.example.thinkpad.icompetition.util.NetWorkHelper;
import com.example.thinkpad.icompetition.view.activity.i.IChangePasswordActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * created by a’su's
 */
public class ChangePasswordActivity extends BaseActivity<ChangePasswordPresenter> implements IChangePasswordActivity, View.OnClickListener {
    private Intent intent;
    private Toolbar mToolbar;
    private TextView mTitleTV;
    private TextView mUserNumTV;
    private EditText mOldPasswordET;
    private EditText mNewPasswordET;
    private EditText mSurePasswordET;
    private Button mChangePasswordBT;
    private ProgressDialog mProgressDialog;
    private InputMethodManager imm;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        imm= (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        intent=getIntent();
        findView();
        setListener();
    }

    private void setListener() {
        mChangePasswordBT.setOnClickListener(this);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void findView() {
        mToolbar = findViewById(R.id.toolbar_main);
        mTitleTV = findViewById(R.id.tv_toolbar_title);
        mUserNumTV = findViewById(R.id.tv_user_num);
        mOldPasswordET = findViewById(R.id.et_old_password);
        mNewPasswordET = findViewById(R.id.et_new_password);
        mSurePasswordET = findViewById(R.id.et_sure_new_password);
        mChangePasswordBT = findViewById(R.id.btn_change_password);
        mToolbar.setNavigationIcon(R.mipmap.back);
        mTitleTV.setText(R.string.password_change);
        if(intent.getStringExtra("user_num")!=null) {
            mUserNumTV.setText(intent.getStringExtra("user_num"));
        }
    }

    /**
     * 得到修改密码所需参数对应的当前页面信息
     */
    private void getCurrentPageInfor(){
        //String uerNum = mUserNumTV.getText().toString();
        String oldPassword = mOldPasswordET.getText().toString();
        String newPassword = mNewPasswordET.getText().toString();
        String surePassword = mSurePasswordET.getText().toString();
        if(judgeOldandNewPassword(oldPassword,newPassword,surePassword)){
            changePassword(oldPassword,newPassword);//更改密码
        }
    }

    /**
     * 对填写的新旧密码进行判断之后再判断2次输入的新密码是否一致
     * 符合要求后返回true
     */
    private boolean judgeOldandNewPassword(String oldPassword,String newPassword,String surePassword) {
        if (oldPassword.isEmpty()){
            showSnackBar(mOldPasswordET,getResources().getString(R.string.not_input_old_password),getMainColor());
            return false;
        }
        else if(newPassword.isEmpty()){
            showSnackBar(mNewPasswordET,getResources().getString(R.string.not_input_new_password),getMainColor());
            return false;
        }
        else if(surePassword.isEmpty()){
            showSnackBar(mNewPasswordET,getResources().getString(R.string.not_input_sure_password),getMainColor());
            return false;
        }
        else if(!surePassword.equals(newPassword)){
            showSnackBar(mNewPasswordET,getResources().getString(R.string.new_password_notmatch),getMainColor());
            return false;
        }
        else {
            if (!isUserfulPassword(newPassword)) {
                showSnackBar(mNewPasswordET, getResources().getString(R.string.new_password_error), getMainColor());
                return false;
            }
        }
        return true;
    }

    /**
     * 校验密码6到16位大小写字母或数字组成
     */
    public boolean isUserfulPassword(String password){
        String regExp="^([a-z]|[A-Z]|[0-9]){6,16}$";
        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher=pattern.matcher(password);
        return matcher.matches();
    }

    @Override
    public void failBecauseNotNetworkReturn(int code) {
        dismissDialog();
        showSnackBar(mChangePasswordBT,getString(R.string.not_network),getMainColor());
    }

    @Override
    protected ChangePasswordPresenter getPresenter() {
        return new ChangePasswordPresenter(this);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        if(NetWorkHelper.isNetworkAvailable(this)){
            mPresenter.changePassword(oldPassword,newPassword);
            showProgressBarDialog();
        }
        else{
            showSnackBar(mOldPasswordET,getString(R.string.not_have_network),getMainColor());
        }
    }

    //修改密码后的回调
    @Override
    public void changePasswordReturn(ChangePasswordRoot root) {
        dismissDialog();
        if (root.getCode()==2){
            showSnackBar(mOldPasswordET,getString(R.string.old_password_notmatch),getMainColor());
        }
        if (root.getCode()==200){
            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("user_login",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("user_login_pwd",mNewPasswordET.getText().toString());
            editor.apply();
            showSnackBar(mOldPasswordET,getString(R.string.change_password_success),getMainColor());
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_change_password:
                //若软键盘已经打开，则关闭软键盘
//                if(imm.isActive()){
//                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
//                }
                getCurrentPageInfor();
                break;
        }
    }

    /**
     * dismissDialog取消dialog
     */
    private void dismissDialog(){
        if (mProgressDialog == null){
            return;
        }
        if (mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
        }
    }

    /**
     * 显示dialog
     */
    private void showProgressBarDialog(){
        try {
            if (mProgressDialog == null){
                mProgressDialog = ProgressDialog.show(this,"提示","密码修改中...请稍后");
                mProgressDialog.setCancelable(true);
            }
            if (mProgressDialog.isShowing() || isFinishing()){
                return;
            }
            mProgressDialog.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
