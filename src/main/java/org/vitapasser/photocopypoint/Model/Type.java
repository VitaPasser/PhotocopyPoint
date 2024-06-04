package org.vitapasser.photocopypoint.Model;

import java.time.LocalDateTime;
import java.util.Date;

public record Type(Long id, String name, String info, Term term, Money money, LocalDateTime create_time) {

}
