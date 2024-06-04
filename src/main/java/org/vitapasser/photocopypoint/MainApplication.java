package org.vitapasser.photocopypoint;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.vitapasser.photocopypoint.Controller.OrderManagementController;
import org.vitapasser.photocopypoint.Model.PickUpStation;
import org.vitapasser.photocopypoint.Model.Register;
import org.vitapasser.photocopypoint.Model.TicketList;
import org.vitapasser.photocopypoint.Model.TypeList;
import org.vitapasser.photocopypoint.Util.Mysql;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

public class MainApplication extends Application {

    Connection connection;

    @Override
    public void start(Stage stage){

        try{
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            System.out.println("Connection driver included!");

            try {
                connection = Mysql.getConnection();

                TypeList typeList = new TypeList(connection);
                PickUpStation pickUpStation = new PickUpStation(connection,
                        "Київ, вул. Хрещатик, 1");
                TicketList ticketList = new TicketList(connection);

                Register register = new Register(typeList, pickUpStation, ticketList);

                FXMLLoader FXMLLoader = new FXMLLoader(Objects.requireNonNull(
                        MainApplication.class.getResource("order-management.fxml")));
                Scene scene = new Scene(FXMLLoader.load());
                OrderManagementController controller = FXMLLoader.getController();
                controller.putData(register);
                stage.setTitle("Hello!");
                stage.setScene(scene);
                stage.show();

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        catch(Exception ex){
            System.out.println("Connection failed...");

            System.out.println(ex.getMessage());
        }

    }

    @Override
    public void stop() throws SQLException {
        connection.close();
    }

    public static void main(String[] args) {
        launch();
    }
}