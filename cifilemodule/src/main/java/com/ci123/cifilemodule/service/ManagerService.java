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
     * @param nameSpace 数据隔离名称
     */
    void setNameSpace(String nameSpace);

    /**
     * 获取当前使用的数据分割文件夹
     */
    String getCurrentSpacePath() throws FileNotFoundException;

    /**
     * 从raw文件夹中读取文件
     * @param rawFileId 文件id:R.raw.file名
     * @param encodeType 文件的编码类型（例："UTF-8"等，如果编码不对会出现乱码）
     * @param listener 读取监听
     * */
    void readRawFile(int rawFileId, String encodeType, ReadFileListener listener);

    /**
     * 读取应用asset文件内容
     * @param fileName 文件名
     * @param encodeType 文件编码类型
     * @param listener 读取监听
     * */
    void readAssetFile(String fileName, String encodeType, ReadFileListener listener);

    /**
     *在应用沙盒路径中写数据"data/data/<应用程序包名>/files/"
     * @param data 写入的数据
     * @param fileName 写入的文件名
     * @param listener 文件监听
     * 注意：如果向同一个文件里面写入文件会覆盖之前写入的内容，如非必须请务必重复写入同一个文件
     * */
    void writeFile(String fileName, String data, FileListener listener);

    /**
     * 读取沙盒路径中的文件数据"data/data/<应用程序包>/files/"
     * @param fileName 文件名
     * @param listener 读取监听
     * */
    void readFile(String fileName, ReadFileListener listener);

    /**
     * 在外存专属app文件目录中写文件,多次写入会覆盖之前的数据
     * @param data 写入内容
     * @param listener 文件监听
     * @param paths 文件路径（“img”,"ceshi.txt"）表示“img/ceshi.txt”
     * */
    void writeExtfile(String data, FileListener listener,String... paths);

    /**
     * 读取外存专属目录文件
     * @param encodeType 编码方式 默认UTF-8
     * @param paths 读取路径（“img”,"ceshi.txt"）表示“img/ceshi.txt”
     * @param listener 读取监听
     * */
    void readExtFile(String encodeType,ReadFileListener listener, String... paths);

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
     * @param dirPaths   文件夹名称例（"img","ceshi"）表示文件路径下的的“/img/ceshi"文件夹
     */
    File[] getFileList(String... dirPaths);

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
    boolean createDirOrFile(String... dirPath) throws IOException;

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
     * 获取缓存保存路径
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
