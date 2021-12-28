package com.example.flashsale.pojo;

public class Page {

    private int id; // The userId;
    private int start; //Start Page
    private int count; // Number of each page
    private int total; //Total number of all elements

    private static final int defaultCount = 5;

    public Page(){
        count = defaultCount;
    }

    public Page(int start,int count){
        this.start = start;
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTotalPage(){
        int totalPage = 0;
        if(total%count==0){
            totalPage = total/count;
        }
        else{
            totalPage = total/count + 1;
        }
        return Math.max(1,totalPage);
    }

    @Override
    public String toString() {
        return "Page{" +
                "start=" + start +
                ", count=" + count +
                ", total=" + total +
                '}';
    }
}
