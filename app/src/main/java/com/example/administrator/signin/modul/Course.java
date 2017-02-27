package com.example.administrator.signin.modul;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017-02-26 .
 */

public class Course extends BmobObject {
    private String cId;
    private String courseName;
    private String teacherName;
    private Integer tId;

    public Integer getId() {
        return tId;
    }

    public void setId(Integer id) {
        tId = id;
    }

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
}
