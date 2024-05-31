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
        order.getTerm(typeList);
        PriceList priceList = new PriceList(connectionToDataBase);
        order.getPrice(priceList);
        String fullName = "ТЕСТДорно ДованоТЕСТ";
        order.setFullName(fullName);
        order.setPhoneNumber("+3809998748904");
        order.setOperator(new Operator(1L, "Петро Петренко"));

        Money pay = new Money(500.00, "UAH");

        Money odd_money = pickUpStation.fixSale(order, pay);
        Money odd_money_check = new Money(pay.count() - order.getPrice().count(), pay.unit());

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

            String fullNameCreated = sqlResult.getString("name");

            Boolean QueryTest = Objects.equals(fullNameCreated, fullName);

            return oddMoneyTest && QueryTest;
        } catch (Exception e)
        {
            System.out.println("Error on test 'FixSale': " +e.getMessage());
        }
        return false;
    }
}
