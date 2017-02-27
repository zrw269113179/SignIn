package com.example.administrator.signin.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.signin.R;
import com.example.administrator.signin.modul.User;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Administrator on 2016-12-26 .
 * 登录界面
 */

public class LoginActivity extends AppCompatActivity {
    private EditText username;
    private EditText keyword;
    private Button login;
    private TextView newuser;
    public static String APPID ="ab82c2ef0f28212838c8f9644bfa2912";
    private User bu = new User();
    private String user;
    private String pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        this.login = (Button) findViewById(R.id.login);
        this.keyword = (EditText) findViewById(R.id.keyword);
        this.username = (EditText) findViewById(R.id.username);
        this.newuser = (TextView) findViewById(R.id.newUser);
        //提供以下两种方式进行初始化操作：
        //第一：设置BmobConfig，允许设置请求超时时间、文件分片上传时每片的大小、文件的过期时间(单位为秒)
        BmobConfig config =new BmobConfig.Builder(this)
                //设置appkey
                .setApplicationId(APPID)
                //请求超时时间（单位为秒）：默认15s
                .setConnectTimeout(30)
                //文件分片上传时每片的大小（单位字节），默认512*1024
                .setUploadBlockSize(1024*1024)
                //文件的过期时间(单位为秒)：默认1800s
                .setFileExpiration(5500)
                .build();
        Bmob.initialize(config);
        //第二：默认初始化
        Bmob.initialize(this,APPID);
        newuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(LoginActivity.this,NewUser.class));
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = username.getText().toString().trim();
                pass = keyword.getText().toString().trim();
                bu.setUsername(user);
                bu.setPassword(pass);
                bu.login(LoginActivity.this, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        User user = BmobUser.getCurrentUser(LoginActivity.this,User.class);
                        if (user.getRole()==0) {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            Toast.makeText(LoginActivity.this, "教师帐号禁止登录", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(int code, String msg) {
                        Toast.makeText(LoginActivity.this, "登录失败" + msg, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
