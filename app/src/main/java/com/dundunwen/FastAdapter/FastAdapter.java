package com.dundunwen.FastAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dundunwen.FastAdapter.Impls.LoadImageFromUrlPolicyImpl;
import com.dundunwen.FastAdapter.bean.ViewAndType;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import edu.cqut.cn.filemanager.R;

/**
 * Created by dun on 2016/2/26.
 *
 */
public class FastAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
    private List<T> list;
    private int viewId;
    private ViewHelper viewHelper;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;
    private Context mContext;
    private LoadImageFromUrlPolicyImpl mLoadImageFromUrlPolicyImpl;

    /**
     *
     * @param viewId     itemView 的 id R.layout.XXX
     * @param list       需要绑定的数据
     * @param mContext  传进来暂时没什么用，传Activity 的Context 或者暂时null也行，只是为了以后修改方便
     *
     * @throws NullPointerException 如果list 为null，则会抛出空指针
     * */
    public FastAdapter(int viewId, List<T> list, Context mContext) {
        if (list == null) {
            throw new NullPointerException("your list is null ? Do you remember to set your date?");
        }
        if(list.size()>0){
            Object object = list.get(0);
            if (object != null) {
                viewHelper = new ViewHelper(object.getClass());
            }
            this.mContext = mContext;
            this.viewId = viewId;
            this.list = list;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(viewId, parent, false);
        view.setClickable(true);

        TypedValue typedValue = new TypedValue();
        mContext.getTheme().resolveAttribute(R.attr.selectableItemBackground, typedValue, true);
        view.setBackgroundResource(typedValue.resourceId);

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

            holder.bindDate(viewAndType,value,mLoadImageFromUrlPolicyImpl);
        }
    }


    @Override
    public int getItemCount() {

        return list==null?0:list.size();
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }


    /**
     * @return 从网络加载图片的策略*/
    public LoadImageFromUrlPolicyImpl getmLoadImageFromUrlPolicyImpl() {
        return mLoadImageFromUrlPolicyImpl;
    }

    /**
     * 设置从网络加载图片的策略 只要 注解使用的ViewType = ImageView.class 且 该注解下面的Field的值为String类型，会自动调用策略从网络加载图片
     *
     * @param mLoadImageFromUrlPolicyImpl 从网络加载图片策略*/
    public void setmLoadImageFromUrlPolicyImpl(LoadImageFromUrlPolicyImpl mLoadImageFromUrlPolicyImpl) {
        this.mLoadImageFromUrlPolicyImpl = mLoadImageFromUrlPolicyImpl;
    }

    /**
     * 利用反射 获取该字段的值
     * */
    private Object getValue(ViewAndType mViewAndType,Object o) {
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
