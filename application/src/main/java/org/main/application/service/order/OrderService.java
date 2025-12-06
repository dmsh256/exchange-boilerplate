package org.main.application.service.order;

import core.MatchingEngine.MatchingEngine;
import core.Order.Order;
import core.Order.OrderType;
import org.main.application.dto.order.LimitOrderRequest;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final MatchingEngine engine;

    public OrderService(MatchingEngine engine) {
        this.engine = engine;
    }

    public void placeBuyLimit(LimitOrderRequest req) {
        Order order = new Order();
        order.type = OrderType.BUY_LIMIT;
        order.quantity = req.quantity;
        order.price = req.price;


        engine.addToRingBuffer(order);
    }
}
