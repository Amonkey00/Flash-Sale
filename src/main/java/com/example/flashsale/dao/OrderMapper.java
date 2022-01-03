package com.example.flashsale.dao;

import com.example.flashsale.pojo.Order;
import com.example.flashsale.pojo.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface OrderMapper {

    // GET
    public Order getOrderById(@Param("orderId")int id);
    public List<Order> getOrderList();
    public List<Order> getOrderListWithPage(Page page);
    public List<Order> getOrderListByProductId(@Param("productId")int id, @Param("filter") String filter);
    public List<Order> getOrderListByUserId(@Param("userId")int id, @Param("filter") String filter);
    public int countOrder();
    public int countOrderByProductId(@Param("productId")int id, @Param("filter") String filter);
    public int countOrderByUserId(@Param("userId")int id, @Param("filter") String filter);

    // INSERT
    public int addOrder(Order order);

    // UPDATE
    /**  Only update number and status */
    public int updateOrder(Order order);

}
