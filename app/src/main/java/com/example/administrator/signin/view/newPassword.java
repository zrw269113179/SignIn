package com.example.administrator.signin.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.signin.R;
import com.example.administrator.signin.modul.User;

import cn.bmob.v3.listener.UpdateListener;

import static com.example.administrator.signin.view.MainActivity.user;

/**
 * Created by Administrator on 2016-12-26 .
 * 更改密码界面
 */

public class newPassword extends AppCompatActivity {
    private EditText usedPass;
    private EditText newPass;
    private EditText confPass;
    private Button newPassOK;
    private TextView showAccount;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_password);
        this.newPassOK = (Button) findViewById(R.id.newPass_OK);
        this.confPass = (EditText) findViewById(R.id.confPass);
        this.newPass = (EditText) findViewById(R.id.newPass);
        this.usedPass = (EditText) findViewById(R.id.usedPass);
        this.showAccount = (TextView) findViewById(R.id.show_account);
        showAccount.setText(user.getName());
        getSupportActionBar().hide();
        newPassOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = usedPass.getText().toString().trim();
                    String con = confPass.getText().toString().trim();
                    String pass = newPass.getText().toString().trim();
                    if (con.equals(pass)){
                        User.updateCurrentUserPassword(newPassword.this, str, pass, new UpdateListener() {

                            @Override
                            public void onSuccess() {
                                // TODO Auto-generated method stub
                                Toast.makeText(newPassword.this, "密码修改成功",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(int code, String msg) {
                                // TODO Auto-generated method stub
                                Toast.makeText(newPassword.this, "密码修改失败："+msg+"("+code+")",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else {
                        Toast.makeText(newPassword.this, "两次输入的密码不同!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }
}
