package org.vitapasser.photocopypoint.Test.PickUpStation;

import org.vitapasser.photocopypoint.Model.Operator;
import org.vitapasser.photocopypoint.Model.PickUpStation;
import org.vitapasser.photocopypoint.Test.AbstractTest;

import java.sql.Connection;
import java.util.Objects;

public class GetOperator extends AbstractTest {
    public GetOperator(Connection connectionToDataBase) {
        super(connectionToDataBase);
    }

    @Override
    public boolean test() {
        PickUpStation pickUpStation = new PickUpStation(connectionToDataBase,
                "Київ, вул. Хрещатик, 1");
        Operator operatorGet = pickUpStation.getOperator();

        Operator operatorReference = new Operator(2L, "Петро Петренко");

        return Objects.equals(operatorGet, operatorReference);
    }
}
