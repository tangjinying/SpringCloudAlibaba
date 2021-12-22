package com.tjy.feign.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description
 * @Author tjy
 * @Date 2021/11/12 16:48
 * @Version 1.0
 */
@Data
public class ProductDTO {

    private int id;
    private String productCode;
    private String productName;
    private int count;
    private BigDecimal price;

}
