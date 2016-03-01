package com.dundunwen.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by dun on 2016/3/1.
 *
 * methodName 设置绑定数据的第三方view的方法 比如 TextView 需要绑定一串 String  那么方法名就是setText(); 暂时只支持一个参数的设置方法
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BindThirdPartId {
    int Id();
    Class viewType();
    String methodName();
}
