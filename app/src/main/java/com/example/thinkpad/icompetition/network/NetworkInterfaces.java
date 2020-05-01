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
        // TODO 测试
        String SERVER_HOST = "http://175.24.34.223:80";
        String REGISTER = "/api/user/register/";
        param.put("phone", name);
        param.put("password", pwd);
        param.put("nickname", "hhh");
        new NetworkRequest(param, SERVER_HOST + REGISTER, callback).sendRequestByMethod("POST");

    }


    /**
     * 用户登陆
     * @param callback .
     * @param user_num 用户名
     * @param user_pwd 密码
     */
    public void userLogIn(Callback callback, String user_num, String user_pwd){
        ArrayMap<String, String> param = new ArrayMap<>();
        // TODO 测试
        String SERVER_HOST = "http://175.24.34.223:80";
        String LOG_IN = "/api/user/login/";
        param.put("phone", user_num);
        param.put("password", user_pwd);
        new NetworkRequest(param, SERVER_HOST + LOG_IN, callback).sendRequestByMethod("POST");
    }

    /**
     * 用户密码修改
     * @param callback .
     * @param newPassword 新密码
     */
    public void changePassword(Callback callback,String oldPassword,String newPassword ){
        ArrayMap<String, String> param = new ArrayMap<>();
        // TODO 测试
        String SERVER_HOST = "http://175.24.34.223:80";
        String CHANGE_PASSWORD = "/api/user/changePassword/";
        param.put("password",newPassword);
        new NetworkRequest(param,SERVER_HOST + CHANGE_PASSWORD,callback).sendRequestByMethod("POST");
    }

    /**
     * 用户信息
     * @param callback 。
     * @param num 用户名
     */
    public void userInfor(Callback callback, String num) {
        ArrayMap<String, String> param = new ArrayMap<>();
        // TODO 测试
        String SERVER_HOST = "http://175.24.34.223:80";
        String USER_INFOR = "/api/user/userInfo/";
        new NetworkRequest(param, SERVER_HOST + USER_INFOR, callback).sendRequestByMethod("GET");
    }

    /**
     * 带头像提交个人用户信息
     * @param callback .
     * @param user_account 用户id
     * @param userName 用户姓名
     * @param userSex 用户性别
     * @param userBirthday 用户生日
     * @param headImage 用户头像
     */
    public void submitUserInfor(Callback callback,String user_account,String userName,String userSex,String userBirthday,String headImage){
        ArrayMap<String,String> param = new ArrayMap<>();
        // TODO 测试
        String SERVER_HOST = "http://175.24.34.223:80";
        String USER_INFOR = "/api/user/userInfo/";
        param.put("user_account",user_account);
        param.put("nickname",userName);
        param.put("name",userName);
        param.put("sex",userSex);
        param.put("birthday",userBirthday);
        param.put("head_image",headImage);
        new NetworkRequest(param,SERVER_HOST + USER_INFOR,callback).sendRequestByMethod("PUT");
    }


    /**
     * 分页查询健身资讯信息
     * @param callback .
     * @param page_no 页码
     * @param page_size 每页数量
     */
    public void getItemNews(Callback callback, int page_no, int page_size){
        ArrayMap<String, String> param = new ArrayMap<>();
        // TODO 测试
        String SERVER_HOST = "http://175.24.34.223:80";
        String PAGING_QUERY_EXAM = "/api/news/newsInfo/?page=" + page_no;
        new NetworkRequest(param, SERVER_HOST + PAGING_QUERY_EXAM, callback).sendRequestByMethod("GET");
    }

    /**
     * 分页查询订单
     * @param callback .
     */
    public void queryByPageOrder(Callback callback, int page_no){
        ArrayMap<String, String > param = new ArrayMap<>();
        // TODO 测试
        String SERVER_HOST = "http://175.24.34.223:80";
        String PAGING_QUERY_USER_ORDER = "/api/order/orderInfo/?page=" + page_no;
        new NetworkRequest(param, SERVER_HOST + PAGING_QUERY_USER_ORDER, callback).sendRequestByMethod("GET");
    }

    /**
     * 删除订单
     * @param callback .
     * @param order_id 订单ID
     */
    public void deleteOrder(Callback callback, int order_id){
        ArrayMap<String, String > param = new ArrayMap<>();
        // TODO 测试
        String SERVER_HOST = "http://175.24.34.223:80";
        String DELETE_ORDER = "/api/order/orderInfo/";
        param.put("order_id", String.valueOf(order_id));
        new NetworkRequest(param, SERVER_HOST + DELETE_ORDER, callback).sendRequestByMethod("DELETE");
    }

    /**
     * 新增评论
     * @param callback .
     * @param order 订单ID
     * @param comment 评论内容
     */
    public void addComment(Callback callback, int order, String comment){
        ArrayMap<String, String > param = new ArrayMap<>();
        // TODO 测试
        String SERVER_HOST = "http://175.24.34.223:80";
        String ADD_COMMENT = "/api/comments/CommentsInfo/";
        param.put("order", String.valueOf(order));
        param.put("comment_content", comment);
        new NetworkRequest(param, SERVER_HOST + ADD_COMMENT, callback).sendRequestByMethod("POST");
    }

    /**
     * 新增订单
     * @param callback .
     * @param order_data 预约日期
     * @param order_time_period 预约时间段
     * @param order_status 状态默认为未完成
     * @param user_account 用户ID
     * @param good_id 商品ID
     */
    public void addOrder(Callback callback, String order_data, String order_time_period, String order_status, String user_account, String good_id){
        ArrayMap<String, String > param = new ArrayMap<>();
        // TODO 测试
        String SERVER_HOST = "http://175.24.34.223:80";
        String ADD_ORDER = "/api/order/orderInfo/";
        param.put("order_data", order_data);
        param.put("order_time_period", order_time_period);
        param.put("order_status", order_status);
        param.put("user_account", user_account);
        param.put("good_id", good_id);
        new NetworkRequest(param, SERVER_HOST + ADD_ORDER, callback).sendRequestByMethod("PUT");
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

}
