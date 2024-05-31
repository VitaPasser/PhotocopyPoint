package org.vitapasser.photocopypoint.Model;

import org.vitapasser.photocopypoint.Util.Mysql;

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
    public Money fixSale(Order order, Money countPayMoney) {
        try {

            StringBuilder sql;
            PreparedStatement statement;

            long id = insertMoney(order);
            id = insertContactInfo(order);



            sql = new StringBuilder("INSERT INTO `PhotocopyPoint`.Client(station_name, contact_info_id) " +
                    "VALUES ('?', @client_contact_info_id);\n");
            sql = new StringBuilder("INSERT INTO `PhotocopyPoint`.`Order`(money_id, client_id) " +
                    "VALUES (@money_id, @client_id);\n");

            sql = new StringBuilder("INSERT INTO `PhotocopyPoint`.TypeServiceOrder(order_id, term, type_service_id, " +
                    "count) VALUES (@order_id, ADDTIME(NOW(), SEC_TO_TIME(?)), ?, ?);\\n");

            sql = new StringBuilder("INSERT INTO `PhotocopyPoint`.OrderStaff(order_id, staff_id, specialization) " +
                    "VALUES (@order_id, ?, 'Оператор принтеру');\n");


            PreparedStatement preparedStatement = connectionToDataBase.prepareStatement(sql.toString(),
                    Statement.RETURN_GENERATED_KEYS);

            Money oddMoney = null;
            Money orderPrice = order.getPrice();
            if (Objects.equals(orderPrice.unit(), countPayMoney.unit()))
                oddMoney = new Money(countPayMoney.count() - orderPrice.count(),
                        orderPrice.unit());

            return oddMoney;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    private long insertContactInfo(Order order) throws SQLException {
        StringBuilder sql;
        PreparedStatement statement;
        sql = new StringBuilder("INSERT INTO `PhotocopyPoint`.Contact_info(full_name, phone_number) " +
                "VALUES ('?', '?');\n");
        statement = connectionToDataBase.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, order.getFullName());
        statement.setString(2, order.getPhoneNumber());

        checkAffectRows(statement.executeUpdate());
        return getId(statement);
    }

    private long insertMoney(Order order) throws SQLException {
        StringBuilder sql;
        PreparedStatement statement;
        sql = new StringBuilder("INSERT INTO `PhotocopyPoint`.Money(count, unit) VALUES (?, '?');\n");
        statement = connectionToDataBase.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
        statement.setDouble(1, order.getPrice().count());
        statement.setString(2, order.getPrice().unit());

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
