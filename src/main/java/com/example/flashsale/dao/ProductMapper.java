package com.example.flashsale.dao;

import com.example.flashsale.pojo.Page;
import com.example.flashsale.pojo.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ProductMapper {

    // GET
    public Product getProductById(@Param("productId") int id);
    public List<Product> getProductList();
    public List<Product> getProductListWithPage(Page page);
    public int countProduct();

    // GET INFO
    public Double getPriceById(@Param("productId")int id);
    public Boolean getFlagById(@Param("productId")int id);

    // INSERT
    public int addProduct(Product product);

    // UPDATE
    public int updateProduct(Product product);
}
