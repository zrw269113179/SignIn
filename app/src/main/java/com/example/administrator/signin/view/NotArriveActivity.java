package com.example.administrator.signin.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.administrator.signin.adapter.NotArriveAdapter;
import com.example.administrator.signin.R;
import com.example.administrator.signin.modul.NotArrive;
import com.example.administrator.signin.modul.User;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.FindListener;

public class NotArriveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_arrive);
        User user = BmobUser.getCurrentUser(NotArriveActivity.this,User.class);
        BmobQuery<NotArrive> query = new BmobQuery<NotArrive>();
        query.addWhereEqualTo("Id",Integer.valueOf(user.getUsername()));
//返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(50);
//执行查询方法
        query.findObjects(this, new FindListener<NotArrive>() {
            @Override
            public void onSuccess(List<NotArrive> object) {
                // TODO Auto-generated method stub
                if (object.size() == 0) {
                    Toast.makeText(NotArriveActivity.this, "无记录", Toast.LENGTH_SHORT).show();
                } else {
                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
                    LinearLayoutManager manager = new LinearLayoutManager(NotArriveActivity.this);
                    recyclerView.setLayoutManager(manager);
                    NotArriveAdapter adapter = new NotArriveAdapter(NotArriveActivity.this,object);
                    recyclerView.setAdapter(adapter);
                }
            }
            @Override
            public void onError(int code, String msg) {
                // TODO Auto-generated method stub
                Toast.makeText(NotArriveActivity.this,"查询失败："+msg,Toast.LENGTH_SHORT).show();
            }
        });

    }
}
