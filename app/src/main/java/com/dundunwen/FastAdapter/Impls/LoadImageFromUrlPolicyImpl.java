package com.dundunwen.FastAdapter.Impls;

import android.widget.ImageView;

/**
 * Created by dun on 2016/3/2.
 * 从网络加载图片的策略
 *
 */
public interface LoadImageFromUrlPolicyImpl {
    void bindImageFormUri(ImageView view,String url);
}
