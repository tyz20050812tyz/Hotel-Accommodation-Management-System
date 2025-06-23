package com.hotel.service;

import com.hotel.dao.RoomDao;
import com.hotel.entity.Room;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

/**
 * 房间管理业务逻辑层
 * 编写者：开发小组
 * 完成时间：2024年7月
 * 功能：处理房间管理的核心业务逻辑，包含状态验证、数据校验等
 */
public class RoomService {
    private final RoomDao roomDao = new RoomDao();

    /**
     * 根据房间号查询房间信息
     * @param roomNumber 房间号
     * @return 房间对象（不存在则返回null）
     * @throws SQLException 数据库操作异常
     */
    public Room getRoomByNumber(String roomNumber) throws SQLException {
        return roomDao.getRoomByNumber(roomNumber);
    }

    /**
     * 更新房间信息（带业务校验）
     * @param room 包含新信息的房间对象
     * @return 更新成功返回true，状态非法返回false
     * @throws SQLException 数据库操作异常
     */
    public boolean updateRoom(Room room) throws SQLException {
        // 校验状态合法性
        if (!room.getRoomStatus().matches("^(空闲|入住|维修)$")) {
            return false;
        }
        roomDao.updateRoom(room);
        return true;
    }

    /**
     * 查询所有房间信息
     * @return 房间对象列表
     * @throws SQLException 数据库操作异常
     */
    public List<Room> getAllRooms() throws SQLException {
        return roomDao.getAllRooms();
    }

    /**
     * 添加新房间（带业务校验）
     * @param room 待添加的房间对象
     * @return 添加成功返回true，房间号已存在返回false
     * @throws SQLException 数据库操作异常
     */
    public boolean addRoom(Room room) throws SQLException {
        // 校验房间号是否已存在
        if (roomDao.getRoomByNumber(room.getRoomNumber()) != null) {
            return false;
        }
        roomDao.addRoom(room);
        return true;
    }

    /**
     * 更新房间状态（带业务校验）
     * @param roomId 房间ID
     * @param newStatus 新状态（必须是'空闲'/'入住'/'维修'）
     * @return 更新成功返回true，状态非法返回false
     * @throws SQLException 数据库操作异常
     */
    public boolean updateRoomStatus(int roomId, String newStatus) throws SQLException {
        // 校验状态合法性
        if (!newStatus.matches("^(空闲|入住|维修)$")) {
            return false;
        }
        Room room = roomDao.getRoomByNumber(roomId + ""); // 实际应通过ID查询，此处示例简化
        if (room == null) {
            return false;
        }
        room.setRoomStatus(newStatus);
        roomDao.updateRoom(room);
        return true;
    }

    /**
     * 查询所有可用房间（空闲状态）
     * @return 可用房间列表
     * @throws SQLException 数据库操作异常
     */
    public List<Room> getAvailableRooms() throws SQLException {
        List<Room> allRooms = roomDao.getAllRooms();
// 导入 ArrayList 类
List<Room> availableRooms = new ArrayList<>();
        for (Room room : allRooms) {
            if ("空闲".equals(room.getRoomStatus())) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    /**
     * 根据房间号删除房间（带业务校验）
     * @param roomNumber 房间号
     * @return 删除成功返回true，房间不存在或状态非空闲返回false
     * @throws SQLException 数据库操作异常
     */
    public boolean deleteRoom(String roomNumber) throws SQLException {
        Room room = roomDao.getRoomByNumber(roomNumber);
        if (room == null) {
            return false;
        }
        // 非空闲状态房间不可删除
        if (!"空闲".equals(room.getRoomStatus())) {
            return false;
        }
        roomDao.deleteRoom(room.getRoomId());
        return true;
    }
}