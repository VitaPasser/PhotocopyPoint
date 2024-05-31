package org.vitapasser.photocopypoint.Test;

import java.sql.Connection;

public abstract class AbstractTest implements Test {
    protected Connection connectionToDataBase;

    public AbstractTest(Connection connectionToDataBase) {
        this.connectionToDataBase = connectionToDataBase;
    }
}
