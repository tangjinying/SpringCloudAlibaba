package com.tjy.dubbo.service.impl;

import com.tjy.dubbo.dto.AccountDTO;
import com.tjy.dubbo.entity.AccountVO;
import com.tjy.dubbo.mapper.AccountMapper;
import com.tjy.dubbo.service.AccountService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description
 */
@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountMapper accountMapper;

	@Override
	public AccountDTO getByCode(String accountCode) {
		AccountDTO accountDTO = new AccountDTO();
		AccountVO account = accountMapper.findByAccountCode(accountCode);
		BeanUtils.copyProperties(account,accountDTO);
		return accountDTO;
	}
}