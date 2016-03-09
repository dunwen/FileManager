package edu.cqut.cn.filemanager.borwseUnit.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.dundunwen.FastAdapter.FastAdapter;
import com.dundunwen.FastAdapter.Impls.LoadImageFromUrlPolicyImpl;

import net.tsz.afinal.FinalBitmap;

import java.io.File;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.cqut.cn.filemanager.R;
import edu.cqut.cn.filemanager.beans.ToFile;
import edu.cqut.cn.filemanager.borwseUnit.fragment.impls.Browse_Present_Impl;
import edu.cqut.cn.filemanager.borwseUnit.fragment.impls.Browse_View_Impl;

/**
 * Created by dun on 2016/2/25.
 *
 */
public class Browse extends Fragment implements Browse_View_Impl{
    private static Browse mBrowse = null;
    private static final String TAG = "Borwse";

    private Browse_Present_Impl mPresent;
    FastAdapter<ToFile> mFastAdapter;
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
        mPresent = new Borwse_Presenter(this);

        initdate();
        initView(view);

        return view;
    }


    @Bind(R.id.browse_FAB)
    FloatingActionButton fab;

    @Bind(R.id.browse_recycler_view)
    RecyclerView rvView;

    List<ToFile> fileList;
    private void initView(View view) {
        ButterKnife.bind(this,view);

        rvView = (RecyclerView) view.findViewById(R.id.browse_recycler_view);



        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        mFastAdapter = new FastAdapter<>(R.layout.browse_rv_item,fileList,getActivity());
        rvView.setLayoutManager(llm);
        mFastAdapter.setOnItemClickListener(new FastAdapter.OnItemClickListener() {
            @Override
            public void onClick(final int position, final View view) {
                mPresent.selectFile(fileList.get(position));
            }
        });
        mFastAdapter.setOnItemLongClickListener(new FastAdapter.OnItemLongClickListener() {
            @Override
            public void onLongClick(int position, View view) {
                Log.i(TAG, "onLongClick: " + fileList.get(position).getFileName());
            }
        });
        mFastAdapter.setmLoadImageFromUrlPolicyImpl(new LoadImageFromUrlPolicyImpl() {
            @Override
            public void bindImageFormUri(ImageView view, String url) {
                try {
                    int id = Integer.parseInt(url);
                    view.setImageBitmap(BitmapFactory.decodeResource(getResources(), id));
                } catch (Exception e) {
                    FinalBitmap finalBitmap = FinalBitmap.create(getActivity());
                    finalBitmap.display(view, url);
                }
            }
        });

        rvView.setAdapter(mFastAdapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenuDialog();
            }
        });

    }

    private void initdate() {
        mPresent.getFileList();
    }

    @Override
    public void setFilesList(List<ToFile> list) {
        this.fileList = list;
        if(mFastAdapter!=null){
            mFastAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void openImage(ToFile toFile) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(toFile.getPath())), "image/*");
        startActivity(intent);
    }

    @Override
    public void openTxt(ToFile toFile) {

    }

    @Override
    public void openVideo(ToFile toFile) {

    }



    private void showMenuDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(R.layout.broese_menu_dialog_view)
                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showNewFileDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.new_file_dialog_view,null,false);
        final EditText mEditText = (EditText) view.findViewById(R.id.et_new_file_dialog_view);
        builder.setView(view)
        .setTitle("创建文件夹")
        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        })
        .setPositiveButton("创建", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = mEditText.getText().toString();
                if (!TextUtils.isEmpty(name)) {
                    mPresent.createNewFile(name);
                } else {
                    Toast.makeText(getActivity(), "请输入文件夹名字", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Dialog d = builder.create();
        d.show();
    }

    Dialog ProgressDialog;
    @Override
    public void showProgressDialog() {
        if(ProgressDialog == null){
            ProgressDialog = new ProgressDialog(getActivity());
            ProgressDialog.setCanceledOnTouchOutside(false);
            ProgressDialog.setTitle("Loading");
        }
        ProgressDialog.show();
    }

    @Override
    public void dismissProgressDialog() {
        if(ProgressDialog==null||!ProgressDialog.isShowing()){
            return;
        }
        ProgressDialog.dismiss();
    }

    @Override
    public void showMsg(String msg) {
        Snackbar.make(mBrowse.getView(),msg,Snackbar.LENGTH_SHORT).show();
    }

}
