package org.vitapasser.photocopypoint.Model;

import org.vitapasser.photocopypoint.Controller.DTO.OrderView;
import org.vitapasser.photocopypoint.Util.Mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public record OrderList(Connection connectionToDataBase) {
        public OrderView getFullInfoAboutOrder(long orderId) {
        try {
            //TODO: Переписать запрос, что бы он выдал всю инфу об заказе
            String sql = """
                    SELECT O.id as OrderId,
                           SFNSPN.StaffFullName as StaffFullName,
                           SFNSPN.StaffPhoneNumber as StaffPhoneNumber,
                           CFNCPN.ClientFullName as ClientFullName,
                           CFNCPN.ClientPhoneNumber as ClientPhoneNumber,
                           OS.specialization as StaffSpecialization,
                           S.address as AddressMake,
                           M.count as CostCount,
                           M.unit as CostUnit,
                           C.station_name as ClientBeginOrderStationName,
                           TSO.count as TypeServiceCount,
                           TSO.term as TypeServiceTerm,
                           TS.info as TypeServiceInfo,
                           TS.name as TypeServiceName,
                           TS.create_time as TypeCreateTime,
                           TS.id as TypeId,
                           T.id as TicketId,
                           T.isReady as isMadeOrder,
                           PPP.priceCount as TypePriceCount,
                           PPP.priceUnit as TypePriceUnit
                    From PhotocopyPoint.`Order` O
                             inner join PhotocopyPoint.OrderStaff OS on O.id = OS.order_id
                             inner join PhotocopyPoint.Staff S on OS.staff_id = S.id
                             inner join PhotocopyPoint.Money M on O.money_id = M.id
                             inner join PhotocopyPoint.Client C on O.client_id = C.id
                             inner join (select SCi.full_name as StaffFullName, SCi.phone_number as StaffPhoneNumber
                                         from Contact_info SCi
                                                  inner join PhotocopyPoint.Staff S2 on SCi.id = S2.contact_info_id) as SFNSPN
                             inner join (select CCi.full_name as ClientFullName, CCi.phone_number as ClientPhoneNumber
                                         from Contact_info CCi
                                                  inner join PhotocopyPoint.Client C2 on CCi.id = C2.contact_info_id) as CFNCPN
                             inner join PhotocopyPoint.TypeServiceOrder TSO on O.id = TSO.order_id
                             inner join PhotocopyPoint.TypeService TS on TSO.type_service_id = TS.id
                             inner join (select PPM.count as priceCount, PPM.unit as priceUnit\s
                                         from PhotocopyPoint.Money PPM\s
                                             inner join PhotocopyPoint.Price P on PPM.id = P.money_id) as PPP
                             inner join PhotocopyPoint.Ticket T on O.id = T.order_id
                    where O.id = ? LIMIT 1""";

            PreparedStatement statement = connectionToDataBase.prepareStatement(sql);
            statement.setLong(1, orderId);
            checkAffectRows(statement.executeUpdate());

            ResultSet sqlResult = statement.getResultSet();

            sqlResult.next();

            var orderId1 = sqlResult.getLong("OrderId");
            var ticketId = sqlResult.getLong("TicketId");
            var isReady = sqlResult.getBoolean("isMadeOrder");
            var clientFullName = sqlResult.getString("ClientFullName");
            var clientPhoneNumber = sqlResult.getString("ClientPhoneNumber");
            var staffFullName = sqlResult.getString("StaffFullName");
            var staffPhoneNumber = sqlResult.getString("StaffPhoneNumber");
            var staffSpecialization = sqlResult.getString("StaffSpecialization");
            var addressMake = sqlResult.getString("AddressMake");
            var costCount = sqlResult.getDouble("CostCount");
            var costUnit = sqlResult.getString("CostUnit");
            var stationName = sqlResult.getString("ClientBeginOrderStationName");

            List<TypeItem> typeItems = new ArrayList<>();
            typeItems.add(new TypeItem(new Type(
                    sqlResult.getLong("TypeId"),
                    sqlResult.getString("TypeServiceName"),
                    sqlResult.getString("TypeServiceInfo"),
                    new Term(sqlResult.getTime("TypeServiceTerm").toLocalTime().toSecondOfDay()),
                    new Money(
                            sqlResult.getDouble("TypePriceCount"),
                            sqlResult.getString("TypePriceUnit")
                    ),
                    Mysql.dbDateTimeToLocalDateTime(sqlResult.getString("TypeCreateTime"))),
                    sqlResult.getInt("TypeServiceCount"))
            );
            while (sqlResult.next()) {
                typeItems.add(new Type(new Type(
                        sqlResult.getLong("TypeId"),
                        sqlResult.getString("TypeServiceName"),
                        sqlResult.getString("TypeServiceInfo"),
                        new Term(sqlResult.getTime("TypeServiceTerm").toLocalTime().toSecondOfDay()),
                        new Money(
                                sqlResult.getDouble("TypePriceCount"),
                                sqlResult.getString("TypePriceUnit")
                        ),
                        Mysql.dbDateTimeToLocalDateTime(sqlResult.getString("TypeCreateTime"))),
                        sqlResult.getInt("TypeServiceCount"))
                );
            }

            OrderView orderView = new OrderView(
                    orderId1,
                    ticketId,
                    isReady,
                    clientFullName,
                    clientPhoneNumber,
                    staffFullName,
                    staffPhoneNumber,
                    staffSpecialization,
                    addressMake,
                    costCount,
                    costUnit,
                    stationName);

            return new OrderViewAndTypesViews(orderView, typeItems);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

            return null;
        }

    private static void checkAffectRows(int affectedRows) throws SQLException {
        if (affectedRows == 0) {
            throw new SQLException("Creating failed, no rows affected.");
        }
    }
}
