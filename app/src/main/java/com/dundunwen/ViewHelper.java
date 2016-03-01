package com.dundunwen;

import android.util.SparseArray;
import com.dundunwen.annotation.BindId;
import com.dundunwen.annotation.BindThirdPartId;
import com.dundunwen.bean.ViewAndType;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dun on 2016/2/28.
 * 管理id对应的viewType
 *
 * */
public class ViewHelper {
//    private static ViewHelper mViewHelper = null;
    private Map<Integer,ViewAndType> viewTypeMap = new HashMap<>();
    public ViewHelper(Class clazz){
        getViewFromClass(clazz);
    }

//    public static ViewHelper getInstance(){
//        if(mViewHelper == null){
//            synchronized (ViewHelper.class){
//                if(mViewHelper == null){
//                    mViewHelper = new ViewHelper();
//                }
//            }
//        }
//        return mViewHelper;
//    }
//    public static ViewHelper getInstance(Class clazz){
//        ViewHelper vh = getInstance();
//        vh.getViewFromClass(clazz);
//        return vh;
//    }

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
            BindThirdPartId thirdPartId = field.getAnnotation(BindThirdPartId.class);
            if(mAnnotation != null){
                int viewId = mAnnotation.Id();
                Class calzz = mAnnotation.ViewType();
                ViewAndType vat = new ViewAndType();
                vat.viewTypeClass = calzz;
                vat.id = viewId;
                vat.field = field;
                viewTypeMap.put(vat.id,vat);
            }else if(thirdPartId != null){
                int viewId = thirdPartId.Id();
                Class cla = thirdPartId.viewType();
                String bindDateMethodName = thirdPartId.methodName();

                ViewAndType vat = new ViewAndType();
                vat.setIsThirdView(true);
                vat.setBindDateMethodName(bindDateMethodName);
                vat.setField(field);
                vat.setId(viewId);
                vat.setViewTypeClass(cla);
                viewTypeMap.put(viewId,vat);

            }


        }
    }
    public void clearViewMap(){
        viewTypeMap.clear();
    }
}
