package com.example.womenshopfx;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//import static jdk.internal.org.jline.utils.Colors.s;

public class DBManager {

    public List<Clothes> loadClothes(){
        List<Clothes> clothesAll = new ArrayList<Clothes>();
        Connection myConn = this.Connector();

        try {
            Statement myStmt = myConn.createStatement();
            String sql = "SELECT p.number, p.name, p.purchasePrice, p.sellPrice, p.discountPrice, p.nbItems, c.size " +
                    "FROM Product p " +
                    "JOIN Clothes c ON p.number = c.product_id";

            ResultSet myRs = myStmt.executeQuery(sql);

            while (myRs.next()) {

                int number = myRs.getInt("number");
                String name = myRs.getString("name");
                double purchase = myRs.getDouble("purchasePrice");
                double sell = myRs.getDouble("sellPrice");
                double  discount = myRs.getDouble("discountPrice");
                int nbItems = myRs.getInt("nbItems");
                int size = myRs.getInt("size");

                Clothes c = new Clothes(number,name,purchase,sell,discount,nbItems,size);
                clothesAll.add(c);
            }
            this.close(myConn,myStmt,myRs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clothesAll;
    }


    public List<Shoes> loadShoes(){
        List<Shoes> shoesAll = new ArrayList<Shoes>();
        Connection myConn = this.Connector();

        try {
            Statement myStmt = myConn.createStatement();
            String sql = "SELECT p.number, p.name, p.purchasePrice, p.sellPrice, p.discountPrice, p.nbItems, s.shoeSize " +
                    "FROM Product p " +
                    "JOIN Shoes s ON p.number = s.product_id";

            ResultSet myRs = myStmt.executeQuery(sql);

            while (myRs.next()) {

                int number = myRs.getInt("number");
                String name = myRs.getString("name");
                double purchase = myRs.getDouble("purchasePrice");
                double sell = myRs.getDouble("sellPrice");
                double  discount = myRs.getDouble("discountPrice");
                int nbItems = myRs.getInt("nbItems");
                int shoeSize = myRs.getInt("shoeSize");

                Shoes s = new Shoes(number,name,purchase,sell,discount,nbItems,shoeSize);
                shoesAll.add(s);
            }
            this.close(myConn,myStmt,myRs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shoesAll;
    }

    public List<Accessories> loadAccessories(){
        List<Accessories> accessoriesAll = new ArrayList<Accessories>();
        Connection myConn = this.Connector();

        try {
            Statement myStmt = myConn.createStatement();
            String sql = "SELECT p.number, p.name, p.purchasePrice, p.sellPrice, p.discountPrice, p.nbItems " +
                    "FROM Product p " +
                    "JOIN Accessories a ON p.number = a.product_id";

            ResultSet myRs = myStmt.executeQuery(sql);

            while (myRs.next()) {

                int number = myRs.getInt("number");
                String name = myRs.getString("name");
                double purchase = myRs.getDouble("purchasePrice");
                double sell = myRs.getDouble("sellPrice");
                double  discount = myRs.getDouble("discountPrice");
                int nbItems = myRs.getInt("nbItems");

                Accessories a = new Accessories(number,name,purchase,sell,discount,nbItems);
                accessoriesAll.add(a);
            }
            this.close(myConn,myStmt,myRs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accessoriesAll;
    }




    public Connection Connector(){
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/womenshop? serverTimezone=Europe%2FParis", "root","root");
            System.out.println("Connexion OK !");
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERREUR CONNEXION : " + e.getMessage());
            return null;
        }
    }

    public void close(Connection myConn, Statement myStmt, ResultSet myRs){
        try {
            if (myStmt != null) {
                myStmt.close();
            }
            if (myRs != null) {
                myRs.close();
            }
            if(myConn != null) {
                myConn.close();
            }
        } catch(Exception e) {
            System.out.println(e.getMessage());

        }
    }
}
