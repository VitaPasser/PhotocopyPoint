package org.vitapasser.photocopypoint.Model;

import org.vitapasser.photocopypoint.Exception.NotExistTypeException;
import org.vitapasser.photocopypoint.Util.Mysql;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TypeList {

    private final Connection connectionToDataBase;

    public TypeList(Connection connectionToDataBase) {
        this.connectionToDataBase = connectionToDataBase;
    }

    public List<Type> getTypes(String likeNameTypes) throws NotExistTypeException {

        List<Type> types = new ArrayList<>();

        try {
            String sql = """
                    SELECT TS.name,
                           TS.id,
                           TS.info,
                           TS.term,
                           PM.count,
                           PM.unit,
                           TS.create_time
                    FROM PhotocopyPoint.TypeService TS
                    INNER JOIN (
                        SELECT name, MAX(create_time) AS max_create_time
                        FROM PhotocopyPoint.TypeService
                        GROUP BY name
                    ) AS latest_services
                    ON TS.name = latest_services.name AND TS.create_time = latest_services.max_create_time
                    INNER JOIN PhotocopyPoint.Price PP ON TS.price_id = PP.id
                    INNER JOIN PhotocopyPoint.Money PM ON PP.money_id = PM.id
                    WHERE TS.name LIKE CONCAT('%', ?, '%')
                    ORDER BY TS.id DESC;
                    """;

            executeQuery(likeNameTypes, sql, types);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        if (types.isEmpty()) {
            throw new NotExistTypeException("Помилкова назва послуги!");
        }

        return types;

    }

    public Type getType(String typeName) {
        try {
            String sql = """
                    SELECT TS.id,
                           TS.name,
                           TS.info,
                           TS.term,
                           PM.count,
                           PM.unit,
                           TS.create_time
                    From PhotocopyPoint.Money PM
                    inner join PhotocopyPoint.Price PP on PM.id = PP.money_id
                    inner join PhotocopyPoint.TypeService TS on PP.id = TS.price_id
                    where TS.name like CONCAT('%', ?, '%')
                    ORDER BY TS.create_time DESC LIMIT 1;""";

            return executeQuery(typeName, sql, new ArrayList<>()).getFirst();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }


    private List<Type> executeQuery(String likeNameTypes, String sql, List<Type> types) throws SQLException {
        PreparedStatement statement = connectionToDataBase.prepareStatement(sql);
        statement.setString(1, likeNameTypes);

        statement.executeQuery();

        ResultSet resultSet = statement.getResultSet();

        while(resultSet.next()){
            types.add(new Type(resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getString("info"),
                    new Term(resultSet.getTime("term").toLocalTime().toSecondOfDay()),
                    new Money(resultSet.getDouble("count"), resultSet.getString("unit")),
                    Mysql.dbDateTimeToLocalDateTime(resultSet.getString("create_time"))));
        }

        return types;
    }
}
