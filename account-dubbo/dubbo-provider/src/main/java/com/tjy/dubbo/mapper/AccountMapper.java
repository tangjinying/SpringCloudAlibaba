package com.tjy.dubbo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tjy.dubbo.entity.AccountVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @Description
 * @Author tjy
 * @Date 2021/11/12 15:45
 * @Version 1.0
 */
@Repository
@Mapper
public interface AccountMapper extends BaseMapper<AccountVO> {

	@Select("select * from account where account_code = #{accountCode}")
	AccountVO findByAccountCode(String accountCode);
}
