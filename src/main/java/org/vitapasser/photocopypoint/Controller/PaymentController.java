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
import org.vitapasser.photocopypoint.Controller.DTO.TypeView;
import org.vitapasser.photocopypoint.Controller.OrderManagement.Controller;
import org.vitapasser.photocopypoint.MainApplication;
import org.vitapasser.photocopypoint.Model.*;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class PaymentController {
    private Register register;
    String nameClient;
    String phoneNumberClient;

    ObservableList<Ticket> ticketsList = FXCollections.observableArrayList();

    ObservableList<TypeView> typeViewsListSelectedServices = FXCollections.observableArrayList();

    @FXML
    private TableColumn<TypeView, Double> countMoneyTypeColumn1;

    @FXML
    private TableColumn<TypeView, String> currencyMoneyTypeColumn1;

    @FXML
    private TableColumn<TypeView, Long> idTypeColumn1;

    @FXML
    private TableColumn<TypeView, String> infoTypeColumn1;

    @FXML
    private TableColumn<TypeView, String> nameTypeColumn1;

    @FXML
    private TableColumn<TypeView, String> termTypeColumn1;

    @FXML
    protected TableColumn<TypeView, Integer> countTypeColumn1;

    @FXML
    protected TableView<TypeView> listSelectedServices;

    private void initLoadTackedTable() {
        List<TypeItem> types = register.getOrderTypes();

        typeViewsListSelectedServices.clear();
        types.forEach(type -> typeViewsListSelectedServices.add(new TypeView(type)));
    }

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
        stage.setTitle("Створення нового замовлення");
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
        stage.setTitle("Менеждер замовлень");
        stage.show();
    }

    @FXML
    private void initialize() {
        Platform.runLater(() -> {
            resultPrice.setText(register.getPrice().toString());
            listSelectedServices.setItems(typeViewsListSelectedServices);
            typeViewsListSelectedServices.clear();

            idTypeColumn1.setCellValueFactory(new PropertyValueFactory<>("id"));

            nameTypeColumn1.setCellValueFactory(new PropertyValueFactory<>("name"));

            infoTypeColumn1.setCellValueFactory(new PropertyValueFactory<>("info"));

            termTypeColumn1.setCellValueFactory(new PropertyValueFactory<>("term"));

            countMoneyTypeColumn1.setCellValueFactory(new PropertyValueFactory<>("countMoney"));

            currencyMoneyTypeColumn1.setCellValueFactory(new PropertyValueFactory<>("currencyMoney"));

            countTypeColumn1.setCellValueFactory(new PropertyValueFactory<>("count"));

            initLoadTackedTable();
        });
    }

    public void putData(Register register, String nameClient, String phoneNumberClient) {
        this.register = register;
        this.nameClient = nameClient;
        this.phoneNumberClient = phoneNumberClient;
    }

}
