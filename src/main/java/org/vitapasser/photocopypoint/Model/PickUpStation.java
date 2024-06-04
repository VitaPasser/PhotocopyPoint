package org.vitapasser.photocopypoint.Model;

import java.sql.*;
import java.util.Objects;

public record PickUpStation(Connection connectionToDataBase, String address) {


    /*
     * Через запрос у базу даних отримуються вільний оператор
     * та повератється методом. (return null - є заглушка)
     */
    public Operator getOperator() {
        try {
            Statement statement = connectionToDataBase.createStatement();

            ResultSet sqlResult =  statement.executeQuery(
                    "SELECT PhotocopyPoint.Contact_info.id, PhotocopyPoint.Contact_info.full_name\n" +
                            "From PhotocopyPoint.Contact_info \n" +
                            "inner join PhotocopyPoint.Staff on PhotocopyPoint.Staff.contact_info_id " +
                            "= PhotocopyPoint.Contact_info.id \n" +
                            "inner join PhotocopyPoint.PickUpStationStaff on " +
                            "PhotocopyPoint.PickUpStationStaff.staff_id = PhotocopyPoint.Staff.id\n" +
                            "left join PhotocopyPoint.OrderStaff on " +
                            "PhotocopyPoint.OrderStaff.staff_id = ALL(" +
                            "Select PhotocopyPoint.Staff.id from PhotocopyPoint.Staff " +
                            "inner join PhotocopyPoint.PickUpStation " +
                            "on PhotocopyPoint.PickUpStation.id = " +
                            "PhotocopyPoint.PickUpStationStaff.pickUpStation_id " +
                            "WHERE PhotocopyPoint.PickUpStation.address = \""+address+"\" )\n" +
                            "left join PhotocopyPoint.`Order` on PhotocopyPoint.`Order`.id = " +
                            "PhotocopyPoint.OrderStaff.order_id\n" +
                            "left join PhotocopyPoint.TypeServiceOrder " +
                            "on PhotocopyPoint.TypeServiceOrder.id = PhotocopyPoint.`Order`.`id`\n" +
                            "where (PhotocopyPoint.TypeServiceOrder.term < NOW() or " +
                            "(PhotocopyPoint.TypeServiceOrder.term is null)) LIMIT 1;");

            sqlResult.next();
            return new Operator(sqlResult.getLong("id"),
                    sqlResult.getString("full_name"));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    /*
     * Через запрос у базу даних отправляється замовлення.
     * Також метод рахує здачу. (return null - є заглушка)
     */
    public OrderIDAndOddMany fixSale(Order order, Money countPayMoney) {
        try {

            long idMoney = insertMoney(order);
            long idContactInfo = insertContactInfo(order);
            long idClient = insertClient(idContactInfo);
            long idOrder = insertOrder(idMoney, idClient);
            order.getTypeItems().forEach(type -> {
                try {
                    insertTypeServiceOrder(idOrder, type, order.getTerm());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            insertOrderStaff(idOrder, order.getOperator());

            Money oddMoney = null;
            Money orderPrice = order.getPrice();
            if (Objects.equals(orderPrice.unit(), countPayMoney.unit()))
                oddMoney = new Money(countPayMoney.value() - orderPrice.value(),
                        orderPrice.unit());

            return new OrderIDAndOddMany(idOrder, oddMoney);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    private void insertOrderStaff(long idOrder, Operator operator) throws SQLException {
        StringBuilder sql;
        PreparedStatement statement;
        sql = new StringBuilder("INSERT INTO `PhotocopyPoint`.OrderStaff(order_id, staff_id, specialization) " +
                "VALUES (?, ?, 'Оператор принтеру');\n");
        statement = connectionToDataBase.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
        statement.setLong(1, idOrder);
        statement.setLong(2, operator.id());

        checkAffectRows(statement.executeUpdate());
        getId(statement);
    }

    private void insertTypeServiceOrder(long idOrder, TypeItem type, Term term) throws SQLException {
        StringBuilder sql;
        PreparedStatement statement;
        sql = new StringBuilder("INSERT INTO `PhotocopyPoint`.TypeServiceOrder(order_id, term, type_service_id, " +
                "count) VALUES (?, ADDTIME(NOW(), SEC_TO_TIME(?)), ?, ?);");
        statement = connectionToDataBase.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
        statement.setLong(1, idOrder);
        //statement.setLong(2, type.);
        statement.setInt(2, term.value());
        statement.setLong(3, type.type().id());
        statement.setInt(4, type.count());

        checkAffectRows(statement.executeUpdate());
        getId(statement);
    }

    private long insertOrder(long idMoney, long idClient) throws SQLException {
        StringBuilder sql;
        PreparedStatement statement;
        sql = new StringBuilder("INSERT INTO `PhotocopyPoint`.`Order`(money_id, client_id) " +
                "VALUES (?, ?);\n");
        statement = connectionToDataBase.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
        statement.setLong(1, idMoney);
        statement.setLong(2, idClient);

        checkAffectRows(statement.executeUpdate());
        return getId(statement);
    }

    private long insertClient(long id) throws SQLException {
        StringBuilder sql;
        PreparedStatement statement;
        sql = new StringBuilder("INSERT INTO `PhotocopyPoint`.Client(station_name, contact_info_id) " +
                "VALUES (?, ?);\n");
        statement = connectionToDataBase.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, address);
        statement.setLong(2, id);

        checkAffectRows(statement.executeUpdate());
        return getId(statement);
    }

    private long insertContactInfo(Order order) throws SQLException {
        StringBuilder sql;
        PreparedStatement statement;
        sql = new StringBuilder("INSERT INTO `PhotocopyPoint`.Contact_info(full_name, phone_number) " +
                "VALUES (?, ?);\n");
        statement = connectionToDataBase.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, order.getFullName());
        statement.setString(2, order.getPhoneNumber());

        checkAffectRows(statement.executeUpdate());
        return getId(statement);
    }

    private long insertMoney(Order order) throws SQLException {
        StringBuilder sql;
        PreparedStatement statement;
        sql = new StringBuilder("INSERT INTO `PhotocopyPoint`.Money(count, unit) VALUES (?, ?);");
        statement = connectionToDataBase.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
        statement.setDouble(1, order.getPrice().value());
        statement.setString(2, order.getPrice().unit());

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
