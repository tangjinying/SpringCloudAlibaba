package com.tjy.service;

import com.tjy.dto.AccountDTO;
import com.tjy.entity.AccountVO;

import java.math.BigDecimal;

/**
 * @Description
 * @Author tjy
 * @Date 2021/11/12 15:42
 * @Version 1.0
 */
public interface AccountService {

    AccountDTO selectByCode(String accountCode);

    AccountVO updateAccount(AccountDTO accountDTO);

    AccountVO insertAccount(AccountDTO accountDTO);

    int deleteAccount(String accountCode);

    void reduceAccount(String accountCode, BigDecimal amount);
}
