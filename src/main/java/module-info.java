module com.mycompany.passwordmanager {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.passwordmanager to javafx.fxml;
    exports com.mycompany.passwordmanager;
    opens com.mycompany.passwordmanager.controllers to javafx.fxml;
    exports com.mycompany.passwordmanager.controllers;
    opens com.mycompany.passwordmanager.dto to javafx.fxml;
    exports com.mycompany.passwordmanager.dto;
    opens com.mycompany.passwordmanager.vo to javafx.fxml;
    exports com.mycompany.passwordmanager.vo;
}
