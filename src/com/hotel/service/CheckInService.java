package com.hotel.service;

import com.hotel.dao.CheckInDao;
import com.hotel.dao.GuestDao;
import com.hotel.dao.RoomDao;
import com.hotel.entity.CheckIn;
import com.hotel.entity.Guest;
import com.hotel.entity.Room;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Duration;

/**
 * 入住及房费结算业务逻辑层
 * 编写者：开发小组
 * 完成时间：2024年7月
 * 功能：处理入住登记、退房结算、房费计算等核心业务逻辑
 */
public class CheckInService {
    private final CheckInDao checkInDao = new CheckInDao();
    private final RoomDao roomDao = new RoomDao();
    private final GuestDao guestDao = new GuestDao();

    /**
     * 处理客人入住登记
     * @param guestId 客人ID
     * @param roomNumber 房间号
     * @return 登记成功返回true，房间不可用或客人不存在返回false
     * @throws SQLException 数据库操作异常
     */
    public boolean checkIn(int guestId, String roomNumber) throws SQLException {
        // 校验客人是否存在
        Guest guest = guestDao.getGuestById(guestId);
        if (guest == null) {
            System.out.println("checkIn guest: " + guest+"不存在");
            return false;
        }

        // 校验房间是否可用
        Room room = roomDao.getRoomByNumber(roomNumber);
        if (room == null || !"空闲".equals(room.getRoomStatus())) {
            System.out.println("checkIn room: " + room+"不可用");
            return false;
        }
        // 更新房间状态为入住
        room.setRoomStatus("入住");
        roomDao.updateRoom(room);
        // 记录入住信息
        CheckIn checkIn = new CheckIn();
        checkIn.setGuestId(guestId);
        checkIn.setRoomId(room.getRoomId());
        checkIn.setCheckInTime(new Timestamp(System.currentTimeMillis()));
        checkInDao.addCheckIn(checkIn);
        return true;
    }

    /**
     * 处理客人退房结算
     * @param roomNumber 房间号
     * @param otherConsumption 其他消费金额
     * @return 结算成功返回总费用，失败返回-1
     * @throws SQLException 数据库操作异常
     */
    public double checkOut(String roomNumber, double otherConsumption) throws SQLException {
        Room room = roomDao.getRoomByNumber(roomNumber);
        if (room == null || !"入住".equals(room.getRoomStatus())) {
            return -1;
        }
        // 查询未退房的入住记录
        CheckIn checkIn = checkInDao.getUncheckedInByRoomId(room.getRoomId());
        if (checkIn == null) {
            return -1;
        }
        // 计算入住天数
        long durationMillis = System.currentTimeMillis() - checkIn.getCheckInTime().getTime();
        long days = Duration.ofMillis(durationMillis).toDays() + 1; // 不足一天按一天计算
        // 计算房费（使用当前房型价格）
        double roomPrice = room.getPrice();
        double total = roomPrice * days + otherConsumption;
        // 更新退房信息
        checkIn.setCheckOutTime(new Timestamp(System.currentTimeMillis()));
        checkIn.setTotalPrice(total);
        checkInDao.updateCheckOut(checkIn.getCheckInId(), checkIn.getCheckOutTime(), total);
        // 更新房间状态为空闲
        room.setRoomStatus("空闲");
        roomDao.updateRoom(room);
        return total;
    }
}