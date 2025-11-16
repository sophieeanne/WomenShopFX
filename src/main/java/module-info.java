module com.example.womenshopfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.womenshopfx to javafx.fxml;
    exports com.example.womenshopfx;
}