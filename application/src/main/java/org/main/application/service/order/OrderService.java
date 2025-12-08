package org.main.application.service.order;

import core.Order.Order;
import core.Order.OrderType;
import core.RingBuffer.RingBuffer;
import org.main.application.dto.order.LimitOrderRequest;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final RingBuffer<Order> ringBuffer;

    public OrderService(RingBuffer<Order> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void placeBuyLimit(LimitOrderRequest req) {
        Order order = new Order();
        order.type = OrderType.BUY_LIMIT;
        order.quantity = req.quantity;
        order.price = req.price;

        ringBuffer.offer(order);
    }
}
