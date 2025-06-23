# 酒店住宿管理系统运行指南

## 一、环境要求
1. **JDK 1.8+**：用于编译和运行Java程序（[下载地址](https://www.oracle.com/java/technologies/downloads/)）
2. **PostgreSQL 12+**：用于数据库存储（[下载地址](https://www.postgresql.org/download/)）
3. **PostgreSQL JDBC驱动**：版本9.4+（[下载地址](https://jdbc.postgresql.org/download/)，需手动添加到项目或通过Maven/Gradle管理）

## 二、数据库初始化
1. 启动PostgreSQL服务
2. 打开数据库管理工具（如pgAdmin）
3. 执行 `database/init.sql` 脚本（路径：`d:\佟雨泽\大二小学期\new\database\init.sql`）
   - 该脚本会创建`hotel_management`数据库及相关表，并插入初始测试数据

## 三、配置数据库连接
1. 打开 `src/com/hotel/dao/util/JdbcUtil.java` 文件
2. 修改以下连接参数（根据本地PostgreSQL配置调整）：
   ```java
   private static final String URL = "jdbc:postgresql://localhost:5432/hotel_management"; // 数据库URL
   private static final String USER = "postgres"; // 数据库用户名
   private static final String PASSWORD = "your_password"; // 数据库密码
   ```

## 四、编译项目
1. 打开命令提示符（CMD），切换到项目根目录：
   ```cmd
   cd d:\佟雨泽\大二小学期\new
   ```
2. 编译所有Java文件（需确保JDK已配置到环境变量）：
   ```cmd
   javac -d bin src/com/hotel/**/*.java
   ```
   - 若提示找不到类，需将PostgreSQL JDBC驱动加入编译路径：
     ```cmd
     javac -cp postgresql-9.4.1212.jar -d bin src/com/hotel/**/*.java
     ```

## 五、运行程序
1. 执行主程序（需携带JDBC驱动路径）：
   ```cmd
   java -cp bin;postgresql-9.4.1212.jar com.hotel.ui.HotelManagementApp
   ```
2. 根据控制台菜单提示操作（如房间管理、客人登记等）

## 六、常见问题
- **数据库连接失败**：检查`JdbcUtil.java`中的URL、用户名、密码是否正确，PostgreSQL服务是否启动
- **找不到类错误**：确认JDBC驱动JAR包已添加到`-cp`参数中
- **表不存在错误**：确保已完整执行`init.sql`脚本