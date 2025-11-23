package com.example.womenshopfx;

public abstract class Product implements Discount, Comparable<Product>{
    //Attributes ---------------------------------------------
    private int number;
    private String name;
    private double purchasePrice;
    private double sellPrice;
    private double discountPrice=0;
    private int nbItems=0;

    private static int counter = 0; //pour l auto incrementation
    private static double capital=30000;
    private static double income = 0;
    private static double cost = 0;

    //Constructor ----------------------------------------------
    public Product(String name, double purchasePrice, double sellPrice) {
        counter++;
        this.number = counter;
        this.name = name;

        if (purchasePrice < 0 || sellPrice < 0) {
            throw new IllegalArgumentException("Negative price!");
        }

        this.purchasePrice = purchasePrice;
        this.sellPrice = sellPrice;
    }
    //Constructor 2----------------------------------------------
    public Product(int number,String name, double purchasePrice, double sellPrice, double discountPrice, int nbItems) {
        //counter++;
        this.number = number;
        this.name = name;

        if (purchasePrice < 0 || sellPrice < 0) {
            throw new IllegalArgumentException("Negative price!");
        }

        this.purchasePrice = purchasePrice;
        this.sellPrice = sellPrice;
        this.discountPrice = discountPrice;
        this.nbItems = nbItems;
    }


    //Getters ----------------------------------------------
    public int getNumber() { return number; }
    public String getName() { return name; }
    public double getPurchasePrice() { return purchasePrice; }
    public double getSellPrice() { return sellPrice; }
    public double getDiscountPrice() { return discountPrice; }
    public int getNbItems() { return nbItems; }
    public static void setNumber(int number) {}

    public static double getCapital() { return capital; }
    public static double getIncome() { return income; }
    public static double getCost() { return cost; }

    //Setters ----------------------------------------------
    //on modifie pas number
    public void setName(String name) { this.name = name; }
    public void setPurchasePrice(double purchasePrice) { this.purchasePrice = purchasePrice; }
    public void setSellPrice(double sellPrice) { this.sellPrice = sellPrice; }
    public void setDiscountPrice(double discountPrice) { this.discountPrice = discountPrice; }
    public void setNbItems(int nbItems) { this.nbItems = nbItems; }

    public static void setCapital(double c) { capital = c; }
    public static void setIncome(double i) { income = i; }
    public static void setCost(double c) { cost = c; }


    //Methods ----------------------------------------------
    @Override
    public String toString() {
        return "Product{" +
                "number=" + number +
                ", name='" + name + '\'' +
                ", purchasePrice=" + purchasePrice +
                ", sellPrice=" + sellPrice +
                ", discountPrice=" + discountPrice +
                ", nbItems=" + nbItems +
                ", capital=" + capital +
                ", income=" + income +
                ", cost=" + cost +
                '}';
    }
    public void sell(int nbItems) {
        try {
            if (nbItems <= 0) throw new IllegalArgumentException("Negative number of items!");
            if (nbItems > this.nbItems) throw new IllegalArgumentException("Product Unavailable");
            this.nbItems -= nbItems;
            income += nbItems * this.sellPrice;
            capital += nbItems * this.sellPrice;

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void sellWithDiscount(int nbItems) {
        try {
            if (nbItems <= 0) throw new IllegalArgumentException("Negative number of items!");
            if (nbItems > this.nbItems) throw new IllegalArgumentException("Product Unavailable");
            this.nbItems -= nbItems;
            income += nbItems * this.discountPrice;
            capital += nbItems * this.discountPrice;

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void purchase(int nbItems) {
        try {
            if (nbItems <= 0) throw new IllegalArgumentException("Negative number of items!");
            double totalCost = this.purchasePrice*nbItems;
            if (totalCost > capital) throw new IllegalArgumentException("Product Unavailable");
            this.nbItems += nbItems;
            cost += totalCost;
            capital -= totalCost; //?
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public int compareTo(Product p) {
        return Double.compare(this.sellPrice, p.sellPrice);
    }



}
