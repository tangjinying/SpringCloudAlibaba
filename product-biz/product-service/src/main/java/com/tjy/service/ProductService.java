package com.tjy.service;

import com.tjy.dto.ProductDTO;

/**
 * @Description
 * @Author tjy
 * @Date 2021/11/12 15:42
 * @Version 1.0
 */
public interface ProductService {

    ProductDTO selectByCode(String productCode);

    ProductDTO updateProduct(ProductDTO productDTO);

    ProductDTO insertProduct(ProductDTO productDTO);

    int deleteProduct(String productCode);

    void deduct(String productCode,Integer deductCount);
}
