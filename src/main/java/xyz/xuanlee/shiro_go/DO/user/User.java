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

    public boolean locked() {
        return !this.isLock.equals("0");
    }

    public Department getBelongDepartment() {
        return belongDepartment;
    }

    @Override
    public String toString() {
        return "用户信息：{" +
                "用户名：" + this.getUsername() + "；" +
                "密码：" + this.getPassword() + "；" +
                "部门名称：" + this.getBelongDepartment().getDepartmentName() +
                "}";
    }
}
