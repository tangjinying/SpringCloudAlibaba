package com.tjy.feign;

import com.tjy.dto.OrderDTO;
import com.tjy.feign.dto.AccountDTO;
import com.tjy.feign.dto.ProductDTO;
import com.tjy.result.ResultData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @Description
 * @Author tjy
 * @Date 2021/11/12 17:02
 * @Version 1.0
 */
@FeignClient(name = "order-service")
public interface OrderFeign {
    @GetMapping("/order/{orderCode}")
    public ResultData getByCode(@PathVariable(value = "orderCode") String orderCode);

    @PostMapping("/order/update")
    public ResultData update(OrderDTO orderDTO) ;

    @PostMapping("/order/insert")
    public ResultData insert(OrderDTO orderDTO) ;

    @PostMapping("/order/delete")
    public ResultData delete(@RequestParam(value = "orderCode") String orderCode);


    @PostMapping("/order/create")
    public ResultData<OrderDTO> create(@RequestBody OrderDTO orderDTO);



    @GetMapping("/order/getAccount/{accountCode}")
    public ResultData<AccountDTO> getAccount(@PathVariable(value = "accountCode") String accountCode);

    @PostMapping("/order/insertAccount")
    public ResultData<String> insertAccount(AccountDTO accountDTO);

    @PostMapping("/order/updateAccount")
    public ResultData<String> updateAccount(AccountDTO accountDTO);

    @PostMapping("/order/deleteAccount/{accountCode}")
    public ResultData<String> deleteAccount(@PathVariable(value = "accountCode") String accountCode);



    @GetMapping("/order/getProduct/{productCode}")

    public ResultData<ProductDTO> getProduct(@PathVariable(value = "productCode") String productCode);

    @PostMapping("/order/insertProduct")
    public ResultData<String> insertProduct(ProductDTO productDTO);

    @PostMapping("/order/updateProduct")
    public ResultData<String> updateAProduct(ProductDTO productDTO);

    @PostMapping("/order/deleteProduct/{productCode}")
    public ResultData<String> deleteProduct(@PathVariable(value = "productCode") String productCode);
}