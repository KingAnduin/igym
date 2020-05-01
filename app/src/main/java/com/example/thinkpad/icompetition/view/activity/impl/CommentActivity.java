package com.example.thinkpad.icompetition.view.activity.impl;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.thinkpad.icompetition.R;
import com.example.thinkpad.icompetition.model.entity.order.OrderItem;
import com.example.thinkpad.icompetition.presenter.impl.CommentPresenter;
import com.example.thinkpad.icompetition.view.activity.i.IBaseActivity;
import com.example.thinkpad.icompetition.view.activity.i.ICommentActivity;

/**
 * 提交评论
 * Created By hjg on 2020/5/1
 */
public class CommentActivity extends BaseActivity<CommentPresenter>
        implements IBaseActivity, ICommentActivity,View.OnClickListener{

    private Toolbar toolbar;
    private EditText mCommentEt;
    private Button mAddBtn;
    private int order_id;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        Intent intent = getIntent();
        order_id = intent.getIntExtra("order_id", 0);
        initBar();
        findView();
        setListener();
    }

    private void initBar() {
        Toolbar mToolbar = findViewById(R.id.toolbar_main);
        mToolbar.setTitle("提交评论");
        mToolbar.setNavigationIcon(R.mipmap.back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommentActivity.this.finish();
            }
        });
    }

    private void findView(){
        mAddBtn = findViewById(R.id.btn_add_comment);
        mCommentEt = findViewById(R.id.et_comment);
    }

    private void setListener(){
        mAddBtn.setOnClickListener(this);
    }

    @Override
    protected CommentPresenter getPresenter() {
        return new CommentPresenter(this);
    }

    @Override
    public void addCommenetResponse() {
        showSnackBar(mCommentEt, "新增评论成功", getMainColor());
        finish();
    }

    @Override
    public void failBecauseNotNetworkReturn(int code) {
        showSnackBar(mCommentEt, getResources().getString(R.string.not_network),getMainColor());
    }

    public void addComment(int order_id, String comment){
        mPresenter.addComment(order_id, comment);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_add_comment ){
            String comment = mCommentEt.getText().toString().trim();
            if(comment.isEmpty()){
                showSnackBar(mCommentEt, "评论内容不能为空", getMainColor());
            }else {
                int id = order_id;
                addComment(id, comment);
            }
        }
    }
}
