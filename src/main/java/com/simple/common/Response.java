package com.simple.common;



public class Response {
    private static final int STATUS_SUCCESS = 0;
    private static final int STATUS_ERROR = -1;
    private static final String MSG_SUCCESS = "操作成功";
    private static final String MSG_ERROR = "操作失败";
    private int status = 0;
    private String msg;
    private Object result;
    private Page page;
    private Object statistics;

    private Response() {
    }

    private Response(int status, String msg, Object result, Page page, Object statistics) {
        this.status = status;
        this.msg = msg;
        this.result = result;
        this.page = page;
        this.statistics = statistics;
    }

    public static Response ok() {
        return new Response(0, "操作成功", (Object)null, (Page)null, (Object)null);
    }

    public static Response ok(Object result) {
        return new Response(0, "操作成功", result, (Page)null, (Object)null);
    }

    public static Response ok(Object result, Page page) {
        return new Response(0, "操作成功", result, page, (Object)null);
    }

    public static Response ok(Object result, Page page, Object statistics) {
        return new Response(0, "操作成功", result, page, statistics);
    }


    public static Response ok(String message, Object result, Page page, Object statistics) {
        return new Response(0, message, result, page, statistics);
    }

    public static Response error() {
        return new Response(-1, "操作失败", (Object)null, (Page)null, (Object)null);
    }

    public static Response error(Object result) {
        return new Response(-1, "操作失败", result, (Page)null, (Object)null);
    }

    public static Response error(String msg) {
        return new Response(-1, msg, null, (Page)null, (Object)null);
    }

    public static Response error(Object result, Page page) {
        return new Response(-1, "操作失败", result, page, (Object)null);
    }

    public static Response error(String message, Object result, Page page, Object statistics) {
        return new Response(-1, message, result, page, statistics);
    }

    public static Response error(int status, String message) {
        return new Response(status, message, (Object)null, (Page)null, (Object)null);
    }

    public int getstatus() {
        return this.status;
    }

    public void setstatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getresult() {
        return this.result;
    }

    public void setresult(Object result) {
        this.result = result;
    }

    public Page getPage() {
        return this.page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public Object getStatistics() {
        return statistics;
    }

    public void setStatistics(Object statistics) {
        this.statistics = statistics;
    }
}
