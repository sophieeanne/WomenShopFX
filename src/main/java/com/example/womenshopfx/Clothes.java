package com.example.womenshopfx;

public class Clothes extends Product {
    //Atributes
    private int size;

    //Constructors
    public Clothes(String name, double purchasePrice, double sellPrice, int size) {
        super(name, purchasePrice, sellPrice);
        try {
            setSize(size);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            this.size = 34; //Valeur défaut
        }
    }
    public Clothes(int number,String name, double purchasePrice, double sellPrice, double discountPrice, int nbItems, int size) {
        super(number,name, purchasePrice, sellPrice, discountPrice,nbItems );
        try {
            setSize(size);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            this.size = 34; //Valeur défaut
        }
    }

    //Getter et setter
    public int getSize() { return this.size; }

    public void setSize(int s) {
        if (s < 34 || s > 54) {
            throw new IllegalArgumentException("Wrong clothe size ! The size is set to the default value of 34.");
        }
        this.size = s;
    }

    //Méthodes
    @Override
    public String toString() {
        return super.toString() + ", size=" + size;
    }

    @Override
    public void applyDiscount() {
        setDiscountPrice((1-CLOTHES_DISCOUNT) * this.getSellPrice());
    }
    @Override
    public void unApplyDiscount() {
        setDiscountPrice(0);
    }



}

