package com.tjy.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tjy.entity.AccountVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Description
 * @Author tjy
 * @Date 2021/11/12 15:45
 * @Version 1.0
 */
@Repository
@Mapper
public interface AccountDao extends BaseMapper<AccountVO> {
}
