package com.example.administrator.signin.modul;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017-02-27 .
 */

public class StudentCourse extends BmobObject {
    private Integer sId;
    private String name;
    private String cId;

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public Integer getsId() {
        return sId;
    }

    public void setsId(Integer sId) {
        this.sId = sId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
