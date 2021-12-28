package com.example.flashsale;

import com.example.flashsale.dao.OrderMapper;
import com.example.flashsale.dao.ProductMapper;
import com.example.flashsale.dao.RecordMapper;
import com.example.flashsale.dao.UserMapper;
import com.example.flashsale.pojo.*;
import com.example.flashsale.redis.RedisPrefix;
import com.example.flashsale.redis.RedisService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Date;

@SpringBootTest
class FlashSaleApplicationTests {

    @Autowired
    public UserMapper userMapper;
    @Autowired
    public ProductMapper productMapper;
    @Autowired
    public OrderMapper orderMapper;
    @Autowired
    public RecordMapper recordMapper;

    @Test
    void UserTest() {
//        User user = new User();
//        user.setUname("SecondUser");
//        user.setEmail("xxx@xx.com");
//        user.setPhone("10000000000");
//        user.setWallet(0d);
//        userMapper.addUser(user);
        User user = userMapper.getUserByName("FirstUser");
        user.setWallet(100d);
        System.out.println(userMapper.updateUser(user));
        System.out.println(userMapper.getUserByName("FirstUser"));
    }

    @Test
    void ProductTest() {
//        Product product = new Product();
//        product.setPname("FirstProduct");
//        product.setImage("no Image");
//        product.setIntroduction("balabablablalbala");
//        product.setPrice(100d);
//        product.setStockCount(10);
//        product.setFlag(true);
//        product.setStartTime(new Date());
//        product.setEndTime(new Date());
//        productMapper.addProduct(product);
        Product product = productMapper.getProductById(1);
        product.setImage("NoImage");
        productMapper.updateProduct(product);
        System.out.println("Object Inserted: " + productMapper.getProductById(1));
        System.out.println("CountTest:" + productMapper.countProduct());

        // Page Test
        Page productPage = new Page(0,10);
        productPage.setTotal(productMapper.countProduct());
        for(Product p : productMapper.getProductListWithPage(productPage)){
            System.out.println(p);
        }

    }

    @Test
    void OrderTest(){
//        Order order = new Order();
//        order.setUid(1);
//        order.setPid(1);
//        order.setNumber(10);
//        order.setStatus(Order.WAITING);
//        orderMapper.addOrder(order);
        System.out.println(orderMapper.getOrderById(1));
        System.out.println(orderMapper.countOrder());

        Order order = orderMapper.getOrderById(1);
        order.setStatus(Order.ACCEPTED);
        orderMapper.updateOrder(order);
        System.out.println(orderMapper.getOrderById(1));
        System.out.println(orderMapper.getOrderListByUserId(1));
        System.out.println(orderMapper.getOrderListByProductId(1));
    }

    @Test
    void RecordTest(){
        Record record = new Record();
        Order order = orderMapper.getOrderById(1);
        record.setOid(order.getOid());
        record.setPid(1);
        record.setUid(1);
        Double totalPrice = productMapper.getPriceById(1) * order.getNumber();
        record.setTotal(totalPrice);
        recordMapper.addRecord(record);
        System.out.println(recordMapper.getRecordById(1));
        System.out.println(recordMapper.countRecord());


    }

}
