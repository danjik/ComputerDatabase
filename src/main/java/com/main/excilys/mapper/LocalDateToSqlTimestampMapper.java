package com.main.excilys.mapper;

import java.sql.Timestamp;
import java.time.LocalDate;

public class LocalDateToSqlTimestampMapper {

  /**
   * Mapping from localDate to sql Timestamp.
   *
   * @param localDate
   *          the date to convert
   * @return the converted date
   */
  public static Timestamp localDateToSqlTimestamp(LocalDate localDate) {
    Timestamp returnTimestamp = localDate != null
        ? Timestamp.valueOf(localDate.atStartOfDay())
        : null;

    return returnTimestamp;
  }

  /**
   * Mapping from sql Timestamp to localDate.
   *
   * @param timestamp
   *          the date to convert
   * @return the converted date
   */
  public static LocalDate sqlTimestampToLocalDate(Timestamp timestamp) {
    LocalDate returnLocalDate = timestamp != null
        ? timestamp.toLocalDateTime().toLocalDate()
        : null;

    return returnLocalDate;

  }

}
