package com.example.flashsale.utils;

import com.example.flashsale.pojo.Page;
import com.example.flashsale.pojo.Product;

import java.util.List;

public class ProductPageContent {
    public List<Product> productList;
    public int count;
    public int start;
    public int totalPage;

    public ProductPageContent(List<Product> list){
        this.productList = list;
        this.count = list.size();
        this.start = -1;
        this.totalPage = -1;
    }

    public ProductPageContent(List<Product> list, Page page){
        this.productList = list;
        this.count = list.size();
        this.start = Math.max(1, (page.getStart() / page.getCount()) + 1);
        this.totalPage = page.getTotalPage();
    }


}
