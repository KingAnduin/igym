package com.example.thinkpad.icompetition.view.activity.impl;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thinkpad.icompetition.presenter.impl.BasePresenter;
import com.example.thinkpad.icompetition.view.adapter.MainFragmentAdapter;
import com.example.thinkpad.icompetition.view.widget.BottomImageView;
import com.example.thinkpad.icompetition.view.fragment.impl.CompetitionFragment;
import com.example.thinkpad.icompetition.view.fragment.impl.MeFragment;
import com.example.thinkpad.icompetition.R;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private TextView mToolbarTitleTV;
    private LinearLayout mCompetitionLayout;
    private LinearLayout mMeLayout;
    private Toolbar mToolbar;
    private BottomImageView mCompetitionBV;
    private BottomImageView mMeBV;
    private TextView mCompetitionTV;
    private TextView mMeTV;
    private long exitTime=0;//记录用户点击back键的时间点
    private ViewPager mViewPager;
    private ArrayList<Fragment> mFragments;
    private CompetitionFragment mCompetitionFragment;
    private MeFragment mMeFragment;
    private MainFragmentAdapter mFragmentAdapter;
    private ArgbEvaluator mColorEvaluator;
    private int mTextNormalColor;// 未选中的字体颜色
    private int mTextSelectedColor;// 选中的字体颜色

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        initSelectIcon();//初始化底部导航栏Icon
        initColor();//初始化底部导航栏字体颜色
        setListener();
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
////        mCompetitionFragment.getHomeHotFragment().FirstFreshData();
//    }

    private void initSelectIcon() {
        mCompetitionBV.setImages(R.mipmap.cup, R.mipmap.cup_select);
        mMeBV.setImages(R.mipmap.man, R.mipmap.man_select);
    }

    private void findView() {
        mToolbarTitleTV = findViewById(R.id.tv_toolbar_title);
        mCompetitionLayout = findViewById(R.id.layout_competion);
        mMeLayout = findViewById(R.id.layout_me);
        mToolbar = findViewById(R.id.toolbar_main);
        mCompetitionTV = findViewById(R.id.tv_competition);
        mMeTV = findViewById(R.id.tv_me);
        mCompetitionBV = findViewById(R.id.bv_competition);
        mMeBV = findViewById(R.id.bv_me);
        mViewPager = findViewById(R.id.vp_activity_main);
        mCompetitionFragment = new CompetitionFragment();
        mMeFragment = new MeFragment();
        mFragments = new ArrayList<>();
        mFragments.add(mCompetitionFragment);
        mFragments.add(mMeFragment);
        mFragmentAdapter = new MainFragmentAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(mFragmentAdapter);
        mToolbarTitleTV.setText(getString(R.string.competition));
        setSupportActionBar(mToolbar);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_competion:
                mViewPager.setCurrentItem(0);
                mToolbarTitleTV.setText(getString(R.string.competition));
                break;
            case R.id.layout_me:
                mViewPager.setCurrentItem(1);
                mToolbarTitleTV.setText(getString(R.string.me));
                break;
        }
    }

    private void setListener() {
        mCompetitionLayout.setOnClickListener(this);
        mMeLayout.setOnClickListener(this);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                setTabTextColorAndImageView(position, positionOffset);// 更改text的颜色还有图片
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        mToolbarTitleTV.setText("竞赛汇聚地");
                        mToolbar.getMenu().setGroupVisible(R.id.group_search,true);
                        break;
                    case 1:
                        mToolbarTitleTV.setText("我的地盘");
                        mToolbar.getMenu().setGroupVisible(R.id.group_search,false);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initColor() {
        mTextNormalColor = getResources().getColor(R.color.main_bottom_tab_textcolor_normal);
        mTextSelectedColor = getResources().getColor(R.color.main_bottom_tab_textcolor_selected);
    }

    private void setTabTextColorAndImageView(int position, float positionOffset) {
        mColorEvaluator = new ArgbEvaluator();
        int evaluateCurrent = (int) mColorEvaluator.evaluate(positionOffset, mTextSelectedColor, mTextNormalColor);//当前tab的颜色值
        int evaluateThe = (int) mColorEvaluator.evaluate(positionOffset, mTextNormalColor, mTextSelectedColor);// 将要到tab的颜色值
        switch (position) {
            case 0:
                mCompetitionTV.setTextColor(evaluateCurrent);  //设置消息的字体颜色
                mMeTV.setTextColor(evaluateThe);  //设置通讯录的字体颜色

                mCompetitionBV.transformPage(positionOffset);  //设置消息的图片
                mMeBV.transformPage(1 - positionOffset); //设置通讯录的图片
                break;
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        if((System.currentTimeMillis()-exitTime>1000)){
            exitTime=System.currentTimeMillis();
            Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
        }else{
            finish();
            System.exit(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_ic, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_search).setVisible(true);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search)
        {
            startActivity(new Intent(MainActivity.this, SearchActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
