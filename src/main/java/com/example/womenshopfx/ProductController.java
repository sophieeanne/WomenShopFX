package com.example.womenshopfx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductController implements Initializable {

    @FXML
    private Button accessBtn;

    @FXML
    private Button addProdbtn;

    @FXML
    private Button allProdBtn;

    @FXML
    private Button applyBtn;

    @FXML
    private Label capitalLabel;

    @FXML
    private TableColumn<Product, String> catCol;

    @FXML
    private ComboBox<String> catComboBox;

    @FXML
    private Button clothesBtn;

    @FXML
    private Label costLabel;

    @FXML
    private Button deleteBtn;

    @FXML
    private TableColumn<Product, Double> discCol;

    @FXML
    private Button editBtn;

    @FXML
    private TableColumn<Product, Integer> idCol;

    @FXML
    private Label incomeLabel;

    @FXML
    private TableColumn<Product, String> nameCol;

    @FXML
    private TableView<Product> prodTable;

    @FXML
    private Button purBtn;

    @FXML
    private TableColumn<Product, Double> purCol;

    @FXML
    private Button remBtn;

    @FXML
    private TextField searchTxtField;

    @FXML
    private Button sellBtn;

    @FXML
    private TableColumn<Product, Double> sellCol;

    @FXML
    private Button shoesBtn;

    @FXML
    private ComboBox<String> sortComboBox;

    @FXML
    private TableColumn<Product, Integer> stockCol;

    @FXML
    private Label stockLabel;

    @FXML
    private Label womenShopLabel;

    // Sample data lists
    private ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private ObservableList<Clothes> clothesProducts = FXCollections.observableArrayList();
    private ObservableList<Shoes> shoesProducts = FXCollections.observableArrayList();
    private ObservableList<Accessories> accessoriesProducts = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTableColumns();
        loadSampleData();
        //setupCategoryButtons();
        //updateStatistics();
    }

    private void setupTableColumns() {
        // Configure each column to use the appropriate getter method from Product class
        idCol.setCellValueFactory(new PropertyValueFactory<>("number"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        purCol.setCellValueFactory(new PropertyValueFactory<>("purchasePrice"));
        sellCol.setCellValueFactory(new PropertyValueFactory<>("sellPrice"));
        discCol.setCellValueFactory(new PropertyValueFactory<>("discountPrice"));
        stockCol.setCellValueFactory(new PropertyValueFactory<>("nbItems"));

        // Custom category column - detects the actual type of product
        catCol.setCellValueFactory(cellData -> {Product product = cellData.getValue();
            if (product instanceof Clothes) {
                return new javafx.beans.property.SimpleStringProperty("Clothes");
            } else if (product instanceof Shoes) {
                return new javafx.beans.property.SimpleStringProperty("Shoes");
            } else if (product instanceof Accessories) {
                return new javafx.beans.property.SimpleStringProperty("Accessories");
            }
            return new javafx.beans.property.SimpleStringProperty("Unknown");
        });

        // Format price columns to show currency
        purCol.setCellFactory(column -> new javafx.scene.control.TableCell<Product, Double>() {
            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if (empty || price == null) {
                    setText(null);
                } else {
                    setText(String.format("$%.2f", price));
                }
            }
        });

        sellCol.setCellFactory(column -> new javafx.scene.control.TableCell<Product, Double>() {
            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if (empty || price == null) {
                    setText(null);
                } else {
                    setText(String.format("$%.2f", price));
                }
            }
        });

        discCol.setCellFactory(column -> new javafx.scene.control.TableCell<Product, Double>() {
            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if (empty || price == null) {
                    setText(null);
                } else {
                    setText(String.format("$%.2f", price));
                }
            }
        });

    }

    private void loadSampleData() {
        // Clear existing data
        allProducts.clear();
        clothesProducts.clear();
        shoesProducts.clear();
        accessoriesProducts.clear();

        // Create sample Clothes
        Clothes dress = new Clothes("Summer Dress", 25.0, 50.0, 1, 38);
        dress.setNbItems(10);
        Clothes shirt = new Clothes("Cotton Shirt", 15.0, 35.0, 2, 40);
        shirt.setNbItems(15);
        Clothes jeans = new Clothes("Blue Jeans", 20.0, 45.0, 3, 42);
        jeans.setNbItems(8);

        // Create sample Shoes
        Shoes sneakers = new Shoes("Running Sneakers", 30.0, 70.0, 42);
        sneakers.setNbItems(8);
        Shoes heels = new Shoes("High Heels", 40.0, 90.0, 38);
        heels.setNbItems(5);
        Shoes boots = new Shoes("Winter Boots", 35.0, 80.0, 40);
        boots.setNbItems(12);

        // Create sample Accessories
        Accessories necklace = new Accessories("Silver Necklace", 20.0, 45.0);
        necklace.setNbItems(20);
        Accessories handbag = new Accessories("Leather Handbag", 35.0, 80.0);
        handbag.setNbItems(12);
        Accessories bracelet = new Accessories("Gold Bracelet", 15.0, 35.0);
        bracelet.setNbItems(25);

        // Add to category-specific lists
        clothesProducts.addAll(dress, shirt, jeans);
        shoesProducts.addAll(sneakers, heels, boots);
        accessoriesProducts.addAll(necklace, handbag, bracelet);

        // Add all to the main list
        allProducts.addAll(clothesProducts);
        allProducts.addAll(shoesProducts);
        allProducts.addAll(accessoriesProducts);

        // Display all products by default
        prodTable.setItems(allProducts);
    }



}