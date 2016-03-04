package edu.cqut.cn.filemanager.Utils;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.cqut.cn.filemanager.beans.ToFile;

/**
 * Created by dun on 2016/3/4.
 */
public class Sort {
    public static void sort(List<ToFile> list){
        ToFile[] files = new ToFile[list.size()];
        list.toArray(files);
        Arrays.sort(files);
//        List<ToFile> newList = new ArrayList<>();
        list.clear();
        for (ToFile file : files) {
            list.add(file);
        }
    }
}
