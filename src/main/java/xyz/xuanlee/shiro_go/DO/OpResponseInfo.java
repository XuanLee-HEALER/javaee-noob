package xyz.xuanlee.shiro_go.DO;

import java.util.HashMap;

public class OpResponseInfo extends HashMap<String, String> {
    private final String infoCode;
    private final String info;

    public OpResponseInfo(String infoCode, String info) {
        this.infoCode = infoCode;
        this.info = info;
    }

    public String getInfoCode() {
        return infoCode;
    }

    public String getInfo() {
        return info;
    }

    @Override
    public String toString() {
        return "OpResponseInfo{" +
                "infoCode='" + infoCode + '\'' +
                ", info='" + info + '\'' +
                "}";
    }
}
