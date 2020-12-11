package xyz.xuanlee.shiro_go.DO.user;

import xyz.xuanlee.shiro_go.DO.department.Department;

import java.time.LocalDateTime;

/**
 * @author mouseLee
 * @Description something
 * @date 11/27/2020
 */
public class User extends AbstractUser {

    private String isLock;
    private Department belongDepartment;

    public User(String username, String password, LocalDateTime lastLoginDatetime, String isLock) {
        super(username, password, lastLoginDatetime);
        this.isLock = isLock;
    }

    public User(String username, String password, String departmentName) {
        super(username, password);
        this.belongDepartment = new Department();
        this.belongDepartment.setDepartmentName(departmentName);
    }

    public User(String username, String password) {
        super(username, password);
    }

    //    public User(String username, String password)


//    public User(long id, String username, String password, String lastLoginDatetime, String isLock) {
//        super(username, password, lastLoginDatetime);
//        this.id = id;
//        this.isLock = isLock;
//    }
//
//    public User(String username, String password, String lastLoginDatetime, String isLock) {
//        super(username, password, lastLoginDatetime);
//        this.isLock = isLock;
//    }
//
//    public User(String username, String password, String lastLoginDatetime) {
//        super(username, password, lastLoginDatetime);
//        this.isLock = "0";
//    }
//
//    public User(String username, String password) {
//        this.username = username;
//        this.password = password;
//    }
//
//    public User(String username) {
//        this.username = username;
//    }
//
//    public User(long id) {
//        this.id = id;
//    }
//
    public boolean locked() {
        return !this.isLock.equals("0");
    }

    public Department getBelongDepartment() {
        return belongDepartment;
    }


//    @Override
//    public OpResponseInfo remove() {
//        if (JDBCUtil.executeModifyStatement(UserSQL.DELTE_USER, String.valueOf(this.id))) {
//            return new OpResponseInfo("1", "删除用户记录成功");
//        } else {
//            return new OpResponseInfo("100", "删除用户记录失败，请重试");
//        }
//    }
//
//    @Override
//    public OpResponseInfo updateInfo(String newName) {
//        if (JDBCUtil.executeModifyStatement(UserSQL.UPDATE_USER_USERNAME, newName, String.valueOf(this.id))) {
//            return new OpResponseInfo("1", "更新用户名成功");
//        } else {
//            return new OpResponseInfo("100", "更新用户名失败，请重试");
//        }
//    }

    @Override
    public String toString() {
        return "用户信息：{" +
                "用户名：" + this.getUsername() + "；" +
                "密码：" + this.getPassword() + "；" +
                "部门名称：" + this.getBelongDepartment().getDepartmentName() +
                "}";
    }
}
