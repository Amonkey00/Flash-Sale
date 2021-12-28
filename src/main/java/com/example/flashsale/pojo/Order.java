package com.example.flashsale.pojo;

import java.util.Date;

public class Order {

    /**
     * Status Code
     * [AC]: The order is paid successfully.
     * [WT]: The order is waiting for customer to pay.
     * [FD]: The order is failed.
     */
    public final static String ACCEPTED = "AC";
    public final static String WAITING = "WT";
    public final static String FAILED = "FD";

    private int o_id;
    private int u_id;
    private int p_id;
    private int number;
    private Date create_time;
    private String status;

    public Order() {
        this.create_time = new Date();
    }

    public Order(int o_id, int u_id, int p_id,
                 int number, String status) {
        this.o_id = o_id;
        this.u_id = u_id;
        this.p_id = p_id;
        this.number = number;
        this.create_time = new Date();
        this.status = status;
    }

    public int getOid() {
        return o_id;
    }

    public void setOid(int o_id) {
        this.o_id = o_id;
    }

    public int getUid() {
        return u_id;
    }

    public void setUid(int u_id) {
        this.u_id = u_id;
    }

    public int getPid() {
        return p_id;
    }

    public void setPid(int p_id) {
        this.p_id = p_id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Date getCreateTime() {
        return create_time;
    }

    public void setCreateTime(Date create_time) {
        this.create_time = create_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "o_id=" + o_id +
                ", u_id=" + u_id +
                ", p_id=" + p_id +
                ", number=" + number +
                ", create_time=" + create_time +
                ", status='" + status + '\'' +
                '}';
    }
}
