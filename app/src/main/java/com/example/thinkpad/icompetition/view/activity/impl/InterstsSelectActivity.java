package com.example.thinkpad.icompetition.view.activity.impl;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.thinkpad.icompetition.IcompetitionApplication;
import com.example.thinkpad.icompetition.R;
import com.example.thinkpad.icompetition.model.entity.Interest.Interestroot;
import com.example.thinkpad.icompetition.model.entity.user.UserInforBean;
import com.example.thinkpad.icompetition.presenter.impl.InterstsSelectActivityPresenter;
import com.example.thinkpad.icompetition.util.NetWorkHelper;
import com.example.thinkpad.icompetition.view.activity.i.IInterstsSelectActivity;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.TextOutsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;
import com.nightonke.boommenu.Util;

import java.util.ArrayList;
import java.util.List;

import greendao.GreenDaoHelper;
import greendao.gen.DaoSession;
import greendao.gen.UserInforBeanDao;

public class InterstsSelectActivity extends BaseActivity<InterstsSelectActivityPresenter> implements IInterstsSelectActivity {
    private Toolbar mToolbar;
    private TextView mTitleTV;
    private GridLayout mGridLayout;
    private RelativeLayout relativelayout;
    private List<String> typeList = new ArrayList<>();
    private List<String> userInterestList = new ArrayList<>();
    private int[] testSelected;
    private String selecedIntersts="";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interests_selected);
        findView();
        setListener();
        getInterestType();
        getUserInterest(1,10);
    }

    @Override
    protected InterstsSelectActivityPresenter getPresenter() {
        return new InterstsSelectActivityPresenter(this);
    }

    private void setListener() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void findView() {
        relativelayout = findViewById(R.id.relativelayout);
        mToolbar = findViewById(R.id.toolbar_main);
        mTitleTV = findViewById(R.id.tv_toolbar_title);
        mGridLayout = findViewById(R.id.gridlayout_interest);
        mToolbar.setNavigationIcon(R.mipmap.back);
        mTitleTV.setText("我的兴趣");
        setSupportActionBar(mToolbar);
    }

    private void drawBoomMenu(final int start) {
        final int nextStart;
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lp.addRule(RelativeLayout.ALIGN_PARENT_END);
        lp.setMargins(0,0,dip2px(this,20),dip2px(this,20));
        final BoomMenuButton boomMenuButton = new BoomMenuButton(this);
        boomMenuButton.setButtonEnum(ButtonEnum.TextOutsideCircle);
        boomMenuButton.setNormalColor(getResources().getColor(R.color.app_theme));
        boomMenuButton.setHighlightedColor(getResources().getColor(R.color.boomHighLight));
        if(typeList.size()-start-1>8){
            setBoomMenuType(boomMenuButton,9);
            nextStart = start+8;
        }else {
            setBoomMenuType(boomMenuButton,typeList.size()-start+1);
            nextStart = 0;
        }
        boomMenuButton.setButtonBottomMargin(40);
        for (int i = 0; i < boomMenuButton.getPiecePlaceEnum().pieceNumber()-1; i++) {
            final int finalI = i;
            boomMenuButton.addBuilder(new TextOutsideCircleButton.Builder()
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {
                            addInterest(typeList.get(index+start));
                        }
                    })
                    .textSize(12)
                    .imageRect(new Rect(Util.dp2px(20), Util.dp2px(20), Util.dp2px(60), Util.dp2px(60)))
                    .normalImageRes(R.mipmap.ic_interest)
                    .normalText(typeList.get(i+start)));
        }
        boomMenuButton.addBuilder(new TextOutsideCircleButton.Builder()
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        boomMenuButton.removeAllViews();
                        drawBoomMenu(nextStart);
                    }
                })
                .textSize(12)
                .imageRect(new Rect(Util.dp2px(20), Util.dp2px(20), Util.dp2px(60), Util.dp2px(60)))
                .normalImageRes(R.mipmap.ic_change)
                .normalText("换一批"));
        boomMenuButton.setLayoutParams(lp);
        relativelayout.addView(boomMenuButton);
    }

    private void setBoomMenuType(BoomMenuButton boomMenuButton,int num) {
        switch (num){
            case 1:
                boomMenuButton.setPiecePlaceEnum(PiecePlaceEnum.DOT_1);
                boomMenuButton.setButtonPlaceEnum(ButtonPlaceEnum.SC_1);
                break;
            case 2:
                boomMenuButton.setPiecePlaceEnum(PiecePlaceEnum.DOT_2_1);
                boomMenuButton.setButtonPlaceEnum(ButtonPlaceEnum.SC_2_1);
                break;
            case 3:
                boomMenuButton.setPiecePlaceEnum(PiecePlaceEnum.DOT_3_1);
                boomMenuButton.setButtonPlaceEnum(ButtonPlaceEnum.SC_3_1);
                break;
            case 4:
                boomMenuButton.setPiecePlaceEnum(PiecePlaceEnum.DOT_4_1);
                boomMenuButton.setButtonPlaceEnum(ButtonPlaceEnum.SC_4_1);
                break;
            case 5:
                boomMenuButton.setPiecePlaceEnum(PiecePlaceEnum.DOT_5_1);
                boomMenuButton.setButtonPlaceEnum(ButtonPlaceEnum.SC_5_1);
                break;
            case 6:
                boomMenuButton.setPiecePlaceEnum(PiecePlaceEnum.DOT_6_1);
                boomMenuButton.setButtonPlaceEnum(ButtonPlaceEnum.SC_6_1);
                break;
            case 7:
                boomMenuButton.setPiecePlaceEnum(PiecePlaceEnum.DOT_7_1);
                boomMenuButton.setButtonPlaceEnum(ButtonPlaceEnum.SC_7_1);
                break;
            case 8:
                boomMenuButton.setPiecePlaceEnum(PiecePlaceEnum.DOT_8_1);
                boomMenuButton.setButtonPlaceEnum(ButtonPlaceEnum.SC_8_1);
                break;
            case 9:
                boomMenuButton.setPiecePlaceEnum(PiecePlaceEnum.DOT_9_1);
                boomMenuButton.setButtonPlaceEnum(ButtonPlaceEnum.SC_9_1);
                break;
        }
    }

    private void drawInterests() {
        for (int i = 0; i< userInterestList.size(); i++){
            final Button button = new Button(this);
            button.setBackground(getDrawable(R.drawable.background_interest_circle));
            final int finalI = i;
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(testSelected[finalI]==0) {
                        testSelected[finalI]=1;
                        button.setTextColor(getResources().getColor(R.color.white));
                        button.setBackground(getDrawable(R.drawable.background_interest_selected_circle));
                    }else {
                        testSelected[finalI]=0;
                        button.setTextColor(getResources().getColor(R.color.app_theme));
                        button.setBackground(getDrawable(R.drawable.background_interest_circle));
                    }
                }
            });
            button.setTextColor(getResources().getColor(R.color.app_theme));
            button.setText(userInterestList.get(i));
            GridLayout.LayoutParams lp = new GridLayout.LayoutParams(GridLayout.spec(i/2),GridLayout.spec(i%2));
            lp.height =dip2px(this,80);
            lp.width = dip2px(this,80); 
            if (i%2==0){
                lp.setMargins(0,dip2px(this,10),dip2px(this,60),0);
                button.setLayoutParams(lp);
            }else {
                lp.setMargins(0,dip2px(this,10),0,0);
                button.setLayoutParams(lp);
            }
            mGridLayout.addView(button);
        }
    }

    public static int dip2px(Context context, float dpValue) {

        final float scale = context.getResources().getDisplayMetrics().density;

        return (int) (dpValue * scale + 0.5f);

    }

    @Override
    public void failBecauseNotNetworkReturn(int code) {
        showSnackBar(mToolbar,getString(R.string.not_network),getMainColor());
    }

    //获取用户兴趣的回调
    @Override
    public void getUserInterestReturn(Interestroot root) {
        if (root.getCode()==200){
            String interests = "";
            userInterestList=root.getData();
            testSelected = new int[userInterestList.size()];
            for(int i=0;i<userInterestList.size();i++){
                if(i!=userInterestList.size()-1){
                    interests += userInterestList.get(i)+" ";
                }
                else {
                    interests += userInterestList.get(i);
                }
            }
            DaoSession daoSession = ((IcompetitionApplication)getApplication()).getDaoSession();
            UserInforBeanDao userInforBeanDao = daoSession.getUserInforBeanDao();
            List<UserInforBean> userInforBeanList = userInforBeanDao.loadAll();
            UserInforBean userInforBean = userInforBeanList.get(0);
            userInforBean.setUser_interest(interests);
            daoSession.update(userInforBean);
            drawInterests();
        }else {
            showSnackBar(mToolbar,root.getMsg(),getMainColor());
        }
    }

    //添加用户兴趣的回调
    @Override
    public void addInterestReturn(Interestroot root) {
        if(root.getCode()==200){
            getUserInterest(1,35);
            showSnackBar(mToolbar,root.getMsg(),getMainColor());
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mGridLayout.removeAllViews();
                }
            });
        }else {
            showSnackBar(mToolbar,root.getMsg(),getMainColor());
        }
    }

    //删除用户兴趣的回调
    @Override
    public void deleteInterestReturn(Interestroot root) {
        if(root.getCode()==200){
            getUserInterest(1,35);
            showSnackBar(mToolbar,root.getMsg(),getMainColor());
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mGridLayout.removeAllViews();
                }
            });
        }else {
            showSnackBar(mToolbar,root.getMsg(),getMainColor());
        }
    }

    //获取兴趣类别的回调
    @Override
    public void getInterestTypeReturn(Interestroot root) {
        if(root.getCode()==200){
            typeList=root.getData();
            drawBoomMenu(0);
        }else {
            showSnackBar(mToolbar,root.getMsg(),getMainColor());
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delete_interest_toolbar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_delete).setVisible(true);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_delete)
        {
            for(int i =0;i<testSelected.length;i++){
                if(testSelected[i]==1){
                    deleteInterest(userInterestList.get(i));
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void getUserInterest(int page,int pageSize){
        if(NetWorkHelper.isNetworkAvailable(this)) {
            mPresenter.getUserInterest(page,pageSize);
        }else {
            showSnackBar(mToolbar,getString(R.string.not_have_network),getMainColor());
        }
    };

    private void addInterest(String typename){
        if(NetWorkHelper.isNetworkAvailable(this)) {
            mPresenter.addInterest(typename);
        }else {
            showSnackBar(mToolbar,getString(R.string.not_have_network),getMainColor());
        }
    };

    private void deleteInterest(String typename){
        if(NetWorkHelper.isNetworkAvailable(this)) {
            mPresenter.deleteInterest(typename);
        }else {
            showSnackBar(mToolbar,getString(R.string.not_have_network),getMainColor());
        }
    };

    private void getInterestType(){
        if(NetWorkHelper.isNetworkAvailable(this)) {
            mPresenter.getInterestType();
        }else {
            showSnackBar(mToolbar,getString(R.string.not_have_network),getMainColor());
        }
    };
}
