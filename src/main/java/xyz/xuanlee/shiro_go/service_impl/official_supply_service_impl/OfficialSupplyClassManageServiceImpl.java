package xyz.xuanlee.shiro_go.service_impl.official_supply_service_impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import xyz.xuanlee.shiro_go.DO.OpResponseInfo;
import xyz.xuanlee.shiro_go.DO.official_supply.OfficialSupplyClassOperationRecord;
import xyz.xuanlee.shiro_go.DO.official_supply.SupplyFirstClass;
import xyz.xuanlee.shiro_go.DO.official_supply.SupplySecondClass;
import xyz.xuanlee.shiro_go.constant.InteractInfo;
import xyz.xuanlee.shiro_go.service.official_supply_service.OfficialSupplyClassManageService;
import xyz.xuanlee.shiro_go.sql.OfficialSupplyClassSQL;
import xyz.xuanlee.shiro_go.sql.UserSQL;
import xyz.xuanlee.shiro_go.util.CommonUtil;
import xyz.xuanlee.shiro_go.util.JDBCUtil;

import java.lang.reflect.Constructor;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OfficialSupplyClassManageServiceImpl implements OfficialSupplyClassManageService {

    private static final String ENTITY_NAME = "办公用品类别";

    @Override
    public OpResponseInfo createSupplyFirstClass(String supplyClassName) {
        String code;
        String reason;
        if (supplyClassName == null) {
            code = "100";
            reason = "请输入办公用品一级类别名称";
            return new OpResponseInfo(code,
                    String.format(InteractInfo.GENERAL_MODIFY_INFO.get(code), ENTITY_NAME, reason));
        }

        try {
            Constructor<Integer> integerConstructor = Integer.class.getConstructor(int.class);
            Integer rec = JDBCUtil.executeQueryStatement(integerConstructor,
                    OfficialSupplyClassSQL.QUERY_FIRST_CLASS_EXIST,
                    supplyClassName);

            assert rec != null;
            reason = "";
            if (rec == 1) {
                code = "100";
                reason = "该名称已存在，请换一个名称再试";
            } else {
                if (JDBCUtil.executeModifyStatement(OfficialSupplyClassSQL.INSERT_FIRST_CLASS, supplyClassName)) {
                    code = "1";
                    new OpResponseInfo(code, InteractInfo.GENERAL_MODIFY_INFO.get(code));
                } else {
                    code = "100";
                    reason = "创建办公用品一级类别失败，系统正忙，请稍后再试";
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

    @Override
    public OpResponseInfo createSupplySecondClass(Long superId, String supplyClassName) {
        String code;
        String reason;
        if (supplyClassName == null) {
            code = "100";
            reason = "请输入办公用品二级类别名称";
            return new OpResponseInfo(code,
                    String.format(InteractInfo.GENERAL_MODIFY_INFO.get(code), ENTITY_NAME, reason));
        }

        try {
            Constructor<Integer> integerConstructor = Integer.class.getConstructor(int.class);
            Integer rec = JDBCUtil.executeQueryStatement(integerConstructor,
                    OfficialSupplyClassSQL.QUERY_FIRST_CLASS_EXIST_BY_ID,
                    superId);

            assert rec != null;
            reason = "";
            if (rec == 1) {
                Integer oRec = JDBCUtil.executeQueryStatement(integerConstructor,
                        OfficialSupplyClassSQL.QUERY_SECOND_CLASS_EXIST,
                        supplyClassName);

                assert oRec != null;
                if (oRec == 1) {
                    code = "100";
                    reason = "该名称已存在，请换一个名称再试";
                } else {
                    if (JDBCUtil.executeModifyStatement(OfficialSupplyClassSQL.INSERT_SECOND_CLASS,
                            supplyClassName, superId)) {
                        code = "1";
                        new OpResponseInfo(code, InteractInfo.GENERAL_MODIFY_INFO.get(code));
                    } else {
                        code = "100";
                        reason = "创建办公用品二级类别失败，系统正忙，请稍后再试";
                    }
                }
            } else {
                code = "100";
                reason = "创建办公用品二级类别失败，提供的一级类别不存在";
            }
            return new OpResponseInfo(code,
                    String.format(InteractInfo.GENERAL_MODIFY_INFO.get(code), ENTITY_NAME, reason));
        } catch (NoSuchMethodException e) {
            code = "-1";
            e.printStackTrace();
            return new OpResponseInfo(code, InteractInfo.GENERAL_ERROR_INFO.get(code));
        }
    }

    @Override
    public OpResponseInfo retrieveAllFirstClass() {
        String code;

        try {
            Constructor<SupplySecondClass> supplySecondClassConstructor =
                    SupplySecondClass.class.getConstructor(String.class, String.class, Long.class);
            List<SupplySecondClass> secondClasses = JDBCUtil.executeQueryBatchStatement(supplySecondClassConstructor,
                    OfficialSupplyClassSQL.QUERY_ALL_FIRST_WITH_SECOND);
            Map<Long, String> idToName = new HashMap<>(16);
            Map<Long, List<String>> groupById = new HashMap<>(16);

            for (SupplySecondClass secondClass: secondClasses) {
                idToName.putIfAbsent(secondClass.getSuperCLass().getId(),
                        secondClass.getSuperCLass().getSupplyClassName());
                groupById.putIfAbsent(secondClass.getSuperCLass().getId(), new ArrayList<>(16));
                groupById.get(secondClass.getSuperCLass().getId()).add(secondClass.getSupplyClassName());
            }

            JSONArray firstClassesInfo = new JSONArray();
            groupById.forEach((k, v) -> {
                JSONObject tmpInfo = new JSONObject();
                tmpInfo.put("firstClassId", k);
                tmpInfo.put("firstClassName", idToName.get(k));
                JSONArray secondClassesInfo = new JSONArray();
                secondClassesInfo.addAll(v);
                tmpInfo.put("secondClassesName", secondClassesInfo);
                firstClassesInfo.add(tmpInfo);
            });

            code = "1";
            JSONObject json = CommonUtil.groupRespData(code, InteractInfo.GENERAL_MODIFY_INFO.get(code));
            json.put("official_supply_first_classes", firstClassesInfo);
            return new OpResponseInfo(code, json.toJSONString());
        } catch (NoSuchMethodException e) {
            code = "-1";
            e.printStackTrace();
            return new OpResponseInfo(code, InteractInfo.GENERAL_ERROR_INFO.get(code));
        }
    }

    @Override
    public OpResponseInfo retrieveAllSecondClass() {
        String code;

        try {
            Constructor<SupplySecondClass> supplySecondClassConstructor =
                    SupplySecondClass.class.getConstructor(String.class, String.class);
            List<SupplySecondClass> secondClasses = JDBCUtil.executeQueryBatchStatement(supplySecondClassConstructor,
                    OfficialSupplyClassSQL.QUERY_ALL_SECOND_WITH_FIRST);
            JSONArray secondClassesInfo = new JSONArray();
            for (SupplySecondClass secondClass: secondClasses) {
                JSONObject tmpObject = new JSONObject();
                tmpObject.put("first_class_name", secondClass.getSuperCLass().getSupplyClassName());
                tmpObject.put("second_class_name", secondClass.getSupplyClassName());

                secondClassesInfo.add(tmpObject);
            }
            code = "1";
            JSONObject json = CommonUtil.groupRespData(code, InteractInfo.GENERAL_MODIFY_INFO.get(code));
            json.put("official_supply_second_class", secondClassesInfo);
            return new OpResponseInfo(code, json.toJSONString());
        } catch (NoSuchMethodException e) {
            code = "-1";
            e.printStackTrace();
            return new OpResponseInfo(code, InteractInfo.GENERAL_ERROR_INFO.get(code));
        }
    }

    @Override
    public OpResponseInfo retrieveAllSecondClassOperationRecord() {
        String code;

        try {
            Constructor<OfficialSupplyClassOperationRecord> officialSupplyClassOperationRecordConstructor =
                    OfficialSupplyClassOperationRecord.class.getConstructor(String.class, String.class);
            List<OfficialSupplyClassOperationRecord> officialSupplyClassOperationRecords =
                    JDBCUtil.executeQueryBatchStatement(officialSupplyClassOperationRecordConstructor,
                            OfficialSupplyClassSQL.QUERY_ALL_SECOND_CLASS_OPERATION_RECORD);
            JSONArray recordsInfo = new JSONArray();
            for (OfficialSupplyClassOperationRecord record: officialSupplyClassOperationRecords) {
                JSONObject tmpObject = new JSONObject();
                tmpObject.put("username", record.getUsername());
                tmpObject.put("second_class_name", record.getBusinessName());

                recordsInfo.add(tmpObject);
            }
            code = "1";
            JSONObject json = CommonUtil.groupRespData(code, InteractInfo.GENERAL_MODIFY_INFO.get(code));
            json.put("official_supply_second_class_operation_record", recordsInfo);
            return new OpResponseInfo(code, json.toJSONString());
        } catch (NoSuchMethodException e) {
            code = "-1";
            e.printStackTrace();
            return new OpResponseInfo(code, InteractInfo.GENERAL_ERROR_INFO.get(code));
        }
    }

    @Override
    public OpResponseInfo retrieveFirstClassById(Long id) {
        String code;

        try {
            Constructor<SupplyFirstClass> supplyFirstClassConstructor =
                    SupplyFirstClass.class.getConstructor(Long.class, String.class);
            SupplyFirstClass supplyFirstClass = JDBCUtil.executeQueryStatement(supplyFirstClassConstructor,
                    OfficialSupplyClassSQL.QUERY_FIRST_CLASS_BY_ID, id);

            JSONArray secondClassesInfo = new JSONArray();
            if (supplyFirstClass != null) {
                Constructor<SupplySecondClass> supplySecondClassConstructor =
                        SupplySecondClass.class.getConstructor(String.class);
                List<SupplySecondClass> supplySecondClasses =
                        JDBCUtil.executeQueryBatchStatement(supplySecondClassConstructor,
                                OfficialSupplyClassSQL.QUERY_SECOND_NAME_WITH_FIRST_BY_ID, supplyFirstClass.getId());
                secondClassesInfo = new JSONArray();
                for (SupplySecondClass secondClass: supplySecondClasses) {
                    secondClassesInfo.add(secondClass.getSupplyClassName());
                }
            }

            code = "1";
            JSONObject json = CommonUtil.groupRespData(code, InteractInfo.GENERAL_MODIFY_INFO.get(code));
            json.put("official_supply_first_class_name", supplyFirstClass.getSupplyClassName());
            json.put("official_supply_second_class_name", secondClassesInfo);
            return new OpResponseInfo(code, json.toJSONString());
        } catch (NoSuchMethodException e) {
            code = "-1";
            e.printStackTrace();
            return new OpResponseInfo(code, InteractInfo.GENERAL_ERROR_INFO.get(code));
        }
    }

    @Override
    public OpResponseInfo retrieveSecondClassById(Long id) {
        String code;

        try {
            Constructor<SupplySecondClass> supplySecondClassConstructor =
                    SupplySecondClass.class.getConstructor(String.class, String.class);
            List<SupplySecondClass> secondClasses = JDBCUtil.executeQueryBatchStatement(supplySecondClassConstructor,
                    OfficialSupplyClassSQL.QUERY_SECOND_WITH_FIRST_BY_ID, id);
            JSONArray secondClassesInfo = new JSONArray();
            for (SupplySecondClass secondClass: secondClasses) {
                JSONObject tmpObject = new JSONObject();
                tmpObject.put("first_class_name", secondClass.getSuperCLass().getSupplyClassName());
                tmpObject.put("second_class_name", secondClass.getSupplyClassName());

                secondClassesInfo.add(tmpObject);
            }
            code = "1";
            JSONObject json = CommonUtil.groupRespData(code, InteractInfo.GENERAL_MODIFY_INFO.get(code));
            json.put("official_supply_second_class", secondClassesInfo);
            return new OpResponseInfo(code, json.toJSONString());
        } catch (NoSuchMethodException e) {
            code = "-1";
            e.printStackTrace();
            return new OpResponseInfo(code, InteractInfo.GENERAL_ERROR_INFO.get(code));
        }
    }

    @Override
    public OpResponseInfo retrieveSecondClassOperationRecordById(Long id) {
        String code;

        try {
            Constructor<OfficialSupplyClassOperationRecord> officialSupplyClassOperationRecordConstructor =
                    OfficialSupplyClassOperationRecord.class.getConstructor(String.class, String.class);
            OfficialSupplyClassOperationRecord record = JDBCUtil.executeQueryStatement(
                    officialSupplyClassOperationRecordConstructor,
                    OfficialSupplyClassSQL.QUERY_ALL_SECOND_CLASS_OPERATION_RECORD_BY_ID,
                    id);

            JSONObject recordJson = new JSONObject();
            if (record != null) {
                recordJson.put("second_class_operation_record_id", id);
                recordJson.put("second_class_operation_record_user_name", record.getUsername());
                recordJson.put("second_class_operation_record_class_name", record.getBusinessName());
            }

            code = "1";
            JSONObject json = CommonUtil.groupRespData(code, InteractInfo.GENERAL_MODIFY_INFO.get(code));
            json.put("official_supply_second_class_operation_record", recordJson);
            return new OpResponseInfo(code, json.toJSONString());
        } catch (NoSuchMethodException e) {
            code = "-1";
            e.printStackTrace();
            return new OpResponseInfo(code, InteractInfo.GENERAL_ERROR_INFO.get(code));
        }
    }

    @Override
    public OpResponseInfo deleteSupplyFirstClass(Long id) {
        String code;

        if (id == null) {
            code = "300";
            String reason = "请输入要删除的办公用品一级类别id";
            return new OpResponseInfo(code,
                    String.format(InteractInfo.GENERAL_MODIFY_INFO.get(code), ENTITY_NAME, reason));
        }

        try {
            Constructor<Long> aLongConstructor = Long.class.getConstructor(long.class);
            List<Long> secondClassIds = JDBCUtil.executeQueryBatchStatement(aLongConstructor,
                    OfficialSupplyClassSQL.QUERY_ALL_SECOND_CLASS_ID_BY_FIRST_CLASS_ID, id);

            JDBCUtil.startTransaction();
            secondClassIds.forEach(k -> {
                JDBCUtil.executeModifyStatement(
                        OfficialSupplyClassSQL.DELETE_SECOND_CLASS_OPERATION_RECORD_BY_SECOND_CLASS_ID,
                        k);
            });
            if (JDBCUtil.executeModifyStatement(OfficialSupplyClassSQL.DELETE_SECOND_CLASS_BY_FIRST_CLASS_ID, id) &&
                    JDBCUtil.executeModifyStatement(OfficialSupplyClassSQL.DELETE_FIRST_CLASS_BY_ID, id)) {
                code = "1";
                JDBCUtil.commitTransaction();
                JDBCUtil.endTransaction();
                return new OpResponseInfo(code, InteractInfo.GENERAL_MODIFY_INFO.get(code));
            } else {
                code = "100";
                JDBCUtil.rollBackTransaction();
                JDBCUtil.endTransaction();
                return new OpResponseInfo(code, InteractInfo.MODIFY_INTERACT_INFO.get(code));
            }
        } catch (NoSuchMethodException | SQLException e) {
            code = "100";
            try {
                JDBCUtil.rollBackTransaction();
                JDBCUtil.endTransaction();
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
            e.printStackTrace();
            return new OpResponseInfo(code, InteractInfo.MODIFY_INTERACT_INFO.get(code));
        }
    }

    @Override
    public OpResponseInfo recordUserOperation(String username, Long secondClassId) {
        String code;

        try {
            Constructor<Long> aLongConstructor = Long.class.getConstructor(long.class);
            Constructor<String> aStringConstructor = String.class.getConstructor(String.class);

            Long userId = JDBCUtil.executeQueryStatement(aLongConstructor, UserSQL.QUERY_USER_ID_BY_USERNAME, username);
            String secondClassName = JDBCUtil.executeQueryStatement(aStringConstructor,
                    OfficialSupplyClassSQL.QUERY_SECOND_NAME_BY_ID, secondClassId);

            if (JDBCUtil.executeModifyStatement(OfficialSupplyClassSQL.INSERT_SECOND_CLASS_OPERATION_RECORD,
                    userId, secondClassId, secondClassName)) {
                code = "1";
                return new OpResponseInfo(code, InteractInfo.GENERAL_MODIFY_INFO.get(code));
            } else {
                code = "100";
                String tmpEntity = "办公用品二级类别操作记录";
                String reason = "系统正忙，操作记录插入失败";
                return new OpResponseInfo(code,
                        String.format(InteractInfo.GENERAL_MODIFY_INFO.get(code), tmpEntity, reason));
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            code = "-1";
            return new OpResponseInfo(code, InteractInfo.GENERAL_ERROR_INFO.get(code));
        }
    }
}
