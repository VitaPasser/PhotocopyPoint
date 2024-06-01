package org.vitapasser.photocopypoint.Model;

import java.sql.*;
import java.util.Objects;

public record TicketList(Connection connectionToDataBase) {

    public long createTicket(long idOrder) {
        long ticketId = 0;
        try {
            ticketId = insertTicket(idOrder);

            return ticketId;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return ticketId;
    }

    private long insertTicket(long idOrder) throws SQLException {
        StringBuilder sql;
        PreparedStatement statement;
        sql = new StringBuilder("INSERT INTO `PhotocopyPoint`.Ticket(order_id) VALUES (?);\n");
        statement = connectionToDataBase.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
        statement.setLong(1, idOrder);

        checkAffectRows(statement.executeUpdate());
        return getId(statement);
    }

    private static long getId(PreparedStatement statement) throws SQLException {
        long id;
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return generatedKeys.getLong(1);
            }
            else {
                throw new SQLException("Creating failed, no ID obtained.");
            }
        }
    }

    private static void checkAffectRows(int affectedRows) throws SQLException {
        if (affectedRows == 0) {
            throw new SQLException("Creating failed, no rows affected.");
        }
    }
}
