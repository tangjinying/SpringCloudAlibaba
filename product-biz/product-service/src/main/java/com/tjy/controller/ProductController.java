package com.tjy.controller;

/**
 * @Description
 * @Author tjy
 * @Date 2021/11/12 15:42
 * @Version 1.0
 */

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.tjy.dto.ProductDTO;
import com.tjy.result.ResultData;
import com.tjy.service.ProductService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
public class ProductController{

    @Autowired
    private ProductService productService;

    @GetMapping("/product/getByCode/{productCode}")
    @SentinelResource(value = "/product/getByCode",fallback = "fallbackHandler")
    public ResultData getByCode(@PathVariable String productCode) {
        log.info("get product detail,productCode is :{}", productCode);
        ProductDTO productDTO = productService.selectByCode(productCode);
//        throw new RuntimeException("error");
        return ResultData.success(productDTO);
    }

    /**
     * 自定义熔断异常
     * 返回值和参数要跟目标函数一样
     */
    public ResultData<ProductDTO> fallbackHandler(String productCode){
        return ResultData.fail("服务被熔断了，不要调用!");
    }

    @PostMapping("/product/update")
    public ResultData update(ProductDTO productDTO) {
        log.info("update product:{}", productDTO);
        productService.updateProduct(productDTO);
        return ResultData.success("update product success");
    }

    @PostMapping("/product/insert")
    public ResultData insert(ProductDTO productDTO) {
        log.info("insert product:{}", productDTO);
        productService.insertProduct(productDTO);
        return ResultData.success("insert product success");
    }

    @PostMapping("/product/delete")
    public ResultData delete(@RequestParam String productCode) {
        log.info("delete product,productCode is {}", productCode);
        productService.deleteProduct(productCode);
        return ResultData.success("delete product success");
    }

    @PostMapping("/product/deduct")
    public void deduct(String productCode, Integer count){
        productService.deduct(productCode,count);
    }
}