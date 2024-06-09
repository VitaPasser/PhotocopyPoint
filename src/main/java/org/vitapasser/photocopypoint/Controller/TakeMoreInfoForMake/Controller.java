package org.vitapasser.photocopypoint.Controller.TakeMoreInfoForMake;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.vitapasser.photocopypoint.Controller.TakeMoreInfo.TypeView;
import org.vitapasser.photocopypoint.MainApplication;
import org.vitapasser.photocopypoint.Model.OrderViewAndTypesViews;
import org.vitapasser.photocopypoint.Model.Register;

import java.io.IOException;
import java.util.Objects;

public class Controller {
    private Register register;
    OrderViewAndTypesViews orderView;
    private long OrderID;
    private String fullNameStaff;
    private ObservableList<String> orderItems = FXCollections.observableArrayList();
    private ObservableList<TypeView> typeServices = FXCollections.observableArrayList();

    @FXML
    private TableColumn<TypeView, String> costTypeService;

    @FXML
    private TableColumn<TypeView, String> countTypeService;

    @FXML
    private TableColumn<TypeView, String> idTypeService;

    @FXML
    private TableColumn<TypeView, String> infoTypeService;

    @FXML
    private TableColumn<TypeView, String> nameTypeService;

    @FXML
    private TableColumn<TypeView, String> termTypeService;

    @FXML
    private ListView<String> listInfo;

    @FXML
    private TableView<TypeView> typeTableView;

    @FXML
    protected void onSetGiveTheOrderButtonClick(ActionEvent event) throws IOException {
        register.madeTheOrder(orderView.orderView().idTicket());
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

    @FXML
    protected void onTakeOrderButtonClick(ActionEvent event) throws IOException {
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
    public void initialize() {
        Platform.runLater(() -> {
            orderView = register.getFullInfoAboutOrder(OrderID);
            orderItems.add("Ідентифікатор замовлення: " + String.valueOf(orderView.orderView().idOrder()));
            orderItems.add("Ідентифікатор квитка: " + String.valueOf(orderView.orderView().idTicket()));
            orderItems.add(orderView.orderView().isReady());
            orderItems.add(orderView.orderView().clientFullName());
            orderItems.add(orderView.orderView().clientPhoneNumber());
            orderItems.add(orderView.orderView().staffFullName());
            orderItems.add(orderView.orderView().staffPhoneNumber());
            orderItems.add(orderView.orderView().staffSpecialization());
            orderItems.add(orderView.orderView().addressMake());
            orderItems.add(orderView.orderView().money());
            orderItems.add(orderView.orderView().term());
            orderItems.add(orderView.orderView().stationName());
            listInfo.setItems(orderItems);
            typeServices.clear();
            typeServices.addAll(orderView.typeViews());

            idTypeService.setCellValueFactory(new PropertyValueFactory<>("id"));

            nameTypeService.setCellValueFactory(new PropertyValueFactory<>("name"));

            infoTypeService.setCellValueFactory(new PropertyValueFactory<>("info"));

            termTypeService.setCellValueFactory(new PropertyValueFactory<>("term"));

            countTypeService.setCellValueFactory(new PropertyValueFactory<>("count"));

            costTypeService.setCellValueFactory(new PropertyValueFactory<>("money"));
            typeTableView.setItems(typeServices);
        });
    }
    public void putData(Register register, long OrderID, String fullNameStaff) {
        this.register = register;
        this.OrderID = OrderID;
        this.fullNameStaff = fullNameStaff;
    }
}
