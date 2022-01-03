package com.example.flashsale.utils;

import com.example.flashsale.pojo.Page;
import com.example.flashsale.pojo.Product;

import java.util.List;

public class PageContent<T> {
    public List<T> contentList;
    public int count;
    public int start;
    public int totalPage;

    public PageContent(List<T> list){
        this.contentList = list;
        this.count = list.size();
        this.start = -1;
        this.totalPage = -1;
    }

    public PageContent(List<T> list, Page page){
        this.contentList = list;
        this.count = list.size();
        this.start = Math.max(1, (page.getStart() / page.getCount()) + 1);
        this.totalPage = page.getTotalPage();
    }


}
