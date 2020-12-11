package xyz.xuanlee.shiro_go.service.user_service;

import xyz.xuanlee.shiro_go.DO.OpResponseInfo;

public interface LoginService {

    /**
     * 用户的登录功能
     * @return OpResponseInfo 操作码和操作结果信息
     */
    OpResponseInfo login(String username, String password);

    /**
     * 锁定用户功能
     * @param username 提供用户名
     * @return OpResponseInfo 操作码和操作结果信息
     */
    OpResponseInfo lock(String username);
}
