package com.example.womenshopfx;

public class Accessories extends Product {
    //No additional attribute

    //Constructor
    public Accessories(String name, double purchasePrice, double sellPrice) {
        super(name, purchasePrice, sellPrice);
    }

    //Method
    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public void applyDiscount() {
        setDiscountPrice((1-ACCESSORY_DISCOUNT) * this.getSellPrice());
    }
    @Override
    public void unApplyDiscount() {
        setDiscountPrice(0);
    }
}
