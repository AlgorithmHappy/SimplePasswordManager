/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.passwordmanager.controllers;

import com.mycompany.passwordmanager.dto.AccountDto;
import com.mycompany.passwordmanager.utils.constants.Constants;
import com.mycompany.passwordmanager.vo.TableAccountVo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
public class DialogUpdateWindowController {
    
    @FXML
    private Label lblUpdateAccount;
    
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
    
    private AccountDto accountDto;
    
    private List<AccountDto> listAccountDto;

    public DialogUpdateWindowController() {
        accountDto = new AccountDto(
            0,
            Constants.EMPTY,
            Constants.EMPTY,
            Constants.EMPTY
        );
        listAccountDto = new ArrayList<>();
        token = Constants.EMPTY;
        password = Constants.EMPTY;
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
                    } else if (passFieldPassword.getText().isBlank() || txtFieldPasswordVisible.getText().isBlank() ) {
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
    private void updateAccount() throws IOException {
        
        List<String> aux = listAccountDto.stream().filter(it -> !it.equals(accountDto) ).map(it -> it.getAccount() ).collect(Collectors.toList());
        
        // Si se repite 2 veces mandar error de que no se puede repetir el elemento
        if(aux.contains(accountDto.getAccount() ) ){
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
            alert.setContentText("Se ha actualizado correctamente la cuenta");
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

    public AccountDto getAccountDto() {
        return accountDto;
    }

    public void setAccountDto(AccountDto accountDto) {
        this.accountDto = accountDto;
        txtFieldCuenta.setText(accountDto.getAccount() );
        if(accountDto.getEmail() != null)
            txtFieldEmail.setText(accountDto.getEmail() );
        txtFieldPasswordVisible.setText(accountDto.getPassword() );
        passFieldPassword.setText(accountDto.getPassword() );
        if(accountDto.getPhone() != null)
            txtFieldPhone.setText(String.valueOf(accountDto.getPhone() ) );
        if(accountDto.getToken() != null){
            txtFieldTokenVisible.setText(accountDto.getToken() );
            passFieldToken.setText(accountDto.getToken() );
        }
        if(accountDto.getUrl() != null)
            txtFieldUrl.setText(accountDto.getUrl() );
        txtFieldUserName.setText(accountDto.getUserName() );
        if(accountDto.getComments() != null)
            txtAreaComments.setText(accountDto.getComments() );
    }

    public TableAccountVo getTableAccountVo() {
        return accountDto.getTableAccountVo();
    }   
    
    private void addTextFieldListener(TextInputControl textField, String name) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            switch(name){
                case "txtFieldCuenta":
                    accountDto.setAccount(newValue);
                    break;
                case "txtFieldUserName":
                    accountDto.setUserName(newValue);
                    break;
                case "passFieldPassword":
                    accountDto.setPassword(newValue);
                    txtFieldPasswordVisible.setText(newValue);
                    break;
                case "txtFieldPasswordVisible":
                    accountDto.setPassword(newValue);
                    passFieldPassword.setText(newValue);
                    break;
                case "passFieldToken":
                    accountDto.setToken(newValue);
                    break;
                case "txtFieldTokenVisible":
                    accountDto.setToken(newValue);
                    break;
                case "txtFieldEmail":
                    accountDto.setAccount(newValue);
                    break;
                case "txtFieldPhone":
                    Pattern pattern2 = Pattern.compile(Constants.REGEX_NUMERIC);
                    Long longNumber = null;
                    if(!newValue.isBlank() && pattern2.matcher(newValue).matches())
                        longNumber = Long.valueOf(newValue);
                    accountDto.setPhone(longNumber);
                    break;
                case "txtFieldUrl":
                    accountDto.setUrl(newValue);
                    break;
                case "txtAreaComments":
                    accountDto.setComments(newValue);
                    break;
            }
        });
    }

    public Label getLblUpdateAccount() {
        return lblUpdateAccount;
    }

    public void setLblUpdateAccount(Label lblUpdateAccount) {
        this.lblUpdateAccount = lblUpdateAccount;
    }

    public void setListAccountDto(List<AccountDto> listAccountDto) {
        this.listAccountDto = listAccountDto;
    }
}
