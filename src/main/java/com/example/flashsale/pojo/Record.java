package com.example.flashsale.pojo;

import java.util.Date;

public class Record {

    private int r_id; // The unique code of record
    private int o_id; // ID of order
    private int u_id; // ID of customer
    private int p_id; // ID of product
    private Date create_time; // The finished
    private Double total; // The total price of a order

    public Record() {
        this.create_time = new Date();
    }

    public Record(int r_id, int o_id, int u_id, int p_id, Double total) {
        this.r_id = r_id;
        this.o_id = o_id;
        this.u_id = u_id;
        this.p_id = p_id;
        this.create_time = new Date();
        this.total = total;
    }

    public int getRid() {
        return r_id;
    }

    public void setRid(int r_id) {
        this.r_id = r_id;
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

    public Date getCreateTime() {
        return create_time;
    }

    public void setCreateTime(Date create_time) {
        this.create_time = create_time;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Record{" +
                "r_id=" + r_id +
                ", o_id=" + o_id +
                ", u_id=" + u_id +
                ", p_id=" + p_id +
                ", create_time=" + create_time +
                ", total=" + total +
                '}';
    }
}
