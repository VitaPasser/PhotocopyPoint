module org.vitapasser.photocopypoint {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires mysql.connector.j;
    requires java.sql;

    opens org.vitapasser.photocopypoint to javafx.fxml;
    exports org.vitapasser.photocopypoint;
    exports org.vitapasser.photocopypoint.Exception;
    exports org.vitapasser.photocopypoint.Controller;
    opens org.vitapasser.photocopypoint.Controller to javafx.fxml;
    exports org.vitapasser.photocopypoint.Model;
    opens org.vitapasser.photocopypoint.Model to javafx.fxml;
    exports org.vitapasser.photocopypoint.Controller.CreatingOrder;
    opens org.vitapasser.photocopypoint.Controller.CreatingOrder to javafx.fxml;
    exports org.vitapasser.photocopypoint.Controller.OrderManagement;
    opens org.vitapasser.photocopypoint.Controller.OrderManagement to javafx.fxml;
    exports org.vitapasser.photocopypoint.Controller.TakeMoreInfo;
    opens org.vitapasser.photocopypoint.Controller.TakeMoreInfo to javafx.fxml;
    exports org.vitapasser.photocopypoint.Controller.TakeMoreInfoForMake;
    opens org.vitapasser.photocopypoint.Controller.TakeMoreInfoForMake to javafx.fxml;
    exports org.vitapasser.photocopypoint.Controller.DTO;
    opens org.vitapasser.photocopypoint.Controller.DTO to javafx.fxml;
    exports org.vitapasser.photocopypoint.Controller.TakeOrders;
    opens org.vitapasser.photocopypoint.Controller.TakeOrders to javafx.fxml;
}