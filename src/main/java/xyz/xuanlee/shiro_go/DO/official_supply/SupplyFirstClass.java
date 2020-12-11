package xyz.xuanlee.shiro_go.DO.official_supply;

public class SupplyFirstClass {

    private Long id;
    private String supplyClassName;

    public SupplyFirstClass(Long id, String supplyClassName) {
        this.id = id;
        this.supplyClassName = supplyClassName;
    }

    public SupplyFirstClass(String supplyClassName) {
        this.supplyClassName = supplyClassName;
    }

    public Long getId() {
        return id;
    }

    public String getSupplyClassName() {
        return supplyClassName;
    }
}
