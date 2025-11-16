package com.example.womenshopfx;

public class Clothes extends Product {
    //Atributes
    private int size;

    //Constructor
    public Clothes(String name, double purchasePrice, double sellPrice,int number, int size) {
        super(name, purchasePrice, sellPrice);
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
            throw new IllegalArgumentException("Wrong clothe size!");
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

