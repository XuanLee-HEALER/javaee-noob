package xyz.xuanlee.shiro_go.service_impl.department_service_impl;

import xyz.xuanlee.shiro_go.DO.OpResponseInfo;
import xyz.xuanlee.shiro_go.constant.InteractInfo;
import xyz.xuanlee.shiro_go.service.department_service.DepartmentManageService;
import xyz.xuanlee.shiro_go.sql.DepartmentSQL;
import xyz.xuanlee.shiro_go.util.JDBCUtil;

import java.lang.reflect.Constructor;

public class DepartmentManageServiceImpl implements DepartmentManageService {

    private static final String ENTITY_NAME = "部门";

    @Override
    public OpResponseInfo createDepartment(String departmentName) {
        String code;
        String reason;
        if (departmentName == null) {
            code = "100";
            reason = "请输入部门名称";
            return new OpResponseInfo(code,
                    String.format(InteractInfo.GENERAL_MODIFY_INFO.get(code), ENTITY_NAME, reason));
        }

        try {
            Constructor<Integer> integerConstructor = Integer.class.getConstructor(int.class);
            Integer rec = JDBCUtil.executeQueryStatement(integerConstructor,
                    DepartmentSQL.QUERY_DEPARTMENT_EXIST,
                    departmentName);

            assert rec != null;
            reason = "";
            if (rec == 1) {
                code = "100";
                reason = "该名称已存在，请换一个名称再试";
            } else {
                if (JDBCUtil.executeModifyStatement(DepartmentSQL.INSERT_DEPARTMENT, departmentName)) {
                    code = "1";
                    new OpResponseInfo(code, InteractInfo.GENERAL_MODIFY_INFO.get(code));
                } else {
                    code = "100";
                    reason = "创建部门失败，系统正忙，请稍后再试";
                }
            }
            return new OpResponseInfo(code,
                    String.format(InteractInfo.GENERAL_MODIFY_INFO.get(code), ENTITY_NAME, reason));
        } catch (NoSuchMethodException e) {
            code = "-1";
            e.printStackTrace();
            return new OpResponseInfo(code, InteractInfo.GENERAL_ERROR_INFO.get(code));
        }
    }
}
