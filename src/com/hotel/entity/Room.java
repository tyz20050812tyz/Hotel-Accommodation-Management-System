package com.hotel.entity;

/**
 * 房间信息实体类
 * 编写者：开发小组
 * 完成时间：2024年7月
 * 功能：对应数据库rooms表的实体映射
 */
public class Room {
    private int roomId;
    private String roomNumber;
    private String roomType;
    private String roomStatus;
    private double price;

    // 无参构造方法
    public Room() {}

    // 全参构造方法
    public Room(int roomId, String roomNumber, String roomType, String roomStatus, double price) {
        this.roomId = roomId;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.roomStatus = roomStatus;
        this.price = price;
    }

    // getter和setter方法
    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(String roomStatus) {
        this.roomStatus = roomStatus;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Room{"
                + "roomId=" + roomId
                + ", roomNumber='" + roomNumber + '\''
                + ", roomType='" + roomType + '\''
                + ", roomStatus='" + roomStatus + '\''
                + ", price=" + price
                + '}';
    }
}