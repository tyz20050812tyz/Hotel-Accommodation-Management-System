-- 酒店住宿管理系统数据库初始化脚本
-- 编写者：开发小组
-- 完成时间：2024年7月
-- 功能：创建数据库及表结构

-- 创建数据库（如果不存在）
CREATE DATABASE IF NOT EXISTS hotel_management;

-- 切换到目标数据库
\c hotel_management;

-- 创建房间信息表
CREATE TABLE IF NOT EXISTS rooms (
    room_id SERIAL PRIMARY KEY,
    room_number VARCHAR(10) UNIQUE NOT NULL,
    room_type VARCHAR(20) NOT NULL,
    room_status VARCHAR(10) NOT NULL CHECK (room_status IN ('空闲', '入住', '维修')),
    price NUMERIC(10,2) NOT NULL
);

-- 创建客人信息表
CREATE TABLE IF NOT EXISTS guests (
    guest_id SERIAL PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    id_card VARCHAR(18) UNIQUE NOT NULL,
    phone VARCHAR(15) NOT NULL
);

-- 创建入住记录表
CREATE TABLE IF NOT EXISTS check_ins (
    check_in_id SERIAL PRIMARY KEY,
    guest_id INT NOT NULL REFERENCES guests(guest_id),
    room_id INT NOT NULL REFERENCES rooms(room_id),
    check_in_time TIMESTAMP NOT NULL,
    check_out_time TIMESTAMP,
    total_price NUMERIC(10,2)
);

-- 创建价格调整记录表
CREATE TABLE IF NOT EXISTS price_adjustments (
    adjustment_id SERIAL PRIMARY KEY,
    room_type VARCHAR(20) NOT NULL,
    original_price NUMERIC(10,2) NOT NULL,
    new_price NUMERIC(10,2) NOT NULL,
    adjustment_date DATE NOT NULL
);

-- 插入初始房间数据示例
INSERT INTO rooms (room_number, room_type, room_status, price) VALUES
('101', '单人间', '空闲', 299.00),
('201', '双人间', '空闲', 499.00),
('301', '套房', '维修', 899.00);

-- 插入初始价格调整记录示例
INSERT INTO price_adjustments (room_type, original_price, new_price, adjustment_date) VALUES
('单人间', 280.00, 299.00, '2024-06-01'),
('双人间', 480.00, 499.00, '2024-06-01');