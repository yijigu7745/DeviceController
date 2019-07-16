package com.gh_hitech.devicecontroller.model;

/**
 * @author yijigu
 */
public class AreaBean {

    /**
     * id : 1
     * province : 中华人民共和国
     * city :
     * district :
     * areaCode : null
     */

    private int id;
    private String province;
    private String city;
    private String district;
    private Object areaCode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Object getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(Object areaCode) {
        this.areaCode = areaCode;
    }
}
