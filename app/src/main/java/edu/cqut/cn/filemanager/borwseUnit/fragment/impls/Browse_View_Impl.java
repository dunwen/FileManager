package edu.cqut.cn.filemanager.borwseUnit.fragment.impls;

import java.util.List;

import edu.cqut.cn.filemanager.beans.ToFile;

/**
 * Created by dun on 2016/3/4.
 *
 */
public interface Browse_View_Impl {
    void setFilesList(List<ToFile> list);
    void openImage(ToFile toFile);
    void openTxt(ToFile toFile);
    void openVideo(ToFile toFile);
    void showProgressDialog();
    void dismissProgressDialog();
    void showMsg(String msg);



}
