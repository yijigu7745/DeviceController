package com.gh_hitech.devicecontroller.model;

import java.io.Serializable;

import cn.com.yijigu.rxnetwork.utils.StringUtils;

/**
 * @author yijigu
 */
public class AreaBean extends IBaseName implements Serializable {

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
    private int pavilionCount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public int getPavilionCount() {
        return pavilionCount;
    }

    public void setPavilionCount(int pavilionCount) {
        this.pavilionCount = pavilionCount;
    }

    @Override
    public String getIName() {
        if (StringUtils.isNotBlank(getDistrict())) {
            return getDistrict();
        } else if (StringUtils.isNotBlank(getCity())) {
            return getCity();
        } else if (StringUtils.isNotBlank(getProvince())) {
            return getProvince();
        } else {
            return "错误的地区";
        }
    }

    public String getDistrict() {
        return district;
    }

    public String getCity() {
        return city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    @Override
    public Long getIid() {
        return new Long((long) id);
    }

    public String getAreaName() {
        if (StringUtils.isNotBlank(getDistrict())) {
            return getDistrict();
        } else if (StringUtils.isNotBlank(getCity())) {
            return getCity();
        } else if (StringUtils.isNotBlank(getProvince())) {
            return getProvince();
        } else {
            return "错误的地区";
        }
    }
}
