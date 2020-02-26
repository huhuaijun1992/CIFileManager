package com.ci123.cifilemodule;

import android.content.Context;
import android.graphics.Bitmap;

import com.ci123.cifilemodule.uitil.ACache;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * @author: 11304
 * @date: 2020/2/26
 * @desc:缓存数据管理类
 */
class CICachDataManager {
    ACache aCache;
    public static int TIME_DAY = ACache.TIME_DAY;
    public static int TIME_HOUR = ACache.TIME_HOUR;
    private static final CICachDataManager ourInstance = new CICachDataManager();

    public static CICachDataManager getInstance() {
        return ourInstance;
    }

    private CICachDataManager() {
    }

    /**
     * 初始化
     */
    public void init(Context context) {
        aCache = ACache.get(context.getApplicationContext());
    }


    /**
     * 缓存String，到期后自动删除
     *
     * @param key      保存的key
     * @param value    保存的值
     * @param saveTime 保存的时间，（例保存10天，savetime = 10*CICachDataManager.TIME_DAY），永久保存就填0
     * @description:
     */
    public void putString(String key, String value, int saveTime) {
        if (saveTime == 0) {
            aCache.put(key, value);
        } else {
            aCache.put(key, value, saveTime);
        }

    }

    /**
     * 缓存JSON
     *
     * @param key
     * @param jsonObject
     * @param saveTime   保存时间，（例保存10天，savetime = 10*CICachDataManager.TIME_DAY），永久保存就填0
     */
    public void putJSONObject(String key, JSONObject jsonObject, int saveTime) {
        if (saveTime == 0) {
            aCache.put(key, jsonObject);
        } else {
            aCache.put(key, jsonObject, saveTime);
        }
    }

    /**
     * 缓存jsonObject
     *
     * @param key
     * @param jsonArray
     * @param saveTime  保存时间，（例保存10天，savetime = 10*CICachDataManager.TIME_DAY），永久保存就填0
     */
    public void putJSONArray(String key, JSONArray jsonArray, int saveTime) {
        if (saveTime == 0) {
            aCache.put(key, jsonArray);
        } else {
            aCache.put(key, jsonArray, saveTime);
        }
    }

    /**
     * 缓存序列化对象
     *
     * @param key
     * @param serializable
     * @param saveTime     保存时间，（例保存10天，savetime = 10*CICachDataManager.TIME_DAY），永久保存就填0
     */
    public void putSerializable(String key, Serializable serializable, int saveTime) {
        if (saveTime == 0) {
            aCache.put(key, serializable);
        } else {
            aCache.put(key, serializable, saveTime);
        }
    }

    /**
     * 缓存bytes
     *
     * @param key      键值
     * @param bytes
     * @param saveTIme 保存时间（例保存10天，savetime = 10*CICachDataManager.TIME_DAY），永久保存就填0
     */
    public void putBytes(String key, Byte[] bytes, int saveTIme) {
        if (saveTIme == 0) {
            aCache.put(key, bytes);
        } else {
            aCache.put(key, bytes, saveTIme);
        }
    }

    /**
     * 缓存bitmap
     *
     * @param key      键值
     * @param bitmap
     * @param saveTime 保存时长
     */
    public void putBitmap(String key, Bitmap bitmap, int saveTime) {
        if (saveTime == 0) {
            aCache.put(key, bitmap);
        } else {
            aCache.put(key, bitmap, saveTime);
        }
    }


    /**
     * 获取string类型值
     *
     * @param key 键值
     */
    public String getAsString(String key) {
        return aCache.getAsString(key);
    }

    /**
     * 获取字节
     *
     * @param key 键值
     */
    public byte[] getAsBytes(String key) {
        return aCache.getAsBinary(key);
    }

    /**
     * 获取序列化对象
     *
     * @param key
     */
    public Object getAsSerializable(String key) {
        return aCache.getAsObject(key);
    }

    /**
     * 获取jsonObject对象
     *
     * @param key 键值
     */
    public JSONObject gettAsJSONObject(String key) {
        return aCache.getAsJSONObject(key);
    }

    /**
     * 获取jsonArray
     *
     * @param key 键值
     */
    public JSONArray getAsJSONarry(String key) {
        return aCache.getAsJSONArray(key);
    }

    /**
     * 获取bitmap
     *
     * @param key 键值
     */
    public Bitmap getAsBitmap(String key) {
        return aCache.getAsBitmap(key);
    }

}
