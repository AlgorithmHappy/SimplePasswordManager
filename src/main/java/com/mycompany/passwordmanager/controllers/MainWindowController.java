/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.passwordmanager.controllers;

//import com.mycompany.passwordmanager.data_base.HibernateUtil;
import com.mycompany.passwordmanager.dto.AccountDto;
import com.mycompany.passwordmanager.services.CrudServiceImplements;
import com.mycompany.passwordmanager.services.CrudServiceInterface;
import com.mycompany.passwordmanager.services.DataBaseFilesManagerServiceImplements;
import com.mycompany.passwordmanager.services.DataBaseFilesManagerServiceInterface;
import com.mycompany.passwordmanager.services.PropertiesServiceImplements;
import com.mycompany.passwordmanager.services.PropertiesServiceInterface;
import com.mycompany.passwordmanager.utils.constants.Constants;
import com.mycompany.passwordmanager.vo.TableAccountVo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Luis Gerardo Marquez
 */
public class MainWindowController implements Initializable {
    
    private DialogWindowController dialogWindowInsertController;
    
    private DialogUpdateWindowController dialogWindowUpdateController;
    
    private List<AccountDto> listAccountDto;
    
    private ObservableList<TableAccountVo> obsListAccounts;
    private ObservableList<TableAccountVo> obsListTblAccountsVoFiltered;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private MenuBar menuBar;

    @FXML
    private Menu menu;

    @FXML
    private MenuItem menuItemSaveAs;

    @FXML
    private MenuItem menuItemOpen;

    @FXML
    private MenuItem menuItemClose;
    
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
    private Button btnIdDeleteAccount;
    
    @FXML
    private Label lblIdAccount;
    
    @FXML
    private TextField txtFieldKeyWordsFilters;

    private AccountDto selectedAccount;

    private CrudServiceInterface crudService;

    private PropertiesServiceInterface propertiesService;

    private DataBaseFilesManagerServiceInterface dataBaseFilesManager;

    private String password;

    public MainWindowController() {
        password = "password";
        crudService = new CrudServiceImplements(password);
        propertiesService = new PropertiesServiceImplements(password);
        if(propertiesService.isPersonalDatabaseEmpty() ){
            this.listAccountDto = new ArrayList<>();
        } else {
            dataBaseFilesManager = new DataBaseFilesManagerServiceImplements(
                password,
                propertiesService.getTemporalDatabasePath(),
                propertiesService.getPersonalDatabasePath()
            );
            dataBaseFilesManager.closeDatabaseFile();
            dataBaseFilesManager.temporarilyDecryptDataBase();
            this.listAccountDto = crudService.getAllAccounts();
        }
    }
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        obsListAccounts = FXCollections.observableArrayList();
        obsListTblAccountsVoFiltered = FXCollections.observableArrayList();
        for(AccountDto it : this.listAccountDto)
            obsListAccounts.add(it.getTableAccountVo() );
        
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

        btnIdDeleteAccount.disableProperty().bind(
            Bindings.createBooleanBinding(
                () -> lblIdAccount.getText().equals("0"),
                lblIdAccount.textProperty()
            )
        );

        menuBar = new MenuBar();
        menu = new Menu("Archivo");
        menuItemOpen = new MenuItem("Abrir");
        menuItemSaveAs = new MenuItem("Guardar como");
        menuItemClose = new MenuItem("Cerrar");
        menu.getItems().add(menuItemOpen);
        menu.getItems().add(menuItemSaveAs);
        menu.getItems().add(menuItemClose);
        menuBar.getMenus().add(menu);
        // Establecer el MenuBar en la parte superior del AnchorPane
        AnchorPane.setTopAnchor(menuBar, 0.0);
        AnchorPane.setLeftAnchor(menuBar, 0.0);
        AnchorPane.setRightAnchor(menuBar, 0.0);
        anchorPane.getChildren().addAll(menuBar);
        menuItemOpen.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Seleccionar archivo");

            // Configurar filtros de extensiones
            fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Cuentas", "*.accounts")
            );

            // Mostrar el explorador de archivos
            File archivoSeleccionado = fileChooser.showOpenDialog(new Stage() );
                
            if (archivoSeleccionado != null) {
                //HibernateUtil.shutdown("password");
                //HibernateUtil.updateFilePropertiesAndSaveEncryptDatabase(archivoSeleccionado.getAbsolutePath(), password );
                obsListAccounts.clear();
                obsListTblAccountsVoFiltered.clear();
                listAccountDto = crudService.getAllAccounts();
                for(AccountDto it : listAccountDto){
                    System.out.println(it.getAccount() );
                    obsListAccounts.add(it.getTableAccountVo() );
                }
                tblViewAccounts.setItems(obsListAccounts);
                tblViewAccounts.refresh();
            } else {
                System.out.println("No se seleccionó ningún archivo.");
            }
        });
        menuItemSaveAs.setOnAction(event -> extracted());
        menuItemClose.setOnAction(event -> {
            //HibernateUtil.shutdown("password");
            System.exit(0);
        });
    }
        
  
    private void extracted() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar cuentas");
        
        // Configurar filtros de extensiones
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Cuentas", "*.accounts")
        );
        
        // Mostrar el explorador de archivos
        File archivoSeleccionado = fileChooser.showSaveDialog(new Stage() );
                    
        if (archivoSeleccionado != null) {
            try{
                propertiesService.setPersonalDatabasePath(archivoSeleccionado.getAbsolutePath() );
                if(dataBaseFilesManager == null){
                    dataBaseFilesManager = new DataBaseFilesManagerServiceImplements(
                        password,
                        propertiesService.getTemporalDatabasePath(),
                        propertiesService.getPersonalDatabasePath()
                    );
                }
                dataBaseFilesManager.saveEncryptedDataBase();
                //HibernateUtil.updateFilePropertiesAndSaveEncryptDatabase(archivoSeleccionado.getAbsolutePath(), "password");
            } catch (Exception e){
                e.printStackTrace();
            }
        }
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
            crudService.insertAccount(dialogWindowInsertController.getAccountDto() );
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

            crudService.updateAccount(dialogWindowUpdateController.getAccountDto() );
        }
        
        if(obsListTblAccountsVoFiltered != null && !obsListTblAccountsVoFiltered.isEmpty() ){
            txtFieldKeyWordsFiltersOnKeyReleased();
            tblViewAccounts.setPlaceholder(new Label("No se encuentra ninguna cuenta con el criterio de busqueda"));
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
                if(obsListTblAccountsVoFiltered != null && obsListTblAccountsVoFiltered.isEmpty() ){
                    tblViewAccounts.setPlaceholder(new Label("No se encuentra ninguna cuenta con el criterio de busqueda"));
                }
                crudService.deletetAccount(selectedAccount);
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
    
    @FXML
    private void txtFieldKeyWordsFiltersOnKeyReleased(){
        if(txtFieldKeyWordsFilters.getText() != null && !txtFieldKeyWordsFilters.getText().isBlank() ){
            String regexFilter = txtFieldKeyWordsFilters
                .getText()
                .trim()
                .replaceAll("\\s+", " ")
                .replace(" ", ".*")
                .concat(".*");

            obsListTblAccountsVoFiltered = obsListAccounts.filtered(it -> it.getAccount().matches(regexFilter) );
            tblViewAccounts.setItems(obsListTblAccountsVoFiltered);
        } else {
            tblViewAccounts.setItems(obsListAccounts);
        }
        tblViewAccounts.refresh();
    }

    /*@FXML
    private void menuItemOpenOnAction(ActionEvent event){
        // Crear un FileChooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar archivo");

        // Configurar filtros de extensiones
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Cuentas", "*.database")
        );

        // Mostrar el explorador de archivos
        File archivoSeleccionado = fileChooser.showOpenDialog(new Stage() );
            
        if (archivoSeleccionado != null) {
            System.out.println("Archivo seleccionado: " + archivoSeleccionado.getAbsolutePath());
        } else {
            System.out.println("No se seleccionó ningún archivo.");
        }
    }*/

    @FXML
    private void menuItemCloseOnAction(ActionEvent event){
        //HibernateUtil.shutdown("password");
        System.exit(0);
    }
}
