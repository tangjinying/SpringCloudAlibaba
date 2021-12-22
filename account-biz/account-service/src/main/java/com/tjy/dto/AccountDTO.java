package com.tjy.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description
 * @Author tjy
 * @Date 2021/11/12 16:34
 * @Version 1.0
 */
@Data
public class AccountDTO {
    private int id;
    private String accountCode;
    private String accountName;
    private BigDecimal amount;
}
