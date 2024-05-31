package org.vitapasser.photocopypoint.Test.Sql;

import org.vitapasser.photocopypoint.Model.Count;
import org.vitapasser.photocopypoint.Model.Type;
import org.vitapasser.photocopypoint.Test.AbstractTest;
import org.vitapasser.photocopypoint.Util.Mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;

public class Get extends AbstractTest {

    public Get(Connection connectionToDataBase) {
        super(connectionToDataBase);
    }

    @Override
    public boolean test() {
        try {
            Statement statement = connectionToDataBase.createStatement();

            ResultSet sqlResult = statement.executeQuery(
                    "SELECT * FROM PhotocopyPoint.TypeService where PhotocopyPoint.TypeService.id = 2;");

            Type typeGot = null;

            sqlResult.next();
            typeGot = new Type(sqlResult.getLong("id"),
                    sqlResult.getString("name"),
                    sqlResult.getString("info"),
                    new Count(35),
                    Mysql.dbDateTimeToLocalDateTime(sqlResult.getString("create_time")));


            Type typeReference = new Type(2L, "Копіювання", "Кольорове копіювання",
                    new Count(35), Mysql.dbDateTimeToLocalDateTime("2024-05-27T11:48:24"));

            return Objects.equals(typeGot, typeReference);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return false;
    }
}
