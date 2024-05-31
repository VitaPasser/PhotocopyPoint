package org.vitapasser.photocopypoint.Test.TypeList;

import org.vitapasser.photocopypoint.Model.Term;
import org.vitapasser.photocopypoint.Model.TypeList;
import org.vitapasser.photocopypoint.Test.AbstractTest;

import java.sql.Connection;
import java.util.Objects;

public class GetTimePerTime extends AbstractTest {
    public GetTimePerTime(Connection connectionToDataBase) {
        super(connectionToDataBase);
    }

    @Override
    public boolean test() {
        TypeList typeList = new TypeList(connectionToDataBase);
        Term termGot = typeList.getTimePerType("Копіювання");

        Term termReference = new Term(150);

        return Objects.equals(termGot, termReference);
    }
}
