package com.tjy.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description
 * @Author tjy
 * @Date 2021/11/12 15:44
 * @Version 1.0
 */
@Data
@TableName("order_vo")
public class OrderVO {
    private int id;
    private String orderNo;
    private String accountCode;
    private String productCode;
    private int count;
    private BigDecimal amount;
}
