package com.tjy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tjy.dao.AccountDao;
import com.tjy.dto.AccountDTO;
import com.tjy.entity.AccountVO;
import com.tjy.service.AccountService;
import io.seata.core.context.RootContext;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * @Description
 * @Author tjy
 * @Date 2021/11/12 15:43
 * @Version 1.0
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Resource
    private AccountDao accountDao;

    @Override
    public AccountDTO selectByCode(String accountCode) {
        LambdaQueryWrapper<AccountVO> wrapper = new LambdaQueryWrapper<AccountVO>();
        wrapper.eq(AccountVO::getAccountCode,accountCode);
        AccountVO accountVO = accountDao.selectOne(wrapper);

        BeanCopier copier = BeanCopier.create(AccountVO.class,AccountDTO.class,false);
        AccountDTO accountDTO = new AccountDTO();
        copier.copy(accountVO,accountDTO,null);
        return accountDTO;
    }

    @Override
    public AccountVO updateAccount(AccountDTO accountDTO) {
        BeanCopier copier = BeanCopier.create(AccountDTO.class,AccountVO.class,false);
        AccountVO accountVO = new AccountVO();
        copier.copy(accountDTO,accountVO,null);
        accountDao.updateById(accountVO);
        return accountVO;
    }

    @Override
    public AccountVO insertAccount(AccountDTO accountDTO) {
        BeanCopier copier = BeanCopier.create(AccountDTO.class,AccountVO.class,false);
        AccountVO accountVO = new AccountVO();
        copier.copy(accountDTO,accountVO,null);
        accountDao.insert(accountVO);
        return accountVO;
    }

    @Override
    public int deleteAccount(String accountCode) {
        LambdaQueryWrapper<AccountVO> wrapper = new LambdaQueryWrapper<AccountVO>();
        wrapper.eq(AccountVO::getAccountCode,accountCode);
        return accountDao.delete(wrapper);
    }

//    @GlobalTransactional(name = "TX_ORDER_CREATE")
    @Override
    public void reduceAccount(String accountCode, BigDecimal amount) {
        String xid = RootContext.getXID();
        System.out.println(xid+"-----------------------------");
        LambdaQueryWrapper<AccountVO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AccountVO::getAccountCode,accountCode);
        AccountVO account = accountDao.selectOne(wrapper);
        if(null == account){
            throw new RuntimeException("can't reduce amount,account is null");
        }
        BigDecimal subAmount = account.getAmount().subtract(amount);
        if(subAmount.compareTo(BigDecimal.ZERO) < 0){
            throw new RuntimeException("can't reduce amount,account'amount is less than reduce amount");
        }
        account.setAmount(subAmount);
        accountDao.updateById(account);
    }
}
