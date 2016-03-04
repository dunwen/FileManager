package edu.cqut.cn.filemanager.beans;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.TextView;

import com.dundunwen.FastAdapter.annotation.BindId;

import java.net.URI;
import java.util.Comparator;

import edu.cqut.cn.filemanager.R;

/**
 * Created by dun on 2016/2/26.
 *
 */
public class ToFile implements Comparable{
    public static final int FILE_TYPE_DIRECTORY = 0;
    public static final int FILE_TYPE_IMAGE = 1;
    public static final int FILE_TYPE_TXT = 2;
    public static final int FILE_TYPE_VIDEO = 3;
    public static final int FILE_TYPE_UNKNOW = 4;

    String path = "";

    int fileType = 4;

    @BindId(ViewType = TextView.class,Id = R.id.tv_file_name)
    String fileName = "";

    @BindId(Id = R.id.imageView,ViewType = ImageView.class)
    String iconPath = "";


    public String getFileName() {
        return fileName;
    }

    public ToFile() {
    }


    public String getPath() {
        return path;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setPath(String path) {
        this.path = path;
    }


    public int getFileType() {
        return fileType;
    }

    public void setFileType(int fileType) {
        this.fileType = fileType;
    }

    @Override
    public String toString() {
        return "ToFile{" +
                "path='" + path + '\'' +
                ", fileType=" + fileType +
                ", fileName='" + fileName + '\'' +
                '}';
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    @Override
    public int compareTo(Object another) {
        if(((ToFile)another).getFileType()!=fileType){
            return fileType - ((ToFile) another).fileType;
        }else{
            return this.fileName.compareTo(((ToFile) another).getFileName());
        }
    }
}
