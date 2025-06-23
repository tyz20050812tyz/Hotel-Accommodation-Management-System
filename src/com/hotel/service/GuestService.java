package com.hotel.service;

import com.hotel.dao.GuestDao;
import com.hotel.entity.Guest;
import java.sql.SQLException;
import com.hotel.entity.CheckIn;
import java.util.List;
import com.hotel.dao.CheckInDao;

/**
 * 客人管理业务逻辑层
 * 编写者：开发小组
 * 完成时间：2024年7月
 * 功能：处理客人信息的核心业务逻辑，包含身份验证、数据校验等
 */
public class GuestService {
    private final GuestDao guestDao = new GuestDao();

    /**
     * 添加新客人（带业务校验）
     * @param guest 待添加的客人对象
     * @return 添加成功返回true，身份证号已存在返回false
     * @throws SQLException 数据库操作异常
     */
    public boolean addGuest(Guest guest) throws SQLException {
        // 校验身份证号是否已存在
        if (guestDao.getGuestByIdCard(guest.getIdCard()) != null) {
            return false;
        }
        guestDao.addGuest(guest);
        return true;
    }

    /**
     * 更新客人信息（带业务校验）
     * @param guest 包含新信息的客人对象（需包含guestId）
     * @return 更新成功返回true，身份证号重复或客人不存在返回false
     * @throws SQLException 数据库操作异常
     */
    public boolean updateGuest(Guest guest) throws SQLException {
        // 检查是否存在其他客人使用相同身份证号
        Guest existing = guestDao.getGuestByIdCard(guest.getIdCard());
        if (existing != null && existing.getGuestId() != guest.getGuestId()) {
            return false;
        }
        // 检查客人是否存在
        if (guestDao.getGuestByIdCard(guest.getIdCard()) == null) {
            return false;
        }
        guestDao.updateGuest(guest);
        return true;
    }

    /**
     * 根据身份证号查询客人信息
     * @param idCard 身份证号
     * @return 客人对象（不存在则返回null）
     * @throws SQLException 数据库操作异常
     */
    public Guest getGuestByIdCard(String idCard) throws SQLException {
        return guestDao.getGuestByIdCard(idCard);
    }

    /**
     * 查询所有客人信息
     * @return 客人对象列表
     * @throws SQLException 数据库操作异常
     */
    public List<Guest> getAllGuests() throws SQLException {
        return guestDao.getAllGuests();
    }

    /**
     * 根据身份证号删除客人（带业务校验）
     * @param idCard 身份证号
     * @return 删除成功返回true，客人不存在或有未结算记录返回false
     * @throws SQLException 数据库操作异常
     */
    public boolean deleteGuest(String idCard) throws SQLException {
        Guest guest = guestDao.getGuestByIdCard(idCard);
        if (guest == null) {
            return false;
        }
        // 检查是否有未结算的入住记录
// 为解决 CheckInDao 无法解析为类型的问题，需导入 CheckInDao 类
CheckInDao checkInDao = new CheckInDao();
// 假设 CheckIn 类存在于 com.hotel.entity 包中，导入该类并修正类型无法解析的问题
List<CheckIn> activeCheckIns = checkInDao.getActiveCheckInsByGuestId(guest.getGuestId());
        if (!activeCheckIns.isEmpty()) {
            return false;
        }
        guestDao.deleteGuest(guest.getGuestId());
        return true;
    }
}