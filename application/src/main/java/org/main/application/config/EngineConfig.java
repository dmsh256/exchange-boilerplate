package org.main.application.config;

import core.MatchingEngine.MatchingEngine;
import core.Order.Order;
import core.OrderBook.ArrayBucketOrderBook;
import core.OrderPool.SPSCOrderPool;
import core.RingBuffer.SPSCRingBuffer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EngineConfig {

    private static final int RING_SIZE = 131072;
    private static final int PRELOAD_ORDERS = 2_000_000;

    @Bean
    public MatchingEngine matchingEngine() {
        return new MatchingEngine(
                new ArrayBucketOrderBook(
                        65000.0, 65500.0, 0.01,
                        _ -> {},
                        Order::reset
                ),
                new SPSCRingBuffer<>(RING_SIZE),
                new SPSCOrderPool(PRELOAD_ORDERS)
        );
    } // TODO make configurable for different markets
}

