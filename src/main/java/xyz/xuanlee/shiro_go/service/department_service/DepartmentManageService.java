package xyz.xuanlee.shiro_go.service.department_service;

import xyz.xuanlee.shiro_go.DO.OpResponseInfo;

public interface DepartmentManageService {

    /**
     * 创建一个新部门
     * @param departmentName 新部门名称
     * @return OpResponseInfo 创建部门的操作结果信息
     */
    OpResponseInfo createDepartment(String departmentName);
}
