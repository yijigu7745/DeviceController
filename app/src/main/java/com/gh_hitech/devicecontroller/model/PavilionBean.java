package com.gh_hitech.devicecontroller.model;

/**
 * @author yijigu
 */
public class PavilionBean extends IBaseName {
    /**
     * id : 1
     * name : p2
     * address : 福州鼓楼区东大路53号
     */

    private Long id;
    private String name;
    private String address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String getIName() {
        return name;
    }

    @Override
    public Long getIid() {
        return id;
    }

    public PavilionBean(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public PavilionBean() {
    }
}