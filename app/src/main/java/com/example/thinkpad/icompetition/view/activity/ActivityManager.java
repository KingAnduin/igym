package com.example.thinkpad.icompetition.view.activity;

import android.app.Activity;

import com.example.thinkpad.icompetition.util.ReStack;
import com.example.thinkpad.icompetition.view.activity.impl.BaseActivity;


/**
 * Activity管理类!只能以standard或singleTask模式启动activity!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * 用于管理Activity,获取Activity
 * 在结束一个activity后应该判断当前栈是否为空,为空则将本类引用置为null,以便于虚拟机回收内存
 * 单例,调用 {@link #getActivityManager()} 获取实例
 * 成员变量 {@link #mActivityStack} 应该与系统的回退栈保持一致,所以在启动activity的时候必须在其onCreate中
 * 将该activity加入栈顶,在activity结束时,必须在onDestroy中将该activity出栈
 * *
 * Created by z
 * on 2017/3/10 0010.
 */

public class ActivityManager {

    private static ReStack<Activity> mActivityStack;    //Activity栈
    private static ActivityManager mInstance;

    private ActivityManager() {
        mActivityStack = new ReStack<>();
    }

    /**
     * 获取ActivityManager的单例.
     *
     * @return ActivityManager实例
     */
    public static ActivityManager getActivityManager() {
        if (mInstance == null) {
            mInstance = new ActivityManager();
        }
        return mInstance;
    }

    /**
     * 添加一个activity到栈顶.
     *
     * @param activity 添加的activity
     */
    public void pushActivity(Activity activity) {
        if (mActivityStack == null) {
            mActivityStack = new ReStack<>();
        }
        mActivityStack.push(activity);
    }

    /**
     * 获取栈顶的Activity.
     *
     * @return 如果栈存在, 返回栈顶的activity
     */
    public Activity peekActivity() {
        if (mActivityStack != null && !mActivityStack.isEmpty()) {
            return mActivityStack.peek();
        } else {
            return null;
        }
    }

    /**
     * 结束当前的activity,在activity的onDestroy中调用.
     */
    public void popActivity() {
        if (mActivityStack != null && !mActivityStack.isEmpty()) {
            mActivityStack.pop();
        }
        //如果移除一个activity之后栈为空,将本类的引用取消,以便于让虚拟机回收
        if (mActivityStack != null && mActivityStack.isEmpty()) {
            mInstance = null;
        }
    }

    /**
     * 结束最接近栈顶的匹配类名的activity.
     * 遍历到的不一定是被结束的,遍历是从栈底开始查找,为了确定栈中有这个activity,并获得一个引用
     * 删除是从栈顶查找,结束查找到的第一个
     * 在activity外结束activity时调用
     *
     * @param klass 类名
     */
    public void popActivity(Class<? extends BaseActivity> klass) {
        for (Activity activity : mActivityStack) {
            if (activity != null && activity.getClass().equals(klass)) {
                activity.finish();
                break;              //只结束一个
            }
        }
    }

}
