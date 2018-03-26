package com.wuyou.merchant.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

/**
 * Created by Administrator on 2018\1\25 0025.
 */
@Entity
public class UserInfo {
    @Id
    private long mid;
    @Property(nameInDb = "USERNAME")
    private String name;
    @Property(nameInDb = "PHONE")
    private String phone;
    @Property(nameInDb = "UID")
    private String uid;
    @Property(nameInDb = "HEAD")
    private String head_image;
    @Property(nameInDb = "TOKEN")
    private String token;
    @Property(nameInDb = "PWD")
    private String password;
    @Property(nameInDb = "RCTOKEN")
    private String rc_token;
    @Property(nameInDb = "SHOPID")
    private String shop_id;
    @Property(nameInDb = "TEL")
    private String tel;
    @Property(nameInDb = "LOGO")
    private String logo;
    @Property(nameInDb = "SHOPNAME")
    private String shop_name;

    @Generated(hash = 1798654350)
    public UserInfo(long mid, String name, String phone, String uid, String head_image, String token,
            String password, String rc_token, String shop_id, String tel, String logo,
            String shop_name) {
        this.mid = mid;
        this.name = name;
        this.phone = phone;
        this.uid = uid;
        this.head_image = head_image;
        this.token = token;
        this.password = password;
        this.rc_token = rc_token;
        this.shop_id = shop_id;
        this.tel = tel;
        this.logo = logo;
        this.shop_name = shop_name;
    }

    @Generated(hash = 1279772520)
    public UserInfo() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUid() {
        return shop_id;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHead_image() {
        return head_image;
    }

    public void setHead_image(String head_image) {
        this.head_image = head_image;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public long getMid() {
        return this.mid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMid(long mid) {

        this.mid = mid;
    }

    public String getRc_token() {
        return this.rc_token;
    }

    public void setRc_token(String rc_token) {
        this.rc_token = rc_token;
    }

    public String getShop_id() {
        return this.shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getTel() {
        return this.tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getLogo() {
        return this.logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getShop_name() {
        return this.shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }
}
