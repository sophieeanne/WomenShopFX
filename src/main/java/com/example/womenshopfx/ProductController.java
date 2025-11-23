package com.example.womenshopfx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.Comparator;
import java.util.List;
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
    private TextField capitalTextField;

    @FXML
    private TableColumn<Product, String> catCol;

    @FXML
    private ComboBox<String> catComboBox;

    @FXML
    private Label catLabel;

    @FXML
    private TextField catTextField;

    @FXML
    private Label costLabel;

    @FXML
    private TextField costTextField;

    @FXML
    private Button deleteBtn;

    @FXML
    private TableColumn<Product, Double> discCol;

    @FXML
    private Label discPriceLabel;
    @FXML
    private TextField discPriceTextField;

    @FXML
    private Button editBtn;

    @FXML
    private TableColumn<Product, Integer> idCol;

    @FXML
    private Label incomeLabel;

    @FXML
    private TextField incomeTextField;

    @FXML
    private TableColumn<Product, String> nameCol;


    @FXML
    private TextField nameTextField;

    @FXML
    private TableView<Product> prodTable;

    @FXML
    private Button purBtn;

    @FXML
    private TableColumn<Product, Double> purCol;

    @FXML
    private Label purPriceLabel;
    @FXML
    private TextField purPriceTextField;

    @FXML
    private Button remBtn;

    @FXML
    private Button sellBtn;

    @FXML
    private TableColumn<Product, Double> sellCol;

    @FXML
    private Button shoesBtn;

    @FXML
    private Label sellPriceLabel;
    @FXML
    private TextField sellPriceTextField;

    @FXML
    private ComboBox<String> sortComboBox;

    @FXML
    private TableColumn<Product, Integer> stockCol;
    @FXML
    private TableColumn<Product, Object> sizeCol;

    @FXML
    private Label stockLabel;

    @FXML
    private TextField stockTextField;

    @FXML
    private Label womenShopLabel;

    @FXML
    private TextField sizeTextField;

    @FXML
    private TextField sellTextField;

    @FXML
    private TextField purchaseTextField;

    DBManager manager;

    // Sample data lists
    private ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private ObservableList<Clothes> clothesProducts = FXCollections.observableArrayList();
    private ObservableList<Shoes> shoesProducts = FXCollections.observableArrayList();
    private ObservableList<Accessories> accessoriesProducts = FXCollections.observableArrayList();

    private Product currentEditingProduct;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupTableColumns();
        //loadSampleData();
        manager = new DBManager();
        fetchProduct();
        setupCategoryFilter();
        setupSortComboBox();
        applyFilterAndSort();
        //handleAddProduct();
        setupActionButtons();
        setupTableSelectionListener();
        displayStatistics();
        setEditableFalse();
    }

    private void setEditableFalse() {
        discPriceTextField.setEditable(false);
        stockTextField.setEditable(false);

        incomeTextField.setEditable(false);
        capitalTextField.setEditable(false);
        costTextField.setEditable(false);
    }
    private void setupTableSelectionListener() {
        // Listen for table selection changes
        prodTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        // Auto-fill the text fields with selected product data
                        fillInputFieldsWithProduct(newValue);
                    } else {
                        // Clear fields when no product is selected
                        clearInputFields();
                    }
                }
        );
    }

    private void fillInputFieldsWithProduct(Product product) {
        try {
            nameTextField.setText(product.getName());
            catTextField.setText(getCategoryName(product));
            purPriceTextField.setText(String.format("%.2f", product.getPurchasePrice()));
            sellPriceTextField.setText(String.format("%.2f", product.getSellPrice()));
            discPriceTextField.setText(String.format("%.2f", product.getDiscountPrice()));
            stockTextField.setText(String.valueOf(product.getNbItems()));

            if (product instanceof Clothes) {
                Clothes clothes = (Clothes) product;
                sizeTextField.setText(String.valueOf(clothes.getSize()));
            } else if (product instanceof Shoes) {
                Shoes shoes = (Shoes) product;
                sizeTextField.setText(String.valueOf(shoes.getShoeSize()));
            }
            else{
                sizeTextField.setText("");
            }

        } catch (Exception e) {
            showError("Error loading product data: " + e.getMessage());
        }
    }
    private void setupActionButtons() {
        // Configure button actions
        addProdbtn.setOnAction(e -> handleAddProduct());
        editBtn.setOnAction(e -> handleEditProduct());
        deleteBtn.setOnAction(e -> handleDeleteProduct());
        sellBtn.setOnAction(e -> handleSellProduct());
        purBtn.setOnAction(e -> handlePurchaseProduct());
        applyBtn.setOnAction(e -> handleApplyDiscount());
        remBtn.setOnAction(e -> handleStopDiscount());

        // Disable edit/delete/sell/purchase until a product is selected
        sellBtn.setDisable(true);
        purBtn.setDisable(true);
        editBtn.setDisable(true);
        deleteBtn.setDisable(true);

        // Listen for table selection changes
        prodTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    boolean productSelected = newValue != null;
                    editBtn.setDisable(!productSelected);
                    deleteBtn.setDisable(!productSelected);
                    sellBtn.setDisable(!productSelected);
                    purBtn.setDisable(!productSelected);
                    applyBtn.setDisable(!productSelected);
                    remBtn.setDisable(!productSelected);
                }
        );
    }

    private void setupTableColumns() {
        // Configure each column to use the appropriate getter method from Product class
        idCol.setCellValueFactory(new PropertyValueFactory<>("number"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        purCol.setCellValueFactory(new PropertyValueFactory<>("purchasePrice"));
        sellCol.setCellValueFactory(new PropertyValueFactory<>("sellPrice"));
        discCol.setCellValueFactory(new PropertyValueFactory<>("discountPrice"));
        stockCol.setCellValueFactory(new PropertyValueFactory<>("nbItems"));

        sizeCol.setCellValueFactory(cellData -> {
            Product product = cellData.getValue();
            if (product instanceof Clothes) {
                return new javafx.beans.property.SimpleObjectProperty<>(((Clothes) product).getSize());
            } else if (product instanceof Shoes) {
                return new javafx.beans.property.SimpleObjectProperty(((Shoes) product).getShoeSize());
            } else {
                return new javafx.beans.property.SimpleObjectProperty<>("N/A");
            }
        });
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
                    setText(String.format("%.2f €", price));
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
                    setText(String.format("%.2f €", price));
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
                    setText(String.format("%.2f €", price));
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
        Clothes dress = new Clothes("Summer Dress", 25.0, 50.0, 38);
        dress.setNbItems(10);
        Clothes shirt = new Clothes("Cotton Shirt", 15.0, 35.0, 40);
        shirt.setNbItems(15);
        Clothes jeans = new Clothes("Blue Jeans", 20.0, 45.0,  42);
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

    //Function to fetch products of the DB
    public void fetchProduct() {
        // Clear existing data
        allProducts.clear();
        clothesProducts.clear();
        shoesProducts.clear();
        accessoriesProducts.clear();

        List<Clothes> listClothes = manager.loadClothes();
        List<Shoes> listShoes = manager.loadShoes();
        List<Accessories> listAccessories = manager.loadAccessories();

        if (clothesProducts!=null) {
            clothesProducts.addAll(listClothes);
        }
        if (shoesProducts!=null) {
            shoesProducts.addAll(listShoes);
        }
        if (accessoriesProducts!=null) {
            accessoriesProducts.addAll(listAccessories);
        }

        // Add all to the main list
        allProducts.addAll(clothesProducts);
        allProducts.addAll(shoesProducts);
        allProducts.addAll(accessoriesProducts);

        // Display all products by default
        prodTable.setItems(allProducts);
    }

    private void setupCategoryFilter() {
        // Fill the Combox with all of the categories
        catComboBox.getItems().addAll("All", "Clothes", "Shoes", "Accessories");
        catComboBox.setValue("All");
        catComboBox.setOnAction(e -> applyFilterAndSort());
    }

    private void setupSortComboBox(){
        // Fill the ComboBox with sort options
        sortComboBox.getItems().addAll(
                "Price (Ascending)",
                "Price (Descending)",
                "Name (A-Z)",
                "Name (Z-A)",
                "Stock (Ascending)",
                "Stock (Descending)"
        );
        sortComboBox.setValue("Price (Ascending)"); // Default value
        sortComboBox.setOnAction(e -> applyFilterAndSort());
    }

    private void applyFilterAndSort() {
        String selectedCategory = catComboBox.getValue();
        String sortOption = sortComboBox.getValue();

        ObservableList<Product> filteredProducts = FXCollections.observableArrayList();
        filteredProducts.addAll(allProducts.filtered(product -> {
            if (selectedCategory == null || "All".equals(selectedCategory)) {
                return true;
            }
            switch (selectedCategory) {
                case "Clothes": return product instanceof Clothes;
                case "Shoes": return product instanceof Shoes;
                case "Accessories": return product instanceof Accessories;
                default: return true;
            }
        }));

        if (sortOption != null) {
            switch(sortOption){
                case "Price (Ascending)":
                    filteredProducts.sort(Comparator.comparing(Product::getSellPrice));
                    break;
                case "Price (Descending)":
                    filteredProducts.sort(Comparator.comparing(Product::getSellPrice).reversed());
                    break;
                case "Name (A-Z)":
                    filteredProducts.sort(Comparator.comparing(Product::getName));
                    break;
                case "Name (Z-A)":
                    filteredProducts.sort(Comparator.comparing(Product::getName).reversed());
                    break;
                case "Stock (Ascending)":
                    filteredProducts.sort(Comparator.comparing(Product::getNbItems));
                    break;
                case "Stock (Descending)":
                    filteredProducts.sort(Comparator.comparing(Product::getNbItems).reversed());
                    break;
            }
        }
        prodTable.setItems(filteredProducts);
    }



    @FXML
    private void handleAddProduct() {
        try {

            // If we're in edit mode, update the existing product
            if (currentEditingProduct != null) {
                handleUpdateProduct();
                return;
            }
            prodTable.getSelectionModel().clearSelection();

            discPriceTextField.setText("0.0");
            stockTextField.setText("0");

            // Get values from TextFields
            String name = nameTextField.getText();
            String category = catTextField.getText();
            double purchasePrice = Double.parseDouble(purPriceTextField.getText());
            double sellPrice = Double.parseDouble(sellPriceTextField.getText());
            double discountPrice = 0.0;
            int stock = 0;

            // Validate data
            if (name.isEmpty()) {
                showError("Name cannot be empty");
                return;
            }

            if (purchasePrice < 0 || sellPrice < 0 || stock < 0) {
                showError("Prices and stock cannot be negative");
                return;
            }

            if(stock!=0){
                showError("Stock should be equal to 0");
                return;
            }
            if(discountPrice!=0){
                showError("Discount prices should be equal to 0");
                return;
            }

            // Create product based on category
            Product newProduct;
            switch (category.toLowerCase()) {
                case "clothes":
                    newProduct = new Clothes(name, purchasePrice, sellPrice, 38); // Default size
                    break;
                case "shoes":
                    newProduct = new Shoes(name, purchasePrice, sellPrice, 40); // Default shoe size
                    break;
                case "accessories":
                    newProduct = new Accessories(name, purchasePrice, sellPrice);
                    break;
                default:
                    showError("Invalid category. Use: Clothes, Shoes or Accessories");
                    return;
            }

            // Set additional properties
            //newProduct.setDiscountPrice(discountPrice);
            //newProduct.setNbItems(stock);

            // Add to the list
            //allProducts.add(newProduct);

            //Add in the database
            manager.addProduct(newProduct);
            //Update table
            fetchProduct();

            // Reapply filters and sorting
            applyFilterAndSort();

            // Clear input fields
            clearInputFields();

            displayStatistics();

            showSuccess("Product added successfully!");

        } catch (NumberFormatException e) {
            showError("Please enter valid numbers for prices and stock");
        } catch (Exception e) {
            showError("Error: " + e.getMessage());
        }
    }

    @FXML
    private void handleEditProduct() {
        Product selectedProduct = prodTable.getSelectionModel().getSelectedItem();

        if (selectedProduct == null) {
            showError("Please select a product to edit");
            return;
        }

        try {
            // Fill fields with selected product data
            nameTextField.setText(selectedProduct.getName());
            catTextField.setText(getCategoryName(selectedProduct));
            purPriceTextField.setText(String.valueOf(selectedProduct.getPurchasePrice()));
            sellPriceTextField.setText(String.valueOf(selectedProduct.getSellPrice()));
            discPriceTextField.setText(String.valueOf(selectedProduct.getDiscountPrice()));
            stockTextField.setText(String.valueOf(selectedProduct.getNbItems()));

            // Store the product being edited
            currentEditingProduct = selectedProduct;

            // Change button text to indicate edit mode
            addProdbtn.setText("Update Product");

            displayStatistics();

        } catch (Exception e) {
            showError("Error loading data: " + e.getMessage());
        }
    }

    @FXML
    private void handleUpdateProduct() {
        if (currentEditingProduct == null) {
            showError("No product being edited");
            return;
        }

        try {
            // Update the product
            currentEditingProduct.setName(nameTextField.getText());
            currentEditingProduct.setPurchasePrice(Double.parseDouble(purPriceTextField.getText()));
            currentEditingProduct.setSellPrice(Double.parseDouble(sellPriceTextField.getText()));
            //Update specfic attribute size
            if (currentEditingProduct instanceof Clothes) {
                Clothes c = (Clothes) currentEditingProduct;
                c.setSize(Integer.parseInt(sizeTextField.getText()));
            }
            else if (currentEditingProduct instanceof Shoes) {
                Shoes s = (Shoes) currentEditingProduct;
                s.setShoeSize(Integer.parseInt(sizeTextField.getText()));
            }
            else if (currentEditingProduct instanceof Accessories) {
                // nothing
            }

            // Refresh the table
            prodTable.refresh();

            //Update in the database
            manager.updateProduct(currentEditingProduct);

            // Clear fields and reset
            clearInputFields();
            currentEditingProduct = null;
            addProdbtn.setText("Add Product"); // Reset button text

            showSuccess("Product updated successfully!");

            displayStatistics();

        } catch (NumberFormatException e) {
            showError("Please enter valid numbers for prices and stock");
        } catch (Exception e) {
            showError("Error: " + e.getMessage());
        }
    }

    @FXML
    private void handleDeleteProduct() {
        Product selectedProduct = prodTable.getSelectionModel().getSelectedItem();

        if (selectedProduct == null) {
            showError("Please select a product to delete");
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Product");
        alert.setHeaderText("Are you sure you want to delete this product ?");

        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);
        if (result == ButtonType.OK) {
            // Remove in the database
            manager.deleteProduct(selectedProduct.getNumber());

            // Remove from all local lists
            allProducts.remove(selectedProduct);
            clothesProducts.remove(selectedProduct);
            shoesProducts.remove(selectedProduct);
            accessoriesProducts.remove(selectedProduct);

            // Reapply filters
            applyFilterAndSort();

            // Clear input fields if we were editing this product
            if (currentEditingProduct == selectedProduct) {
                clearInputFields();
                currentEditingProduct = null;
                addProdbtn.setText("Add Product");
            }
            displayStatistics();

            showSuccess("Product deleted successfully!");
        }
    }

    private void handleSellProduct() {
        Product selectedProduct = prodTable.getSelectionModel().getSelectedItem();

        if (selectedProduct == null) {
            showError("Please select a product to sell");
            return;
        }

        try {
            // Update stock
            int nbItemBase = selectedProduct.getNbItems();
            int nbItemsToSell = Integer.parseInt(sellTextField.getText());
            if (nbItemsToSell > nbItemBase ||  nbItemsToSell < 1) {
                showError("Please select a valid number for sell");
                return;
            }


            // 2 cases
            if (selectedProduct.getDiscountPrice()==0) {
                selectedProduct.sell(nbItemsToSell);
            }
            else {
                selectedProduct.sellWithDiscount(nbItemsToSell);
            }

            //Update stock in DB
            manager.updateStock(selectedProduct);


            // Refresh the table
            prodTable.refresh();

            // Clear field
            sellTextField.clear();
            clearInputFields();
            showSuccess("Product sold successfully!");

            displayStatistics();

        } catch (NumberFormatException e) {
            showError("Please enter valid number to sell");
        }
    }

    private void handlePurchaseProduct() {
        Product selectedProduct = prodTable.getSelectionModel().getSelectedItem();

        if (selectedProduct == null) {
            showError("Please select a product to purchase");
            return;
        }

        try {
            // Update stock
            int nbItemBase = selectedProduct.getNbItems();
            int nbItemsToPurchase = Integer.parseInt(purchaseTextField.getText());
            if (nbItemsToPurchase < 1) {
                showError("Please select a valid number for purchase");
                return;
            }

            // Calcul new cost and capital
            selectedProduct.purchase(nbItemsToPurchase);

            //Update stock in DB
            manager.updateStock(selectedProduct);

            // Refresh the table
            prodTable.refresh();

            // Clear fields and reset
            purchaseTextField.clear();
            clearInputFields();
            showSuccess("Product purchased successfully!");

            displayStatistics();

        } catch (NumberFormatException e) {
            showError("Please enter valid number to purchase");
        }
    }


    private void handleApplyDiscount() {
        boolean anyApplied = false;

        for (Product p : allProducts) {

            if (p.getDiscountPrice() == 0) {
                p.applyDiscount();
                manager.updateDiscount(p); // update of the DB
                anyApplied = true;
            }
        }

        if (!anyApplied) {
            showError("Discount already applied to all products");
            return;
        }

        prodTable.refresh();
        showSuccess("Discount applied to ALL products!");
    }



    private void handleStopDiscount() {
        boolean anyStopped = false;

        for (Product p : allProducts) {

            if (p.getDiscountPrice() != 0) {
                p.unApplyDiscount();
                manager.updateDiscount(p); // update DB
                anyStopped = true;
            }
        }

        if (!anyStopped) {
            showError("No product currently has a discount");
            return;
        }

        prodTable.refresh();
        showSuccess("Discount removed from ALL products!");
    }

    private String getCategoryName(Product product) {
        if (product instanceof Clothes) return "Clothes";
        if (product instanceof Shoes) return "Shoes";
        if (product instanceof Accessories) return "Accessories";
        return "Unknown";
    }

    private void clearInputFields() {
        nameTextField.clear();
        sizeTextField.clear();
        catTextField.clear();
        purPriceTextField.clear();
        sellPriceTextField.clear();
        discPriceTextField.clear();
        stockTextField.clear();
    }

    private void showError(String message) {
        // You can use Alert dialog or display in a Label
        System.err.println("ERROR: " + message);
        // Example with Alert:
        // Alert alert = new Alert(Alert.AlertType.ERROR);
        // alert.setContentText(message);
        // alert.show();
    }

    private void showSuccess(String message) {
        System.out.println("SUCCESS: " + message);
    }

    private boolean showConfirmation(String message) {
        // Implement a proper confirmation dialog later
        System.out.println("CONFIRMATION: " + message);
        return true; // For now, always return true
    }

    private void displayStatistics(){
        double capital = Product.getCapital();
        double income = Product.getIncome();
        double cost = Product.getCost();

        capitalTextField.setText(String.format(" €%.2f", capital));
        incomeTextField.setText(String.format(" €%.2f", income));
        costTextField.setText(String.format(" €%.2f", cost));
    }








}