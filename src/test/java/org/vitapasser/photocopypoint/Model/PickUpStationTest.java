package org.vitapasser.photocopypoint.Model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.vitapasser.photocopypoint.Util.Mysql;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class PickUpStationTest {

    Connection connection = Mysql.getConnection();

    PickUpStationTest() throws SQLException, IOException {
    }

    @Test
    void getOperator() {
        PickUpStation pickUpStation = new PickUpStation(connection,
                "Київ, вул. Хрещатик, 1");
        Operator operatorGet = pickUpStation.getOperator();

        Operator operatorReference = new Operator(2L, "Петро Петренко");

        Assertions.assertEquals(operatorReference, operatorGet);
    }

    @Test
    void fixSale() {
        PickUpStation pickUpStation = new PickUpStation(connection,
                "Київ, вул. Хрещатик, 1");
        Order order = Order.create();

        TypeList typeList = new TypeList(connection);
        order.addType("Друк", typeList, 3);
        order.getTerm();
        order.getPrice();
        String fullName = "ТЕСТДорно ДованоТЕСТ";
        order.setFullName(fullName);
        order.setPhoneNumber("+3809998748904");
        order.setOperator(new Operator(1L, "Петро Петренко"));

        Money pay = new Money(500.00, "UAH");

        OrderIDAndOddMany orderIDOddMoney = pickUpStation.fixSale(order, pay);
        assert orderIDOddMoney != null;
        Money odd_money = orderIDOddMoney.oddMoney();
        Money odd_money_check = new Money(pay.value() - order.getPrice().value(), pay.unit());

        Boolean oddMoneyTest = Objects.equals(odd_money, odd_money_check);

        String sqlCheck = "SELECT `PhotocopyPoint`.`Contact_info`.`full_name`, `PhotocopyPoint`.`Order`.id FROM `PhotocopyPoint`.`Contact_info`\n" +
                          "INNER JOIN `PhotocopyPoint`.Client on `PhotocopyPoint`.Client.contact_info_id = `PhotocopyPoint`.`Contact_info`.`id`\n" +
                          "INNER JOIN `PhotocopyPoint`.`Order` on `PhotocopyPoint`.`Order`.`client_id` = `PhotocopyPoint`.Client.id\n" +
                          "WHERE `PhotocopyPoint`.`Contact_info`.`full_name` = '"+fullName+"'\n" +
                          "GROUP BY `PhotocopyPoint`.`Order`.id ORDER BY `PhotocopyPoint`.`Order`.id DESC LIMIT 1;";

        String fullNameCreated = null;
        long orderId = -1;
        try {
            Statement statement = connection.createStatement();

            ResultSet sqlResult = statement.executeQuery(sqlCheck);
            sqlResult.next();

            fullNameCreated = sqlResult.getString("full_name");
            orderId = sqlResult.getLong("id");


        } catch (Exception e)
        {
            System.out.println("Error on test 'FixSale': " +e.getMessage());
            e.printStackTrace();
        }

        Assertions.assertEquals(fullNameCreated, fullName);
        Assertions.assertEquals(orderId, orderIDOddMoney.orderID());
        Assertions.assertEquals(odd_money, odd_money_check);
    }
}