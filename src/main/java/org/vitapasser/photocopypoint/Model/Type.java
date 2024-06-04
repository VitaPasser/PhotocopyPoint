package org.vitapasser.photocopypoint.Model;

import java.time.LocalDateTime;

public record Type(Long id, String name, String info, Term term, Money money, LocalDateTime create_time) {

}
