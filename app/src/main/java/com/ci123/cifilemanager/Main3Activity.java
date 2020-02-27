package com.ci123.cifilemanager;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ci123.cifilemodule.Book;
import com.ci123.cifilemodule.CISpManager;

public class Main3Activity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 保存key
     */
    private Button mSaveString;
    /**
     * 清除某个key的值
     */
    private Button mDeleteString;
    /**
     * 获取某个key的值
     */
    private Button mGetString;
    /**
     * 清空所有的key
     */
    private Button mDeleteAll;
    private LinearLayout mLin;
    private TextView mContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initView();
    }

    private void initView() {
        mSaveString = (Button) findViewById(R.id.save_string);
        mSaveString.setOnClickListener(this);
        mDeleteString = (Button) findViewById(R.id.delete_string);
        mDeleteString.setOnClickListener(this);
        mGetString = (Button) findViewById(R.id.get_string);
        mGetString.setOnClickListener(this);
        mDeleteAll = (Button) findViewById(R.id.delete_all);
        mDeleteAll.setOnClickListener(this);
        mLin = (LinearLayout) findViewById(R.id.lin);
        mContent = (TextView) findViewById(R.id.content);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.save_string:
                CISpManager.getInstance().put("accountId", 112);
                Book book = new Book(1, "数学书", "张三");
                CISpManager.getInstance().saveObject("book", book);
                break;
            case R.id.delete_string:
                CISpManager.getInstance().remove("book");

                break;
            case R.id.get_string:
                int accountId = CISpManager.getInstance().get("accountId", 0);
                Book book1 = CISpManager.getInstance().readObject("book", Book.class);
                if (book1 != null)
                    mContent.setText("accountId的值：" + accountId + "\n" + "书：" + book1.toString());
                else
                    mContent.setText("accountId的值：" + accountId);
                break;
            case R.id.delete_all:
                CISpManager.getInstance().clear();
                break;
        }
    }
}
