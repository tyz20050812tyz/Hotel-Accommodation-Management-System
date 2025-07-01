package com.hotel.entity;

import java.sql.Timestamp;

/**
 * 入住记录实体类
 * 编写者：开发小组
 * 完成时间：2024年7月
 * 功能：对应数据库check_ins表的实体映射
 */
public class CheckIn {
    private int checkInId;
    private int guestId;
    private int roomId;
    private Timestamp checkInTime;
    private Timestamp checkOutTime;
    private double totalPrice;

    // 无参构造方法
    public CheckIn() {}

    // 全参构造方法
    public CheckIn(int checkInId, int guestId, int roomId, Timestamp checkInTime, Timestamp checkOutTime, double totalPrice) {
        this.checkInId = checkInId;
        this.guestId = guestId;
        this.roomId = roomId;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.totalPrice = totalPrice;
    }

    // getter和setter方法
    public int getCheckInId() {
        return checkInId;
    }

    public void setCheckInId(int checkInId) {
        this.checkInId = checkInId;
    }

    public int getGuestId() {
        return guestId;
    }

    public void setGuestId(int guestId) {
        this.guestId = guestId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public Timestamp getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(Timestamp checkInTime) {
        this.checkInTime = checkInTime;
    }

    public Timestamp getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(Timestamp checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "CheckIn{"
                + "checkInId=" + checkInId
                + ", guestId=" + guestId
                + ", roomId=" + roomId
                + ", checkInTime=" + checkInTime
                + ", checkOutTime=" + checkOutTime
                + ", totalPrice=" + totalPrice
                + '}';
    }
}