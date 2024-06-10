package org.vitapasser.photocopypoint.Controller.OrderManagement;

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


    @FXML
    TableView<Ticket> listOfTicketsTableView;

    public final Runnable changeValuesOnListOfTicketsTableView = () -> {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                int index = listOfTicketsTableView.getSelectionModel().getFocusedIndex();
                List<Ticket> tickets = register.getAllMadeTickets();
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
    private String fullNameStaff = "";

    @FXML
    protected void onStartCreateOrderButtonClick(ActionEvent event) throws IOException {
        register.newOrder();

        this.executor.shutdown();
        FXMLLoader FXMLLoader = new FXMLLoader(Objects.requireNonNull(
                MainApplication.class.getResource("creating-order.fxml")));
        Scene scene = new Scene(FXMLLoader.load());
        org.vitapasser.photocopypoint.Controller.CreatingOrder.Controller controller = FXMLLoader.getController();
        controller.putData(register);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Створення нового замовлення");
        stage.show();
    }

    @FXML
    protected void onGetMoreNotMadeOrdersButtonClick(ActionEvent event) throws IOException {

        this.executor.shutdown();
        FXMLLoader FXMLLoader = new FXMLLoader(Objects.requireNonNull(
                MainApplication.class.getResource("take-orders.fxml")));
        Scene scene = new Scene(FXMLLoader.load());
        org.vitapasser.photocopypoint.Controller.TakeOrders.Controller controller = FXMLLoader.getController();
        controller.putData(register, fullNameStaff);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Виконання існуючого замовлення");
        stage.show();
    }

    @FXML
    protected void onGetMoreInfoOrderButtonClick(ActionEvent event) throws IOException {
        if (ticketsList.isEmpty()) return;

        this.executor.shutdown();
        FXMLLoader FXMLLoader = new FXMLLoader(Objects.requireNonNull(
                MainApplication.class.getResource("take-more-info-order.fxml")));
        Scene scene = new Scene(FXMLLoader.load());
        org.vitapasser.photocopypoint.Controller.TakeMoreInfo.Controller controller = FXMLLoader.getController();
        controller.putData(register, listOfTicketsTableView.getSelectionModel().getSelectedItem().getId());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Оглядач виконаного замовлення");
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

            namesTypeServiceColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

            changeTable();
            this.executor.submit(changeValuesOnListOfTicketsTableView);
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