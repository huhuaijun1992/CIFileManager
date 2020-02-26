package com.ci123.cifilemodule.service;

import android.content.Context;

import com.ci123.cifilemodule.listener.CopyListener;
import com.ci123.cifilemodule.listener.FileListener;
import com.ci123.cifilemodule.listener.ReadFileListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author: 11304
 * @date: 2020/2/24
 * @desc:
 */
public interface ManagerService {

    public static int cacheFileType = 1;
    public static int longTimeFile = 2;
    public static int imageType = 1;
    public static int publicFile = 2;
    public static int audioFile = 3;
    public static int videoFile = 4;


    /**
     * 初始化，
     *
     * @param context 上下文
     */
    boolean init(Context context);

    /**
     * 设置数据隔离文件夹
     */
    void setNameSpace(String nameSpace);

    /**
     * 获取当前使用的数据分割文件夹
     */
    String getCurrentSpacePath() throws FileNotFoundException;

    /**
     * 将string写入文件
     * @param encoder 编码方式（utf-8等）
     * @param data 写入的内容
     * @param paths 保存文件的位置及文件名（例）("accountId","txt","ceshi.txt")
     * @param listener 文件监听
     *
     */
    void saveFile(String encoder, ArrayList<String> data, FileListener listener,String... paths);

    /**
     * 获取文件数据
     *
     * @param  encode 编码方式
     * @param listener 读取监听
     * @param paths 保存的位置（例）("accountId","txt","ceshi.txt") 既路径地址为：/accountId/txt/ceshi.txt
     *
     */
    void readFileData(String encode, ReadFileListener listener,String... paths);

    /**
     * 拷贝文件
     *
     * @param source             源文件
     * @param copyListener       复制监听
     * @param dirNames           文件夹名称例（"img","ceshi.txt"）表示文件路径下的的“/img/ceshi.txt”
     */
    void copyFile(File source, CopyListener copyListener, String... dirNames);

    /**
     * 文件夹下的文件所有文件
     *
     * @param dirPath 文件夹路径 （"img","ceshi.txt"）表示文件路径下的的“/img/ceshi.txt”
     */
    File[] getFileList(String dirPath);

    /**
     * 删除文件夹或者文件
     *
     * @param paths
     */
    boolean deleteDirOrFile(String... paths) throws FileNotFoundException;

    /**
     * 创建文件夹或者文件
     *
     * @param dirPath 示例("cache","img")
     */
    File createDirOrFile(String... dirPath) throws IOException;

    /**
     * 清除缓存
     */
    void clearCache(FileListener listener);

    /**
     * 获取app缓存大小
     */
    long getTotalCacheSize();

    /**
     * 获取app保存根路径
     */
    String getSaveRootPath();

    /**
     * 获取临时文件保存路径
     */
    String getcaCehePath();

    /**
     * 获取文件夹路径
     *
     * @param DirNames 文件夹名
     */
    String getDirPath(String... DirNames);

    /**
     * 获取文件大小
     *
     * @param dirNames 文件夹名称
     */
    long getDirSize(String... dirNames);

    /**
     * 获取手机存储总大小
     */
    long getPhoneTotalSize();

    /**
     * 获取目录可用控件大小
     */
    long getAvailSpace(String path);

}
