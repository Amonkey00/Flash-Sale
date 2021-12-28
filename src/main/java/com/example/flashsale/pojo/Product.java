package com.example.flashsale.pojo;

import java.util.Date;

public class Product {

    private int p_id; // Product Id
    private String p_name; // Product Name
    private String image; // The url of product image
    private String introduction; // The detailed introduction of product
    private Double price; // The price of one product
    private int stock_count; // The stocks of the product
    private Boolean flag; // Mark whether the product is in flash-sale
    private Date start_time; // The start time of flash-sale
    private Date end_time; // The end time of flash-sale

    public Product() {
    }

    public Product(String p_name, String image,
                   String introduction, Double price,
                   int stock_count, Boolean flag,
                   Date start_time, Date end_time) {
        this.p_name = p_name;
        this.image = image;
        this.introduction = introduction;
        this.price = price;
        this.stock_count = stock_count;
        this.flag = flag;
        this.start_time = start_time;
        this.end_time = end_time;
    }

    public int getPid() {
        return p_id;
    }

    public void setPid(int p_id) {
        this.p_id = p_id;
    }

    public String getPname() {
        return p_name;
    }

    public void setPname(String p_name) {
        this.p_name = p_name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getStockCount() {
        return stock_count;
    }

    public void setStockCount(int stock_count) {
        this.stock_count = stock_count;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public Date getStartTime() {
        return start_time;
    }

    public void setStartTime(Date start_time) {
        this.start_time = start_time;
    }

    public Date getEndTime() {
        return end_time;
    }

    public void setEndTime(Date end_time) {
        this.end_time = end_time;
    }

    @Override
    public String toString() {
        return "Product{" +
                "p_id=" + p_id +
                ", p_name='" + p_name + '\'' +
                ", image='" + image + '\'' +
                ", introduction='" + introduction + '\'' +
                ", price=" + price +
                ", stock_count=" + stock_count +
                ", flag=" + flag +
                ", start_time=" + start_time +
                ", end_time=" + end_time +
                '}';
    }
}
