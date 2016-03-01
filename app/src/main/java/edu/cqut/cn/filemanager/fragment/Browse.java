package edu.cqut.cn.filemanager.fragment;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dundunwen.FastAdapter;
import com.dundunwen.SimpleAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.cqut.cn.filemanager.R;
import edu.cqut.cn.filemanager.beans.ToFile;

/**
 * Created by dun on 2016/2/25.
 *
 */
public class Browse extends Fragment{
    private static Browse mBrowse = null;
    private static final String TAG = "Borwse";
    public static Browse getInstance(){
        if(mBrowse == null){
            mBrowse = new Browse();
        }
        return  mBrowse;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.browse, container, false);
        initView(view);
        return view;
    }


    @Bind(R.id.browse_FAB)
    FloatingActionButton fab;

    @Bind(R.id.browse_recycler_view)
    RecyclerView rvView;

    List<ToFile> list;
    private void initView(View view) {
        ButterKnife.bind(this,view);

        rvView = (RecyclerView) view.findViewById(R.id.browse_recycler_view);
        initdate();


        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        FastAdapter<ToFile> fa = new FastAdapter<>(R.layout.rv_item,list,getActivity());
        rvView.setLayoutManager(llm);
        fa.setOnItemClickListener(new FastAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position, View view) {
                Log.i(TAG, "onClick: " + list.get(position).getFileName());
            }
        });
        fa.setOnItemLongClickListener(new FastAdapter.OnItemLongClickListener() {
            @Override
            public void onLongClick(int position, View view) {
                Log.i(TAG, "onLoingClick: "+ list.get(position).getFileName());
            }
        });
        rvView.setAdapter(fa);


        SimpleAdapter sa = new SimpleAdapter(list);
    }

    private void initdate() {
        list = new ArrayList<>();

        for(int i = 0; i<20;i++){
            ToFile file = new ToFile();
            file.setFileName("hi + " + i);
            Bitmap b = BitmapFactory.decodeResource(getResources(),R.mipmap.head_icon);
            file.setIcon(b);
            file.setIconUrl("https://www.baidu.com/img/bd_logo1.png");
            list.add(file);
        }

    }
}
