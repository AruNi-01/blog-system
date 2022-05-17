package com.run.blog.utils;

import com.run.blog.dao.pojo.SysUser;

/**
 * @author AruNi_Lu
 * @data 2022/4/11
 */

/**
 * **ThreadLocal是什么**
 *
 * ThreadLocal叫做线程变量，意思是ThreadLocal中填充的变量属于当前线程，该变量对其他线程而言是隔离的。
 * ThreadLocal为变量在每个线程中都创建了一个副本，那么每个线程可以访问自己内部的副本变量。
 *
 * 从字面意思来看非常容易理解，但是从实际使用的角度来看，就没那么容易了，作为一个面试常问的点，使用场景那也是相当的丰富：
 * 1、在进行对象跨层传递的时候，使用ThreadLocal可以避免多次传递，打破层次间的约束
 * 2、线程间数据隔离
 * 3、进行事务操作，用于存储线程事务信息
 * 4、数据库连接，Session会话管理
 */
public class UserThreadLocal {

    // 声明为私有，副本存放的信息都只是当前线程的
    private UserThreadLocal() {}

    // 实例化一个ThreadLocal的类，也就是启用
    private static final ThreadLocal<SysUser> LOCAL = new ThreadLocal<>();

    public static void put(SysUser sysUser) {
        LOCAL.set(sysUser);
    }

    public static SysUser get() {
        return LOCAL.get();
    }

    public static void remove() {
        LOCAL.remove();
    }
}
