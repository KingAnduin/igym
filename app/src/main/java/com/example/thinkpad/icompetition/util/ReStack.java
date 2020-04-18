package com.example.thinkpad.icompetition.util;

import java.util.Stack;

/**
 * 继承Stack,为了实现remove最后一个匹配项的方法
 * Created by z
 * on 2017/3/10 0010.
 */

public class ReStack<T> extends Stack<T> {

    /**
     * 查找并删除最后一个匹配项
     * @param obj 匹配项
     * @return 成功返回true,失败返回false
     */
    public synchronized boolean removeLastElement(Object obj) {
        modCount++;
        int i = lastIndexOf(obj);
        if (i >= 0) {
            removeElementAt(i);
            return true;
        }
        return false;
    }

}
