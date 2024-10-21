package org.vitapasser.photocopypoint.Controller.CreatingOrder;

import com.mysql.cj.util.StringUtils;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.vitapasser.photocopypoint.Controller.PaymentController;
import org.vitapasser.photocopypoint.Controller.DTO.TypeView;
import org.vitapasser.photocopypoint.Exception.NotExistTypeException;
import org.vitapasser.photocopypoint.MainApplication;
import org.vitapasser.photocopypoint.Model.Register;
import org.vitapasser.photocopypoint.Model.Type;
import org.vitapasser.photocopypoint.Model.TypeItem;

import java.io.IOException;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

public class Controller {

    ObservableList<TypeView> typeViewsListOfSelectableServices = FXCollections.observableArrayList();
    ObservableList<TypeView> typeViewsListSelectedServices = FXCollections.observableArrayList();
    Register register;

    @FXML
    private TableColumn<TypeView, Double> countMoneyTypeColumn;

    @FXML
    private TableColumn<TypeView, Double> countMoneyTypeColumn1;

    @FXML
    private TableColumn<TypeView, String> currencyMoneyTypeColumn;

    @FXML
    private TableColumn<TypeView, String> currencyMoneyTypeColumn1;

    @FXML
    private TableColumn<TypeView, Long> idTypeColumn;

    @FXML
    private TableColumn<TypeView, Long> idTypeColumn1;

    @FXML
    private TableColumn<TypeView, String> infoTypeColumn;

    @FXML
    private TableColumn<TypeView, String> infoTypeColumn1;

    @FXML
    private TableColumn<TypeView, String> nameTypeColumn;

    @FXML
    private TableColumn<TypeView, String> nameTypeColumn1;

    @FXML
    private TableColumn<TypeView, LocalTime> termTypeColumn;

    @FXML
    private TableColumn<TypeView, LocalTime> termTypeColumn1;

    @FXML
    protected TableColumn<TypeView, Integer> countTypeColumn1;

    @FXML
    protected TextField nameTypeService;
    @FXML
    protected TextField countTypeService;

    @FXML
    protected VBox clientVBox;
    @FXML
    protected TextField nameClient;
    @FXML
    protected TextField phoneNumberClient;

    @FXML
    protected Label resultPrice;


    @FXML
    protected Label resultTerm;


    @FXML
    protected Label nameNameTypeService;


    @FXML
    protected TableView<TypeView> listSelectedServices;

    @FXML
    protected TableView<TypeView> listOfSelectableServices;

    @FXML
    protected Button paymentButton;

    @FXML
    protected Button addTypeServiceButton;

    @FXML
    protected Label countTypeServiceLabel;

    @FXML
    protected Label phoneNumberLabel;

    @FXML
    protected void onChangeNameTypeServiceTextField() {
        changeTable(typeViewsListOfSelectableServices);
    }

    @FXML
    protected void onGetNameTypeServiceTableView() {
        TypeView typeView = listOfSelectableServices.getSelectionModel().getSelectedItem();
        if (typeView == null) return;
        nameTypeService.setText(typeView.getName());
    }

    @FXML
    protected void onWriteCheckTextField() {
        countTypeServiceLabel.setText("Кількість послуги");
        if (!StringUtils.isStrictlyNumeric(countTypeService.getText())) {
            addTypeServiceButton.setDisable(true);
            countTypeServiceLabel.setText("Кількість послуги| Введіть тільки числове значення!");
        } else {
            addTypeServiceButton.setDisable(false);
        }

    }

    private void changeTable(ObservableList<TypeView> list) {
        List<Type> types;
        nameNameTypeService.setText("Назва послуги");
        addTypeServiceButton.setDisable(false);
        try {
            types = register.getTypes(nameTypeService.getText());

            list.clear();
            types.forEach(type -> list.add(new TypeView(type)));
        } catch (NotExistTypeException e) {
            String string = "Назва послуги| " + e.getMessage();
            nameNameTypeService.setText(string);
            addTypeServiceButton.setDisable(true);
        }
    }

    private void initLoadTackedTable() {
        List<TypeItem> types = register.getOrderTypes();

        typeViewsListSelectedServices.clear();
        types.forEach(type -> typeViewsListSelectedServices.add(new TypeView(type)));
    }

    @FXML
    protected void onAddTypeServiceButtonClick() {
        if (nameTypeService.getText().isEmpty() || countTypeService.getText().isEmpty()) {
            return;
        }

        List<TypeItem> typeItem = register.addType(nameTypeService.getText(), Integer.parseInt(countTypeService.getText()));

        typeViewsListSelectedServices.clear();
        typeItem.forEach(typeItem1 -> typeViewsListSelectedServices.add(new TypeView(typeItem1)));
        clientVBox.setDisable(false);

        resultPrice.setText(register.getPrice().toString());
        resultTerm.setText(register.getTerm().toString());
    }

    @FXML
    protected void onWriteNamePhoneNumber() {
        if (!phoneNumberClient.getText().matches("^(\\+\\d{1,3}\\s?)?1?-?\\.?\\s?\\(?\\d{3}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{4}$")){
            phoneNumberLabel.setText("Номер телефону клієнта| Помилковий вираз!");
            paymentButton.setDisable(true);
            return;
        }
        phoneNumberLabel.setText("Номер телефону клієнта");

        paymentButton.setDisable(false);

    }

    private boolean isEmptyNamePhoneNumberClient() {
        return nameClient.getText().isEmpty() || phoneNumberClient.getText().isEmpty();
    }

    @FXML
    protected void onPaymentButtonClick(ActionEvent event) throws IOException {
        if (isEmptyNamePhoneNumberClient()) {
            return;
        }

        FXMLLoader FXMLLoader = new FXMLLoader(Objects.requireNonNull(
                MainApplication.class.getResource("payment.fxml")));
        Scene scene = new Scene(FXMLLoader.load());
        PaymentController controller = FXMLLoader.getController();
        controller.putData(register, nameClient.getText(), phoneNumberClient.getText());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Оплата замовлення");
        stage.show();
    }
    @FXML
    protected void onCancelButtonClick(ActionEvent event) throws IOException {
        FXMLLoader FXMLLoader = new FXMLLoader(Objects.requireNonNull(
                MainApplication.class.getResource("order-management.fxml")));
        Scene scene = new Scene(FXMLLoader.load());
        org.vitapasser.photocopypoint.Controller.OrderManagement.Controller controller = FXMLLoader.getController();
        controller.putData(register);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Менеждер замовлень");
        stage.show();
    }
    public void initialize() {
        Platform.runLater(() -> {
            listOfSelectableServices.setItems(typeViewsListOfSelectableServices);
            listSelectedServices.setItems(typeViewsListSelectedServices);
            typeViewsListOfSelectableServices.clear();
            typeViewsListSelectedServices.clear();

            idTypeColumn.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getId()));
            idTypeColumn1.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getId()));

            nameTypeColumn.setCellValueFactory (p -> new SimpleObjectProperty<>(p.getValue().getName()));
            nameTypeColumn1.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getName()));

            infoTypeColumn.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getInfo()));
            infoTypeColumn1.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getInfo()));

            termTypeColumn.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getTerm()));
            termTypeColumn1.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getTerm()));

            countMoneyTypeColumn.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getCountMoney()));
            countMoneyTypeColumn1.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getCountMoney()));

            currencyMoneyTypeColumn.setCellValueFactory (p -> new SimpleObjectProperty<>(p.getValue().getCurrencyMoney()));
            currencyMoneyTypeColumn1.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getCurrencyMoney()));

            countTypeColumn1.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getCount()));

            changeTable(typeViewsListOfSelectableServices);
            initLoadTackedTable();

        });

    }
    public void putData(Register register) {
        this.register = register;
    }
}
