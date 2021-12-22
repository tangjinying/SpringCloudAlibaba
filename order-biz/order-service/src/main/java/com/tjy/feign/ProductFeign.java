package com.tjy.feign;

import com.tjy.feign.dto.ProductDTO;
import com.tjy.result.ResultData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @Description
 * @Author tjy
 * @Date 2021/11/12 17:14
 * @Version 1.0
 */
@FeignClient(name = "product-service")
public interface ProductFeign {

    @PostMapping("/product/insert")
    ResultData<String> insert(@RequestBody ProductDTO productDTO);

    @PostMapping("/product/delete")
    ResultData<String> delete(@RequestParam("productCode") String productCode);

    @PostMapping("/product/update")
    ResultData<String> update(@RequestBody ProductDTO productDTO);

    @GetMapping("/product/getByCode/{productCode}")
    ResultData<ProductDTO> getByCode(@PathVariable(value = "productCode") String productCode);

    @PostMapping("/product/deduct")
    void deduct(@RequestParam("productCode")String productCode, @RequestParam("count")Integer count);
}
