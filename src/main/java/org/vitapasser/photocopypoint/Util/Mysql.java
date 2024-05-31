package org.vitapasser.photocopypoint.Util;

import java.time.LocalDateTime;

public class Mysql {
    public static LocalDateTime dbDateTimeToLocalDateTime(String dateTime){
        String dateTimeForParse = dateTime.replace(' ', 'T');
        return LocalDateTime.parse(dateTimeForParse);
    }
}
