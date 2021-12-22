package com.tjy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tjy.dao.OrderDao;
import com.tjy.dto.OrderDTO;
import com.tjy.entity.OrderVO;
import com.tjy.feign.AccountFeign;
import com.tjy.feign.OrderFeign;
import com.tjy.feign.ProductFeign;
import com.tjy.service.OrderService;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
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
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderDao orderDao;

    @Resource
    private ProductFeign productFeign;

    @Resource
    private AccountFeign accountFeign;

    @Resource
    private OrderFeign orderFeign;

    @Override
    public OrderDTO selectByCode(String orderCode) {
        LambdaQueryWrapper<OrderVO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrderVO::getOrderNo,orderCode);
        OrderVO orderVO = orderDao.selectOne(wrapper);

        BeanCopier copier = BeanCopier.create(OrderVO.class,OrderDTO.class,false);
        OrderDTO orderDTO = new OrderDTO();
        copier.copy(orderVO,orderDTO,null);
        return orderDTO;
    }

    @Override
    public OrderVO updateOrder(OrderDTO orderDTO) {

        BeanCopier copier = BeanCopier.create(OrderDTO.class,OrderVO.class,false);
        OrderVO orderVO = new OrderVO();
        copier.copy(orderDTO,orderVO,null);

        orderDao.updateById(orderVO);
        return orderVO;
    }

    @Override
    public OrderVO insertOrder(OrderDTO orderDTO) {
        BeanCopier copier = BeanCopier.create(OrderDTO.class,OrderVO.class,false);
        OrderVO orderVO = new OrderVO();
        copier.copy(orderDTO,orderVO,null);

        orderDao.insert(orderVO);
        return orderVO;
    }

    @Override
    public int deleteOrder(String orderCode) {
        LambdaQueryWrapper<OrderVO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrderVO::getOrderNo,orderCode);
        return orderDao.delete(wrapper);
    }

    @GlobalTransactional(name = "TX_ORDER_CREATE")
    @Override
    public void createOrder(OrderDTO orderDTO) {
        String xid = RootContext.getXID();
        System.out.println(xid+"-----------------------------");
        //本地存储Order
//        orderFeign.insert(orderDTO);
        this.insertOrder(orderDTO);
        //库存扣减
        productFeign.deduct(orderDTO.getProductCode(),orderDTO.getCount());
        //账户余额扣减
        accountFeign.reduce(orderDTO.getAccountCode(), orderDTO.getAmount());

//        int i = 1/0;
    }
}
