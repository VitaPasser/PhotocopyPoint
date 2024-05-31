package org.vitapasser.photocopypoint.Model;

import org.vitapasser.photocopypoint.Util.Mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class TypeList {

    private final Connection connectionToDataBase;

    public TypeList(Connection connectionToDataBase) {
        this.connectionToDataBase = connectionToDataBase;
    }

    public Type addType(String typeName, int count) {
        try {
            Statement statement = connectionToDataBase.createStatement();

            ResultSet sqlResult = statement.executeQuery(
                    "SELECT PhotocopyPoint.TypeService.id, PhotocopyPoint.TypeService.name, PhotocopyPoint.TypeService.info, PhotocopyPoint.TypeService.create_time From PhotocopyPoint.TypeService\n" +
                            "where PhotocopyPoint.TypeService.name = \"" + typeName + "\"\n" +
                            "ORDER BY PhotocopyPoint.TypeService.create_time DESC LIMIT 1;");
            sqlResult.next();
            return new Type(sqlResult.getLong("id"),
                    sqlResult.getString("name"),
                    sqlResult.getString("info"),
                    new Count(count),
                    Mysql.dbDateTimeToLocalDateTime(sqlResult.getString("create_time")));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public Term getTimePerType(String typeName) {
        try {
            Statement statement = connectionToDataBase.createStatement();

            ResultSet sqlResult =  statement.executeQuery(
                    "SELECT PhotocopyPoint.TypeService.term, PhotocopyPoint.TypeService.create_time\n" +
                            "From PhotocopyPoint.TypeService \n" +
                            "where TypeService.name = \""+typeName+"\" \n" +
                            "ORDER BY PhotocopyPoint.TypeService.create_time DESC LIMIT 1;\n");

            sqlResult.next();
            return new Term(sqlResult.getTime("term").toLocalTime().toSecondOfDay());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }
}
