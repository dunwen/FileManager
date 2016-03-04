package edu.cqut.cn.filemanager.borwseUnit.fragment;

import java.util.List;

import edu.cqut.cn.filemanager.beans.ToFile;
import edu.cqut.cn.filemanager.borwseUnit.fragment.impls.Browse_Model_Impl;
import edu.cqut.cn.filemanager.borwseUnit.fragment.impls.Browse_Present_Impl;
import edu.cqut.cn.filemanager.borwseUnit.fragment.impls.Browse_View_Impl;

/**
 * Created by dun on 2016/3/4.
 *
 */
public class Borwse_Presenter implements Browse_Present_Impl{
    private Browse_Model_Impl model;
    private Browse_View_Impl view;

    public Borwse_Presenter(Browse_View_Impl view) {
        this.view = view;
        this.model = new Borwse_Model();
    }

    @Override
    public void getFileList() {
        List<ToFile> list = model.getFileList();
        view.setFilesList(list);
    }

    @Override
    public void selectFile(ToFile file) {
        switch (file.getFileType()){
            case ToFile.FILE_TYPE_DIRECTORY :{
                model.selectNextPath(file.getPath());
                getFileList();
            } break;
            case ToFile.FILE_TYPE_IMAGE: view.openImage(file);break;
            case ToFile.FILE_TYPE_VIDEO: view.openVideo(file);break;
            case ToFile.FILE_TYPE_TXT:view.openTxt(file);break;
            case ToFile.FILE_TYPE_UNKNOW:break;
        }
    }
}
