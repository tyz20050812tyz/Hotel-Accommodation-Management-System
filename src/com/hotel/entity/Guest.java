package com.hotel.entity;

/**
 * 客人信息实体类
 * 编写者：开发小组
 * 完成时间：2024年7月
 * 功能：对应数据库guests表的实体映射
 */
public class Guest {
    private int guestId;
    private String name;
    private String idCard;
    private String phone;

    // 无参构造方法
    public Guest() {}

    // 全参构造方法
    public Guest(int guestId, String name, String idCard, String phone) {
        this.guestId = guestId;
        this.name = name;
        this.idCard = idCard;
        this.phone = phone;
    }

    // getter和setter方法
    public int getGuestId() {
        return guestId;
    }

    public void setGuestId(int guestId) {
        this.guestId = guestId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Guest{"
                + "guestId=" + guestId
                + ", name='" + name + '\''
                + ", idCard='" + idCard + '\''
                + ", phone='" + phone + '\''
                + '}';
    }
}