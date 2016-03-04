package com.dundunwen.FastAdapter;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.dundunwen.FastAdapter.annotation.BindId;
import com.dundunwen.FastAdapter.bean.ViewAndType;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dun on 2016/2/28.
 * 管理id对应的viewType
 *
 * */
public class ViewHelper {
    private Map<Integer,ViewAndType> viewTypeMap = new HashMap<>();
    public ViewHelper(Class clazz){
        getViewFromClass(clazz);
    }
    public Map<Integer,ViewAndType> getViewTypeMap() {
        return viewTypeMap;
    }

    /**
     * 使用viewHelper前请先调用这个方法
     *
     * @param clazz 获得该类的所有需要绑定数据源的viewId
     *
     *
     * */
    public void getViewFromClass(Class clazz){
        clearViewMap();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            BindId mAnnotation = field.getAnnotation(BindId.class);
            if(mAnnotation != null){
                int viewId = mAnnotation.Id();
                Class viewType = mAnnotation.ViewType();
                ViewAndType vat = new ViewAndType();
                String bindDateMethodName = getBindDateMethodName(viewType,field.getType());
                if(TextUtils.isEmpty(bindDateMethodName)){
                    bindDateMethodName = mAnnotation.BindDateMethodName();
                    if(TextUtils.isEmpty(bindDateMethodName)){
                        throw new RuntimeException("the view you set is unsupported please set your methodName for" + field.getName());
                    }
                }
                vat.viewTypeClass = viewType;
                vat.id = viewId;
                vat.field = field;
                vat.setBindDateMethodName(bindDateMethodName);
                viewTypeMap.put(vat.id,vat);
            }
        }
    }

    private String getBindDateMethodName(Class<?> viewType, Class<?> valueType){
        if(viewType.isAssignableFrom(TextView.class)||viewType==TextView.class){
            return "setText";
        }else if(viewType.isAssignableFrom(ImageView.class)||viewType==ImageView.class){
            if(valueType == Drawable.class){
                return "setImageDrawable";
            }else{
                return "setImageBitmap";
            }
        }
        return null;
    }

    public void clearViewMap(){
        viewTypeMap.clear();
    }
}
