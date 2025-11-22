package com.example.womenshopfx;

public class Accessories extends Product {
    //No additional attribute

    //Constructors
    public Accessories(String name, double purchasePrice, double sellPrice) {
        super(name, purchasePrice, sellPrice);
    }
    public Accessories(int number,String name, double purchasePrice, double sellPrice, double discountPrice,int nbItems) {
        super(number,name, purchasePrice, sellPrice, discountPrice, nbItems);
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
