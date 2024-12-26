module com.mycompany.passwordmanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;

    opens com.mycompany.passwordmanager to javafx.fxml;
    exports com.mycompany.passwordmanager;
    opens com.mycompany.passwordmanager.controllers to javafx.fxml;
    exports com.mycompany.passwordmanager.controllers;
    opens com.mycompany.passwordmanager.dto to javafx.fxml;
    exports com.mycompany.passwordmanager.dto;
    opens com.mycompany.passwordmanager.vo to javafx.fxml;
    exports com.mycompany.passwordmanager.vo;
    opens com.mycompany.passwordmanager.data_base to javafx.fxml;
    exports com.mycompany.passwordmanager.data_base;
    opens com.mycompany.passwordmanager.entities to org.hibernate.orm.core;
    exports com.mycompany.passwordmanager.entities;
}
