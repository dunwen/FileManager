package edu.cqut.cn.filemanager.Utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.cqut.cn.filemanager.R;
import edu.cqut.cn.filemanager.beans.ToFile;

/**
 * Created by dun on 2016/3/3.
 */
public class FileHelper {

    private static final String TAG = "FileHelper";

    private String currentPath;
    private List<ToFile> fileListOfCurrentPath;
    private static FileHelper fileHelper;
    private File fileOfCurrentPath;
    private String rootPath = "";
    private boolean isDebug = true;
    private Context applicationContext;

    private FileHelper() {
        fileOfCurrentPath = Environment.getExternalStorageDirectory();
//        fileOfCurrentPath = new File(Environment.getExternalStorageDirectory().getPath()+"/AddressBook");
        currentPath = fileOfCurrentPath.getAbsolutePath();
        rootPath = currentPath;
        fileListOfCurrentPath = new ArrayList<>();
        getAllFilesFromCurrentPath();
    }

    public static FileHelper getInstance() {
        if (fileHelper == null) {
            fileHelper = new FileHelper();
        }
        return fileHelper;
    }

    public void selectNextFileDirectory(String DirectoryName) {
        currentPath = DirectoryName;
        notifyPathChange();
    }

    private void notifyPathChange() {
        fileOfCurrentPath = new File(currentPath);
        getAllFilesFromCurrentPath();
    }

    public List<ToFile> getFileFromCurrentPath() {
        return fileListOfCurrentPath;
    }


    public void getAllFilesFromCurrentPath() {
        fileListOfCurrentPath.clear();
        if(!currentPath.equals(rootPath)){
            ToFile file = new ToFile();
            file.setPath(getBackPath());
            file.setFileType(ToFile.FILE_TYPE_DIRECTORY);
            file.setIconPath(getIconPath(ToFile.FILE_TYPE_DIRECTORY, null));
            file.setFileName("...");
            fileListOfCurrentPath.add(file);
        }

        File[] files = fileOfCurrentPath.listFiles();
        for (File file : files) {
            String fileName = file.getName();
            int fileType = getFileType(file);
            String path = null;

            path = file.getAbsolutePath();
            String iconPath = getIconPath(fileType,path);

            ToFile to = new ToFile();
            to.setFileName(fileName);
            to.setFileType(fileType);
            to.setPath(path);
            to.setIconPath(iconPath);

            fileListOfCurrentPath.add(to);

            debug(to.toString());
        }

            Sort.sort(fileListOfCurrentPath);

    }

    private String getIconPath(int fileType, String path) {
        switch (fileType){
            case 0 : return R.mipmap.iconfont_wenjianjia + "";
            case 1 : return "file://"+path;
            case 2 : return R.mipmap.iconfont_txt + "";
            case 3 : return R.mipmap.iconfont_dianying + "";
            case 4 : return R.mipmap.iconfont_qita + "";
        }
        return null;
    }

    private int getFileType(File file) {
        if (!file.isDirectory()) {
            String fileName = file.getName();
            int index = fileName.lastIndexOf(".");
            String ect = fileName.substring(index+1, fileName.length());

            ect = ect.toLowerCase();
            if (ect.equals("txt")) {
                return ToFile.FILE_TYPE_TXT;
            } else if (isImage(ect)) {
                return ToFile.FILE_TYPE_IMAGE;
            } else if (isVideo(ect)) {
                return ToFile.FILE_TYPE_VIDEO;
            } else {
                return ToFile.FILE_TYPE_UNKNOW;
            }
        } else {
            return ToFile.FILE_TYPE_DIRECTORY;
        }
    }

    private void debug(String msg) {
        if (isDebug) {
            Log.i(TAG, "debug: msg>>>>>>>" + msg);
        }
    }

    public String getBackPath(){
        int index = currentPath.lastIndexOf("/");
        return currentPath.substring(0,index);

    }


    private boolean isImage(String ect) {
        return ect.equals("png") || ect.equals("jpg") || ect.equals("jpeg");
    }

    public Context getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    private boolean isVideo(String ect) {
        return ect.equals("mp4") || ect.equals("rmvb");
    }

}
