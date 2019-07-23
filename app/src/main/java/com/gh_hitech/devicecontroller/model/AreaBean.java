package com.gh_hitech.devicecontroller.model;

import cn.com.yijigu.rxnetwork.utils.StringUtils;

/**
 * @author yijigu
 */
public class AreaBean extends IBaseName{

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
    private String areaCode;

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

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    @Override
    public String getIName() {
        if(StringUtils.isNotBlank(getDistrict())){
            return getDistrict();
        }else if(StringUtils.isNotBlank(getCity())){
            return getCity();
        }else if(StringUtils.isNotBlank(getProvince())){
            return getProvince();
        }else{
            return "错误的地区";
        }
    }

    public String getAreaName(){
        if(StringUtils.isNotBlank(getDistrict())){
            return getDistrict();
        }else if(StringUtils.isNotBlank(getCity())){
            return getCity();
        }else if(StringUtils.isNotBlank(getProvince())){
            return getProvince();
        }else{
            return "错误的地区";
        }
    }

    @Override
    public Long getIid() {
        return new Long((long)id);
    }
}
