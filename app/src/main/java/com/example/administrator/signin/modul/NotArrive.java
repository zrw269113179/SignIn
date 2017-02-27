package com.example.administrator.signin.modul;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017-01-23 .
 */

public class NotArrive extends BmobObject {
    private Integer Id;
    private String name;
    private String Title;
    private String cId;

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public Long getTime() {
        return Time;
    }

    public void setTime(Long time) {
        Time = time;
    }

    private Long Time;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
