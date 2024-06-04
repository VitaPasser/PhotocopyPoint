package org.vitapasser.photocopypoint;

import javafx.application.Application;
import javafx.stage.Stage;
import org.vitapasser.photocopypoint.Test.EntryTests;
import org.vitapasser.photocopypoint.Util.Mysql;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class TestApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            System.out.println("Connection driver included!");

            try (Connection connection = Mysql.getConnection();) {

                new EntryTests(connection);

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        catch(Exception ex){
            System.out.println("Connection failed...");

            System.out.println(ex.getMessage());
        }

    }

    public static void main(String[] args) {
        launch();
    }
}