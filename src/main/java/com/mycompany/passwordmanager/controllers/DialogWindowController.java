/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.passwordmanager.controllers;

import com.mycompany.passwordmanager.dto.AccountDto;
import com.mycompany.passwordmanager.utils.constants.Constants;
import com.mycompany.passwordmanager.vo.TableAccountVo;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author Luis Gerardo Marquez
 */
public class DialogWindowController {
    
    @FXML
    private TextField txtFieldCuenta;
    
    @FXML
    private TextField txtFieldUserName;
    
    @FXML
    private HBox hBoxPassword;
    
    @FXML
    private PasswordField passFieldPassword;
    
    @FXML
    private TextField txtFieldPasswordVisible;
    
    @FXML
    private Button btnEyePassword;
    
    private boolean showPassword = false;
    
    private String password;
    
    @FXML
    private HBox hBoxToken;
    
    @FXML
    private PasswordField passFieldToken;
    
    @FXML
    private TextField txtFieldTokenVisible;
    
    @FXML
    private Button btnEyeToken;
    
    private boolean showToken = false;
    
    private String token;
    
    @FXML
    private TextField txtFieldEmail;
    
    @FXML
    private TextField txtFieldPhone;
    
    @FXML
    private TextField txtFieldUrl;
    
    @FXML
    private TextArea txtAreaComments;
    
    @FXML
    private Label lblShowError;
    
    @FXML
    private Button btnInsertAccount;
    
    private ObservableList<TableAccountVo> obsListAccounts;
    
    private TableAccountVo tableAccountVo;
    
    private AccountDto accountDto;
    
    private Integer idMax;

    public DialogWindowController() {
        idMax = 0;
        tableAccountVo = new TableAccountVo(
            idMax,
            Constants.EMPTY,
            Constants.EMPTY,
            Constants.EMPTY
        );
        accountDto = new AccountDto(
            idMax,
            Constants.EMPTY,
            Constants.EMPTY,
            Constants.EMPTY
        );
        token = Constants.EMPTY;
        password = Constants.EMPTY;
        obsListAccounts = FXCollections.observableArrayList();
    }
    
    @FXML
    void initialize(){
               
        // Vincular el texto del Label a un binding compuesto
        lblShowError.textProperty().bind(
            Bindings.createStringBinding(
                () -> {
                    String email = txtFieldEmail.getText();
                    Pattern pattern = Pattern.compile(Constants.REGEX_EMAIL);
                    String phone = txtFieldPhone.getText();
                    Pattern pattern2 = Pattern.compile(Constants.REGEX_NUMERIC);
                    
                    if (txtFieldCuenta.getText().isBlank() ) {
                        lblShowError.setTextFill(Color.RED);
                        return "El campo cuenta está vacío.";
                    } else if (txtFieldUserName.getText().isBlank() ) {
                        lblShowError.setTextFill(Color.RED);
                        return "El campo nombre de usuario está vacío.";
                    } else if (passFieldPassword.getText().isBlank() && txtFieldPasswordVisible.getText().isBlank() ) {
                        lblShowError.setTextFill(Color.RED);
                        return "El campo contraseña está vacío.";
                    } else if (!txtFieldEmail.getText().isBlank() && !pattern.matcher(email).matches() ){
                        lblShowError.setTextFill(Color.RED);
                        return "Correo electrónico no válido";
                    } else if (!txtFieldPhone.getText().isBlank() && !pattern2.matcher(phone).matches() ){
                        lblShowError.setTextFill(Color.RED);
                        return "Celular no válido";
                    } else {
                        lblShowError.setTextFill(Color.GREEN);
                        return "..."; // Todos los campos están llenos
                    }
                },
                txtFieldCuenta.textProperty(),
                txtFieldUserName.textProperty(),
                passFieldPassword.textProperty(),
                txtFieldPasswordVisible.textProperty(),
                txtFieldEmail.textProperty(),
                txtFieldPhone.textProperty()
            )
        );
        
        // Vincular la propiedad "disable" del botón a la condición de los TextFields
        btnInsertAccount.disableProperty().bind(
            Bindings.createBooleanBinding(
                () -> !lblShowError.getText().equals("..."),
                lblShowError.textProperty()
            )
        );
       
        addTextFieldListener(txtFieldCuenta, "txtFieldCuenta");
        addTextFieldListener(txtFieldUserName, "txtFieldUserName");
        addTextFieldListener(passFieldPassword, "passFieldPassword");
        addTextFieldListener(txtFieldPasswordVisible, "txtFieldPasswordVisible");
        addTextFieldListener(passFieldToken, "passFieldToken");
        addTextFieldListener(txtFieldTokenVisible, "txtFieldTokenVisible");
        addTextFieldListener(txtFieldEmail, "txtFieldEmail");
        addTextFieldListener(txtFieldPhone, "txtFieldPhone");
        addTextFieldListener(txtFieldUrl, "txtFieldUrl");
        addTextFieldListener(txtAreaComments, "txtAreaComments");
    }
    
    @FXML
    private void insertAccount() throws IOException {
        
        List<String> listAux = obsListAccounts.stream().map(it -> it.getAccount() ).collect(Collectors.toList() );
        
        if (listAux.contains(tableAccountVo.getAccount() ) ){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("La cuenta que esta tratando de añadir ya existe");
            alert.showAndWait();
            accountDto.setId(0);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Información");
            alert.setContentText("Se ha añadido correctamente la cuenta");
            alert.showAndWait();
        }
        Stage stage = (Stage)btnInsertAccount.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void passwordToggleVisibility() throws IOException {
        showPassword = !showPassword;
        if(showPassword){
            // Intercambiar posiciones para que no se vea graficamente que se oculta un textField y se muestra el passwordField
            Integer indexStart = hBoxPassword.getChildren().indexOf(passFieldPassword);
            Integer indexMiddle = hBoxPassword.getChildren().indexOf(txtFieldPasswordVisible);
            Integer indexFinal = hBoxPassword.getChildren().indexOf(btnEyePassword);
            hBoxPassword.getChildren().remove(passFieldPassword);
            hBoxPassword.getChildren().remove(txtFieldPasswordVisible);
            hBoxPassword.getChildren().remove(btnEyePassword);
            hBoxPassword.getChildren().add(indexStart, txtFieldPasswordVisible);
            hBoxPassword.getChildren().add(indexMiddle, passFieldPassword);
            hBoxPassword.getChildren().add(indexFinal, btnEyePassword);
            
            
            // Logica para mostrar un text field y ocultar el otro
            txtFieldPasswordVisible.setPrefSize(passFieldPassword.getPrefWidth(), passFieldPassword.getPrefHeight());
            passFieldPassword.setPrefSize(Constants.PREF_SIZE_HIDE, Constants.PREF_SIZE_HIDE);
            txtFieldPasswordVisible.setVisible(showPassword);
            passFieldPassword.setVisible(!showPassword);
            txtFieldPasswordVisible.setText(passFieldPassword.getText());
            
            password = passFieldPassword.getText();
        } else {
            // Intercambiar posiciones para que no se vea graficamente que se oculta un textField y se muestra el passwordField
            Integer indexStart = hBoxPassword.getChildren().indexOf(txtFieldPasswordVisible);
            Integer indexMiddle = hBoxPassword.getChildren().indexOf(passFieldPassword);
            Integer indexFinal = hBoxPassword.getChildren().indexOf(btnEyePassword);
            hBoxPassword.getChildren().remove(passFieldPassword);
            hBoxPassword.getChildren().remove(txtFieldPasswordVisible);
            hBoxPassword.getChildren().remove(btnEyePassword);
            hBoxPassword.getChildren().add(indexStart, passFieldPassword);
            hBoxPassword.getChildren().add(indexMiddle, txtFieldPasswordVisible);
            hBoxPassword.getChildren().add(indexFinal, btnEyePassword);
            
            // Logica para mostrar un text field y ocultar el otro
            passFieldPassword.setPrefSize(txtFieldPasswordVisible.getPrefWidth(), txtFieldPasswordVisible.getPrefHeight());
            txtFieldPasswordVisible.setPrefSize(Constants.PREF_SIZE_HIDE, Constants.PREF_SIZE_HIDE);
            passFieldPassword.setVisible(!showPassword);
            txtFieldPasswordVisible.setVisible(showPassword);
            passFieldPassword.setText(txtFieldPasswordVisible.getText());
            
            password = txtFieldPasswordVisible.getText();
        }
    }
    
    @FXML
    private void tokenToggleVisibility() throws IOException {
        showToken = !showToken;
        if(showToken){
            // Intercambiar posiciones para que no se vea graficamente que se oculta un textField y se muestra el passwordField
            Integer indexStart = hBoxToken.getChildren().indexOf(passFieldToken);
            Integer indexMiddle = hBoxToken.getChildren().indexOf(txtFieldTokenVisible);
            Integer indexFinal = hBoxToken.getChildren().indexOf(btnEyeToken);
            hBoxToken.getChildren().remove(passFieldToken);
            hBoxToken.getChildren().remove(txtFieldTokenVisible);
            hBoxToken.getChildren().remove(btnEyeToken);
            hBoxToken.getChildren().add(indexStart, txtFieldTokenVisible);
            hBoxToken.getChildren().add(indexMiddle, passFieldToken);
            hBoxToken.getChildren().add(indexFinal, btnEyeToken);
            
            
            // Logica para mostrar un text field y ocultar el otro
            txtFieldTokenVisible.setPrefSize(passFieldToken.getPrefWidth(), passFieldToken.getPrefHeight());
            passFieldToken.setPrefSize(Constants.PREF_SIZE_HIDE, Constants.PREF_SIZE_HIDE);
            txtFieldTokenVisible.setVisible(showToken);
            passFieldToken.setVisible(!showToken);
            txtFieldTokenVisible.setText(passFieldToken.getText());
            
            token = passFieldToken.getText();
        } else {
            // Intercambiar posiciones para que no se vea graficamente que se oculta un textField y se muestra el passwordField
            Integer indexStart = hBoxToken.getChildren().indexOf(txtFieldTokenVisible);
            Integer indexMiddle = hBoxToken.getChildren().indexOf(passFieldToken);
            Integer indexFinal = hBoxToken.getChildren().indexOf(btnEyeToken);
            hBoxToken.getChildren().remove(passFieldToken);
            hBoxToken.getChildren().remove(txtFieldTokenVisible);
            hBoxToken.getChildren().remove(btnEyeToken);
            hBoxToken.getChildren().add(indexStart, passFieldToken);
            hBoxToken.getChildren().add(indexMiddle, txtFieldTokenVisible);
            hBoxToken.getChildren().add(indexFinal, btnEyeToken);
            
            // Logica para mostrar un text field y ocultar el otro
            passFieldToken.setPrefSize(txtFieldTokenVisible.getPrefWidth(), txtFieldTokenVisible.getPrefHeight());
            txtFieldTokenVisible.setPrefSize(Constants.PREF_SIZE_HIDE, Constants.PREF_SIZE_HIDE);
            passFieldToken.setVisible(!showToken);
            txtFieldTokenVisible.setVisible(showToken);
            passFieldToken.setText(txtFieldTokenVisible.getText());
            
            token = txtFieldTokenVisible.getText();
        }
    }

    public TableAccountVo getTableAccountVo() {
        return tableAccountVo;
    }

    public ObservableList<TableAccountVo> getObsListAccounts() {
        return obsListAccounts;
    }

    public void setObsListAccounts(ObservableList<TableAccountVo> obsListAccounts) {
        if(obsListAccounts != null)
            this.obsListAccounts = obsListAccounts;
    }

    public Integer getIdMax() {
        return idMax;
    }

    public void setIdMax(Integer idMax) {
        this.idMax = idMax;
    }

    public AccountDto getAccountDto() {
        return accountDto;
    }
    
    private void addTextFieldListener(TextInputControl textField, String name) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            switch(name){
                case "txtFieldCuenta":
                    tableAccountVo.setAccount(newValue);
                    accountDto.setAccount(newValue);
                    break;
                case "txtFieldUserName":
                    tableAccountVo.setUserName(newValue);
                    accountDto.setUserName(newValue);
                    break;
                case "passFieldPassword":
                    tableAccountVo.setPassword(newValue);
                    accountDto.setPassword(newValue);
                    break;
                case "txtFieldPasswordVisible":
                    tableAccountVo.setPassword(newValue);
                    accountDto.setPassword(newValue);
                    break;
                case "passFieldToken":
                    tableAccountVo.setToken(newValue);
                    accountDto.setToken(newValue);
                    break;
                case "txtFieldTokenVisible":
                    tableAccountVo.setToken(newValue);
                    accountDto.setToken(newValue);
                    break;
                case "txtFieldEmail":
                    tableAccountVo.setAccount(newValue);
                    accountDto.setAccount(newValue);
                    break;
                case "txtFieldPhone":
                    Pattern pattern2 = Pattern.compile(Constants.REGEX_NUMERIC);
                    Long longNumber = null;
                    if(!newValue.isBlank() && pattern2.matcher(newValue).matches())
                        longNumber = Long.valueOf(newValue);
                    tableAccountVo.setPhone(longNumber);
                    accountDto.setPhone(longNumber);
                    break;
                case "txtFieldUrl":
                    tableAccountVo.setUrl(newValue);
                    accountDto.setUrl(newValue);
                    break;
                case "txtAreaComments":
                    tableAccountVo.setComments(newValue);
                    accountDto.setComments(newValue);
                    break;
            }
            tableAccountVo.setId(idMax + 1);
            accountDto.setId(idMax + 1);
        });
    }
}
