package com.gh_hitech.devicecontroller.model;

/**
 * 删除选项
 *
 * @author yijigu
 */
public class SelectorItem extends IBaseName {

    private String selector;
    private Long selectorId;

    public Long getSelectorId() {
        return selectorId;
    }

    public void setSelectorId(Long selectorId) {
        this.selectorId = selectorId;
    }

    public String getSelector() {
        return selector;
    }

    public void setSelector(String selector) {
        this.selector = selector;
    }

    @Override
    public String getIName() {
        return selector;
    }

    @Override
    public Long getIid() {
        return selectorId;
    }
}
