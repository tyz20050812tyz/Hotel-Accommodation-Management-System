package com.hotel.dao;

import com.hotel.dao.util.JdbcUtil;
import com.hotel.entity.Room;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 房间信息数据访问层
 * 编写者：开发小组
 * 完成时间：2024年7月
 * 功能：实现房间信息表的增删改查操作
 */
public class RoomDao {

    /**
     * 添加新房间
     * @param room 房间对象
     * @throws SQLException 数据库操作异常
     */
    public void addRoom(Room room) throws SQLException {
        String sql = "INSERT INTO rooms (room_number, room_type, room_status, price) VALUES (?, ?, ?, ?)";
        try (Connection conn = JdbcUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, room.getRoomNumber());
            pstmt.setString(2, room.getRoomType());
            pstmt.setString(3, room.getRoomStatus());
            pstmt.setDouble(4, room.getPrice());
            pstmt.executeUpdate();
        }
    }

    /**
     * 根据房间ID删除房间
     * @param roomId 房间ID
     * @throws SQLException 数据库操作异常
     */
    public void deleteRoom(int roomId) throws SQLException {
        String sql = "DELETE FROM rooms WHERE room_id = ?";
        try (Connection conn = JdbcUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, roomId);
            pstmt.executeUpdate();
        }
    }

    /**
     * 更新房间信息
     * @param room 包含新信息的房间对象（需包含roomId）
     * @throws SQLException 数据库操作异常
     */
    public void updateRoom(Room room) throws SQLException {
        String sql = "UPDATE rooms SET room_number=?, room_type=?, room_status=?, price=? WHERE room_id=?";
        try (Connection conn = JdbcUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, room.getRoomNumber());
            pstmt.setString(2, room.getRoomType());
            pstmt.setString(3, room.getRoomStatus());
            pstmt.setDouble(4, room.getPrice());
            pstmt.setInt(5, room.getRoomId());
            pstmt.executeUpdate();
        }
    }

    /**
     * 查询所有房间信息
     * @return 房间对象列表
     * @throws SQLException 数据库操作异常
     */
    public List<Room> getAllRooms() throws SQLException {
        List<Room> roomList = new ArrayList<>();
        String sql = "SELECT * FROM rooms";
        try (Connection conn = JdbcUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Room room = new Room();
                room.setRoomId(rs.getInt("room_id"));
                room.setRoomNumber(rs.getString("room_number"));
                room.setRoomType(rs.getString("room_type"));
                room.setRoomStatus(rs.getString("room_status"));
                room.setPrice(rs.getDouble("price"));
                roomList.add(room);
            }
        }
        return roomList;
    }

    /**
     * 根据房间号查询房间信息
     * @param roomNumber 房间号
     * @return 房间对象（不存在则返回null）
     * @throws SQLException 数据库操作异常
     */
    public Room getRoomByNumber(String roomNumber) throws SQLException {
        String sql = "SELECT * FROM rooms WHERE room_number = ?";
        try (Connection conn = JdbcUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, roomNumber);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Room room = new Room();
                    room.setRoomId(rs.getInt("room_id"));
                    room.setRoomNumber(rs.getString("room_number"));
                    room.setRoomType(rs.getString("room_type"));
                    room.setRoomStatus(rs.getString("room_status"));
                    room.setPrice(rs.getDouble("price"));
                    return room;
                }
                return null;
            }
        }
    }
}