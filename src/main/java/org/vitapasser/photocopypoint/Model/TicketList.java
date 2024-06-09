package org.vitapasser.photocopypoint.Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public record TicketList(Connection connectionToDataBase) {

    public void setEndOrderTicket(long id) {
        try {
            String sql = """
                    UPDATE PhotocopyPoint.Ticket PT
                    SET PT.is_done = TRUE
                    WHERE PT.id = ?;""";

            PreparedStatement statement = connectionToDataBase.prepareStatement(sql);
            statement.setLong(1, id);

            checkAffectRows(statement.executeUpdate());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void setMadeOrderTicket(long id) {
        try {
            String sql = """
                    UPDATE PhotocopyPoint.Ticket PT
                    SET PT.isReady = TRUE
                    WHERE PT.id = ?;""";

            PreparedStatement statement = connectionToDataBase.prepareStatement(sql);
            statement.setLong(1, id);

            checkAffectRows(statement.executeUpdate());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public List<Ticket> getAllTickets() {
        try {
            String sql = """
                    SELECT PT.id,
                           PT.order_id,
                           GROUP_CONCAT(TS.name SEPARATOR ', ') AS all_names,
                           PT.isReady,
                           PT.is_done,
                           Ci.full_name,
                           Ci.phone_number
                    FROM PhotocopyPoint.Ticket PT
                             INNER JOIN PhotocopyPoint.`Order` PO ON PO.id = PT.order_id
                             INNER JOIN PhotocopyPoint.Client C ON PO.client_id = C.id
                             INNER JOIN PhotocopyPoint.Contact_info Ci ON C.contact_info_id = Ci.id
                             INNER JOIN PhotocopyPoint.TypeServiceOrder TSO ON PO.id = TSO.order_id
                             INNER JOIN PhotocopyPoint.TypeService TS ON TSO.type_service_id = TS.id
                    GROUP BY PT.id, PT.order_id, PT.isReady, Ci.full_name, Ci.phone_number
                    ORDER BY PT.id ASC;
                    """;

            PreparedStatement statement = connectionToDataBase.prepareStatement(sql);

            statement.executeQuery();

            return getTickets(statement);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;

    }

    public List<Ticket> getAllMadeTickets() {
        try {
            String sql = """
                    SELECT PT.id,
                           PT.order_id,
                           GROUP_CONCAT(TS.name SEPARATOR ', ') AS all_names,
                           PT.isReady,
                           PT.is_done,
                           Ci.full_name,
                           Ci.phone_number
                    FROM PhotocopyPoint.Ticket PT
                             INNER JOIN PhotocopyPoint.`Order` PO ON PO.id = PT.order_id
                             INNER JOIN PhotocopyPoint.Client C ON PO.client_id = C.id
                             INNER JOIN PhotocopyPoint.Contact_info Ci ON C.contact_info_id = Ci.id
                             INNER JOIN PhotocopyPoint.TypeServiceOrder TSO ON PO.id = TSO.order_id
                             INNER JOIN PhotocopyPoint.TypeService TS ON TSO.type_service_id = TS.id
                    where PT.isReady = TRUE and PT.is_done = FALSE
                    GROUP BY PT.id, PT.order_id, PT.isReady, PT.is_done, Ci.full_name, Ci.phone_number
                    ORDER BY PT.id ASC;
                    """;

            PreparedStatement statement = connectionToDataBase.prepareStatement(sql);

            statement.executeQuery();

            return getTickets(statement);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;

    }

    public List<Ticket> getAllNotMadeTickets(String fullNameStaff) {
        try {
            String sql = """
                    SELECT PT.id,
                           PT.order_id,
                           GROUP_CONCAT(TS.name SEPARATOR ', ') AS all_names,
                           PT.isReady,
                           PT.is_done,
                           Ci.full_name,
                           Ci.phone_number
                    FROM PhotocopyPoint.Ticket PT
                             INNER JOIN PhotocopyPoint.`Order` PO ON PO.id = PT.order_id
                             INNER JOIN PhotocopyPoint.Client C ON PO.client_id = C.id
                             INNER JOIN PhotocopyPoint.Contact_info Ci ON C.contact_info_id = Ci.id
                             INNER JOIN PhotocopyPoint.TypeServiceOrder TSO ON PO.id = TSO.order_id
                             INNER JOIN PhotocopyPoint.TypeService TS ON TSO.type_service_id = TS.id
                             inner join PhotocopyPoint.OrderStaff OS on PO.id = OS.order_id
                             inner join PhotocopyPoint.Staff S on OS.staff_id = S.id
                             inner join (select SCI.full_name, SCI.id from PhotocopyPoint.Contact_info SCI) as SCI on SCI.id = S.contact_info_id
                    where PT.isReady = FALSE and PT.is_done = FALSE and SCI.full_name = ?
                    GROUP BY PT.id, PT.order_id, PT.isReady, PT.is_done, Ci.full_name, Ci.phone_number
                    ORDER BY PT.id ASC;
                    """;

            PreparedStatement statement = connectionToDataBase.prepareStatement(sql);
            statement.setString(1, fullNameStaff);

            statement.executeQuery();

            return getTickets(statement);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;

    }

    private static List<Ticket> getTickets(PreparedStatement statement) throws SQLException {
        ResultSet resultSet = statement.getResultSet();

        List<Ticket> tickets = new ArrayList<>();

        while(resultSet.next()){
            tickets.add(new Ticket(resultSet.getLong("id"),
                    resultSet.getLong("order_id"),
                    resultSet.getString("all_names"),
                    resultSet.getBoolean("isReady"),
                    resultSet.getBoolean("is_done"),
                    resultSet.getString("full_name"),
                    resultSet.getString("phone_number")));
        }
        return tickets;
    }

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
