package org.vitapasser.photocopypoint.Controller.TakeOrders;

import javafx.application.Platform;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.vitapasser.photocopypoint.MainApplication;
import org.vitapasser.photocopypoint.Model.Register;
import org.vitapasser.photocopypoint.Model.Ticket;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Controller {
    ObservableList<Ticket> ticketsList = FXCollections.observableArrayList();

    private Register register;
    private String fullNameStaff = "";


    @FXML
    TableView<Ticket> listOfTicketsTableView;

    public final Runnable changeValuesOnListOfTicketsTableView = () -> {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                int index = listOfTicketsTableView.getSelectionModel().getFocusedIndex();
                List<Ticket> tickets = register.getAllNotMadeTickets(fullNameStaff);
                ticketsList.clear();
                ticketsList.addAll(tickets);
                listOfTicketsTableView.getSelectionModel().select(index);
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    };

    private final ExecutorService executor = Executors.newSingleThreadExecutor(runnable -> {
        Thread t = new Thread(runnable);
        t.setDaemon(true);
        return t ;
    });

    @FXML
    private TableColumn<Ticket, String> fullNameColumn;

    @FXML
    private TableColumn<Ticket, Long> idTicketColumn;

    @FXML
    private TableColumn<Ticket, String> namesTypeServiceColumn;

    @FXML
    private TableColumn<Ticket, String> phoneNumberColumn;

    @FXML
    private TextField fullNameTextField;

    @FXML
    private Button takeMakeOrderButton;

    @FXML
    protected void onTakeMakeOrderButtonClick(ActionEvent event) throws IOException {
        if (ticketsList.isEmpty() || Objects.equals(fullNameStaff, "")) return;

        this.executor.shutdown();
        FXMLLoader FXMLLoader = new FXMLLoader(Objects.requireNonNull(
                MainApplication.class.getResource("take-more-info-order-for-make.fxml")));
        Scene scene = new Scene(FXMLLoader.load());
        org.vitapasser.photocopypoint.Controller.TakeMoreInfoForMake.Controller controller = FXMLLoader.getController();
        controller.putData(register, listOfTicketsTableView.getSelectionModel().getSelectedItem().id(), fullNameStaff);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Оглядач не виготовленого замовлення");
        stage.show();
    }

    @FXML
    protected void onCancelButtonClick(ActionEvent event) throws IOException {
        FXMLLoader FXMLLoader = new FXMLLoader(Objects.requireNonNull(
                MainApplication.class.getResource("order-management.fxml")));
        Scene scene = new Scene(FXMLLoader.load());
        org.vitapasser.photocopypoint.Controller.OrderManagement.Controller controller = FXMLLoader.getController();
        controller.putData(register, fullNameStaff);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Менеждер замовлень");
        stage.show();
    }

    @FXML
    protected void onAuthorizationButtonClick(ActionEvent event) throws IOException {
        authorization();
    }

    private void authorization()
    {
        fullNameStaff = fullNameTextField.getText();
        if(fullNameStaff.isEmpty()) return;

        changeTable();
        this.executor.submit(changeValuesOnListOfTicketsTableView);
        takeMakeOrderButton.setDisable(false);
    }

    private void changeTable() {
        List<Ticket> tickets = register.getAllNotMadeTickets(fullNameStaff);

        ticketsList.clear();
        ticketsList.addAll(tickets);
    }
    public void initialize() {
        Platform.runLater(() -> {

            listOfTicketsTableView.setItems(ticketsList);
            ticketsList.clear();

            idTicketColumn.setCellValueFactory(
                    p -> new SimpleLongProperty(p.getValue().id()).asObject());

            fullNameColumn.setCellValueFactory(
                    p-> new SimpleStringProperty(p.getValue().fullName()));

            phoneNumberColumn.setCellValueFactory(
                    p -> new SimpleStringProperty(p.getValue().phoneNumber()));

            namesTypeServiceColumn.setCellValueFactory(
                    p -> new SimpleStringProperty(p.getValue().name()));

            fullNameTextField.setText(fullNameStaff);
            authorization();

        });
    }
    public void putData(Register register) {
        this.register = register;
    }
    public void putData(Register register, String fullNameStaff) {
        this.register = register;
        this.fullNameStaff = fullNameStaff;
    }
}
