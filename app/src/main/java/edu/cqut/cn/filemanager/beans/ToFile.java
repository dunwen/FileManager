package edu.cqut.cn.filemanager.beans;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.widget.ImageView;
import android.widget.TextView;

import com.dundunwen.annotation.BindId;
import com.dundunwen.annotation.BindThirdPartId;

import edu.cqut.cn.filemanager.R;

/**
 * Created by dun on 2016/2/26.
 */
public class ToFile {
    String path = "";
    boolean isDirectory = false;
    String fileType = "";

    @BindId(Id = R.id.simpleTv,ViewType = TextView.class)
    String fileName = "";

    @BindThirdPartId(Id = R.id.imageView,viewType = de.hdodenhof.circleimageview.CircleImageView.class,methodName = "setImageBitmap")
    Bitmap icon;

    @BindId(Id = R.id.imageViewFormHttp,ViewType = ImageView.class)
    String iconUrl = "";

    public String getFileName() {
        return fileName;
    }

    public ToFile() {
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public Bitmap getIcon() {
        return icon;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
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

    public boolean isDirectory() {
        return isDirectory;
    }

    public void setIsDirectory(boolean isDirectory) {
        this.isDirectory = isDirectory;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}
