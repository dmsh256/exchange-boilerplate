package core.MatchingEngine;

import core.Order.Order;
import core.Order.OrderType;
import core.OrderBook.OrderBook;
import core.OrderPool.SPSCOrderPool;
import core.RingBuffer.RingBuffer;

import java.util.concurrent.atomic.AtomicLong;

public class MatchingEngine implements Runnable {

    private final OrderBook orderBook;
    public final RingBuffer<Order> ringBuffer;
    public final SPSCOrderPool orderPool;

    private volatile boolean running = true;

    private final AtomicLong processedCount = new AtomicLong();

    public MatchingEngine(OrderBook orderBook, RingBuffer<Order> ringBuffer, SPSCOrderPool orderPool) {
        this.orderBook = orderBook;
        this.ringBuffer = ringBuffer;
        this.orderPool = orderPool;
    }

    @Override
    public void run() {
        while (running) {
            Order order = ringBuffer.poll();
            if (order == null) {
                Thread.yield();

                continue;
            }

            processOrder(order);
        }
    }

    public void processOrder(Order order) {
         switch (order.type) {
            case BUY_LIMIT -> orderBook.placeBuyLimit(order);
            case SELL_LIMIT -> orderBook.placeSellLimit(order);
            case BUY_MARKET -> orderBook.placeBuyMarket(order);
            case SELL_MARKET -> orderBook.placeSellMarket(order);
        }

        if (order.type == OrderType.BUY_LIMIT || order.type == OrderType.SELL_LIMIT) {
            if (order.quantity == 0)
                orderPool.release(order); //release only filled LIMIT orders
        } else {
            orderPool.release(order);
        }

        processedCount.incrementAndGet();
    }

    /**
     * For benchmark, todo remove later
     */
    public void matchOne() {
        Order order = ringBuffer.poll();
        if (order == null)
            return;

        switch (order.type) {
            case BUY_LIMIT -> orderBook.placeBuyLimit(order);
            case SELL_LIMIT -> orderBook.placeSellLimit(order);
            case BUY_MARKET -> orderBook.placeBuyMarket(order);
            case SELL_MARKET -> orderBook.placeSellMarket(order);
        }

        if (order.type == OrderType.BUY_LIMIT || order.type == OrderType.SELL_LIMIT) {
            if (order.quantity == 0)
                orderPool.release(order);
        } else {
            orderPool.release(order);
        }
    }

    public void addToRingBuffer(Order order) {
        this.ringBuffer.offer(order);
    }

    public void stop() {
        running = false;
    }

    public long getProcessedCount() {
        return processedCount.get();
    }
}
