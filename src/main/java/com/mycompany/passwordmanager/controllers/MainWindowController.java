/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.passwordmanager.controllers;

import com.mycompany.passwordmanager.dto.AccountDto;
import com.mycompany.passwordmanager.utils.constants.Constants;
import com.mycompany.passwordmanager.vo.TableAccountVo;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Luis Gerardo Marquez
 */
public class MainWindowController {
    
    private DialogWindowController dialogWindowInsertController;
    
    private DialogUpdateWindowController dialogWindowUpdateController;
    
    private List<AccountDto> listAccountDto;
    
    private ObservableList<TableAccountVo> obsListAccounts;
    
    @FXML
    private TableView<TableAccountVo> tblViewAccounts;
    
    @FXML
    private TableColumn tblColId;
    
    @FXML
    private TableColumn tblColAccount;
    
    @FXML
    private TableColumn tblColUserName;
    
    @FXML
    private TableColumn tblColPassword;
    
    @FXML
    private TableColumn tblColToken;
    
    @FXML
    private TableColumn tblColEmail;
    
    @FXML
    private TableColumn tblColPhone;
    
    @FXML
    private TableColumn tblColUrl;
    
    @FXML
    private TableColumn tblColComments;
    
    @FXML
    private Button btnIdUpdateAccount;
    
    @FXML
    private Label lblIdAccount;

    private AccountDto selectedAccount;

    public MainWindowController() {
        this.listAccountDto = new ArrayList<>();
    }
    
    @FXML
    public void initialize() {
        obsListAccounts = FXCollections.observableArrayList();
        
        tblColId.setCellValueFactory(new PropertyValueFactory("id"));
        tblColAccount.setCellValueFactory(new PropertyValueFactory("account"));
        tblColUserName.setCellValueFactory(new PropertyValueFactory("userName"));
        tblColPassword.setCellValueFactory(new PropertyValueFactory("password"));
        tblColToken.setCellValueFactory(new PropertyValueFactory("token"));
        tblColEmail.setCellValueFactory(new PropertyValueFactory("email"));
        tblColPhone.setCellValueFactory(new PropertyValueFactory("phone"));
        tblColUrl.setCellValueFactory(new PropertyValueFactory("url"));
        tblColComments.setCellValueFactory(new PropertyValueFactory("comments"));
        
        tblViewAccounts.setItems(obsListAccounts);
        
        // Vincular la propiedad "disable" del botón a la condición de el label de seleccion
        btnIdUpdateAccount.disableProperty().bind(
            Bindings.createBooleanBinding(
                () -> lblIdAccount.getText().equals("0"),
                lblIdAccount.textProperty()
            )
        );        
    }
    
    @FXML
    private void openDialogInsertWindow() throws IOException {
        // Cargar el archivo FXML de la ventana principal para abrir otra
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Constants.DIALOG_INSERT_WINDOW));
        Parent root = loader.load();
        dialogWindowInsertController = loader.getController();
        dialogWindowInsertController.setObsListAccounts(obsListAccounts);
        // Usar Stream y el método max() con un Comparator para obtener el id maximo de las cuentas de la lita
        Optional<Integer> maximo = obsListAccounts.stream().map(it -> it.getId() ).max(Comparator.naturalOrder());
        if(maximo.isPresent())
            dialogWindowInsertController.setIdMax(maximo.get());
        else
            dialogWindowInsertController.setIdMax(0);

        // Crear el Stage (ventana) para la ventana modal
        Stage modalStage = new Stage();
        modalStage.setTitle(Constants.TITLE_DIALOG_INSERT_WINDOW);
        modalStage.setScene(new Scene(root));
        
        // Configurar la modalidad
        modalStage.initModality(Modality.APPLICATION_MODAL); // Bloquea la ventana principal hasta que se cierra
        modalStage.setResizable(false);

        // Escuchar el evento de cerrar la ventana
        modalStage.setOnCloseRequest(event -> {
            dialogWindowInsertController.getAccountDto().setId(0);
        });
        // Mostrar la ventana modal
        modalStage.showAndWait();
        
        if(dialogWindowInsertController.getAccountDto().getId() != 0){
            obsListAccounts.add(dialogWindowInsertController.getTableAccountVo() );
            listAccountDto.add(dialogWindowInsertController.getAccountDto() );
        }
        tblViewAccounts.refresh();
        
    }
    
    @FXML
    private void openDialogUpdateWindow() throws IOException {
        // Cargar el archivo FXML de la ventana principal para abrir otra
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Constants.DIALOG_UPDATE_WINDOW));
        Parent root = loader.load();
        dialogWindowUpdateController = loader.getController();
       
        Label lblAux = dialogWindowUpdateController.getLblUpdateAccount();
        lblAux.setText(lblAux.getText() + lblIdAccount.getText());
        dialogWindowUpdateController.setLblUpdateAccount(lblAux);
        dialogWindowUpdateController.setAccountDto(selectedAccount.copy() );
        dialogWindowUpdateController.setListAccountDto(listAccountDto);

        // Crear el Stage (ventana) para la ventana modal
        Stage modalStage = new Stage();
        modalStage.setTitle(Constants.TITLE_DIALOG_UPDATE_WINDOW);
        modalStage.setScene(new Scene(root));
        
        // Configurar la modalidad
        modalStage.initModality(Modality.APPLICATION_MODAL); // Bloquea la ventana principal hasta que se cierra
        modalStage.setResizable(false);

        // Escuchar el evento de cerrar la ventana para setear la cuenta en 0 y no actualizar la cuenta que se selecciono
        modalStage.setOnCloseRequest(event -> {
            dialogWindowUpdateController.getAccountDto().setId(0);
        });
        // Mostrar la ventana modal
        modalStage.showAndWait();
        
        if(dialogWindowUpdateController.getAccountDto().getId() != 0){ // El id se usa como bandera para indicar que presiono el boton de Actualizar cuenta
            obsListAccounts.forEach(it -> {
                if(it.equals(dialogWindowUpdateController.getAccountDto().getTableAccountVo() ) ){
                    it.setAccount(dialogWindowUpdateController.getAccountDto().getAccount() );
                    it.setComments(dialogWindowUpdateController.getAccountDto().getComments() );
                    it.setEmail(dialogWindowUpdateController.getAccountDto().getEmail() );
                    it.setPassword(dialogWindowUpdateController.getAccountDto().getPassword() );
                    it.setPhone(dialogWindowUpdateController.getAccountDto().getPhone() );
                    it.setToken(dialogWindowUpdateController.getAccountDto().getToken() );
                    it.setUrl(dialogWindowUpdateController.getAccountDto().getUrl() );
                    it.setUserName(dialogWindowUpdateController.getAccountDto().getUserName() );
                }
            });
            
            listAccountDto.forEach(it -> {
                if(it.equals(dialogWindowUpdateController.getAccountDto() ) ){
                    it.setAccount(dialogWindowUpdateController.getAccountDto().getAccount() );
                    it.setComments(dialogWindowUpdateController.getAccountDto().getComments() );
                    it.setEmail(dialogWindowUpdateController.getAccountDto().getEmail() );
                    it.setPassword(dialogWindowUpdateController.getAccountDto().getPassword() );
                    it.setPhone(dialogWindowUpdateController.getAccountDto().getPhone() );
                    it.setToken(dialogWindowUpdateController.getAccountDto().getToken() );
                    it.setUrl(dialogWindowUpdateController.getAccountDto().getUrl() );
                    it.setUserName(dialogWindowUpdateController.getAccountDto().getUserName() );
                }
            });
        }
        tblViewAccounts.refresh();
    }
    
    @FXML
    private void deleteAccount() throws IOException {
        if(!lblIdAccount.getText().equals("0") ){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setTitle("Advertencia");
            alert.setContentText("La cuenta con id: " + lblIdAccount.getText() + " se eliminara por completo, ¿desea continuar?");
            // Mostrar el alert y esperar la respuesta
            Optional<ButtonType> result = alert.showAndWait();
            // Verificar la respuesta
            if (result.isPresent() && result.get() == ButtonType.OK) {
                obsListAccounts.removeIf(it -> it.equals(selectedAccount.getTableAccountVo() ) );
                listAccountDto.removeIf(it -> it.equals(selectedAccount) );
            }
        }
        tblViewAccounts.refresh();
    }
    
    @FXML
    private void selectAccount(MouseEvent event) {
        if(tblViewAccounts.getSelectionModel().getSelectedItem() != null){
            TableAccountVo auxTableAccount = tblViewAccounts.getSelectionModel().getSelectedItem();
            selectedAccount = new AccountDto(auxTableAccount);
            selectedAccount = listAccountDto.get(listAccountDto.indexOf(selectedAccount) ); // Se hace esto para setear el password y token en claro como lo tiene la lista normal no la lista observable de la tabla que tiene asteriscos
            lblIdAccount.setText(Integer.toString(selectedAccount.getId() ) );
        }
    }
}
