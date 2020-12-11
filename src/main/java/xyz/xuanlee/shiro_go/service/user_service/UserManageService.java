package xyz.xuanlee.shiro_go.service.user_service;

import xyz.xuanlee.shiro_go.DO.OpResponseInfo;

public interface UserManageService {

    /**
     * 用户注册功能
     * @param username 用户名
     * @param password 密码
     * @return OpResponseInfo 注册是否成功
     */
    OpResponseInfo signup(String username, String password);

    /**
     * 获取所有用户信息
     * 登录后操作
     * @return OpResponseInfo 用户数据信息的JSON格式或错误信息
     */
    OpResponseInfo retrieveAllUserInfo();

    /**
     * 根据用户最近一次登录时间来获取用户信息
     * 登录后操作
     * @param startTime 开始时间点
     * @param endTime 结束时间点
     * @return OpResponseInfo 用户数据信息的JSON格式或错误信息
     */
    OpResponseInfo retrieveUserInfoByLoginTime(String startTime, String endTime);

    /**
     * 根据用户的id来修改用户名
     * 登录后操作
     * @param id 用户id
     * @param newUsername 要修改的用户名
     * @return OpResponseInfo 修改操作是否成功
     */
    OpResponseInfo modifyUsernameById(Long id, String newUsername);

    /**
     * 根据用户的id来修改用户部门id
     * 登录后操作
     * @param id 用户id
     * @param departmentId 部门id
     * @return OpResponseInfo 修改操作是否成功
     */
    OpResponseInfo modifyUserDepartmentById(Long id, Long departmentId);

    /**
     * 根据用户id删除对应的用户记录
     * 登录后操作
     * @param id 用户id
     * @return OpResponseInfo 删除操作是否成功
     */
    OpResponseInfo deleteUserById(Long id);
}
