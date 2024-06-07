package org.vitapasser.photocopypoint.Controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.vitapasser.photocopypoint.Controller.CreatingOrder.Controller;
import org.vitapasser.photocopypoint.MainApplication;
import org.vitapasser.photocopypoint.Model.Register;
import org.vitapasser.photocopypoint.Model.Ticket;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class OrderManagementController {

    ObservableList<Ticket> ticketsList = FXCollections.observableArrayList();

    private Register register;

    @FXML
    TableView<Ticket> listOfTicketsTableView;

    @FXML
    private TableColumn<Ticket, String> fullNameColumn;

    @FXML
    private TableColumn<Ticket, Long> idTicketColumn;

    @FXML
    private TableColumn<Ticket, Boolean> isReadyColumn;

    @FXML
    private TableColumn<Ticket, String> namesTypeServiceColumn;

    @FXML
    private TableColumn<Ticket, String> phoneNumberColumn;

    @FXML
    protected void onStartCreateOrderButtonClick(ActionEvent event) throws IOException {
        register.newOrder();

        FXMLLoader FXMLLoader = new FXMLLoader(Objects.requireNonNull(
                MainApplication.class.getResource("creating-order.fxml")));
        Scene scene = new Scene(FXMLLoader.load());
        Controller controller = FXMLLoader.getController();
        controller.putData(register);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    private void changeTable() {
        List<Ticket> tickets = register.getAllMadeTickets();

        ticketsList.clear();
        ticketsList.addAll(tickets);
    }
    public void initialize() {
        Platform.runLater(() -> {

            listOfTicketsTableView.setItems(ticketsList);
            ticketsList.clear();

            idTicketColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

            fullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));

            phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

            isReadyColumn.setCellValueFactory(new PropertyValueFactory<>("isReady"));

            namesTypeServiceColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

            changeTable();
        });}
    public void putData(Register register) {
        this.register = register;
    }
}