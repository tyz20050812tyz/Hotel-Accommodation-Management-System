package com.hotel.ui;

import com.hotel.entity.Room;
import com.hotel.service.RoomService;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import com.hotel.entity.Guest;
import com.hotel.service.PriceAdjustmentService;
import com.hotel.entity.PriceAdjustment;
import com.hotel.service.GuestService;
import com.hotel.entity.CheckIn;
import com.hotel.service.CheckInService;

/**
 * 酒店住宿管理系统控制台主程序
 * 编写者：开发小组
 * 完成时间：2024年7月
 * 功能：实现用户与系统的交互，提供菜单导航和输入输出
 */
public class HotelManagementApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final RoomService roomService = new RoomService();

    public static void main(String[] args) {
        System.out.println("\n===== 酒店住宿管理系统 =====");
        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = getIntInput("请输入选项（1-5）：");
            switch (choice) {
                case 1:
                    manageRooms();
                    break;
                case 2:
                    managePrices();
                    break;
                case 3:
                    manageGuests();
                    break;
                case 4:
                    manageCheckIns();
                    break;
                case 5:
                    running = false;
                    System.out.println("感谢使用，系统已退出！");
                    break;
                default:
                    System.out.println("输入错误，请重新输入！");
            }
        }
        scanner.close();
    }

    /**
     * 打印主菜单
     */
    private static void printMainMenu() {
        System.out.println("\n主菜单：");
        System.out.println("1. 房间管理");
        System.out.println("2. 价格管理");
        System.out.println("3. 客人管理");
        System.out.println("4. 房费结算");
        System.out.println("5. 退出系统");
    }

    /**
     * 房间管理子菜单
     */
    private static void manageRooms() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- 房间管理 ---\n1. 添加房间\n2. 删除房间\n3. 修改房间信息\n4. 查询所有房间\n5. 查询可用房间\n6. 返回主菜单");
            int choice = getIntInput("请输入选项（1-6）：");
            try {
                switch (choice) {
                    case 1:
                        addRoomMenu();
                        break;
                    case 2:
                        deleteRoomMenu();
                        break;
                    case 3:
                        updateRoomMenu();
                        break;
                    case 4:
                        listAllRooms();
                        break;
                    case 5:
                        listAvailableRooms();
                        break;
                    case 6:
                        back = true;
                        break;
                    default:
                        System.out.println("输入错误，请重新输入！");
                }
            } catch (SQLException e) {
                System.out.println("操作失败，数据库异常：" + e.getMessage());
            }
        }
    }

    /**
     * 价格管理子菜单
     */
    private static void managePrices() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- 价格管理 ---\n1. 调整房型价格\n2. 查询价格调整历史\n3. 返回主菜单");
            int choice = getIntInput("请输入选项（1-3）：");
            try {
                switch (choice) {
                    case 1:
                        adjustPriceMenu();
                        break;
                    case 2:
                        listPriceHistoryMenu();
                        break;
                    case 3:
                        back = true;
                        break;
                    default:
                        System.out.println("输入错误，请重新输入！");
                }
            } catch (SQLException e) {
                System.out.println("操作失败，数据库异常：" + e.getMessage());
            }
        }
    }

    /**
     * 客人管理子菜单
     */
    private static void manageGuests() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- 客人管理 ---\n1. 新增客人\n2. 修改客人信息\n3. 删除客人\n4. 查询所有客人\n5. 返回主菜单");
            int choice = getIntInput("请输入选项（1-5）：");
            try {
                switch (choice) {
                    case 1:
                        addGuestMenu();
                        break;
                    case 2:
                        updateGuestMenu();
                        break;
                    case 3:
                        deleteGuestMenu();
                        break;
                    case 4:
                        listAllGuests();
                        break;
                    case 5:
                        back = true;
                        break;
                    default:
                        System.out.println("输入错误，请重新输入！");
                }
            } catch (SQLException e) {
                System.out.println("操作失败，数据库异常：" + e.getMessage());
            }
        }
    }

    /**
     * 房费结算子菜单
     */
    private static void manageCheckIns() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- 房费结算 ---\n1. 办理入住登记\n2. 办理退房结算\n3. 返回主菜单");
            int choice = getIntInput("请输入选项（1-3）：");
            try {
                switch (choice) {
                    case 1:
                        checkInMenu();
                        break;
                    case 2:
                        checkOutMenu();
                        break;
                    case 3:
                        back = true;
                        break;
                    default:
                        System.out.println("输入错误，请重新输入！");
                }
            } catch (SQLException e) {
                System.out.println("操作失败，数据库异常：" + e.getMessage());
            }
        }
    }

    /**
     * 添加房间的交互流程
     */
    private static void addRoomMenu() throws SQLException {
        System.out.println("\n--- 添加房间 ---");
        String roomNumber = getStringInput("请输入房间号（1-10位）：");
        // 允许输入拼音
        String roomType = getStringInput("请输入房间类型（单人间/双人间/套房等）：");
        System.out.println("你输入的房间号：" + roomNumber + "，房间类型：" + roomType);
        String roomStatus = getStringInput("请输入房间状态（空闲/入住/维修）：");
        double price = getDoubleInput("请输入房间价格（保留2位小数）：");


        Room room = new Room();
        room.setRoomNumber(roomNumber);
        room.setRoomType(roomType);
        room.setRoomStatus(roomStatus);
        room.setPrice(price);

        boolean success = roomService.addRoom(room);
        System.out.println(success ? "添加成功！" : "添加失败，房间号已存在！");
    }

    /**
     * 更新房间信息的交互流程
     */
    private static void updateRoomMenu() throws SQLException {
        System.out.println("\n--- 更新房间信息 ---");
        String roomNumber = getStringInput("请输入需要更新的房间号：");
        Room room = roomService.getRoomByNumber(roomNumber);
        if (room == null) {
            System.out.println("房间不存在！");
            return;
        }
        // 允许输入拼音
        String newRoomType = getStringInput("请输入新房型（当前：" + room.getRoomType() + "，可用拼音输入）：");
        String newStatus = getStringInput("请输入新状态（空闲/入住/维修，当前：" + room.getRoomStatus() + "）：");
        double newPrice = getDoubleInput("请输入新价格（保留2位小数，当前：" + room.getPrice() + "）：");

        room.setRoomType(newRoomType);
        room.setRoomStatus(newStatus);
        room.setPrice(newPrice);

        boolean success = roomService.updateRoom(room);
        System.out.println(success ? "房间信息更新成功！" : "房间信息更新失败（状态非法）！");
    }

    /**
     * 调整房型价格的交互流程
     */
    private static void adjustPriceMenu() throws SQLException {
        System.out.println("\n--- 调整房型价格 ---");
        // 允许输入拼音
        String roomType = getStringInput("请输入目标房型（如单人间）：");
        double newPrice = getDoubleInput("请输入新价格（保留2位小数）：");

// 假设 PriceAdjustmentService 位于 com.hotel.service 包下，添加导入语句
PriceAdjustmentService priceService = new PriceAdjustmentService();
        boolean success = priceService.adjustRoomTypePrice(roomType, newPrice);
        System.out.println(success ? "价格调整成功！" : "价格调整失败（房型不存在或价格非法）！");
    }

    /**
     * 查询价格调整历史的交互流程
     */
    private static void listPriceHistoryMenu() throws SQLException {
        System.out.println("\n--- 价格调整历史 ---");
        String roomType = getStringInput("请输入目标房型（如单人间）：");

        PriceAdjustmentService priceService = new PriceAdjustmentService();
// 假设 PriceAdjustment 类在 com.hotel.entity 包下，添加泛型类型导入
List<PriceAdjustment> history = priceService.getPriceAdjustmentHistory(roomType);
        if (history.isEmpty()) {
            System.out.println("该房型无价格调整记录！");
            return;
        }
        history.forEach(System.out::println);
    }

    /**
     * 新增客人的交互流程
     */
    private static void addGuestMenu() throws SQLException {
        System.out.println("\n--- 新增客人 ---");
        String name = getStringInput("请输入客人姓名：");
        String idCard = getStringInput("请输入身份证号（18位）：");
        String phone = getStringInput("请输入联系方式：");

// 由于 Guest 类型无法解析，需要添加对应的导入语句
Guest guest = new Guest();
        guest.setName(name);
        guest.setIdCard(idCard);
        guest.setPhone(phone);

        GuestService guestService = new GuestService();
        boolean success = guestService.addGuest(guest);
        System.out.println(success ? "客人添加成功！" : "客人添加失败（身份证号已存在）！");
    }

    /**
     * 修改客人信息的交互流程
     */
    private static void updateGuestMenu() throws SQLException {
        System.out.println("\n--- 修改客人信息 ---");
        String idCard = getStringInput("请输入需要修改的客人身份证号：");
        String newName = getStringInput("请输入新姓名：");
        String newPhone = getStringInput("请输入新联系方式：");

        GuestService guestService = new GuestService();
        Guest guest = guestService.getGuestByIdCard(idCard);
        if (guest == null) {
            System.out.println("客人不存在！");
            return;
        }
        guest.setName(newName);
        guest.setPhone(newPhone);

        boolean success = guestService.updateGuest(guest);
        System.out.println(success ? "信息修改成功！" : "信息修改失败（身份证号重复）！");
    }

    /**
     * 删除客人的交互流程
     */
    private static void deleteGuestMenu() throws SQLException {
        System.out.println("\n--- 删除客人 ---");
        String idCard = getStringInput("请输入需要删除的客人身份证号：");

        GuestService guestService = new GuestService();
        boolean success = guestService.deleteGuest(idCard);
        System.out.println(success ? "客人删除成功！" : "客人删除失败（不存在或有未结算记录）！");
    }

    /**
     * 查询所有客人的交互流程
     */
    private static void listAllGuests() throws SQLException {
        System.out.println("\n--- 所有客人列表 ---");
        GuestService guestService = new GuestService();
        List<Guest> guests = guestService.getAllGuests();
        if (guests.isEmpty()) {
            System.out.println("暂无客人信息！");
            return;
        }
        guests.forEach(System.out::println);
    }

    /**
     * 办理入住登记的交互流程
     */
    private static void checkInMenu() throws SQLException {
        System.out.println("\n--- 办理入住登记 ---");
        int guestId = getIntInput("请输入客人ID：");
        String roomNumber = getStringInput("请输入房间号：");

        CheckInService checkInService = new CheckInService();
        boolean success = checkInService.checkIn(guestId, roomNumber);
        System.out.println(success ? "入住登记成功！" : "入住登记失败（房间不可用或客人不存在）！");
    }

    /**
     * 办理退房结算的交互流程
     */
    private static void checkOutMenu() throws SQLException {
        System.out.println("\n--- 办理退房结算 ---");
        String roomNumber = getStringInput("请输入房间号：");
        double otherConsumption = getDoubleInput("请输入其他消费金额（保留2位小数）：");

        CheckInService checkInService = new CheckInService();
        double total = checkInService.checkOut(roomNumber, otherConsumption);
        if (total == -1) {
            System.out.println("退房结算失败（房间状态异常或无未结算记录）！");
        } else {
            System.out.printf("退房结算成功！总费用：%.2f元\n", total);
        }
    }

    /**
     * 删除房间的交互流程
     */
    private static void deleteRoomMenu() throws SQLException {
        System.out.println("\n--- 删除房间 ---");
        String roomNumber = getStringInput("请输入要删除的房间号：");
        boolean success = roomService.deleteRoom(roomNumber);
        System.out.println(success ? "删除成功！" : "删除失败，房间不存在或非空闲状态！");
    }

    /**
     * 查询所有房间的交互流程
     */
    private static void listAllRooms() throws SQLException {
        System.out.println("\n--- 所有房间列表 ---");
        List<Room> rooms = roomService.getAllRooms();
        if (rooms.isEmpty()) {
            System.out.println("暂无房间信息！");
            return;
        }
        rooms.forEach(System.out::println);
    }

    /**
     * 查询可用房间的交互流程
     */
    private static void listAvailableRooms() throws SQLException {
        System.out.println("\n--- 可用房间列表 ---");
        List<Room> rooms = roomService.getAvailableRooms();
        if (rooms.isEmpty()) {
            System.out.println("暂无可用房间！");
            return;
        }
        rooms.forEach(System.out::println);
    }

    // 工具方法：获取整数输入
    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("输入格式错误，请输入整数！");
            }
        }
    }

    // 工具方法：获取字符串输入
    private static String getStringInput(String prompt) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    // 工具方法：获取双精度输入
    private static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("输入格式错误，请输入数字！");
            }
        }
    }
}