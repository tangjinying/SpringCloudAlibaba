package com.tjy.dubbo.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Description
 * @Author tjy
 * @Date 2021/11/12 16:34
 * @Version 1.0
 */
@Data
public class AccountDTO implements Serializable {
	private static final long serialVersionUID = -8327288984905873629L;
	private int id;
    private String accountCode;
    private String accountName;
    private BigDecimal amount;
}
