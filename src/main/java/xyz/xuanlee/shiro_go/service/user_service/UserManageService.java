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
     * 根据用户的id和用户名来查询符合条件的用户信息
     * @param id 用户id
     * @param username 用户名
     * @return OpResponseInfo 用户数据信息的JSON格式或错误信息
     */
    OpResponseInfo retrieveUserInfoByIdAndUsername(String id, String username);

    /**
     * 根据用户的id来修改用户名
     * 登录后操作
     * @param id 用户id
     * @param newUsername 要修改的用户名
     * @return OpResponseInfo 修改操作是否成功
     */
    OpResponseInfo modifyUsernameById(Long id, String newUsername);

    /**
     * 根据用户名来修改用户名和密码
     * @param username 旧用户名
     * @param newUsername 新用户名
     * @param newPassword 新密码
     * @return OpResponseInfo 修改操作的结果
     */
    OpResponseInfo modifyUserInfoByUsername(String username, String newUsername, String newPassword);
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

    /**
     * 根据用户名删除对应的用户记录
     * @param usernames 提供的用户名
     * @return OpResponseInfo 删除操作的结果信息
     */
    OpResponseInfo deleteUserByUsername(String[] usernames);
}
