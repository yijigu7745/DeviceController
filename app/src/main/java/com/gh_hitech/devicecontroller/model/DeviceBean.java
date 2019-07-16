package com.gh_hitech.devicecontroller.model;

import java.io.Serializable;

/**
 * @author yijigu
 */
public class DeviceBean implements Serializable {

    /**
     * id : 1
     * name : d3
     * host : 127.0.0.1:5001
     * pavilion : {"id":1,"name":"p2","address":"福州鼓楼区东大路53号"}
     * startTime : 2019-05-30T09:36:10.000+0000
     */

    private Long id;
    private String name;
    private String host;
    private PavilionBean pavilion;
    private String startTime;

    public DeviceBean() {
    }

    public DeviceBean(Long id, String name, String host, PavilionBean pavilion, String startTime) {
        this.id = id;
        this.name = name;
        this.host = host;
        this.pavilion = pavilion;
        this.startTime = startTime;
    }

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

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public PavilionBean getPavilion() {
        return pavilion;
    }

    public void setPavilion(PavilionBean pavilion) {
        this.pavilion = pavilion;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public static class PavilionBean extends IBaseName implements Serializable{
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
}
