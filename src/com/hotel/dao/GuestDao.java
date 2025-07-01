package com.hotel.dao;

import com.hotel.dao.util.JdbcUtil;
import com.hotel.entity.Guest;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 客人信息数据访问层
 * 编写者：开发小组
 * 完成时间：2024年7月
 * 功能：实现客人信息表的增删改查操作
 */
public class GuestDao {

    /**
     * 添加新客人
     * @param guest 客人对象
     * @throws SQLException 数据库操作异常
     */
    public void addGuest(Guest guest) throws SQLException {
        String sql = "INSERT INTO guests (name, id_card, phone) VALUES (?, ?, ?)";
        try (Connection conn = JdbcUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, guest.getName());
            pstmt.setString(2, guest.getIdCard());
            pstmt.setString(3, guest.getPhone());
            pstmt.executeUpdate();
            /**
     * 根据客人ID查询客人信息
     * @param guestId 客人ID
     * @return 客人对象（不存在则返回null）
     * @throws SQLException 数据库操作异常
     */

        }
    }
    public Guest getGuestById(int guestId) throws SQLException {
        String sql = "SELECT * FROM guests WHERE guest_id = ?";
        try (Connection conn = JdbcUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, guestId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Guest guest = new Guest();
                    guest.setGuestId(rs.getInt("guest_id"));
                    guest.setName(rs.getString("name"));
                    guest.setIdCard(rs.getString("id_card"));
                    guest.setPhone(rs.getString("phone"));
                    return guest;
                }
                return null;
            }
        }
    }




    /**
     * 根据客人ID删除客人
     * @param guestId 客人ID
     * @throws SQLException 数据库操作异常
     */
    public void deleteGuest(int guestId) throws SQLException {
        String sql = "DELETE FROM guests WHERE guest_id = ?";
        try (Connection conn = JdbcUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, guestId);
            pstmt.executeUpdate();
        }
    }

    /**
     * 更新客人信息
     * @param guest 包含新信息的客人对象（需包含guestId）
     * @throws SQLException 数据库操作异常
     */
    public void updateGuest(Guest guest) throws SQLException {
        String sql = "UPDATE guests SET name=?, id_card=?, phone=? WHERE guest_id=?";
        try (Connection conn = JdbcUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, guest.getName());
            pstmt.setString(2, guest.getIdCard());
            pstmt.setString(3, guest.getPhone());
            pstmt.setInt(4, guest.getGuestId());
            pstmt.executeUpdate();
        }
    }

    /**
     * 查询所有客人信息
     * @return 客人对象列表
     * @throws SQLException 数据库操作异常
     */
    public List<Guest> getAllGuests() throws SQLException {
        List<Guest> guestList = new ArrayList<>();
        String sql = "SELECT * FROM guests";
        try (Connection conn = JdbcUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Guest guest = new Guest();
                guest.setGuestId(rs.getInt("guest_id"));
                guest.setName(rs.getString("name"));
                guest.setIdCard(rs.getString("id_card"));
                guest.setPhone(rs.getString("phone"));
                guestList.add(guest);
            }
        }
        return guestList;
    }

    /**
     * 根据身份证号查询客人信息
     * @param idCard 身份证号
     * @return 客人对象（不存在则返回null）
     * @throws SQLException 数据库操作异常
     */
    public Guest getGuestByIdCard(String idCard) throws SQLException {
        String sql = "SELECT * FROM guests WHERE id_card = ?";
        try (Connection conn = JdbcUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, idCard);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Guest guest = new Guest();
                    guest.setGuestId(rs.getInt("guest_id"));
                    guest.setName(rs.getString("name"));
                    guest.setIdCard(rs.getString("id_card"));
                    guest.setPhone(rs.getString("phone"));
                    return guest;
                }
                return null;
            }
        }
    }
}