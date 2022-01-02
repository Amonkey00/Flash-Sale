package com.example.flashsale.service;

import com.example.flashsale.dao.ProductMapper;
import com.example.flashsale.pojo.Page;
import com.example.flashsale.pojo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductMapper productMapper;

    /**
     * Check the product information that is going to insert.
     * And fill default information for name and introduction.
     * @param product
     * @return
     */
    public Product checkAndFilling(Product product){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeStamp = sdf.format(new Date());
        if(product.getPname().isEmpty()) product.setPname(timeStamp + ": Default_Name");
        if(product.getIntroduction().isEmpty()) product.setIntroduction("There is no introduction of this product");
        if(product.getImage() == null || product.getImage().isEmpty()) product.setImage("default-imageUrl");
        return product;
    }

    /**
     * Cancel Flash-Sale state of the product
     * @param product
     * @return
     */
    public int cancelFlashSale(Product product){
        product.setFlag(false);
        return productMapper.updateProduct(product);
    }

    /**
     * Methods to set Flash-Sale state of the product
     * @return
     */
    public int setFlashSale(Product product, Date startTime, Date endTime){
        product.setStartTime(startTime);
        product.setEndTime(endTime);
        return productMapper.updateProduct(product);
    }

    /**
     * Get product Methods
     */
    public Product getProduct(int productId){
        return productMapper.getProductById(productId);
    }
    public Product getProduct(String productName){
        return productMapper.getProductByName(productName);
    }
    /**
     * Get product list without page information.
     * Return all the products.
     * @return List of products
     */
    public List<Product> getProductList(){
        return productMapper.getProductList();
    }

    /**
     * Get product list with page information
     * @param page Page information
     * @return List of products
     */
    public List<Product> getProductList(Page page){
        return productMapper.getProductListWithPage(page);
    }

    /**
     * Check the Flash-Sale state of the product
     * @param productId The productId of product.
     * @return The flag of product.
     */
    public Boolean checkProductFlag(int productId){
        Product product = productMapper.getProductById(productId);
        return product.getFlag();
    }

    /**
     * Modify the stock number of product
     * @param product The product modified
     * @param change The number of change
     * @return result
     */
    @Transactional(rollbackFor = Exception.class)
    public int modifyStock(Product product, int change){
        int expected = product.getStockCount() + change;
        System.out.println(expected);
        if(expected < 0) return -1;
        product.setStockCount(expected);
        return productMapper.updateProduct(product);
    }

    /**
     * Count the number of all products
     */
    public int countProduct(){
        return productMapper.countProduct();
    }

    /**
     * Add product
     */
    public int addProduct(Product product){
        return productMapper.addProduct(product);
    }

    /**
     * Update product
     */
    public int updateProduct(Product product){
        return productMapper.updateProduct(product);
    }
}
