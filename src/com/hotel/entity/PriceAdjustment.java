package com.hotel.entity;

import java.util.Date;

/**
 * 价格调整记录实体类
 * 编写者：开发小组
 * 完成时间：2024年7月
 * 功能：对应数据库price_adjustments表的实体映射
 */
public class PriceAdjustment {
    private int adjustmentId;
    private String roomType;
    private double originalPrice;
    private double newPrice;
    private Date adjustmentDate;

    // 无参构造方法
    public PriceAdjustment() {}

    // 全参构造方法
    public PriceAdjustment(int adjustmentId, String roomType, double originalPrice, double newPrice, Date adjustmentDate) {
        this.adjustmentId = adjustmentId;
        this.roomType = roomType;
        this.originalPrice = originalPrice;
        this.newPrice = newPrice;
        this.adjustmentDate = adjustmentDate;
    }

    // getter和setter方法
    public int getAdjustmentId() {
        return adjustmentId;
    }

    public void setAdjustmentId(int adjustmentId) {
        this.adjustmentId = adjustmentId;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public double getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(double newPrice) {
        this.newPrice = newPrice;
    }

    public Date getAdjustmentDate() {
        return adjustmentDate;
    }

    public void setAdjustmentDate(Date adjustmentDate) {
        this.adjustmentDate = adjustmentDate;
    }

    @Override
    public String toString() {
        return "PriceAdjustment{"
                + "adjustmentId=" + adjustmentId
                + ", roomType='" + roomType + '\''
                + ", originalPrice=" + originalPrice
                + ", newPrice=" + newPrice
                + ", adjustmentDate=" + adjustmentDate
                + '}';
    }
}