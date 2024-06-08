package org.vitapasser.photocopypoint.Controller.TakeMoreInfo;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.vitapasser.photocopypoint.MainApplication;
import org.vitapasser.photocopypoint.Model.Register;

import java.io.IOException;
import java.util.Objects;

public class Controller {
    private Register register;
    private long OrderID;

    private ListView<String> listInfo;

    @FXML
    protected void onOrderManagementButtonClick(ActionEvent event) throws IOException {
        FXMLLoader FXMLLoader = new FXMLLoader(Objects.requireNonNull(
                MainApplication.class.getResource("order-management.fxml")));
        Scene scene = new Scene(FXMLLoader.load());
        org.vitapasser.photocopypoint.Controller.OrderManagement.Controller controller = FXMLLoader.getController();
        controller.putData(register);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    public void initialize() {
        Platform.runLater(() -> {
            //listInfo
        });
    }
    public void putData(Register register, long OrderID) {
        this.register = register;
        this.OrderID = OrderID;
    }
}
