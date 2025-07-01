package com.hotel.dao;

import com.hotel.dao.util.JdbcUtil;
import com.hotel.entity.PriceAdjustment;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 价格调整记录数据访问层
 * 编写者：开发小组
 * 完成时间：2024年7月
 * 功能：实现价格调整记录表的增删改查操作
 */
public class PriceAdjustmentDao {

    /**
     * 添加价格调整记录
     * @param adjustment 价格调整对象
     * @throws SQLException 数据库操作异常
     */
    public void addPriceAdjustment(PriceAdjustment adjustment) throws SQLException {
        String sql = "INSERT INTO price_adjustments (room_type, original_price, new_price, adjustment_date) VALUES (?, ?, ?, ?)";
        try (Connection conn = JdbcUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, adjustment.getRoomType());
            pstmt.setDouble(2, adjustment.getOriginalPrice());
            pstmt.setDouble(3, adjustment.getNewPrice());
            pstmt.setDate(4, new Date(adjustment.getAdjustmentDate().getTime()));
            pstmt.executeUpdate();
        }
    }

    /**
     * 根据房型查询价格调整历史
     * @param roomType 房型名称
     * @return 价格调整记录列表
     * @throws SQLException 数据库操作异常
     */
    public List<PriceAdjustment> getPriceHistoryByRoomType(String roomType) throws SQLException {
        List<PriceAdjustment> history = new ArrayList<>();
        String sql = "SELECT * FROM price_adjustments WHERE room_type = ? ORDER BY adjustment_date DESC";
        try (Connection conn = JdbcUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, roomType);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    PriceAdjustment adjustment = new PriceAdjustment();
                    adjustment.setAdjustmentId(rs.getInt("adjustment_id"));
                    adjustment.setRoomType(rs.getString("room_type"));
                    adjustment.setOriginalPrice(rs.getDouble("original_price"));
                    adjustment.setNewPrice(rs.getDouble("new_price"));
                    adjustment.setAdjustmentDate(rs.getDate("adjustment_date"));
                    history.add(adjustment);
                }
            }
        }
        return history;
    }
}