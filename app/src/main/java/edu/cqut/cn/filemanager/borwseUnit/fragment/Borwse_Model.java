package edu.cqut.cn.filemanager.borwseUnit.fragment;

import java.util.List;

import edu.cqut.cn.filemanager.Utils.FileHelper;
import edu.cqut.cn.filemanager.beans.ToFile;
import edu.cqut.cn.filemanager.borwseUnit.fragment.impls.Browse_Model_Impl;

/**
 * Created by dun on 2016/3/4.
 *
 */
public class Borwse_Model implements Browse_Model_Impl {
    FileHelper mFileHelper;

    public Borwse_Model() {
        mFileHelper = FileHelper.getInstance();
    }

    @Override
    public List<ToFile> getFileList() {
        return mFileHelper.getFileFromCurrentPath();
    }

    @Override
    public void selectNextPath(String fileName) {
        mFileHelper.selectNextFileDirectory(fileName);
    }


    @Override
    public void selectBackPath() {


    }

    @Override
    public boolean createNewFile(String name) {
        return mFileHelper.createFileOnCurrentPath(name);
    }
}
