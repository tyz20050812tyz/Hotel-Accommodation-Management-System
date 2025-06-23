package com.hotel.dao;

import com.hotel.dao.util.JdbcUtil;
import com.hotel.entity.CheckIn;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 入住记录数据访问层
 * 编写者：开发小组
 * 完成时间：2024年7月
 * 功能：实现入住记录表的增删改查及房费计算操作
 */
public class CheckInDao {

    /**
     * 添加新入住记录
     * @param checkIn 入住记录对象
     * @throws SQLException 数据库操作异常
     */
    public void addCheckIn(CheckIn checkIn) throws SQLException {
        String sql = "INSERT INTO check_ins (guest_id, room_id, check_in_time, check_out_time, total_price) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = JdbcUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, checkIn.getGuestId());
            pstmt.setInt(2, checkIn.getRoomId());
            pstmt.setTimestamp(3, checkIn.getCheckInTime());
            pstmt.setTimestamp(4, checkIn.getCheckOutTime());
            pstmt.setDouble(5, checkIn.getTotalPrice());
            pstmt.executeUpdate();
        }
    }

    /**
     * 根据房间ID查询未退房的记录
     * @param roomId 房间ID
     * @return 未退房的入住记录（无则返回null）
     * @throws SQLException 数据库操作异常
     */
    public CheckIn getUncheckedInByRoomId(int roomId) throws SQLException {
        String sql = "SELECT * FROM check_ins WHERE room_id = ? AND check_out_time IS NULL";
        try (Connection conn = JdbcUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, roomId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    CheckIn checkIn = new CheckIn();
                    checkIn.setCheckInId(rs.getInt("check_in_id"));
                    checkIn.setGuestId(rs.getInt("guest_id"));
                    checkIn.setRoomId(rs.getInt("room_id"));
                    checkIn.setCheckInTime(rs.getTimestamp("check_in_time"));
                    checkIn.setCheckOutTime(rs.getTimestamp("check_out_time"));
                    checkIn.setTotalPrice(rs.getDouble("total_price"));
                    return checkIn;
                }
                return null;
            }
        }
    }

    /**
     * 根据客人ID查询未结算的入住记录
     * @param guestId 客人ID
     * @return 未结算的入住记录列表
     * @throws SQLException 数据库操作异常
     */
    public List<CheckIn> getActiveCheckInsByGuestId(int guestId) throws SQLException {
        List<CheckIn> activeCheckIns = new ArrayList<>();
        String sql = "SELECT * FROM check_ins WHERE guest_id = ? AND check_out_time IS NULL";
        try (Connection conn = JdbcUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, guestId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    CheckIn checkIn = new CheckIn();
                    checkIn.setCheckInId(rs.getInt("check_in_id"));
                    checkIn.setGuestId(rs.getInt("guest_id"));
                    checkIn.setRoomId(rs.getInt("room_id"));
                    checkIn.setCheckInTime(rs.getTimestamp("check_in_time"));
                    checkIn.setCheckOutTime(rs.getTimestamp("check_out_time"));
                    checkIn.setTotalPrice(rs.getDouble("total_price"));
                    activeCheckIns.add(checkIn);
                }
            }
        }
        return activeCheckIns;
    }

    /**
     * 更新退房时间并计算总房费
     * @param checkInId 入住记录ID
     * @param checkOutTime 退房时间
     * @param totalPrice 总房费
     * @throws SQLException 数据库操作异常
     */
    public void updateCheckOut(int checkInId, Timestamp checkOutTime, double totalPrice) throws SQLException {
        String sql = "UPDATE check_ins SET check_out_time = ?, total_price = ? WHERE check_in_id = ?";
        try (Connection conn = JdbcUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setTimestamp(1, checkOutTime);
            pstmt.setDouble(2, totalPrice);
            pstmt.setInt(3, checkInId);
            pstmt.executeUpdate();
        }
    }
}