package com.ci123.cifilemodule.listener;

/**
 * @author: 11304
 * @date: 2020/2/26
 * @desc:
 */
public interface ReadFileListener {
    void onSuccess(String data);
    void onfail(String errorMsg);
}
