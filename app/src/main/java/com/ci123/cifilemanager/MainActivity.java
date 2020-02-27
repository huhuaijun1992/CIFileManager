package com.ci123.cifilemanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.ci123.cifilemodule.CIFileManager;
import com.ci123.cifilemodule.listener.FileListener;
import com.ci123.cifilemodule.listener.ReadFileListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 读取raw资源文件夹中的文件数据
     */
    private Button readRaw;
    /**
     * 读取asset资源文件夹中的文件数据
     */
    private Button readAssets;
    /**
     * 向app专属文件夹中写数据（不可见）
     */
    private Button writeinternal;
    /**
     * 向app专属文件夹中读取数据
     */
    private Button readinternal;
    /**
     * 向外存专属文件夹中写数据（可见）
     */
    private Button writeexternal;
    /**
     * 向外存专属文件夹中读取数据（可见）
     */
    private Button readexternal;
    /**
     * 获取外存中专属文件夹中的文件列表
     */
    private Button getallfiles;
    /**
     * 创建文件或者文件夹
     */
    private Button createfileOrdir;
    /**
     * 删除文件夹或者文件
     */
    private Button deletefileOrdir;
    /**
     * 获取app缓存大小
     */
    private Button gettotalcacheszie;
    /**
     * 清除app缓存
     */
    private Button clearApp;
    /**
     * 获取外存根路径大小
     */
    private Button getrootpath;
    private TextView contentTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        CIFileManager.getInstance().setNameSpace("fileManager");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent();
        switch (item.getItemId()) {
            case R.id.cache:
                intent.setClass(getBaseContext(),Main2Activity.class);
                startActivity(intent);
                break;
            case R.id.sharedpreference:
                intent.setClass(getBaseContext(),Main3Activity.class);
                startActivity(intent);
                break;
        }
        return true;
    }

    private void initView() {
        readRaw = (Button) findViewById(R.id.readRaw);
        readRaw.setOnClickListener(this);
        readAssets = (Button) findViewById(R.id.readAssets);
        readAssets.setOnClickListener(this);
        writeinternal = (Button) findViewById(R.id.writeinternal);
        writeinternal.setOnClickListener(this);
        readinternal = (Button) findViewById(R.id.readinternal);
        readinternal.setOnClickListener(this);
        writeexternal = (Button) findViewById(R.id.writeexternal);
        writeexternal.setOnClickListener(this);
        readexternal = (Button) findViewById(R.id.readexternal);
        readexternal.setOnClickListener(this);
        getallfiles = (Button) findViewById(R.id.getallfiles);
        getallfiles.setOnClickListener(this);
        createfileOrdir = (Button) findViewById(R.id.createfileOrdir);
        createfileOrdir.setOnClickListener(this);
        deletefileOrdir = (Button) findViewById(R.id.deletefileOrdir);
        deletefileOrdir.setOnClickListener(this);
        gettotalcacheszie = (Button) findViewById(R.id.gettotalcacheszie);
        gettotalcacheszie.setOnClickListener(this);
        clearApp = (Button) findViewById(R.id.clearApp);
        clearApp.setOnClickListener(this);
        getrootpath = (Button) findViewById(R.id.getrootpath);
        getrootpath.setOnClickListener(this);
        contentTv = (TextView) findViewById(R.id.content_tv);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.readRaw:
                CIFileManager.getInstance().readRawFile(R.raw.hello, "UTF-8", new ReadFileListener() {
                    @Override
                    public void onSuccess(String data) {
                        contentTv.setText(data);
                        Toast.makeText(getBaseContext(), "读取文件成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onfail(String errorMsg) {

                    }
                });
                break;
            case R.id.readAssets:
                CIFileManager.getInstance().readAssetFile("hello.txt", null, new ReadFileListener() {
                    @Override
                    public void onSuccess(String data) {
                        contentTv.setText(data);
                        Toast.makeText(getBaseContext(), "读取文件成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onfail(String errorMsg) {

                    }
                });
                break;
            case R.id.writeinternal:
                String data = "这是一个写入app专属文件夹的内容，用户在不可见的文件，一般文件不是私密文件不使用这种方式写入文件";
                CIFileManager.getInstance().writeFile("internal.txt", data, new FileListener() {
                    @Override
                    public void onSuccess(boolean isSuccess, String msg) {
                        Toast.makeText(getBaseContext(), "写入专属app路径文件夹" + isSuccess + msg, Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.readinternal:
                CIFileManager.getInstance().readFile("internal.txt", new ReadFileListener() {
                    @Override
                    public void onSuccess(String data) {
                        Toast.makeText(getBaseContext(), "文件内容" + data, Toast.LENGTH_SHORT).show();
                        contentTv.setText(data);
                    }

                    @Override
                    public void onfail(String errorMsg) {
                        Toast.makeText(getBaseContext(), "读取文件失败" + errorMsg, Toast.LENGTH_SHORT).show();
                    }
                });

                break;
            case R.id.writeexternal:
                CIFileManager.getInstance().writeExtfile("你好啊陌生人，这是测试将内容写进外存文件", new FileListener() {
                    @Override
                    public void onSuccess(boolean isSuccess, String msg) {
                        Toast.makeText(getBaseContext(), "写入文件" + isSuccess, Toast.LENGTH_SHORT).show();
                    }
                }, "ceshi","ceshi.txt");
                break;
            case R.id.readexternal:
                CIFileManager.getInstance().readExtFile(null, new ReadFileListener() {
                    @Override
                    public void onSuccess(String data) {
                        Toast.makeText(getBaseContext(), "读取文件：" + data, Toast.LENGTH_SHORT).show();
                        contentTv.setText(data);
                    }

                    @Override
                    public void onfail(String errorMsg) {

                    }
                }, "ceshi","ceshi.txt");
                break;
            case R.id.getallfiles:
               File[] files = CIFileManager.getInstance().getFileList("ceshi");
                String s="";
                if (files.length>0){
                    for (File file:files){
                        s =s+file.getName()+"\n";
                        contentTv.setText(s);
                    }
                }else {
                    contentTv.setText("空文件夹");
                }
                break;
            case R.id.createfileOrdir:
                try {
                   boolean b= CIFileManager.getInstance().createDirOrFile("ceshi","ceshi2","ceshi3","ceshi.txt");
                   if (b){
                       Toast.makeText(getBaseContext(),"创建成功",Toast.LENGTH_SHORT).show();
                   }else {
                       Toast.makeText(getBaseContext(),"创建失败",Toast.LENGTH_SHORT).show();
                   }
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getBaseContext(),"创建失败",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.deletefileOrdir:
                try {
                  boolean b=  CIFileManager.getInstance().deleteDirOrFile("ceshi","ceshi.txt");
                  if (b){
                      Toast.makeText(getBaseContext(),"删除成功",Toast.LENGTH_SHORT).show();
                  }else {
                      Toast.makeText(getBaseContext(),"删除失败",Toast.LENGTH_SHORT).show();
                  }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(getBaseContext(),"文件夹不存在",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.gettotalcacheszie:
                long size = CIFileManager.getInstance().getTotalCacheSize();
                contentTv.setText(CIFileManager.getInstance().getFormatSize(size));
                break;
            case R.id.clearApp:
                CIFileManager.getInstance().clearCache(new FileListener() {
                    @Override
                    public void onSuccess(boolean isSuccess, String msg) {
                        Toast.makeText(getBaseContext(),"清除缓存成功",Toast.LENGTH_SHORT).show();
                        long size = CIFileManager.getInstance().getTotalCacheSize();
                        contentTv.setText(CIFileManager.getInstance().getFormatSize(size));
                    }
                });
                break;
            case R.id.getrootpath:
               String path = CIFileManager.getInstance().getSaveRootPath();
               contentTv.setText(path);
                break;
        }
    }
}
