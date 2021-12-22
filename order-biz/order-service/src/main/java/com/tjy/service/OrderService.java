package com.tjy.service;

import com.tjy.dto.OrderDTO;
import com.tjy.entity.OrderVO;

/**
 * @Description
 * @Author tjy
 * @Date 2021/11/12 15:42
 * @Version 1.0
 */
public interface OrderService {

    OrderDTO selectByCode(String orderCode);

    OrderVO updateOrder(OrderDTO orderDTO);

    OrderVO insertOrder(OrderDTO orderDTO);

    int deleteOrder(String orderCode);

    void createOrder(OrderDTO orderDTO);
}
