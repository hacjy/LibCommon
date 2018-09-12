package com.ha.cjy.common.util.bean;

import java.io.Serializable;

/**
 * 第三方登录需要的信息
 * Created by cjy on 2018/8/6.
 */

public class LoginThirdInfo implements Serializable {
    public String openid ;
    public String uid ;
    public String name ;
    public String pic ;
    public String gender;
    public String token ;
    public int sex;

    public LoginThirdInfo(String openid, String uid, String name, String pic, String gender, String token, int sex) {
        this.openid = openid;
        this.uid = uid;
        this.name = name;
        this.pic = pic;
        this.gender = gender;
        this.token = token;
        this.sex = sex;
    }
}
