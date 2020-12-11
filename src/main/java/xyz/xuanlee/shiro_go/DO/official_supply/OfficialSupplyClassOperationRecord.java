package xyz.xuanlee.shiro_go.DO.official_supply;

public class OfficialSupplyClassOperationRecord {
    private Long id;
    private Long userId;
    private String username;
    private Long businessId;
    private String businessName;

    public OfficialSupplyClassOperationRecord(String username, String businessName) {
        this.username = username;
        this.businessName = businessName;
    }

    public String getUsername() {
        return username;
    }

    public String getBusinessName() {
        return businessName;
    }
}
