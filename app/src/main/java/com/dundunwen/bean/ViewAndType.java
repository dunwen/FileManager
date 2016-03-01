package com.dundunwen.bean;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by dun on 2016/2/28.
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
