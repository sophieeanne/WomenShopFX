module com.example.womenshopfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.womenshopfx to javafx.fxml;
    exports com.example.womenshopfx;
}