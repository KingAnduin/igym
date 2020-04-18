package com.example.thinkpad.icompetition.view.activity.impl;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.thinkpad.icompetition.R;
import com.example.thinkpad.icompetition.model.entity.user.RegisterRoot;
import com.example.thinkpad.icompetition.model.event.RegisterEvent;
import com.example.thinkpad.icompetition.presenter.impl.RegisterPresenter;
import com.example.thinkpad.icompetition.util.NetWorkHelper;
import com.example.thinkpad.icompetition.view.activity.i.IRegisterActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Create By a'su's on 2018/11/27
 * 注册界面
 */
public class RegisterActivity extends BaseActivity<RegisterPresenter> implements IRegisterActivity,View.OnClickListener {

    private TextView mRegisterToLoginTV;
    private EditText mRegisterIdET;
    private EditText mRegisterPasswordET;
    private EditText mRegisterSurePasswordET;
    private Button mRegisterBtn;
    private String mUserID;
    private String mPassword;
    private ProgressDialog mProgressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findView();
        setListener();
    }

    private void setListener() {
        mRegisterBtn.setOnClickListener(this);
        mRegisterIdET.setOnClickListener(this);
        mRegisterPasswordET.setOnClickListener(this);
        mRegisterToLoginTV.setOnClickListener(this);
        mRegisterSurePasswordET.setOnClickListener(this);
    }

    private void findView() {
        mRegisterBtn=findViewById(R.id.btn_register);
        mRegisterIdET=findViewById(R.id.et_register_userID);
        mRegisterPasswordET=findViewById(R.id.et_register_password);
        mRegisterToLoginTV=findViewById(R.id.tv_register_tologin);
        mRegisterSurePasswordET=findViewById(R.id.et_register_surepassword);
    }

    @Override
    protected RegisterPresenter getPresenter() {
        return new RegisterPresenter(this);
    }

    //注册的回调
    @Override
    public void registeredReturn(RegisterRoot root) {
        dismissDialog();
        if(root.getCode()==1) {
            showSnackBar(mRegisterBtn, getResources().getString(R.string.register_repeat), getMainColor());
        }
        else if (root.getCode()==200){
            showSnackBar(mRegisterBtn, getResources().getString(R.string.register_success), getMainColor());
        }
        clearEditText();
    }

    private void clearEditText() {
        mRegisterIdET.setText(null);
        mRegisterPasswordET.setText(null);
        mRegisterSurePasswordET.setText(null);
    }

    @Override
    public void failBecauseNotNetworkReturn(int code) {
        dismissDialog();
        showToast(getResources().getString(R.string.not_network));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_register:
                registerUser();
                break;
            case R.id.tv_register_tologin:
                //startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                finish();
        }
    }

    private void registerUser() {
        if(NetWorkHelper.isNetworkAvailable(this)) {
            if (judgeIDandPassword()) {
                String surePassword = mRegisterSurePasswordET.getText().toString();
                if (matchPassword(surePassword)) {
                    mUserID = mRegisterIdET.getText().toString();
                    mPassword = mRegisterPasswordET.getText().toString();
                    mPresenter.register(mUserID, mPassword);
                    showProgressBarDialog();
                }
            }
        }else {
            showSnackBar(mRegisterBtn,getResources().getString(R.string.not_have_network),getMainColor());
        }
    }

    /**
     * 对填写的账户与密码进行判断
     */
    private boolean judgeIDandPassword() {
        if (mRegisterIdET.getText().toString().isEmpty()){
            showSnackBar(mRegisterBtn,getResources().getString(R.string.register_userid_notnull),getMainColor());
            return false;
        }
        else if(mRegisterPasswordET.getText().toString().isEmpty()){
            showSnackBar(mRegisterBtn,getResources().getString(R.string.register_password_notnull),getMainColor());
            return false;
        }
        else {
            if (!isMobile(mRegisterIdET.getText().toString())) {
                showSnackBar(mRegisterBtn, getResources().getString(R.string.register_userid_error), getMainColor());
                return false;
            } else if (!isUserfulPassword(mRegisterPasswordET.getText().toString())) {
                showSnackBar(mRegisterBtn, getResources().getString(R.string.register_password_error), getMainColor());
                return false;
            }
        }
        return true;
    }

    /**
     * 校验手机号
     */
    public boolean isMobile(String mobile){
        String regExp="^((13[0-9])|(15[^4])|(166)|(17[0-8])|(18[0-9])|(19[8-9])|(147,145))\\d{8}$";
        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher=pattern.matcher(mobile);
        return matcher.matches();
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

    /**
     * 两次密码匹配比较
     */
    public boolean matchPassword(String surePassword){
        if(!mRegisterPasswordET.getText().toString().equals(surePassword)){
            showSnackBar(mRegisterBtn,getResources().getString(R.string.register_nomatch_password),getMainColor());
            mRegisterSurePasswordET.setText(null);
            return false;
        }
        return true;
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
                mProgressDialog = ProgressDialog.show(this,"提示","注册中...请稍后");
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
