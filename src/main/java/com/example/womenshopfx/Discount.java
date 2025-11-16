package com.example.womenshopfx;

public interface Discount {
    //constant
    double CLOTHES_DISCOUNT = 0.3;
    double SHOES_DISCOUNT = 0.2;
    double ACCESSORY_DISCOUNT = 0.5;

    public void applyDiscount();
    public void unApplyDiscount();
}
