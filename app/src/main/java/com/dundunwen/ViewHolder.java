package com.dundunwen;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dundunwen.bean.ViewAndType;

import net.tsz.afinal.FinalBitmap;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dun on 2016/2/27.
 */
public class ViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> viewMap = new SparseArray<>();
    private ViewHelper mViewHelper;

    private static final String TAG = "ViewHolder";

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

    public void bindDateForTextView(int id, String s) {
        TextView tv = (TextView) viewMap.get(id);
        tv.setText(s);
    }

    public void bindDateForImageView(int id, Bitmap bitmap) {
        ImageView iv = (ImageView) viewMap.get(id);
        iv.setImageBitmap(bitmap);
    }

    public void bindDateForImageViewFormHttp(FinalBitmap finalBitmap, int id, String url) {
        ImageView iv = (ImageView) viewMap.get(id);
        finalBitmap.display(iv, url);
    }

    public void bindDateForThird(int id, Object value, ViewAndType mViewAndType) {
        View view = viewMap.get(id);
        Method mMethod = mViewAndType.getBindDateMethod();
        if (mMethod == null) {
            Class clazz = mViewAndType.getViewTypeClass();
            String methodName = mViewAndType.getBindDateMethodName();
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                if (method.getName().equals(methodName)) {
                    mViewAndType.setBindDateMethod(method);
                    mMethod = method;
                }
            }
        }

        try {
            mMethod.invoke(view,value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

}
