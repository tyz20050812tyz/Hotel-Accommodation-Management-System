package com.hotel.service;

import com.hotel.dao.PriceAdjustmentDao;
import com.hotel.dao.RoomDao;
import com.hotel.entity.PriceAdjustment;
import com.hotel.entity.Room;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * 价格管理业务逻辑层
 * 编写者：开发小组
 * 完成时间：2024年7月
 * 功能：处理价格调整的核心业务逻辑，包含价格校验、历史记录等
 */
public class PriceAdjustmentService {
    private final PriceAdjustmentDao priceAdjustmentDao = new PriceAdjustmentDao();
    private final RoomDao roomDao = new RoomDao();

    /**
     * 调整房型价格（带业务校验）
     * @param roomType 目标房型
     * @param newPrice 新价格
     * @return 调整成功返回true，房型不存在或价格非法返回false
     * @throws SQLException 数据库操作异常
     */
    public boolean adjustRoomTypePrice(String roomType, double newPrice) throws SQLException {
        // 校验价格合法性（必须大于0）
        if (newPrice <= 0) {
            return false;
        }
        // 校验房型是否存在（通过房间表判断）
        List<Room> rooms = roomDao.getAllRooms();
        boolean typeExists = rooms.stream().anyMatch(r -> roomType.equals(r.getRoomType()));
        if (!typeExists) {
            return false;
        }
        // 获取当前价格
        double originalPrice = rooms.stream()
                .filter(r -> roomType.equals(r.getRoomType()))
                .findFirst()
                .map(Room::getPrice)
                .orElse(0.0);
        // 记录价格调整历史
        PriceAdjustment adjustment = new PriceAdjustment();
        adjustment.setRoomType(roomType);
        adjustment.setOriginalPrice(originalPrice);
        adjustment.setNewPrice(newPrice);
        adjustment.setAdjustmentDate(new Date());
        priceAdjustmentDao.addPriceAdjustment(adjustment);
        // 更新所有该房型房间的价格
        for (Room room : rooms) {
            if (roomType.equals(room.getRoomType())) {
                room.setPrice(newPrice);
                roomDao.updateRoom(room);
            }
        }
        return true;
    }

    /**
     * 查询指定房型的价格调整历史
     * @param roomType 目标房型
     * @return 价格调整记录列表（按时间倒序）
     * @throws SQLException 数据库操作异常
     */
    public List<PriceAdjustment> getPriceAdjustmentHistory(String roomType) throws SQLException {
        return priceAdjustmentDao.getPriceHistoryByRoomType(roomType);
    }
}