package com.ci123.cifilemodule.listener;

/**
 * @author: 11304
 * @date: 2020/2/25
 * @desc: 拷贝文件监听
 */
public interface CopyListener {
    void onSuccess(String localSavePath);

    void progress(int progress);

    void onFail(String msg);
}
