package com.example.administrator.signin.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.administrator.signin.R;
import com.example.administrator.signin.adapter.CourseAdapter;
import com.example.administrator.signin.modul.Course;
import com.example.administrator.signin.modul.StudentCourse;
import com.example.administrator.signin.modul.User;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class SetCourse extends AppCompatActivity {
    private RecyclerView setCourse;
    private CourseAdapter adapter;
    private User user;
    private List<Course> mList;
    private ArrayList<Boolean> flag;
    private List<BmobObject> courseList;
    private List<BmobObject> deleteList;
    private void initCourseData(){
        BmobQuery<Course> query = new BmobQuery<>();
        query.findObjects(SetCourse.this, new FindListener<Course>() {
            @Override
            public void onSuccess(List<Course> list) {
                mList = new ArrayList<Course>();
                flag = new ArrayList<Boolean>();
                for(Course course:list){
                    mList.add(course);
                    flag.add(false);
                }

                findSelectedCourse();

            }
            @Override
            public void onError(int i, String s) {
            }
        });
    }
    private void findSelectedCourse(){

        BmobQuery<StudentCourse> query = new BmobQuery<>();
        query.addWhereEqualTo("sId",Integer.valueOf(user.getUsername()));
        query.findObjects(SetCourse.this, new FindListener<StudentCourse>() {
            @Override
            public void onSuccess(List<StudentCourse> list) {
                for (StudentCourse stu:list) {
                    for (int i = 0; i < mList.size(); i++) {
                        if (mList.get(i).getObjectId().equals(stu.getcId())) {
                            flag.set(i,true);
                        }
                    }
                }
                adapter = new CourseAdapter(mList,flag);
                setCourse.setAdapter(adapter);
            }

            @Override
            public void onError(int i, String s) {

            }
        });

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_course);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        user = BmobUser.getCurrentUser(SetCourse.this,User.class);
        setCourse = (RecyclerView) findViewById(R.id.setCourse);
        LinearLayoutManager manager = new LinearLayoutManager(SetCourse.this);
        setCourse.setLayoutManager(manager);
        initCourseData();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.course_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.addCourse) {
            deleteList = new ArrayList<>();
            BmobQuery<StudentCourse> query = new BmobQuery<>();
            query.addWhereEqualTo("sId",Integer.valueOf(user.getUsername()));
            query.findObjects(SetCourse.this, new FindListener<StudentCourse>() {
                @Override
                public void onSuccess(List<StudentCourse> list) {
                    for (StudentCourse stu:list) {
                        deleteList.add(stu);
                    }
                    new BmobObject().deleteBatch(SetCourse.this,deleteList, new DeleteListener() {
                        @Override
                        public void onSuccess() {
                            addCourseToDB();
                        }
                        @Override
                        public void onFailure(int i, String s) {
                            Toast.makeText(SetCourse.this, i+s, Toast.LENGTH_SHORT).show();
                                //addCourseToDB();
                        }
                    });
                }

                @Override
                public void onError(int i, String s) {
                }
            });
        }
        return true;
    }
    private void addCourseToDB(){
        ArrayList<Boolean> flags = adapter.getFlag();
        courseList = new ArrayList<>();
        for (int i = 0; i < flags.size(); i++) {
            if (flags.get(i)){
                StudentCourse stuCourse = new StudentCourse();
                Course course = mList.get(i);
                stuCourse.setcId(course.getObjectId());
                stuCourse.setName(user.getName());
                stuCourse.setsId(Integer.valueOf(user.getUsername()));
                courseList.add(stuCourse);
            }
        }
        new BmobObject().insertBatch(SetCourse.this, courseList, new SaveListener() {
            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
                Toast.makeText(SetCourse.this, "修改成功", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(int code, String msg) {
                // TODO Auto-generated method stub
                Toast.makeText(SetCourse.this, "修改失败" + msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
