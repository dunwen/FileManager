package com.dundunwen;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dundunwen.bean.ViewAndType;

import net.tsz.afinal.FinalBitmap;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by dun on 2016/2/26.
 *
 */
public class FastAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
    private List<T> list;
    private int viewId;
    ViewHelper viewHelper;
    OnItemClickListener onItemClickListener;
    OnItemLongClickListener onItemLongClickListener;
    FinalBitmap finalBitmap;
    Context mContext;

    public FastAdapter(int viewId, List<T> list, Context mContext) {
        if (list == null) {
            throw new RuntimeException("your list is null ? Do you remember to set your date?");
        }

        Object object = list.get(0);
        if (object != null) {
            viewHelper = new ViewHelper(object.getClass());
        }
        this.mContext = mContext;
        this.viewId = viewId;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewId, parent, false);

        if (view == null) {
            throw new RuntimeException("your view id : " + viewId + " is right?");
        }
        return new ViewHolder(view, viewHelper, onItemClickListener, onItemLongClickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Object object = list.get(position);

        Map<Integer, ViewAndType> map = viewHelper.getViewTypeMap();
        Collection<ViewAndType> collection = map.values();

        for (ViewAndType viewAndType : collection) {
            Object value = getValue(viewAndType,object);

            if(value == null){
                throw new RuntimeException("get value form field fail");
            }

            holder.bindDate(viewAndType,value,mContext);
        }
    }


    public FinalBitmap getFinalBitmap() {
        return finalBitmap;
    }

    public void setFinalBitmap(FinalBitmap finalBitmap) {
        this.finalBitmap = finalBitmap;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public Object getValue(ViewAndType mViewAndType,Object o) {
        Field f = mViewAndType.getField();
        f.setAccessible(true);
        try {
           return f.get(o);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public interface OnItemClickListener {
        void onClick(int position, View view);
    }

    public interface OnItemLongClickListener {
        void onLongClick(int position, View view);
    }

}
