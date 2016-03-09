package edu.cqut.cn.filemanager.borwseUnit.fragment.impls;

import java.util.List;

import edu.cqut.cn.filemanager.beans.ToFile;

/**
 * Created by dun on 2016/3/4.
 */
public interface Browse_Model_Impl {
    List<ToFile> getFileList();
    void selectNextPath(String fileName);
    void selectBackPath();
    boolean createNewFile(String fileName);
}
