package xyz.xuanlee.shiro_go.constant;

import java.util.HashMap;
import java.util.Map;

public class InteractInfo {

    public static final Map<String, String> LOGIN_INTERACT_INFO = new HashMap<>(16);
    public static final Map<String, String> REGISTER_INTERACT_INFO = new HashMap<>(16);
    public static final Map<String, String> MODIFY_INTERACT_INFO = new HashMap<>(16);

    public static final Map<String, String> GENERAL_MODIFY_INFO = new HashMap<>(16);
    public static final Map<String, String> GENERAL_ERROR_INFO = new HashMap<>(16);

    static {
        // 登录服务返回的操作码
        LOGIN_INTERACT_INFO.put("100", "请输入用户名/密码");
        LOGIN_INTERACT_INFO.put("200", "用户不存在");
        LOGIN_INTERACT_INFO.put("300", "用户名/密码输入错误");
        LOGIN_INTERACT_INFO.put("400", "用户已经被锁定，无法登录，请联系管理员解锁");
        LOGIN_INTERACT_INFO.put("1", "用户登录成功，本用户上一次登录时间为：%s");
        // 锁定服务返回的操作码
        LOGIN_INTERACT_INFO.put("500", "用户名/密码输入错误过多，账户已锁定");
        // 请求接口返回的操作码
        LOGIN_INTERACT_INFO.put("600", "用户名/密码输入错误，错误次数为%d次");

        // 注册服务返回的操作码
        REGISTER_INTERACT_INFO.put("100", "请输入用户名/密码");
        REGISTER_INTERACT_INFO.put("200", "用户名或密码长度过长/短，请将长度控制在6-20个字符");
        REGISTER_INTERACT_INFO.put("300", "用户名已存在");
        REGISTER_INTERACT_INFO.put("400", "系统正在维护，注册失败，请稍后再试");
        REGISTER_INTERACT_INFO.put("1", "注册用户成功");

        // 修改的数据与原数据重复
        MODIFY_INTERACT_INFO.put("100", "修改失败，被修改数据没有变化或数据不存在");
        MODIFY_INTERACT_INFO.put("200", "删除失败，被删除的数据已不存在");

        GENERAL_ERROR_INFO.put("-1", "服务器开小差了，操作失败");
        GENERAL_ERROR_INFO.put("-2", "请先登录再进行操作");
        GENERAL_ERROR_INFO.put("-3", "参数类型不符，请输入%s");

        // 数据修改服务返回的操作码
        GENERAL_MODIFY_INFO.put("100", "创建%s失败，原因是：%s");
        GENERAL_MODIFY_INFO.put("200", "更新%s失败，原因是：%s");
        GENERAL_MODIFY_INFO.put("300", "删除%s失败，原因是：%s");
        GENERAL_MODIFY_INFO.put("400", "查询%s失败，原因是：%s");
        GENERAL_MODIFY_INFO.put("1", "操作成功");
    }
}
