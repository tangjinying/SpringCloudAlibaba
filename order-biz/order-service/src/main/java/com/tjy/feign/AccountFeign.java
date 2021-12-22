package com.tjy.feign;

import com.tjy.feign.dto.AccountDTO;
import com.tjy.feign.fallback.AccountFeignFallbackFactory;
import com.tjy.result.ResultData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * @Description
 * @Author tjy
 * @Date 2021/11/12 17:02
 * @Version 1.0
 */
@FeignClient(name = "account-service",fallbackFactory = AccountFeignFallbackFactory.class)
public interface AccountFeign {
    @PostMapping("/account/insert")
    ResultData<String> insert(@RequestBody AccountDTO accountDTO);

    @PostMapping("/account/delete")
    ResultData<String> delete(@RequestParam(value = "accountCode") String accountCode);

    @PostMapping("/account/update")
    ResultData<String> update(@RequestBody AccountDTO accountDTO);

    @GetMapping("/account/getByCode/{accountCode}")
    ResultData<AccountDTO> getByCode(@PathVariable(value = "accountCode") String accountCode);

    @PostMapping("/account/reduce")
	ResultData<String> reduce(@RequestParam(value = "accountCode")String accountCode,@RequestParam(value = "amount") BigDecimal amount);
}