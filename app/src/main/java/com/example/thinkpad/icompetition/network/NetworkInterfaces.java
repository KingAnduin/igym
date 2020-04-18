package com.example.thinkpad.icompetition.network;

import android.support.v4.util.ArrayMap;
import android.telephony.SignalStrength;
import android.util.Log;

import okhttp3.Callback;

/**
 * Created by Hjg
 * on 2018/11/13.
 */

public class NetworkInterfaces {
    private static final String SERVER_HOST = "http://www.pipicat.top:5000";
    //系统参数，接口名字的字段名,为了减少参数传递,在调用网络接口的方法时将这个字段加入到map中,而不是在处理系统
    // 参数的时候加入
    //private static final String METHOD = "method";

    //以下是请求地址
    private static final String REGISTER = "/api/users/register"; //用户注册
    private static final String LOG_IN = "/api/users/login"; //登陆系统
    private static final String USER_INFOR = "/api/users/message"; //用户信息数据
    private static final String PAGING_QUERY_EXAM = "/api/competitions/bypage"; //分页查询竞赛信息
    private static final String PAGING_QUERY_HOT = "/api/competitions/hot"; //分页查询热门竞赛
    private static final String SUBMIT_USERINFOR = "/api/users/update"; //提交用户信息
    private static final String CHANGE_PASSWORD = "/api/users/updatepwd"; //修改密码
    private static final String SEARCH_INFOR = "/api/competitions/search"; //搜索信息
    private static final String PAGING_QUERY_INTEREST = "/api/competitions/type";//分页查询分类的竞赛
    private static final String COLLECTION_BY_ID = "/api/collections/byid"; //获取是否收藏（单个）
    private static final String COLLECTION_ADD = "/api/collections/add"; //添加收藏
    private static final String COLLECTION_CANCLE = "/api/collections/delete "; //删除收藏
    private static final String GET_ISCONCERN = "/api/focus/search"; //获取单个用户是否被关注
    private static final String ADD_CONCERN ="/api/focus/add"; //添加关注
    private static final String DELETE_CONCERN ="/api/focus/delete"; //删除关注
    private static final String PAGING_QUERY_COLLECTION = "/api/collections/bypage";//分页查询收藏
    private static final String INTERESTS_ADD = "/api/interests/add";//添加兴趣
    private static final String INTERESTS_DELETE = "/api/interests/delete";//删除兴趣
    private static final String INTERESTS_TYPE ="/api/types";//兴趣类别
    private static final String INTERESTS_USER = "/api/interests/bypage";//用户的兴趣
    private static final String PAGING_QUERY_FOCUS = "/api/focus/bypage"; //获取关注分页

    /**
     * 用户注册
     * @param callback 。
     * @param name 用户名
     * @param pwd 密码
     */
    public void userRegister(Callback callback, String name, String pwd){
        ArrayMap<String, String> param = new ArrayMap<>();
        param.put("user_num", name);
        param.put("user_pwd", pwd);
        new NetworkRequest(param, SERVER_HOST + REGISTER, callback).sendRequest();

    }


    /**
     * 用户登陆
     * @param callback .
     * @param user_num 用户名
     * @param user_pwd 密码
     */
    public void userLogIn(Callback callback, String user_num, String user_pwd){
        ArrayMap<String, String> param = new ArrayMap<>();
        param.put("user_num", user_num);
        param.put("user_pwd", user_pwd);
        new NetworkRequest(param, SERVER_HOST + LOG_IN, callback).sendRequest();
    }

    /**
     * 用户密码修改
     * @param callback .
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     */
    public void changePassword(Callback callback,String oldPassword,String newPassword ){
        ArrayMap<String, String> param = new ArrayMap<>();
        param.put("user_pwd", oldPassword);
        param.put("new_pwd",newPassword);
        new NetworkRequest(param,SERVER_HOST + CHANGE_PASSWORD,callback).sendRequest();
    }

    /**
     * 用户信息
     * @param callback 。
     * @param num 用户名
     */
    public void userInfor(Callback callback, String num) {
        ArrayMap<String, String> param = new ArrayMap<>();
        param.put("user_num", num);
        new NetworkRequest(param, SERVER_HOST + USER_INFOR, callback).sendRequest();
    }

    /**
     * 分页查询竞赛信息
     * @param callback .
     * @param page_no 页码
     * @param page_size 每页数量
     */
    public void getItemExam(Callback callback, int page_no, int page_size){
        ArrayMap<String, String> param = new ArrayMap<>();
        param.put("page", String.valueOf(page_no));
        param.put("pageSize", String.valueOf(page_size));
        new NetworkRequest(param, SERVER_HOST + PAGING_QUERY_EXAM, callback).sendRequest();
    }

    /**
     * 分页查询热门竞赛信息
     * @param callback .
     * @param page_no 页码
     * @param page_size 每页数量
     */
    public void getHotsExam(Callback callback, int page_no, int page_size){
        ArrayMap<String, String> param = new ArrayMap<>();
        param.put("page", String.valueOf(page_no));
        param.put("pageSize", String.valueOf(page_size));
        new NetworkRequest(param, SERVER_HOST + PAGING_QUERY_HOT, callback).sendRequest();

    }

    /**
     * 带头像提交个人用户信息
     * @param callback .
     * @param userName 用户姓名
     * @param userSex 用户性别
     * @param userBirthday 用户生日
     * @param headImage 用户头像
     */
    public void submitUserInfor(Callback callback,String userName,String userSex,String userBirthday,String headImage){
        ArrayMap<String,String> param = new ArrayMap<>();
        param.put("user_name",userName);
        param.put("user_sex",userSex);
        param.put("user_birthday",userBirthday);
        param.put("user_headimage",headImage);
        new NetworkRequest(param,SERVER_HOST + SUBMIT_USERINFOR,callback).sendRequest();
    }

    /**
     * 应后台要求添加
     * 不带头像提交个人用户信息
     * @param callback .
     * @param userName 用户姓名
     * @param userSex 用户性别
     * @param userBirthday 用户生日
     */
    public void submitUserInforWithoutHeadImage(Callback callback,String userName,String userSex,String userBirthday){
        ArrayMap<String,String> param = new ArrayMap<>();
        param.put("user_name",userName);
        param.put("user_sex",userSex);
        param.put("user_birthday",userBirthday);
        new NetworkRequest(param,SERVER_HOST + SUBMIT_USERINFOR,callback).sendRequest();
    }

    /**
     *
     * @param callback .
     * @param page 请求页号
     * @param page_size 每页请求的数据条数
     * @param words 关键字
     */
    public void searchInfor(Callback callback,int page,int page_size,String words){
        ArrayMap<String,String> param = new ArrayMap<>();
        param.put("page",String.valueOf(page));
        param.put("pageSize",String.valueOf(page_size));
        param.put("words",words);
        new NetworkRequest(param,SERVER_HOST + SEARCH_INFOR ,callback).sendRequest();
    }
    /**
     * 分页查询兴趣的竞赛信息
     * @param callback .
     * @param page_no 页码
     * @param page_size 每页数量
     * @param type 类型
     */
    public void getTypeExam(Callback callback, int page_no, int page_size, String type){
        ArrayMap<String, String> param = new ArrayMap<>();
        param.put("page", String.valueOf(page_no));
        param.put("pageSize", String.valueOf(page_size));
        param.put("type", type);
        new NetworkRequest(param, SERVER_HOST + PAGING_QUERY_INTEREST, callback).sendRequest();
    }

    /**
     * 删除收藏
     * @param callback .
     * @param comid 比赛id
     */
    public void cancleCollection(Callback callback, String comid){
        ArrayMap<String, String> param = new ArrayMap<>();
        param.put("comid", comid);
        new NetworkRequest(param, SERVER_HOST + COLLECTION_CANCLE, callback).sendRequest();
    }

    /**
     * 添加收藏
     * @param callback .
     * @param usernum 用户账号
     * @param comid 比赛id
     */
    public void addCollection(Callback callback, String usernum, String comid){
        ArrayMap<String, String> param = new ArrayMap<>();
        param.put("usernum", usernum);
        param.put("comid", comid);
        new NetworkRequest(param, SERVER_HOST + COLLECTION_ADD, callback).sendRequest();
    }

    /**
     * 获取是否收藏（单个）
     * @param callback .
     * @param comid 比赛id
     */
    public void getIsCollection(Callback callback,String comid){
        ArrayMap<String, String> param = new ArrayMap<>();
        param.put("comid", comid);
        new NetworkRequest(param, SERVER_HOST + COLLECTION_BY_ID, callback).sendRequest();
    }

    /**
     * 获取搜索到的其他用户是否被关注
     * @param callback .
     * @param other_num 其他用户的id
     */
    public void getIsConcern(Callback callback,String other_num){
        ArrayMap<String, String> param = new ArrayMap<>();
        param.put("other_num", other_num);
        new NetworkRequest(param, SERVER_HOST + GET_ISCONCERN, callback).sendRequest();
    }

    /**
     * 添加关注
     * @param callback .
     * @param other_num 其他用户的id
     */
    public void addConcern(Callback callback,String other_num){
        ArrayMap<String, String> param = new ArrayMap<>();
        param.put("star", other_num);
        new NetworkRequest(param, SERVER_HOST + ADD_CONCERN, callback).sendRequest();
    }

    /**
     * 删除关注
     * @param callback .
     * @param other_num 其他用户的id
     */
    public void deleteConcern(Callback callback,String other_num){
        ArrayMap<String, String> param = new ArrayMap<>();
        param.put("star", other_num);
        new NetworkRequest(param, SERVER_HOST + DELETE_CONCERN, callback).sendRequest();
    }

    /**
     * 分页查询收藏
     * @param callback .
     * @param page_no 页号
     * @param page_size 页面大小
     */
    public void queryByPageCollection(Callback callback,String userNum, int page_no, int page_size){
        ArrayMap<String, String > param = new ArrayMap<>();
        param.put("page", String.valueOf(page_no));
        param.put("pageSize", String.valueOf(page_size));
        param.put("usernum", userNum);
        new NetworkRequest(param, SERVER_HOST + PAGING_QUERY_COLLECTION, callback).sendRequest();
    }

    /**
     * 获取兴趣类别
     * @param callback .
     */
    public void interestsType(Callback callback){
        ArrayMap<String, String > param = new ArrayMap<>();
        new NetworkRequest(param, SERVER_HOST + INTERESTS_TYPE, callback).sendRequest();
    }

    /**
     * 添加兴趣
     * @param callback .
     * @param typename 添加兴趣的类名
     */
    public void addInterest(Callback callback,String typename){
        ArrayMap<String, String > param = new ArrayMap<>();
        param.put("typename", typename);
        new NetworkRequest(param, SERVER_HOST + INTERESTS_ADD, callback).sendRequest();
    }

    /**
     * 删除兴趣
     * @param callback .
     * @param typename 删除兴趣的类名
     */
    public void deleteInterest(Callback callback,String typename){
        ArrayMap<String, String > param = new ArrayMap<>();
        param.put("typename", String.valueOf(typename));
        new NetworkRequest(param, SERVER_HOST + INTERESTS_DELETE, callback).sendRequest();
    }

    /**
     * 获取用户兴趣
     * @param callback .
     * @param page_no 请求页数
     * @param page_size 每页条数
     */
    public void userInterests(Callback callback, int page_no, int page_size){
        ArrayMap<String, String > param = new ArrayMap<>();
        param.put("page", String.valueOf(page_no));
        param.put("pageSize", String.valueOf(page_size));
        new NetworkRequest(param, SERVER_HOST + INTERESTS_USER, callback).sendRequest();
    }


    /**
     * 分页获取用户关注
     * @param callback .
     * @param page_no 页码
     * @param page_size 页面大小
     * @param userNum 用户账号
     */
    public void queryByPageFocus(Callback callback, int page_no, int page_size, String userNum){
        ArrayMap<String, String> param = new ArrayMap<>();
        param.put("page", String.valueOf(page_no));
        param.put("pageSize", String.valueOf(page_size));
        param.put("master", userNum);
        new NetworkRequest(param, SERVER_HOST + PAGING_QUERY_FOCUS, callback).sendRequest();
    }

}
