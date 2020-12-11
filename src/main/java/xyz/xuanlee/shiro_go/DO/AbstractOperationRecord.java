package xyz.xuanlee.shiro_go.DO;

abstract public class AbstractOperationRecord {

    private Long id;
    private Long userId;
    private Long businessId;
    private Long businessName;

    public AbstractOperationRecord(Long id, Long userId, Long businessId) {
        this.id = id;
        this.userId = userId;
        this.businessId = businessId;
    }
}
