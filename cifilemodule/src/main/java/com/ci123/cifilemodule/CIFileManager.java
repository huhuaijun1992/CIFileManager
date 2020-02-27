package com.ci123.cifilemodule;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;

import com.ci123.cifilemodule.listener.CopyListener;
import com.ci123.cifilemodule.listener.FileListener;
import com.ci123.cifilemodule.listener.ReadFileListener;
import com.ci123.cifilemodule.service.ManagerService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

/**
 * @author: 11304
 * @date: 2020/2/24
 * @desc:
 */
public class CIFileManager implements ManagerService {

    /**
     * 上下文
     */
    private Context context;
    /**
     * 保存的根路径
     */
    private String rootPath;
    /**
     * 数据隔离路径
     */
    private String savePath;

    private static final CIFileManager ourInstance = new CIFileManager();

    public static CIFileManager getInstance() {
        return ourInstance;
    }

    private CIFileManager() {
    }

    @Override
    public boolean init(Context context) {
        this.context = context.getApplicationContext();
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            rootPath = context.getExternalFilesDir(null).getPath();

        } else {
            rootPath = context.getFilesDir().getPath();
        }
        CICachDataManager.getInstance().init(context.getApplicationContext());
        CISpManager.getInstance().init(context.getApplicationContext());
        return true;
    }

    @Override
    public void setNameSpace(String nameSpace) {
        File file = new File(rootPath);
        if (file.exists()) {
            File file1 = new File(rootPath, nameSpace);
            if (!file1.exists()) {
                file1.mkdir();
            }
        } else {
            file.mkdir();
            File file1 = new File(rootPath, nameSpace);
            if (!file1.exists()) {
                file1.mkdir();
            }
        }
        this.savePath = rootPath + "/" + nameSpace;
    }

    @Override
    public String getCurrentSpacePath() throws FileNotFoundException {
        File file = new File(savePath);
        if (file.exists()) {
            return file.getPath();
        } else {
            throw new FileNotFoundException();
        }
    }

    @Override
    public void readRawFile(final int rawFileId, final String encodeType, final ReadFileListener listener) {
        String res = "";
        try {
            //得到资源中的Raw数据流
            InputStream in = context.getResources().openRawResource(rawFileId);
            //得到数据的大小
            int length = in.available();
            byte[] buffer = new byte[length];
            //读取数据
            in.read(buffer);
            //依test.txt的编码类型选择合适的编码，如果不调整会乱码
            if (encodeType != null && encodeType.length() > 0) {
                res = EncodingUtils.getString(buffer, encodeType);
            } else {
                res = EncodingUtils.getString(buffer, "UTF-8");
            }

            //关闭
            in.close();
            listener.onSuccess(res);

        } catch (Exception e) {
            e.printStackTrace();
            listener.onfail("读取失败");
        }

    }

    @Override
    public void readAssetFile(String fileName, String encodeType, ReadFileListener listener) {
        String res = "";
        try {

            //得到资源中的asset数据流
            InputStream in = context.getResources().getAssets().open(fileName);

            int length = in.available();
            byte[] buffer = new byte[length];

            in.read(buffer);
            in.close();
            if (encodeType != null && encodeType.length() > 0) {
                res = EncodingUtils.getString(buffer, encodeType);
            } else {
                res = EncodingUtils.getString(buffer, "UTF-8");
            }
            listener.onSuccess(res);
        } catch (Exception e) {
            e.printStackTrace();
            listener.onfail("读取文件失败");

        }
    }

    @Override
    public void writeFile(String fileName, String data, FileListener listener) {
        try {
            FileOutputStream fout = context.openFileOutput(fileName, context.MODE_PRIVATE);
            byte[] bytes = data.getBytes();
            fout.write(bytes);
            fout.close();
            listener.onSuccess(true, "");
        } catch (Exception e) {
            e.printStackTrace();
            listener.onSuccess(false, e.getMessage());
        }

    }

    @Override
    public void readFile(String fileName, ReadFileListener listener) {
        String res = "";
        try {
            FileInputStream fin = context.openFileInput(fileName);
            int length = fin.available();
            byte[] buffer = new byte[length];
            fin.read(buffer);
            res = EncodingUtils.getString(buffer, "UTF-8");
            fin.close();
            listener.onSuccess(res);
        } catch (Exception e) {
            e.printStackTrace();
            listener.onfail(e.getMessage());
        }
    }

    @Override
    public void writeExtfile(String data, FileListener listener, String... paths) {
        String path = savePath;
        for (String s : paths) {
            path = path + "/" + s;
        }
        try {
            File file = new File(path);
            if (!file.getParentFile().exists()) {
                if (!file.getParentFile().mkdirs()) {
                    listener.onSuccess(false, "文件夹不存在");
                }
            }
            FileOutputStream fout = new FileOutputStream(path);
            byte[] bytes = data.getBytes();
            fout.write(bytes);
            fout.close();
            listener.onSuccess(true, "");
        } catch (Exception e) {
            e.printStackTrace();
            listener.onSuccess(false, e.getMessage());
        }
    }

    @Override
    public void readExtFile(String encodeType, ReadFileListener listener, String... paths) {
        String path = savePath;
        for (String s : paths) {
            path = path + "/" + s;
        }
        String res = "";
        try {
            FileInputStream fin = new FileInputStream(path);
            int length = fin.available();
            byte[] buffer = new byte[length];
            fin.read(buffer);
            if (encodeType != null && encodeType.length() > 0) {
                res = EncodingUtils.getString(buffer, encodeType);
            } else {
                res = EncodingUtils.getString(buffer, "UTF-8");
            }
            fin.close();
            listener.onSuccess(res);
        } catch (Exception e) {
            e.printStackTrace();
            listener.onfail(e.getMessage());
        }
    }

    @Override
    public void copyFile(File source, CopyListener copyListener, String... dirNames) {
        String destPath = this.savePath;
        for (String s : dirNames) {
            destPath = destPath + "/" + s;
        }
        File dest = new File(destPath);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
            if (is != null) {
                is.close();
            }
            if (os != null) {
                os.close();
            }
            copyListener.onSuccess(dest.getPath());
        } catch (Exception e) {
            copyListener.onFail("error");
        }

    }


    @Override
    public File[] getFileList(String... dirPath) {
        String path = savePath;
        for (String s : dirPath) {
            path = path + "/" + s;
        }
        File file = new File(path);
        if (file.exists() && file.isDirectory()) {
            return file.listFiles();
        }
        return new File[0];
    }

    @Override
    public boolean deleteDirOrFile(String... filePath) throws FileNotFoundException {
        String path = savePath;
        for (String s : filePath) {
            path = path + "/" + s;
        }
        File dir = new File(path);
        if (dir.exists()) {
            if (dir.isDirectory()) {
                String[] children = dir.list();
                for (String child : children) {
                    boolean success = deleteFile(dir);
                    if (!success) {
                        return false;
                    }
                }
            } else {
                return dir.delete();
            }
        } else {
            throw new FileNotFoundException();
        }
        return true;
    }

    @Override
    public boolean createDirOrFile(String... dirPath) throws IOException {
        String path = savePath;
        for (String s : dirPath) {
            path = path + "/" + s;
        }

        File dir = new File(path);
        if (dir.isDirectory()) {
            if (dir.exists()) {
                return true;
            } else {
                dir.mkdirs();
                return true;
            }
        } else {
            File file = new File(path);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (file.exists()){
                return false;
            }else {
                return file.createNewFile();
            }
        }
    }

    @Override
    public void clearCache(final FileListener listener) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                File cacheDir = context.getCacheDir();
                if (cacheDir.exists()) {
                    if (cacheDir.isDirectory()) {
                        String[] children = cacheDir.list();
                        for (String child : children) {
                            boolean success = deleteFile(new File(cacheDir, child));
                            if (!success) {
                                if (listener != null) {
                                    listener.onSuccess(success, "");
                                }
                            }
                        }
                    } else {
                        cacheDir.delete();
                    }
                }

                if (listener != null) {
                    listener.onSuccess(true, "");

                }
            }
        }.start();
    }

    @Override
    public long getTotalCacheSize() {
        long cacheSize = getFolderSize(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheSize += getFolderSize(context.getExternalCacheDir());
        }
        return cacheSize;
    }

    @Override
    public String getSaveRootPath() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return context.getExternalFilesDir(null).getPath();
        } else {
            return context.getFilesDir().getPath();
        }
    }

    @Override
    public String getcaCehePath() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return context.getExternalCacheDir().getPath();
        } else {
            return context.getCacheDir().getPath();
        }
    }

    @Override
    public String getDirPath(String... DirNames) {
        String path = savePath;
        for (String s : DirNames) {
            path = path + "/" + s;
        }
        File dir = new File(savePath);
        if (dir.exists()) {
            return dir.getPath();

        }
        return null;
    }

    @Override
    public long getDirSize(String... dirNames) {
        String path = savePath;
        for (String s : dirNames) {
            path = path + "/" + s;
        }
        File file = new File(path);
        return getFolderSize(file);
    }

    @Override
    public long getPhoneTotalSize() {
        File path = Environment.getDataDirectory();
        //系统的空间描述类
        StatFs stat = new StatFs(path.getPath());
        //每个区块占字节数
        long blockSize = stat.getBlockSize();
        //区块总数
        long totalBlocks = stat.getBlockCount();
        return totalBlocks * blockSize;
    }

    @Override
    public long getAvailSpace(String path) {
        StatFs statfs = new StatFs(path);

        long size = statfs.getBlockSize();//获取分区的大小

        long count = statfs.getAvailableBlocks();//获取可用分区块的个数

        return size * count;
    }

    /**
     * 删除文件夹
     *
     * @param dir 文件夹
     */
    private boolean deleteFile(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (String child : children) {
                boolean success = deleteFile(new File(dir, child));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

    /**
     * 获取文件大小
     *
     * @param file 文件夹
     */
    public long getFolderSize(File file) {
        if (!file.exists()) {
            file.mkdirs();
        }
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                // 如果下面还有文件
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 格式化单位
     *
     * @param size
     * @return
     */
    public String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
//            return size + "Byte";
            return "0KB";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "TB";
    }

    static class EncodingUtils {
        public static String getString(byte[] bytes, String encode) {
            String str = "";
            try {
                str = new String(bytes, encode);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return str;
        }
    }
}
