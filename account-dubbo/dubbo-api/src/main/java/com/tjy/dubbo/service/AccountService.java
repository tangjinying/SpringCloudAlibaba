package com.tjy.dubbo.service;

import com.tjy.dubbo.dto.AccountDTO;

/**
 * @Description
 */
public interface AccountService {
	AccountDTO getByCode(String accountCode);
}