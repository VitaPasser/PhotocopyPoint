package org.vitapasser.photocopypoint.Model;

import org.vitapasser.photocopypoint.Controller.DTO.OrderView;
import org.vitapasser.photocopypoint.Controller.TakeMoreInfo.TypeView;
import org.vitapasser.photocopypoint.Util.Mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public record OrderList(Connection connectionToDataBase) {
        public OrderViewAndTypesViews getFullInfoAboutOrder(long ticketId) {
        try {
            String sql = """
                    SELECT T.id as TicketId,
                           O.id as OrderId,
                           SCi.full_name as StaffFullName,
                           SCi.phone_number as StaffPhoneNumber,
                           CCi.full_name as ClientFullName,
                           CCi.phone_number as ClientPhoneNumber,
                           OS.specialization as StaffSpecialization,
                           S.address as AddressMake,
                           M.count as CostCount,
                           M.unit as CostUnit,
                           C.station_name as ClientBeginOrderStationName,
                           TSO.count as TypeServiceCount,
                           TSO.term as TypeServiceOrderTerm,
                           TS.info as TypeServiceInfo,
                           TS.name as TypeServiceName,
                           TS.term as TypeServiceTerm,
                           TS.create_time as TypeCreateTime,
                           TS.id as TypeId,
                           T.isReady as isMadeOrder,
                           MP.count as TypePriceCount,
                           MP.unit as TypePriceUnit
                    From PhotocopyPoint.Ticket T
                         inner join PhotocopyPoint.`Order` O on O.id = T.order_id
                         inner join PhotocopyPoint.OrderStaff OS on O.id = OS.order_id
                         inner join PhotocopyPoint.Staff S on OS.staff_id = S.id
                         inner join PhotocopyPoint.Money M on O.money_id = M.id
                         inner join PhotocopyPoint.Client C on O.client_id = C.id
                         inner join PhotocopyPoint.Contact_info SCi on SCi.id = S.contact_info_id
                         inner join PhotocopyPoint.Contact_info CCi on CCi.id = C.contact_info_id
                         inner join PhotocopyPoint.TypeServiceOrder TSO on O.id = TSO.order_id
                         inner join PhotocopyPoint.TypeService TS on TSO.type_service_id = TS.id
                         inner join PhotocopyPoint.Price P on TS.price_id = P.id
                         inner join PhotocopyPoint.Money MP on P.money_id = MP.id
                    where T.id = ?
                    group by TicketId,\s
                             OrderId,\s
                             StaffFullName,\s
                             StaffPhoneNumber,\s
                             ClientFullName,\s
                             ClientPhoneNumber,
                             StaffSpecialization,
                             AddressMake,
                             CostCount,
                             CostUnit,
                             ClientBeginOrderStationName,
                             TypeServiceCount,
                             TypeServiceOrderTerm,
                             TypeServiceInfo,
                             TypeServiceName,
                             TypeServiceTerm,
                             TypeCreateTime,
                             TypeId,
                             TicketId,
                             isMadeOrder,
                             TypePriceCount,
                             TypePriceUnit""";

            PreparedStatement statement = connectionToDataBase.prepareStatement(sql);
            statement.setLong(1, ticketId);
            statement.execute();

            ResultSet sqlResult = statement.getResultSet();

            sqlResult.next();

            var orderId1 = sqlResult.getLong("OrderId");
            var ticketId1 = sqlResult.getLong("TicketId");
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
            var orderTerm = sqlResult.getString("TypeServiceOrderTerm");

            List<TypeItem> typeItems = new ArrayList<>();
            do {
                typeItems.add(new TypeItem(new Type(
                        sqlResult.getLong("TypeId"),
                        sqlResult.getString("TypeServiceName"),
                        sqlResult.getString("TypeServiceInfo"),
                        new Term(sqlResult.getTime("TypeServiceTerm")
                                .toLocalTime()
                                .toSecondOfDay()),
                        new Money(
                                sqlResult.getDouble("TypePriceCount"),
                                sqlResult.getString("TypePriceUnit")
                        ),
                        Mysql.dbDateTimeToLocalDateTime(sqlResult.getString("TypeCreateTime"))),
                        sqlResult.getInt("TypeServiceCount"))
                );
            } while (sqlResult.next());

            OrderView orderView = new OrderView(
                    orderId1,
                    ticketId1,
                    "Замовлення готово: " + String.valueOf(isReady),
                    "ФІО клієнта: " + clientFullName,
                    "Номер телефона клієнта: " + clientPhoneNumber,
                    "ФІО оператора: " + staffFullName,
                    "Номер телефона оператора: " + staffPhoneNumber,
                    "Спеціалізація оператора: " + staffSpecialization,
                    "Адреса створення: " + addressMake,
                    "Ціна: " + String.valueOf(costCount) + costUnit,
                    "Назва станції, де замовили: " + stationName,
                    "Термін створення замовлення: " + orderTerm);

            return new OrderViewAndTypesViews(orderView, typeItems.stream().map(TypeView::new).toList());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

            return null;
        }
}
