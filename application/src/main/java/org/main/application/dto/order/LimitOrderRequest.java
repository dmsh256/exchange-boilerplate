package org.main.application.dto.order;

public class LimitOrderRequest {
    public double price;
    public long quantity;
    public String type;

    @Override
    public String toString() {
        return price + "," + quantity + "," + type;
    }
}