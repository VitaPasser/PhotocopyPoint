package org.vitapasser.photocopypoint.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.vitapasser.photocopypoint.Controller.CreatingOrder.Controller;
import org.vitapasser.photocopypoint.MainApplication;
import org.vitapasser.photocopypoint.Model.Register;

import java.io.IOException;
import java.util.Objects;

public class OrderManagementController {

    private Register register;

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
    public void initialize() {}
    public void putData(Register register) {
        this.register = register;
    }
}