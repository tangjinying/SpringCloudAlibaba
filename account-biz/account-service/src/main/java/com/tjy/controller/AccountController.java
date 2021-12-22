package com.tjy.controller;

/**
 * @Description
 * @Author tjy
 * @Date 2021/11/12 15:42
 * @Version 1.0
 */

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.tjy.dto.AccountDTO;
import com.tjy.logging.annotation.SysLog;
import com.tjy.result.ResultData;
import com.tjy.security.user.SecurityUser;
import com.tjy.security.utils.SecurityUtils;
import com.tjy.service.AccountService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@Log4j2
public class AccountController{

    @Autowired
    private AccountService accountService;

    @GetMapping("/account/getByCode/{accountCode}")
	@PreAuthorize("hasAuthority('test')")
    @SentinelResource(value = "getByCode",blockHandler = "handlerException")
	@SysLog("查询账户")
    public ResultData getByCode(@PathVariable String accountCode) {
        log.info("get account detail,accountCode is :{}", accountCode);
		SecurityUser securityUser = SecurityUtils.getUser();
		System.out.println(securityUser.getId());
		System.out.println(securityUser.getMobile());
		System.out.println(securityUser.getUsername());
		AccountDTO accountDTO = accountService.selectByCode(accountCode);
        return ResultData.success(accountDTO);
    }

	@PostMapping("/account/getByCode/{accountCode}")
	//	@PreAuthorize("hasAuthority('/test')")
	public ResultData test(@PathVariable String accountCode) {
		System.out.println(accountCode);
		return ResultData.success(null);
	}

//    /*
//     * @Date: 2021/11/15 11:08
//     * 限流异常处理
//     */
//    public ResultData handlerException(String accountCode, BlockedException e) {
//        log.info("flow exception{}",e.getClass().getCanonicalName());
//        return ResultData.fail("达到阈值了,不要再访问了!");
//    }

    @PostMapping("/account/insert")
    public ResultData<String> insert(@RequestBody AccountDTO accountDTO){
        log.info("insert account:{}",accountDTO);
        accountService.insertAccount(accountDTO);
        return ResultData.success("insert account succeed");
    }

    @PostMapping("/account/delete")
    public ResultData<String> delete(@RequestParam String accountCode){
        log.info("delete account,accountCode is {}",accountCode);
        accountService.deleteAccount(accountCode);
        return ResultData.success("delete account succeed");
    }

    @PostMapping("/account/update")
    public  ResultData<String> update(@RequestBody AccountDTO accountDTO){
        log.info("update account:{}",accountDTO);
        accountService.updateAccount(accountDTO);
        return ResultData.success("update account succeed");
    }


    @PostMapping("/account/reduce")
    public ResultData<String> reduce(String accountCode, BigDecimal amount){
        accountService.reduceAccount(accountCode,amount);
		return ResultData.success("reduce account succeed");
    }
}