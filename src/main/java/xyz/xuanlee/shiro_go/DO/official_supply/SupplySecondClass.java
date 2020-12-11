package xyz.xuanlee.shiro_go.DO.official_supply;

public class SupplySecondClass {

    private Long id;
    private String supplyClassName;
    private SupplyFirstClass superCLass;

    public SupplySecondClass(String supplyClassName) {
        this.supplyClassName = supplyClassName;
    }

    public SupplySecondClass(String supplyClassName, String superClassName) {
        this.supplyClassName = supplyClassName;
        this.superCLass = new SupplyFirstClass(superClassName);
    }

    public SupplySecondClass(String supplyClassName, String superClassName, Long superClassId) {
        this.supplyClassName = supplyClassName;
        this.superCLass = new SupplyFirstClass(superClassId, superClassName);
    }

    public String getSupplyClassName() {
        return supplyClassName;
    }

    public SupplyFirstClass getSuperCLass() {
        return superCLass;
    }
}
