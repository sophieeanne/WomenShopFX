package com.example.womenshopfx;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

//    public void addProduct(Product product){
//        Connection myConn=null;
//        PreparedStatement myStmt = null;
//        ResultSet myRs= null;
//        try {
//            myConn = this.Connector();
//            String sql = "INSERT INTO Product (name, purchasePrice, sellPrice) VALUES (?, ?, ?)";;
//            myStmt = myConn.prepareStatement(sql);
//            myStmt.setString(1, student.getName());
//            myStmt.setString(2, student.getGender());
//            myStmt.execute();
//            System.out.println("test1");
//        }
//        catch(Exception e){
//            System.out.println(e.getMessage());
//        }
//        finally{
//            close(myConn,myStmt,myRs);
//        }
//    }


//    public void addProduct(Product p) {
//
//        Connection myConn=null;
//        PreparedStatement myStmt = null;
//        ResultSet myRs= null;
//
//        try {
//            myConn = this.Connector();
//            String sql = "INSERT INTO Product (name, purchasePrice, sellPrice) VALUES (?, ?, ?)";
//            myStmt = myConn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//            myStmt.setString(1, p.getName());
//            myStmt.setDouble(2, p.getPurchasePrice());
//            myStmt.setDouble(3, p.getSellPrice());
//            myStmt.execute();
//            System.out.println("Insertion OK dans Product");
//
//            //retrieve the generated product ID
//            myRs = myStmt.getGeneratedKeys();
//            if (myRs.next()) {
//                int generatedId = myRs.getInt(1);
//                p.setNumber(generatedId); // si tu veux stocker l'ID dans l'objet Java
//            }
//            myRs.close();
//            myStmt.close();
//            int productId = p.getNumber();
//            System.out.println(productId);
//
//            // Insert into specific table
//            if (p instanceof Clothes) {
//                Clothes c = (Clothes) p;
//                String sql1 = "INSERT INTO Clothes (product_id, size) VALUES (?, ?)";
//                try (PreparedStatement st = myConn.prepareStatement(sql1)) {
//                    st.setInt(1, productId);
//                    st.setInt(2, c.getSize());
//                    st.executeUpdate();
//                }
//
//            } else if (p instanceof Shoes) {
//                Shoes s = (Shoes) p;
//                String sql2 = "INSERT INTO Shoes (product_id, shoeSize) VALUES (?, ?)";
//                try (PreparedStatement st = myConn.prepareStatement(sql2)) {
//                    st.setInt(1, productId);
//                    st.setInt(2, s.getShoeSize());
//                    st.executeUpdate();
//                }
//
//            } else if (p instanceof Accessories) {
//                String sql3 = "INSERT INTO Accessories (product_id) VALUES (?)";
//                try (PreparedStatement st = myConn.prepareStatement(sql3)) {
//                    st.setInt(1, productId);
//                    st.executeUpdate();
//
//                }
//            }
//
//            System.out.println("SUCCESS: Product added to database.");
//
//        } catch (Exception e) {
//            System.err.println("ERROR (addProduct): " + e.getMessage());
//            e.printStackTrace();
//        } finally{
//            close(myConn,myStmt,myRs);
//        }
//    }

    public void addProduct(Product p) {
        Connection myConn = null;
        PreparedStatement myStmt = null;
        ResultSet myRs = null;

        try {
            myConn = this.Connector();

            // insertion in Product
            String sql = "INSERT INTO Product (name, purchasePrice, sellPrice, discountPrice, nbItems) VALUES (?, ?, ?, ?, ?)";
            myStmt = myConn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            myStmt.setString(1, p.getName());
            myStmt.setDouble(2, p.getPurchasePrice());
            myStmt.setDouble(3, p.getSellPrice());
            myStmt.setDouble(4, p.getDiscountPrice());
            myStmt.setInt(5, p.getNbItems());

            //Nb of affected lines (1)
            int affectedRows = myStmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Insertion failed, no rows affected.");
            }

            //take the id
            myRs = myStmt.getGeneratedKeys();
            int generatedId;
            if (myRs.next()) {
                generatedId = myRs.getInt(1);
            } else {
                throw new SQLException("Insertion failed, no ID obtained.");
            }

            // insert in the specific table : clothes, accessories or shoes
            if (p instanceof Clothes) {
                Clothes c = (Clothes) p;
                String sqlClothes = "INSERT INTO Clothes (product_id, size) VALUES (?, ?)";
                try (PreparedStatement st = myConn.prepareStatement(sqlClothes)) {
                    st.setInt(1, generatedId);
                    st.setInt(2, c.getSize());
                    st.executeUpdate();
                }
            } else if (p instanceof Shoes) {
                Shoes s = (Shoes) p;
                String sqlShoes = "INSERT INTO Shoes (product_id, shoeSize) VALUES (?, ?)";
                try (PreparedStatement st = myConn.prepareStatement(sqlShoes)) {
                    st.setInt(1, generatedId);
                    st.setInt(2, s.getShoeSize());
                    st.executeUpdate();
                }
            } else if (p instanceof Accessories) {
                String sqlAcc = "INSERT INTO Accessories (product_id) VALUES (?)";
                try (PreparedStatement st = myConn.prepareStatement(sqlAcc)) {
                    st.setInt(1, generatedId);
                    st.executeUpdate();
                }
            }

            System.out.println("SUCCESS: Product added to database.");

        } catch (Exception e) {
            System.err.println("ERROR (addProduct): " + e.getMessage());
            e.printStackTrace();
        } finally {
            close(myConn, myStmt, myRs);
        }
    }

    public void deleteProduct(int productId) {
        Connection myConn = null;
        PreparedStatement myStmt = null;
        ResultSet myRs = null;

        try {
            myConn = this.Connector();

            // insertion in Product
            String sql = "DELETE FROM Product WHERE number = ?";
            myStmt = myConn.prepareStatement(sql);
            myStmt.setInt(1, productId);
            int affectedRows = myStmt.executeUpdate();

            if (affectedRows == 0) {
                System.err.println("No product deleted, ID not found in the database : " + productId);
            } else {
                System.out.println("Product deleted successfully in the database : " + productId);
            }

        } catch (Exception e) {
            System.err.println("ERROR (deleteProduct): " + e.getMessage());
            e.printStackTrace();
        } finally {
            close(myConn, myStmt, myRs);
        }
    }

}
