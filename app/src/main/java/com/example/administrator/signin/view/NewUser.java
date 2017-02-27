package com.example.administrator.signin.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.signin.R;
import com.example.administrator.signin.modul.User;

import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Administrator on 2016-12-29 .
 * 注册新用户
 */

public class NewUser extends AppCompatActivity {
    private android.widget.EditText newUserAccount;
    private android.widget.EditText newUserPassword;
    private android.widget.EditText newPasswordConfirm;
    private android.widget.Button newUserOk;
    private android.widget.EditText name;
    private User bu = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_user_layout);
        this.newUserOk = (Button) findViewById(R.id.newUserOk);
        this.newPasswordConfirm = (EditText) findViewById(R.id.newPasswordConfirm);
        this.newUserPassword = (EditText) findViewById(R.id.newUserPassword);
        this.newUserAccount = (EditText) findViewById(R.id.newUserAccount);
        this.name = (EditText) findViewById(R.id.name);

        newUserOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account, pass,na;
                na = name.getText().toString();
                account = newUserAccount.getText().toString();
                pass = newUserPassword.getText().toString();
                if (account.isEmpty()||pass.isEmpty()) {
                    Toast.makeText(NewUser.this, "帐号或密码不能为空", Toast.LENGTH_SHORT).show();
                }else {
                    if (pass.equals(newPasswordConfirm.getText().toString())) {
                         {
                            bu.setUsername(account);
                            bu.setPassword(pass);
                            bu.setName(na);
                            bu.setRole(0);
                            bu.signUp(NewUser.this, new SaveListener() {
                                @Override
                                public void onSuccess() {
                                    Toast.makeText(NewUser.this, "注册成功", Toast.LENGTH_SHORT).show();
                                    //通过BmobUser.getCurrentUser(context)方法获取登录成功后的本地用户信息
                                }
                                @Override
                                public void onFailure(int code, String msg) {
                                    // TODO Auto-generated method stub
                                    Toast.makeText(NewUser.this, "注册失败" + msg, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        finish();
                    } else {
                        Toast.makeText(NewUser.this, "两次密码不正确", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}

