package com.example.thinkpad.icompetition.view.activity.impl;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.android.camera.CropImageIntentBuilder;
import com.example.thinkpad.icompetition.R;
import com.example.thinkpad.icompetition.model.entity.user.EditUserInforRoot;
import com.example.thinkpad.icompetition.model.entity.user.UserInforBean;
import com.example.thinkpad.icompetition.model.entity.user.UserInforRoot;
import com.example.thinkpad.icompetition.presenter.impl.EditUserInforPresenter;
import com.example.thinkpad.icompetition.util.BitmapUtil;
import com.example.thinkpad.icompetition.util.NetWorkHelper;
import com.example.thinkpad.icompetition.view.activity.i.IBaseActivity;
import com.example.thinkpad.icompetition.view.activity.i.IEditUserInforActivity;
import com.example.thinkpad.icompetition.view.widget.AsyncImageView;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import io.rong.imageloader.core.DisplayImageOptions;
import io.rong.imageloader.core.ImageLoader;
import io.rong.imageloader.core.ImageLoaderConfiguration;
import io.rong.imageloader.core.display.FadeInBitmapDisplayer;

/**
 * created by a’su's
 */
public class EditUserInforActivity extends BaseActivity<EditUserInforPresenter> implements IBaseActivity,IEditUserInforActivity,View.OnClickListener {
    private DisplayImageOptions options;
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private Toolbar mToolbar;
    private TextView mTitleTV;
    private AsyncImageView mUserHeadImageAIV;
    private EditText mUserNameET;
    private TextView mUserSexTV;
    private TextView mUserBirthdayTV;
    private UserInforBean mUserInforBean;
    Calendar calendar = Calendar.getInstance();
    private int mYear=calendar.get(Calendar.YEAR);
    private int mMonth=calendar.get(Calendar.MONTH);
    private int mDay=calendar.get(Calendar.DAY_OF_MONTH);
    private String birthday = null;
    private ProgressDialog mProgressDialog;
    private String user_account;
    //以下是有关头像处理的变量
    private Bitmap bitmap;
    protected static Uri tempUri; //用于给出图片的uri
    private String str_icon_stream;//头像文件压缩后的字符串转型
    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    protected static final int CROP_SMALL_PICTURE = 2;
    android.support.v7.app.AlertDialog.Builder builder = null;
    String[] permissionPictrue = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    String[] permissionCamera = {Manifest.permission.CAMERA};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_userinfor);
        findView();
        setListener();
        initDisplayImageOptions();
        //将信息填入对应控件
        if(getIntent().getSerializableExtra("userinfor")!=null){
            mUserInforBean=(UserInforBean) getIntent().getSerializableExtra("userinfor");///////////////////////
            if(!TextUtils.isEmpty(mUserInforBean.getNickname())) {
                mUserNameET.setText(mUserInforBean.getNickname());
            }
            if(!TextUtils.isEmpty(mUserInforBean.getSex())) {
                mUserSexTV.setText(mUserInforBean.getSex());
            }
            if(!TextUtils.isEmpty(mUserInforBean.getBirthday())) {
                mUserBirthdayTV.setText(mUserInforBean.getBirthday());
            }
            if(!TextUtils.isEmpty(mUserInforBean.getHead_image())) {
                imageLoader.displayImage(mUserInforBean.getHead_image(), mUserHeadImageAIV, options);
            }
        }
    }

    public void initDisplayImageOptions() {
        this.imageLoader.init(ImageLoaderConfiguration.createDefault(this));
        this.options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.mipmap.no_user_head)
                .showImageOnFail(R.mipmap.no_user_head)
                .showImageOnLoading(R.mipmap.ic_news_listview_img_loading)
                .displayer(new FadeInBitmapDisplayer(300))
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
    }

    private void setListener() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mUserBirthdayTV.setOnClickListener(this);
        mUserSexTV.setOnClickListener(this);
        mUserHeadImageAIV.setOnClickListener(this);
    }

    private void findView() {
        mTitleTV=findViewById(R.id.tv_toolbar_title);
        mToolbar = findViewById(R.id.toolbar_main);
        mUserHeadImageAIV=findViewById(R.id.aiv_edit_headimage);
        mUserNameET=findViewById(R.id.et_edit_name);
        mUserSexTV=findViewById(R.id.tv_edit_sex);
        mUserBirthdayTV=findViewById(R.id.tv_edit_birthday);
        mTitleTV.setText("编辑信息");
        mToolbar.setNavigationIcon(R.mipmap.back);
        setSupportActionBar(mToolbar);
    }

    //获取用户信息的回调
    @Override
    public void getUserInforReturn(UserInforRoot root) {
        dismissDialog();
        setResult(100);
        finish();//网络获取个人信息并存入数据库后将activity弹出栈
    }

    //提交用户编辑信息的回调
    @Override
    public void getSubmitReturn(EditUserInforRoot root) {
        if (root.getCode()!=200){
            dismissDialog();
            showSnackBar(mUserNameET,"保存失败",getMainColor());
        }
        else {
            if (NetWorkHelper.isNetworkAvailable(this)) {
                mPresenter.getUserInfor(String.valueOf(mUserInforBean.getContact_phone()));
            } else {
                showSnackBar(mUserNameET, getString(R.string.not_have_network), getMainColor());
            }
            //showSnackBar(mUserSexTV,getResources().getString(R.string.submit_success),getMainColor());
        }
    }

    @Override
    public void failBecauseNotNetworkReturn(int code) {
        dismissDialog();
        showToast(getResources().getString(R.string.not_network));
    }

    @Override
    protected EditUserInforPresenter getPresenter() {
        return new EditUserInforPresenter(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_userinfor_toolbar_ic,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_submit).setVisible(true);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_submit)
        {
            if (str_icon_stream == null) {
                this.str_icon_stream = "";
            }
            submitUserInfor();
        }
        return super.onOptionsItemSelected(item);
    }

    //修改了头像提交用户信息
    private void submitUserInfor() {
        if(NetWorkHelper.isNetworkAvailable(this)) {
            if(!TextUtils.isEmpty(mUserNameET.getText().toString())) {
                mUserInforBean.setNickname(mUserNameET.getText().toString());
            }
            if(!TextUtils.isEmpty(mUserSexTV.getText().toString())) {
                mUserInforBean.setSex(mUserSexTV.getText().toString());
            }
            if(!TextUtils.isEmpty(mUserBirthdayTV.getText().toString())) {
                mUserInforBean.setBirthday(mUserBirthdayTV.getText().toString());
            }
            if(!TextUtils.isEmpty(str_icon_stream)) {
                mUserInforBean.setHead_image(str_icon_stream);
            }
            //mUserInforBean.setUser_interest("计算机");
            SharedPreferences sharedPreferences = getSharedPreferences("ApplicationBase", MODE_PRIVATE);
            user_account = sharedPreferences.getString("userNumber", "");
            mPresenter.submitUserInfor(mUserInforBean,user_account);
            showProgressBarDialog();
        }else{
            showSnackBar(mUserSexTV,getString(R.string.not_have_network),getMainColor());
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.tv_edit_birthday:
                showDatePickDialog();
                break;
            case R.id.tv_edit_sex:
                showSexDialog();
                break;
            case R.id.aiv_edit_headimage:
                showChoosePicDialog();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final File croppedImageFile = new File(getFilesDir(), "test.jpg");
        if (resultCode == RESULT_OK) { // 如果返回码是可以用的
            switch (requestCode) {
                case TAKE_PICTURE:
                    Uri croppedImage = Uri.fromFile(croppedImageFile);
                    startPhotoZoom(croppedImage, tempUri); // 开始对图片进行裁剪处理
                    break;
                case CHOOSE_PICTURE:
                    croppedImage = Uri.fromFile(croppedImageFile);
                    startPhotoZoom(croppedImage, data.getData()); // 开始对图片进行裁剪处理
                    break;
                case CROP_SMALL_PICTURE:
                    bitmap = BitmapFactory.decodeFile(croppedImageFile.getAbsolutePath());
                    mUserHeadImageAIV.setImageBitmap(bitmap);//得到裁剪后的图片
                    new Thread(new Runnable() {
                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                        @Override
                        public void run() {
//                            Bitmap bitmap = BitmapFactory.decodeFile(croppedImageFile.getAbsolutePath());
//                            img_icon_stream.setImageBitmap(bitmap);
                            BitmapUtil bm = new BitmapUtil();
                            bm.zoomImage(bitmap, 100, 100);//压缩大小
                            str_icon_stream = Base64.encodeToString(bm.bitmapToBytes(bitmap), Base64.DEFAULT);
                        }
                    }).start();
                    break;
            }
        }
    }

    /**
     * 裁剪图片方法实现 选中图片URI
     * @param saveUri 保存图片的URI
     * @param sourceUri 待裁剪的图片
     */
    protected void startPhotoZoom(Uri saveUri, Uri sourceUri) {

        CropImageIntentBuilder cropImage = new CropImageIntentBuilder(150, 150, saveUri);
        cropImage.setOutlineColor(0xFF03A9F4);
        cropImage.setSourceImage(sourceUri);
        cropImage.setCircleCrop(true);

        startActivityForResult(cropImage.getIntent(this), CROP_SMALL_PICTURE);

    }

    private void showChoosePicDialog() {
        builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle("设置头像");
        String[] items = {"选择本地照片", "拍照"};
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case CHOOSE_PICTURE: // 选择本地照片
                        checkePS(false);
                        break;
                    case TAKE_PICTURE: // 拍照
                        checkePS(true);
                        break;
                }
            }
        });
        builder.create().show();
    }

    //检查权限，若无则动态添加
    private void checkePS(boolean isTakePicture) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //取消严格模式
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy( builder.build() );
            if (isTakePicture) {
                //拍照
                int cp2 = ContextCompat.checkSelfPermission(this, permissionCamera[0]);
                if (cp2 != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, permissionCamera, 222);
                }else {
                    takePicture();
                }
            } else {
                int cp1 = ContextCompat.checkSelfPermission(this, permissionPictrue[0]);
                if (cp1 != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, permissionPictrue, 111);
                }else {
                    selectImageFromSD();
                }
            }
        }else {
            if (isTakePicture) {
                //拍照
                takePicture();
            } else {
                selectImageFromSD();
            }
        }
    }

    //从相册中选取照片
    private void selectImageFromSD() {
        Intent openAlbumIntent = new Intent();
        openAlbumIntent.setType("image/*");
        openAlbumIntent.setAction(Intent.ACTION_PICK);
        openAlbumIntent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
    }

    //拍照
    private void takePicture() {
        Intent openCameraIntent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        File cameraFile = new File(Environment
                .getExternalStorageDirectory(), "image.jpg");
        if (!cameraFile.exists()) {
            try {
                cameraFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        tempUri = Uri.fromFile(cameraFile);
        // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
        startActivityForResult(openCameraIntent, TAKE_PICTURE);
    }

    private void showDatePickDialog() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mYear=year;
                mMonth=month;
                mDay=dayOfMonth;
                birthday = new StringBuffer().append(mYear).append("年").append(mMonth+1).append("月").append(mDay).append("日").toString();
                mUserBirthdayTV.setText(birthday);
            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,dateSetListener,mYear,mMonth,mDay);
        datePickerDialog.show();
    }

    private void showSexDialog(){
        final String[] sexItems = new String[]{"男","女"};
        final AlertDialog sexDialog = new AlertDialog.Builder(EditUserInforActivity.this)
                .setTitle("性别选择")
                .setCancelable(true)
                .setIcon(R.mipmap.ic_sex)
                .setSingleChoiceItems(sexItems, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mUserSexTV.setText(sexItems[which]);
                        dialog.dismiss();
                    }
                }).create();
        sexDialog.show();
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
                mProgressDialog = ProgressDialog.show(this,"提示","保存中...请稍后");
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
