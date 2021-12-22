package com.tjy.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description
 * @Author tjy
 * @Date 2021/11/12 16:38
 * @Version 1.0
 */
@Data
public class OrderDTO {
    private int id;
    private String orderNo;
    private String accountCode;
    private String productCode;
    private int count;
    private BigDecimal amount;
    private BigDecimal price;

}
