package com.dundunwen;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import edu.cqut.cn.filemanager.R;
import edu.cqut.cn.filemanager.beans.ToFile;

/**
 * Created by dun on 2016/2/26.
 */
public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.ViewHolder>{
    List<ToFile> list;
    private static final String TAG = "SimpleAdapter";
    public SimpleAdapter(List<ToFile> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item,parent,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ToFile file = list.get(position);
        holder.textViewSetText(holder.tv,file.getFileName());
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        View tv = null;

        public ViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.simpleTv);
            if(tv instanceof TextView){
                Log.i(TAG, "ViewHolder: >>>>>>>>");
            }
        }

        public void textViewSetText(View v,String s){
            if(v instanceof TextView){
                ((TextView)v).setText(s);
            }
        }


    }
}
