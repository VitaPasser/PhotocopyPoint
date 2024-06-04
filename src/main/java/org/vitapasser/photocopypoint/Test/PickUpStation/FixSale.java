package org.vitapasser.photocopypoint.Test.PickUpStation;

import org.vitapasser.photocopypoint.Model.*;
import org.vitapasser.photocopypoint.Test.AbstractTest;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;

public class FixSale extends AbstractTest {
    public FixSale(Connection connectionToDataBase) {
        super(connectionToDataBase);
    }

    @Override
    public boolean test() {
        PickUpStation pickUpStation = new PickUpStation(connectionToDataBase,
                "Київ, вул. Хрещатик, 1");
        Order order = Order.create();

        TypeList typeList = new TypeList(connectionToDataBase);
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

        try {
            Statement statement = connectionToDataBase.createStatement();

            ResultSet sqlResult = statement.executeQuery(sqlCheck);
            sqlResult.next();

            String fullNameCreated = sqlResult.getString("full_name");
            long orderId = sqlResult.getLong("id");

            Boolean QueryTest = Objects.equals(fullNameCreated, fullName) &&
                    Objects.equals(orderId, orderIDOddMoney.orderID());


            return oddMoneyTest && QueryTest;
        } catch (Exception e)
        {
            System.out.println("Error on test 'FixSale': " +e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}
