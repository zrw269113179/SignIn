package com.example.administrator.signin.modul;
import cn.bmob.v3.BmobUser;

/**
 * Created by Administrator on 2017-01-23 .
 */

public class User extends BmobUser {
    private String name;
    private Integer role;

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
