package com.example.womenshopfx;

public class Shoes extends Product {

    //Attribute
    private int shoeSize;

    //Constructor
    public Shoes(String name, double purchasePrice, double sellPrice, int shoeSize) {
        super(name, purchasePrice, sellPrice);
        try {
            setShoeSize(shoeSize);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            this.shoeSize = 36; //valeur par défaut
        }
    }

    //Getter and Setter
    public int getShoeSize() {
        return shoeSize;
    }

    public void setShoeSize(int shoeSize) {
        if (shoeSize < 36 || shoeSize > 50) {
            throw new IllegalArgumentException("Wrong shoe size!");
        }
        this.shoeSize = shoeSize;
    }

    @Override
    public String toString() {
        return super.toString() + ", shoeSize=" + shoeSize;
    }

    @Override
    public void applyDiscount() {
        setDiscountPrice((1-SHOES_DISCOUNT) * this.getSellPrice());
    }
    @Override
    public void unApplyDiscount() {
        setDiscountPrice(0);
    }
}
