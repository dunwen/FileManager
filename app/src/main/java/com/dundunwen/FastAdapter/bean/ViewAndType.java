package com.dundunwen.FastAdapter.bean;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by dun on 2016/2/28.
 *
 *这个类的作用是为了保存每一个使用了注解的 Field 的数据，需要保存的数据有 对应的view的Id，view的type，该
 * 字段的Field的引用，绑定数据使用的方法名，绑定数据使用的方法的引用
 *
 */
public class ViewAndType {
        public int id;
        public Class viewTypeClass;
        public Field field;
        public String bindDateMethodName;
        public Method bindDateMethod;
    public ViewAndType() {
    }

    public int getId() {
        return id;
    }

    public String getBindDateMethodName() {
        return bindDateMethodName;
    }

    public void setBindDateMethodName(String bindDateMethodName) {
            this.bindDateMethodName = bindDateMethodName;
    }


    public Method getBindDateMethod() {
        return bindDateMethod;
    }

    public void setBindDateMethod(Method bindDateMethod) {
        this.bindDateMethod = bindDateMethod;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Class getViewTypeClass() {
        return viewTypeClass;
    }

    public void setViewTypeClass(Class viewTypeClass) {
        this.viewTypeClass = viewTypeClass;
    }
}
