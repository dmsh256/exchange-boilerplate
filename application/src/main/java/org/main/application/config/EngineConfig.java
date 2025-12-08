package org.main.application.config;

import core.MatchingEngine.MatchingEngine;
import core.Order.Order;
import core.OrderBook.ArrayBucketOrderBook;
import core.OrderPool.SPSCOrderPool;
import core.RingBuffer.RingBuffer;
import core.RingBuffer.SPSCRingBuffer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EngineConfig {

    private static final int RING_SIZE = 131072;
    private static final int PRELOAD_ORDERS = 2_000_000;

    @Bean
    public RingBuffer<Order> ringBuffer() {
        return new SPSCRingBuffer<>(RING_SIZE);
    }

    @Bean
    public MatchingEngine matchingEngine() {
        MatchingEngine matchingEngine = new MatchingEngine(
                new ArrayBucketOrderBook(
                        65000.0, 75500.0, 0.01,
                        _ -> {},
                        Order::reset
                ),
                ringBuffer(),
                new SPSCOrderPool(PRELOAD_ORDERS)
        );

        Thread engineThread = new Thread(matchingEngine, "matching-engine-thread");
        engineThread.start();

        System.out.println("Matching Engine started on thread: " + engineThread.getName());

        return matchingEngine;
    } // TODO make configurable for different markets
}

