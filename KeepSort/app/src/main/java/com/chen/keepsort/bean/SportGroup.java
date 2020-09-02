package com.chen.keepsort.bean;

import java.util.List;

/**
 * 一组运动
 */
public class SportGroup {
    private String key = null;

    private List<SportItem> list;
    private String name;

    public List<SportItem> getList() {
        return list;
    }

    public void setList(List<SportItem> list) {
        this.list = list;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
