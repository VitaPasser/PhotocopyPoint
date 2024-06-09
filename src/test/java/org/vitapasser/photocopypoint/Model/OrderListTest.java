package org.vitapasser.photocopypoint.Model;

import org.junit.jupiter.api.Test;
import org.vitapasser.photocopypoint.Util.Mysql;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class OrderListTest {

    Connection connection = Mysql.getConnection();

    OrderListTest() throws SQLException, IOException {
    }

    @Test
    void getFullInfoAboutOrder() {
        OrderList orderList = new OrderList(connection);
        assertNotNull(orderList.getFullInfoAboutOrder(1));
    }
}