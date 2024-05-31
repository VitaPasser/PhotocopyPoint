package org.vitapasser.photocopypoint.Test.TypeList;

import org.vitapasser.photocopypoint.Model.Count;
import org.vitapasser.photocopypoint.Model.Type;
import org.vitapasser.photocopypoint.Model.TypeList;
import org.vitapasser.photocopypoint.Test.AbstractTest;
import org.vitapasser.photocopypoint.Util.Mysql;

import java.sql.Connection;
import java.util.Objects;

public class GetType extends AbstractTest {
    public GetType(Connection connectionToDataBase) {
        super(connectionToDataBase);
    }

    @Override
    public boolean test() {
        TypeList typeList = new TypeList(connectionToDataBase);
        Type typeGot = typeList.addType("Копіювання", 35);

        Type typeReference = new Type(2L, "Копіювання", "Кольорове копіювання",
                new Count(35), Mysql.dbDateTimeToLocalDateTime("2024-05-27 11:48:24"));

        return Objects.equals(typeGot, typeReference);
    }
}
