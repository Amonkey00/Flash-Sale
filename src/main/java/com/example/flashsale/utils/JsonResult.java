package com.example.flashsale.utils;

public class JsonResult<T> {
    private T data;
    private long code;
    private String msg;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     *  If there is no data to return
     *  code = 0, default msg -"Operation Success."
     */
    public JsonResult(){
        this.code = 0;
        this.msg = "Operation Success.";
    }

    /**
     *  You can determine the code and message
     * @param code status code
     * @param msg message information
     */
    public JsonResult(long code, String msg){
        this.code = code;
        this.msg = msg;
    }

    /**
     * If there is data to return
     * @param data data to return
     * @param msg message information
     */
    public JsonResult(T data, String msg){
        this.data = data;
        this.msg = msg;
    }

    /**
     *
     * @param data data to return
     * @param msg message information
     * @param code code information [0:Success, 1:Error]
     */
    public JsonResult(T data, String msg, long code){
        this.code = code;
        this.msg = msg;
        this.data =data;
    }
}
