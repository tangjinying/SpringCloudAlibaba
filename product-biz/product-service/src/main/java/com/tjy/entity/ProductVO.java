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
@TableName("product")
public class ProductVO {
    private int id;
    private String productCode;
    private String productName;
    private int count;
    private BigDecimal price;
}
