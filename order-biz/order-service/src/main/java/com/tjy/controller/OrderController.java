package com.tjy.controller;

/**
 * @Description
 * @Author tjy
 * @Date 2021/11/12 15:42
 * @Version 1.0
 */

import com.tjy.dto.OrderDTO;
import com.tjy.dubbo.service.AccountService;
import com.tjy.feign.AccountFeign;
import com.tjy.feign.ProductFeign;
import com.tjy.feign.dto.AccountDTO;
import com.tjy.feign.dto.ProductDTO;
import com.tjy.result.ResultData;
import com.tjy.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.UUID;

@RestController
@Log4j2
public class OrderController {

    @Resource
    private AccountFeign accountFeign;

    @Resource
    private ProductFeign productFeign;

    @Autowired
    private OrderService orderService;

    @Reference
    private AccountService accountService;


    @GetMapping("/order/{orderCode}")
    public ResultData getByCode(@PathVariable String orderCode) {
        log.info("get order detail,orderCode is :{}", orderCode);
        OrderDTO orderDTO = orderService.selectByCode(orderCode);
        return ResultData.success(orderDTO);
    }

    @PostMapping("/order/update")
    public ResultData update(OrderDTO orderDTO) {
        log.info("update order:{}", orderDTO);
        orderService.updateOrder(orderDTO);
        return ResultData.success("update order success");
    }

    @PostMapping("/order/insert")
    public ResultData insert(OrderDTO orderDTO) {
        log.info("insert order:{}", orderDTO);
        orderService.insertOrder(orderDTO);
        return ResultData.success("insert order success");
    }

    @PostMapping("/order/delete")
    public ResultData delete(@RequestParam String orderCode) {
        log.info("delete order,orderCode is {}", orderCode);
        orderService.deleteOrder(orderCode);
        return ResultData.success("delete order success");
    }


    @PostMapping("/order/create")
    public ResultData<OrderDTO> create(@RequestBody OrderDTO orderDTO){
        log.info("create order:{}",orderDTO);
        orderDTO.setOrderNo(UUID.randomUUID().toString());
        orderDTO.setAmount(orderDTO.getPrice().multiply(new BigDecimal(orderDTO.getCount())));
        orderService.createOrder(orderDTO);
        return ResultData.success("create order success");
    }



    @GetMapping("/order/getAccount/{accountCode}")
    public ResultData<AccountDTO> getAccount(@PathVariable String accountCode){
//		com.tjy.dubbo.dto.AccountDTO accountDTO = accountService.getByCode(accountCode);
//		return ResultData.success(accountDTO);

		return accountFeign.getByCode(accountCode);
    }

    @PostMapping("/order/insertAccount")
    public ResultData<String> insertAccount(AccountDTO accountDTO){
        return accountFeign.insert(accountDTO);
    }

    @PostMapping("/order/updateAccount")
    public ResultData<String> updateAccount(AccountDTO accountDTO){
        return accountFeign.update(accountDTO);
    }

    @PostMapping("/order/deleteAccount/{accountCode}")
    public ResultData<String> deleteAccount(@PathVariable String accountCode){
        return accountFeign.delete(accountCode);
    }



    @GetMapping("/order/getProduct/{productCode}")

    public ResultData<ProductDTO> getProduct(@PathVariable String productCode){
        return productFeign.getByCode(productCode);
    }

    @PostMapping("/order/insertProduct")
    public ResultData<String> insertProduct(ProductDTO productDTO){
        return productFeign.insert(productDTO);
    }

    @PostMapping("/order/updateProduct")
    public ResultData<String> updateAProduct(ProductDTO productDTO){
        return productFeign.update(productDTO);
    }

    @PostMapping("/order/deleteProduct/{productCode}")
    public ResultData<String> deleteProduct(@PathVariable String productCode){
        return productFeign.delete(productCode);
    }
}