package com.ci123.cifilemanager;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ci123.cifilemodule.CICachDataManager;

import org.json.JSONException;
import org.json.JSONObject;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 保存key = accountId
     */
    private Button saveString;
    /**
     * 保存key = accountId
     */
    private Button deleteString;
    private TextView content;
    /**
     * 保存key
     */
    private Button mSaveString;
    /**
     * 清除某个key的值
     */
    private Button mDeleteString;
    /**
     * 清空所有的key
     */
    private Button mDeleteAll;
    /**
     * 获取某个key的值
     */
    private Button getString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initView();
    }

    private void initView() {
        saveString = (Button) findViewById(R.id.save_string);
        saveString.setOnClickListener(this);
        deleteString = (Button) findViewById(R.id.delete_string);
        deleteString.setOnClickListener(this);
        content = (TextView) findViewById(R.id.content);

        mSaveString = (Button) findViewById(R.id.save_string);
        mSaveString.setOnClickListener(this);
        mDeleteString = (Button) findViewById(R.id.delete_string);
        mDeleteString.setOnClickListener(this);
        mDeleteAll = (Button) findViewById(R.id.delete_all);
        mDeleteAll.setOnClickListener(this);
        getString = (Button) findViewById(R.id.get_string);
        getString.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.save_string:
                CICachDataManager.getInstance().putString("accountId", "测试值，在内存中存储10秒", 10);
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("with", 10);
                    jsonObject.put("height", 100);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //保存10天
                CICachDataManager.getInstance().putJSONObject("json", jsonObject, 10 * CICachDataManager.TIME_DAY);


                break;
            case R.id.delete_string:
                boolean b = CICachDataManager.getInstance().remove("json");
                Toast.makeText(getBaseContext(), String.valueOf(b), Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete_all:
                CICachDataManager.getInstance().clear();
                content.setText("");

                break;
            case R.id.get_string:
                String s = CICachDataManager.getInstance().getAsString("accountId");
                JSONObject jsonObject1 = CICachDataManager.getInstance().gettAsJSONObject("json");
                if (jsonObject1 != null) {
                    s = "accountId的值："+s + "\n" +"保存的的json值："+ jsonObject1.toString();
                }
                if (s != null && s.length() > 0) {
                    content.setText(s);
                } else {
                    Toast.makeText(this, "暂无该记录", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
