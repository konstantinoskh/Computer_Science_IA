module com.example.computer_science_ia {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;

    requires org.xerial.sqlitejdbc;
    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;

    requires com.fasterxml.jackson.datatype.jsr310;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires jbcrypt;


    exports com.example.computer_science_ia.Handling;
    exports com.example.computer_science_ia.Controllers to javafx.fxml;
    opens com.example.computer_science_ia to javafx.fxml, com.fasterxml.jackson.databind, jbcrypt;
    opens com.example.computer_science_ia.Handling to com.fasterxml.jackson.databind, javafx.fxml, jbcrypt;
    opens com.example.computer_science_ia.Controllers to javafx.fxml, com.fasterxml.jackson.databind, jbcrypt;
    exports com.example.computer_science_ia;
}