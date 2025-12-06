package org.main.application.controller.api;

import org.main.application.dto.order.LimitOrderRequest;
import org.main.application.service.order.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderPostController {
    private final OrderService orderService;

    public OrderPostController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/postLimit")
    public void placeLimit(@RequestBody LimitOrderRequest req) {
        // TODO add necessary checks

        orderService.placeBuyLimit(req);

        System.out.println("Order placed."); // tmp
    }
}
