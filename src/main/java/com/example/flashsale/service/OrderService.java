package com.example.flashsale.service;

import com.example.flashsale.dao.OrderMapper;
import com.example.flashsale.dao.ProductMapper;
import com.example.flashsale.dao.UserMapper;
import com.example.flashsale.pojo.Order;
import com.example.flashsale.pojo.Product;
import com.example.flashsale.pojo.Record;
import com.example.flashsale.pojo.User;
import com.example.flashsale.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {

    static final int FLASHLIMIT = 2;

    @Autowired
    UserMapper userMapper;
    @Autowired
    ProductMapper productMapper;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    UserService userService;
    @Autowired
    ProductService productService;
    @Autowired
    RecordService recordService;

    /**
     * Add Order
     */
    @Transactional(rollbackFor = Exception.class)
    public JsonResult addOrder(Order order){
        // Check product status
        Product product = productMapper.getProductById(order.getPid());
        if(product == null){
            return new JsonResult(1, "No such product");
        }
        // Flash-Sale limit
        if(product.getFlag() && order.getNumber() > FLASHLIMIT){
            return new JsonResult(1, "You can not buy >2's Flash_Sale product");
        }
        int stocks = product.getStockCount();
        int expected = stocks - order.getNumber();
        if(expected < 0){
            return new JsonResult(1, "Stock is not enough");
        }
        int productStatus = productService.modifyStock(product, -order.getNumber());
        if(productStatus <= 0) return new JsonResult(1, "Change product stock error");
        int status = orderMapper.addOrder(order);
        if(status > 0) return new JsonResult();
        return new JsonResult(1, "Operation failed");
    }

    /**
     * Pay the Order
     */
    @Transactional(rollbackFor = Exception.class)
    public JsonResult payOrder(Integer orderId, Integer userId){
        Order order = orderMapper.getOrderById(orderId);
        // Check Order status
        if(order == null || !order.getStatus().equals(Order.WAITING)){
            return new JsonResult(1, "Order not found or is finished.");
        }
        // Get user and product
        User user = userMapper.getUserById(userId);
        Product product = productMapper.getProductById(order.getPid());
        if(user == null || product == null){
            return new JsonResult(1, "User or product not found");
        }

        // Check logic
        int orderNumber = order.getNumber();
        Double wallet = user.getWallet();
        Double total = product.getPrice() * orderNumber;
        Double expected = wallet - total;
        if(expected < 0){
            return new JsonResult(1, "Money is not enough to pay.");
        }

        // Execute
        int  userStatus = userService.modifyWallet(userId, -total);
        if(userStatus <= 0){
            return new JsonResult(1, "User wallet change error");
        }
        // Save Record
        Record record = new Record(orderId, userId,product.getPid(), total);
        int recordStatus = recordService.addRecord(record);
        if(recordStatus <= 0){
            return new JsonResult(1, "Record save error");
        }
        order.setStatus(Order.ACCEPTED);
        int status = orderMapper.updateOrder(order);
        if(status > 0) return new JsonResult();
        return new JsonResult(1, "Operation failed");
    }

    /**
     * Cancel the order
     */
    @Transactional(rollbackFor = Exception.class)
    public JsonResult cancelOrder(Integer orderId, Integer userId){
        // Get order
        Order order = orderMapper.getOrderById(orderId);
        // Check order status
        if(order == null){
            return new JsonResult(1, "No such order");
        }
        if(order.getUid() != userId){
            return new JsonResult(1, "You are not the owner of this order");
        }
        if(order.getStatus().equals(Order.FAILED)){
            return new JsonResult(1, "The order is failed");
        }
        if(order.getStatus().equals(Order.ACCEPTED)){
            return new JsonResult(1, "The order is already paid");
        }

        // Check cancel logic
        Product product  = productMapper.getProductById(order.getPid());
        if(product == null){
            return new JsonResult(1, "Product is deleted");
        }
        int productStatus = productService.modifyStock(product, order.getNumber());
        if(productStatus <= 0){
            return new JsonResult(1, "The product stock change error");
        }
        order.setStatus(Order.FAILED);
        int status = orderMapper.updateOrder(order);
        if(status > 0) return new JsonResult();
        return new JsonResult(1, "Operation failed");
    }

    /**
     * Modify the order
     */
    @Transactional(rollbackFor = Exception.class)
    public JsonResult modifyOrder(Integer orderId, Integer userId, Integer number){
        Order order = orderMapper.getOrderById(orderId);
        if(order == null) return new JsonResult(1, "Order not found");
        if(order.getUid() != userId) return new JsonResult(1, "You don't own this order");
        if(!order.getStatus().equals(Order.WAITING)) return new JsonResult(1, "Order is failed or finished");
        Product product = productMapper.getProductById(order.getPid());
        if(product == null) return new JsonResult(1, "Product not found");

        // Check logic
        int expected = product.getStockCount() - number;
        if(expected < 0) return new JsonResult(1, "Stock is not enough");

        // Execute
        product.setStockCount(expected);
        int productStatus = productMapper.updateProduct(product);
        if(productStatus <= 0) return new JsonResult(1, "Stock operation failed");
        order.setNumber(order.getNumber() + number);
        int status = orderMapper.updateOrder(order);
        if(status > 0) return new JsonResult();
        return new JsonResult(1, "Operation failed");
    }


    public Order getOrder(Integer orderId){
        return orderMapper.getOrderById(orderId);
    }

    /**
     * Methods to get order_list with filter [User|Product related]
     */
    public List<Order> getOrderList(User user, String filter){
        return orderMapper.getOrderListByUserId(user.getUid(), filter);
    }
    public List<Order> getOrderList(Product product, String filter){
        return orderMapper.getOrderListByProductId(product.getPid(), filter);
    }

}
