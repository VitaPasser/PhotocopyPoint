package org.vitapasser.photocopypoint.Controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.vitapasser.photocopypoint.Controller.OrderManagement.Controller;
import org.vitapasser.photocopypoint.MainApplication;
import org.vitapasser.photocopypoint.Model.Money;
import org.vitapasser.photocopypoint.Model.Register;
import org.vitapasser.photocopypoint.Model.Ticket;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class PaymentController {
    private Register register;
    String nameClient;
    String phoneNumberClient;

    ObservableList<Ticket> ticketsList = FXCollections.observableArrayList();


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
    Label resultOddMoney;

    @FXML
    Label resultPrice;

    @FXML
    Label payLabel;

    @FXML
    TextField countClientPayTextField;

    @FXML
    VBox oddPayVBox;

    @FXML
    Button acceptButton;

    @FXML
    Button cancelButton;

    @FXML
    private void onPaymentButtonClick(ActionEvent event) {
        if (countClientPayTextField.getText().isEmpty()) {
            payLabel.setText("До сплати| Введіть суму оплаченою клієнтом");
            return;
        }


        try {
            if (Double.parseDouble(countClientPayTextField.getText()) < register.getPrice().value()){
                payLabel.setText("До сплати| Не вистачає\n грошей для оплати!");
                return;
            } else {
                payLabel.setText("До сплати");
            }

            oddPayVBox.setDisable(false);

            resultOddMoney.setText(register.makePayment(
                    new Money(Double.parseDouble(countClientPayTextField.getText()), register.getPrice().unit()),
                    nameClient,
                    phoneNumberClient).toString());

            cancelButton.setDisable(true);
            acceptButton.setDisable(true);
            changeTable();
        } catch (NumberFormatException e) {
            payLabel.setText("До сплати| Використовуйте\n тільки числа та точку!");
        }
    }

    @FXML
    private void onOrderManagementButtonClick(ActionEvent event) throws IOException {

        FXMLLoader FXMLLoader = new FXMLLoader(Objects.requireNonNull(
                MainApplication.class.getResource("order-management.fxml")));
        Scene scene = new Scene(FXMLLoader.load());
        Controller controller = FXMLLoader.getController();
        controller.putData(register);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void onCancelButtonClick(ActionEvent event) throws IOException {

        FXMLLoader FXMLLoader = new FXMLLoader(Objects.requireNonNull(
                MainApplication.class.getResource("creating-order.fxml")));
        Scene scene = new Scene(FXMLLoader.load());
        org.vitapasser.photocopypoint.Controller.CreatingOrder.Controller controller = FXMLLoader.getController();
        controller.putData(register);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    private void changeTable() {
        List<Ticket> tickets = register.getAllTickets();

        ticketsList.clear();
        ticketsList.addAll(tickets);
    }

    @FXML
    private void initialize() {
        Platform.runLater(() -> {
            resultPrice.setText(register.getPrice().toString());

            listOfTicketsTableView.setItems(ticketsList);
            ticketsList.clear();

            idTicketColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

            fullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));

            phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

            isReadyColumn.setCellValueFactory(new PropertyValueFactory<>("isReady"));

            namesTypeServiceColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

            changeTable();
        });
    }

    public void putData(Register register, String nameClient, String phoneNumberClient) {
        this.register = register;
        this.nameClient = nameClient;
        this.phoneNumberClient = phoneNumberClient;
    }
}
