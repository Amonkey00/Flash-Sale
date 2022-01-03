package com.example.flashsale.controller;

import com.example.flashsale.pojo.Page;
import com.example.flashsale.pojo.Product;
import com.example.flashsale.service.ProductService;
import com.example.flashsale.utils.JsonResult;
import com.example.flashsale.utils.PageContent;
import com.example.flashsale.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @Value("${path.default-image-path}")
    private String defaultImagePath;

    @GetMapping("/getList")
    public JsonResult getList(ServletRequest request){
        String startStr = request.getParameter("start");
        String countStr = request.getParameter("count");

        // Check the page information
        PageContent<Product> content = null;
        if(startStr != null && countStr != null){
            int start = Integer.parseInt(startStr);
            int count = Integer.parseInt(countStr);
            int computeStart = Math.max(0, (start-1) * count);
            Page page = new Page(computeStart, count);
            page.setTotal(productService.countProduct());
            List<Product> productList = productService.getProductList(page);
            content = new PageContent<>(productList, page);
        }
        else{
            List<Product> productList = productService.getProductList();
            content = new PageContent<>(productList);
        }
        return new JsonResult<>(content, "List of products", 0);
    }

    @GetMapping("/get")
    public JsonResult get(int productId){
        Product product = productService.getProduct(productId);
        if(product != null){
            return new JsonResult<>(product, "The information of product", 0);
        }
        return new JsonResult<Product>(1, "No such product");
    }

    @PostMapping("/add")
    public JsonResult addProduct(MultipartHttpServletRequest request){
        // Initial Product information
        String productName = request.getParameter("productName");
        String introduction = request.getParameter("introduction");
        Double price = Double.parseDouble(request.getParameter("price"));
        Integer stockCount = Integer.parseInt(request.getParameter("stockCount"));
        Boolean flag = request.getParameter("flag").equals("true");
        Long startTime = Long.parseLong(request.getParameter("startTime"));
        Long endTime = Long.parseLong(request.getParameter("endTime"));
        MultipartFile imageFile = request.getFile("image");
        Product product = new Product(productName,null, introduction,
                            price,stockCount,flag,
                            new Date(startTime), new Date(endTime));
        // Filling empty blanks
        product = productService.checkAndFilling(product);

        // Check duplication
        if(productService.getProduct(productName) != null){
            return new JsonResult(1, "Product Name duplicated.");
        }
        // Check Flash-Sale Time
        if(product.getFlag()){
            if(endTime <= new Date().getTime()){
                return new JsonResult(1, "End Date must after present");
            }
            Long minGap = 24 * 60 * 60L; // One day
            if(endTime - startTime < minGap) {
                return new JsonResult(1, "Flash-Sale time gap is too small.");
            }
        }
        // Check Price And Stock
        if(product.getPrice() < 0){
            return new JsonResult(1, "Price must >= 0");
        }
        if(product.getStockCount() <= 0){
            return new JsonResult(1, "Stock must > 0");
        }

        // Upload product Images
        if(imageFile != null){
            // Initialize the image dictionary for added product
            //String path = request.getServletContext().getRealPath(defaultImagePath);
            String path = System.getProperty("user.dir") + defaultImagePath;
            File realPath = new File(path);
            if(!realPath.exists()){
                realPath.mkdir();
            }
            // Get imageUrl
            String filename = imageFile.getOriginalFilename();
            String suffix = filename.substring(filename.lastIndexOf("."));
            String imageUrl = realPath + "\\" + productName + suffix;
            try{
                imageFile.transferTo(new File(imageUrl));
            }catch (Exception e){
                e.printStackTrace();
                return new JsonResult(1, "Errors when uploading image file");
            }
            // Update the imageUrl information of product
            product.setImage(imageUrl);
        }

        // Execute
        int status = productService.addProduct(product);
        if(status > 0) return new JsonResult(0, "Add product successfully");

        return new JsonResult(1, "Operation failed.");
    }

    @PostMapping("/uploadImage")
    public JsonResult uploadImage(MultipartHttpServletRequest request) throws IOException {
        Integer productId = Integer.parseInt(request.getParameter("productId"));
        MultipartFile imageFile = request.getFile("image");

        // Check productId
        Product product = productService.getProduct(productId);
        if(product == null){
            return new JsonResult(1, "No such product");
        }

        // Upload product Images
        if(imageFile == null) return new JsonResult(0, "No Changes");
        // Initialize the image dictionary for added product
        String path = System.getProperty("user.dir") + defaultImagePath;
        File realPath = new File(path);
        if(!realPath.exists()) {
            realPath.mkdir();
        }
        // Get imageUrl
        String filename = imageFile.getOriginalFilename();
        String suffix = filename.substring(filename.lastIndexOf("."));
        String imageUrl = realPath + "\\" + product.getPname() + suffix;
        try{
            imageFile.transferTo(new File(imageUrl));
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(1, "Errors when uploading image file");
        }
        // Update the imageUrl information of product
        product.setImage(imageUrl);
        int status = productService.updateProduct(product);
        if(status > 0) return new JsonResult(0, "Change product image successfully");

        return new JsonResult(1, "Operation failed.");
    }

    @PostMapping("/cancelFlashSale")
    public JsonResult cancelFlashSale(ServletRequest request){
        Integer productId = Integer.parseInt(request.getParameter("productId"));
        Product product = productService.getProduct(productId);
        if(product == null) return new JsonResult(1, "No such product");

        int status = productService.cancelFlashSale(product);
        if(status > 0) return new JsonResult();
        return new JsonResult(1, "Operation failed.");
    }

    @PostMapping("/modifyStock")
    public JsonResult modifyStock(ServletRequest request){
        Integer productId = Integer.parseInt(request.getParameter("productId"));
        Integer change = Integer.parseInt(request.getParameter("change"));
        Product product = productService.getProduct(productId);
        if(product == null){
            return new JsonResult(1, "No such product.");
        }
        try{
            int status = productService.modifyStock(product, change);
            if(status > 0) return new JsonResult();
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(1, "Transaction failed");
        }
        return new JsonResult(1, "Operation failed");
    }

    @PostMapping("/update")
    public JsonResult update(ServletRequest request){
        // Data initialization
        Integer productId = Integer.parseInt(request.getParameter("productId"));
        String productName = request.getParameter("productName");
        String introduction = request.getParameter("introduction");
        Double price = Double.parseDouble(request.getParameter("price"));
        Boolean flag = request.getParameter("flag").equals("true");
        Long startTime = Long.parseLong(request.getParameter("startTime"));
        Long endTime = Long.parseLong(request.getParameter("endTime"));
        // Get product from database
        Product product = productService.getProduct(productId);
        if(product == null){
            return new JsonResult(1, "No such product");
        }

        // Check
        if(!productName.equals(product.getPname()) && productService.getProduct(productName) != null){
            return new JsonResult(1, "New Product Name exists");
        }
        if(price < 0){
            return new JsonResult(1, "Price must >= 0");
        }

        // Set New information
        product.setPname(productName);
        product.setIntroduction(introduction);
        product.setPrice(price);
        product.setFlag(flag);
        product.setStartTime(new Date(startTime));
        product.setEndTime(new Date(endTime));
        // Execute
        int status = productService.updateProduct(product);
        if(status > 0) return new JsonResult();
        return new JsonResult(1, "Operation failed");
    }

}
