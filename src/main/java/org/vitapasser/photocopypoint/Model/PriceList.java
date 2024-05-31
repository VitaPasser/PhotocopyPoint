package org.vitapasser.photocopypoint.Model;

import org.vitapasser.photocopypoint.Util.Mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class PriceList {

    private final Connection connectionToDataBase;

    public PriceList(Connection connectionToDataBase) {
        this.connectionToDataBase = connectionToDataBase;
    }

    public Money getPrice(String typeName) {
        try {
            Statement statement = connectionToDataBase.createStatement();

            ResultSet sqlResult =  statement.executeQuery(
                    "SELECT PhotocopyPoint.Money.count, PhotocopyPoint.Money.unit, PhotocopyPoint.Price.create_time\n" +
                            "From PhotocopyPoint.Money \n" +
                            "inner join PhotocopyPoint.Price on PhotocopyPoint.Price.money_id = PhotocopyPoint.Money.id \n" +
                            "inner join PhotocopyPoint.TypeService on PhotocopyPoint.TypeService.price_id = PhotocopyPoint.Price.id \n" +
                            "where PhotocopyPoint.TypeService.name = \""+typeName+"\"\n" +
                            "ORDER BY PhotocopyPoint.Price.create_time DESC LIMIT 1;");
            sqlResult.next();
            return new Money(sqlResult.getDouble("count"),
                    sqlResult.getString("unit"));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }
}
