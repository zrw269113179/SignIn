package com.example.administrator.signin.modul;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017-01-23 .
 */

public class Record extends BmobObject {
    private Integer ID;
    private String name;
    private Long in_time;
    private Long out_time;

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    private String cId;

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getIn_time() {
        return in_time;
    }

    public void setIn_time(Long in_time) {
        this.in_time = in_time;
    }

    public Long getOut_time() {
        return out_time;
    }

    public void setOut_time(Long out_time) {
        this.out_time = out_time;
    }
}
