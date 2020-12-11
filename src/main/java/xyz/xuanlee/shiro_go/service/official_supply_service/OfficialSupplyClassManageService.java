package xyz.xuanlee.shiro_go.service.official_supply_service;

import xyz.xuanlee.shiro_go.DO.OpResponseInfo;

public interface OfficialSupplyClassManageService {

    /**
     * 创建一个一级办公用品类别
     * @param supplyClassName 类别名称
     * @return OpResponseInfo 创建操作的结果信息
     */
    OpResponseInfo createSupplyFirstClass(String supplyClassName);

    /**
     * 创建一个二级办公用品类别
     * @param superId 一级类别id
     * @param supplyClassName 类别名称
     * @return OpResponseInfo 创建操作的结果信息
     */
    OpResponseInfo createSupplySecondClass(Long superId, String supplyClassName);

    /**
     * 获取所有的一级类别信息（名称及其对应的二级类别名称）
     * @return OpResponseInfo 包含操作码和获取的信息
     */
    OpResponseInfo retrieveAllFirstClass();

    /**
     * 获取所有的二级类别信息（名称及其对应的一级类别名称）
     * @return OpResponseInfo 包含操作码和获取的信息
     */
    OpResponseInfo retrieveAllSecondClass();

    /**
     * 获取所有的二级类别的操作记录信息（用户名-办公用品二级类别名称）
     * @return OpResponseInfo 包含操作码和获取的信息
     */
    OpResponseInfo retrieveAllSecondClassOperationRecord();

    /**
     * 通过指定的一级类别id来获取其信息（名称及其对应的二级类别名称）
     * @param id 一级类别id
     * @return OpResponseInfo 包含操作码和获取的信息
     */
    OpResponseInfo retrieveFirstClassById(Long id);

    /**
     * 通过指定的二级类别id来获取其信息（名称及其对应的一级类别名称）
     * @param id 二级类别id
     * @return OpResponseInfo 包含操作码和获取的信息
     */
    OpResponseInfo retrieveSecondClassById(Long id);

    /**
     * 通过指定的二级类别操作记录id来获取其信息（用户名-办公用品二级类别名称）
     * @param id 二级类别操作记录id
     * @return OpResponseInfo 包含操作码和获取的信息
     */
    OpResponseInfo retrieveSecondClassOperationRecordById(Long id);

    /**
     * 删除指定id的一级类别（级联删除所有二级类别）
     * @param id 一级类别id
     * @return OpResponseInfo 操作码
     */
    OpResponseInfo deleteSupplyFirstClass(Long id);

    /**
     * 记录用户对每个办公用品二级类别的操作
     * @param username 用户名
     * @param secondClassId 办公用品二级类别id
     * @return OpResponseInfo 插入记录的操作结果
     */
    OpResponseInfo recordUserOperation(String username, Long secondClassId);
}
