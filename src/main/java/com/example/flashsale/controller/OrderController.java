package com.example.flashsale.controller;

import com.example.flashsale.pojo.Order;
import com.example.flashsale.pojo.Product;
import com.example.flashsale.pojo.User;
import com.example.flashsale.service.OrderService;
import com.example.flashsale.service.ProductService;
import com.example.flashsale.service.UserService;
import com.example.flashsale.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;
    @Autowired
    UserService userService;
    @Autowired
    ProductService productService;

    @PostMapping("/add")
    public JsonResult add(ServletRequest request){
        Integer userId = Integer.parseInt(request.getParameter("userId"));
        Integer productId = Integer.parseInt(request.getParameter("productId"));
        Integer number = Integer.parseInt(request.getParameter("number"));
        Order order = new Order(userId, productId, number, Order.WAITING);
        // Check logic
        User user = userService.getUser(UserService.IDKEY, String.valueOf(userId));
        if(user == null) return new JsonResult(1, "No such user");
        if(number <= 0) return new JsonResult(1, "Order Number must > 0");
        // Start Transactional service
        try{
            return orderService.addOrder(order);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(1, "Transactional service failed");
        }
    }

    @PostMapping("/pay")
    public JsonResult payOrder(ServletRequest request){
        Integer orderId = Integer.parseInt(request.getParameter("orderId"));
        Integer userId = Integer.parseInt(request.getParameter("userId"));
        // Start Transactional service
        try{
            return orderService.payOrder(orderId, userId);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(1, "Transactional service failed");
        }
    }

    @PostMapping("/modify")
    public JsonResult modify(ServletRequest request){
        Integer orderId = Integer.parseInt(request.getParameter("orderId"));
        Integer userId = Integer.parseInt(request.getParameter("userId"));
        Integer number = Integer.parseInt(request.getParameter("number"));
        try{
            return orderService.modifyOrder(orderId, userId, number);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(1, "Transactional service failed");
        }
    }

    @PostMapping("/cancel")
    public JsonResult cancel(ServletRequest request){
        Integer orderId = Integer.parseInt(request.getParameter("orderId"));
        Integer userId = Integer.parseInt(request.getParameter("userId"));
        try{
            return orderService.cancelOrder(orderId, userId);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(1, "Transactional service failed");
        }
    }

    @GetMapping("/get")
    public JsonResult get(ServletRequest request){
        Integer orderId = Integer.parseInt(request.getParameter("orderId"));
        Order order = orderService.getOrder(orderId);
        if(order == null) return new JsonResult(1, "No such order.");
        return new JsonResult<>(order, "Operation succeed", 0);
    }

    @GetMapping("/getList")
    public JsonResult getList(ServletRequest request){
        // Get data in body.
        String userIdStr = request.getParameter("userId");
        String productIdStr = request.getParameter("productId");
        String filter = request.getParameter("filter");
        // Format filter
        if(filter != null && filter.isEmpty()){
            filter = null;
        }
        if(userIdStr != null){
            User user = userService.getUser(UserService.IDKEY, userIdStr);
            if(user == null) return new JsonResult(1, "No such user");
            List<Order> orderList = orderService.getOrderList(user, filter);
            return new JsonResult(orderList, "Operation succeed", 0);
        }
        else if(productIdStr != null){
            Integer productId = Integer.parseInt(productIdStr);
            Product product = productService.getProduct(productId);
            if(product == null) return new JsonResult(1, "No such product");
            List<Order> orderList = orderService.getOrderList(product, filter);
            return new JsonResult(orderList, "Operation succeed", 0);
        }
        return new JsonResult(1, "Operation failed");
    }
}
