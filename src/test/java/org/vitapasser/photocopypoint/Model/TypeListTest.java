package org.vitapasser.photocopypoint.Model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.vitapasser.photocopypoint.Exception.NotExistTypeException;
import org.vitapasser.photocopypoint.Util.Mysql;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

class TypeListTest {

    Connection connection = Mysql.getConnection();

    TypeListTest() throws SQLException, IOException {
    }

    @Test
    void getTypes(){
        TypeList typeList = new TypeList(connection);
        List<Type> typesGet = null;
        try {
            typesGet = typeList.getTypes("д");
        } catch (NotExistTypeException e) {
            throw new RuntimeException(e);
        }

        List<Type> typesReference = new ArrayList<Type>();

        typesReference.add(new Type(5L, "Друк", "Чорно-білий друк",
                        new Term(60), new Money(100.0000, "UAH"), Mysql.dbDateTimeToLocalDateTime("2024-05-27 11:48:27")));

        Assertions.assertEquals(typesReference, typesGet);

    }

    @Test
    void addType(){
        TypeList typeList = new TypeList(connection);
        Type typeGot = typeList.getType("Копіювання");

        Type typeReference = new Type(2L, "Копіювання", "Кольорове копіювання",
                new Term(150), new Money(200.0000, "UAH"), Mysql.dbDateTimeToLocalDateTime("2024-05-27 11:48:24"));

        Assertions.assertEquals(typeGot, typeReference);

    }
}