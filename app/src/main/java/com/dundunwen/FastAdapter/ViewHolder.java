package com.dundunwen.FastAdapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;

import com.dundunwen.FastAdapter.Impls.LoadImageFromUrlPolicyImpl;
import com.dundunwen.FastAdapter.bean.ViewAndType;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by dun on 2016/2/27.
 * 
 */
public class ViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> viewMap = new SparseArray<>();
    private ViewHelper mViewHelper;

    private static final String TAG = "ViewHolder";

    /**
     * @param itemView
     * @param viewHelper 从管理数据的ViewHelper
     * @param onClickListener 单击事件监听
     * @param onLongClickListener 长按事件监听
     *
     * */
    public ViewHolder(final View itemView, ViewHelper viewHelper, final FastAdapter.OnItemClickListener onClickListener, final FastAdapter.OnItemLongClickListener onLongClickListener) {
        super(itemView);
        this.mViewHelper = viewHelper;

        if (onClickListener != null) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onClick(getPosition(), v);
                }
            });
        }

        if (onLongClickListener != null) {
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onLongClickListener.onLongClick(getPosition(), v);
                    return true;
                }
            });
        }

        HashMap<Integer, ViewAndType> map = (HashMap<Integer, ViewAndType>) mViewHelper.getViewTypeMap();
        Collection<ViewAndType> set = map.values();
        for (ViewAndType viewAndType : set) {
            View view = itemView.findViewById(viewAndType.getId());
            viewMap.put(viewAndType.getId(), view);
        }
    }



    /**
     *
     * 绑定view与数据，假若 需要绑定的view是imageView的子类且需要绑定的数据是String，则会自动调用网络加载策略
     *
     * @param mViewAndType 管理类
     * @param value 需要绑定的数据
     * @param mLoadImageFromUrlPolicyImpl 从网络加载图片的策略，由用户自定义
     *
     * */
    public void bindDate(ViewAndType mViewAndType, Object value, LoadImageFromUrlPolicyImpl mLoadImageFromUrlPolicyImpl) {
        int id = mViewAndType.getId();
        View view = viewMap.get(id);

        if(view instanceof ImageView && value instanceof String){
            if(mLoadImageFromUrlPolicyImpl != null){
                mLoadImageFromUrlPolicyImpl.bindImageFormUri((ImageView)view,(String)value);
            }else{
                Log.e(TAG, "bindDate: have you remember to set LoadImageFromUrlPolicyImpl on FastAdapter?");
            }
            return;
        }

        Method mMethod = mViewAndType.getBindDateMethod();
        if (mMethod == null) {
            Class clazz = mViewAndType.getViewTypeClass();
            String methodName = mViewAndType.getBindDateMethodName();
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                if (method.getName().equals(methodName)&&method.getParameterTypes().length==1) {
                    mViewAndType.setBindDateMethod(method);
                    mMethod = method;
                }
            }
        }

        try {
            if(mMethod!=null){
                mMethod.invoke(view,value);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }


}
