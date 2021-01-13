package com.datayes.clouddemo.request;

public class TestRequestBean {

    private final String sceneEnName;
    private final String orderBy;
    private final String order;
    private final int pageNum;
    private final int pageSize;

    public TestRequestBean(String sceneEnName, String orderBy, String order, int pageNum, int pageSize) {
        this.sceneEnName = sceneEnName;
        this.orderBy = orderBy;
        this.order = order;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }
}
