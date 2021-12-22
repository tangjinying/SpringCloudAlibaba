package com.tjy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tjy.dao.ProductDao;
import com.tjy.dto.ProductDTO;
import com.tjy.entity.ProductVO;
import com.tjy.service.ProductService;
import io.seata.core.context.RootContext;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description
 * @Author tjy
 * @Date 2021/11/12 15:43
 * @Version 1.0
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Resource
    private ProductDao productDao;

    @Override
    public ProductDTO selectByCode(String productCode) {
        LambdaQueryWrapper<ProductVO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProductVO::getProductCode,productCode);
        ProductVO productVO = productDao.selectOne(wrapper);

        BeanCopier copier = BeanCopier.create(ProductVO.class,ProductDTO.class,false);
        ProductDTO productDTO = new ProductDTO();
        copier.copy(productVO,productDTO,null);

        return productDTO;
    }

    @Override
    public ProductDTO updateProduct(ProductDTO productDTO) {
        BeanCopier copier = BeanCopier.create(ProductDTO.class,ProductVO.class,false);
        ProductVO productVO = new ProductVO();
        copier.copy(productDTO,productVO,null);

        productDao.updateById(productVO);

        return productDTO;
    }

    @Override
    public ProductDTO insertProduct(ProductDTO productDTO) {
        BeanCopier copier = BeanCopier.create(ProductDTO.class,ProductVO.class,false);
        ProductVO productVO = new ProductVO();
        copier.copy(productDTO,productVO,null);

        productDao.insert(productVO);
        return productDTO;
    }

    @Override
    public int deleteProduct(String productCode) {
        LambdaQueryWrapper<ProductVO> wrapper = new LambdaQueryWrapper<ProductVO>();
        wrapper.eq(ProductVO::getProductCode,productCode);
        return productDao.delete(wrapper);
    }

//    @GlobalTransactional(name = "TX_ORDER_CREATE")
    @Override
    public void deduct(String productCode,Integer deductCount) {
        String xid = RootContext.getXID();
        System.out.println(xid+"-----------------------------");
        LambdaQueryWrapper<ProductVO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProductVO::getProductCode,productCode);
        ProductVO product = productDao.selectOne(wrapper);
        if(null == product){
            throw new RuntimeException("can't deduct product,product is null");
        }
        int surplus = product.getCount() - deductCount;
        if(surplus < 0){
            throw new RuntimeException("can't deduct product,product's count is less than deduct count");
        }
        product.setCount(surplus);
        productDao.updateById(product);
    }
}
