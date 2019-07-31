package com.ruoyi.common.enums;

/**
 * 当当导入文件文件名枚举
 * @author addice
 */
public enum DangDangFileType {

    ADDITONAL("品专后端", "品专"),
    SEARCH("搜索后端","搜索"),
    ANDROID("安卓后端", "安卓"),
    SEARCH_ANDROID("搜索安卓后端", "搜索安卓"),
    BAIDU("百度小程序后端","百度"),
    IOS("ios后端","ios"),
    SEARCH_IOS("搜索ios后端","搜索ios"),
    APP_FRONT("app前端","app"),
    APPLETS("applets前端","applets");

    private final String description;
    private final String msg;

    DangDangFileType(String description, String msg) {
        this.description = description;
        this.msg = msg;
    }

    public String getdescription() {
        return description;
    }

    public String getMsg() {
        return msg;
    }
    }
