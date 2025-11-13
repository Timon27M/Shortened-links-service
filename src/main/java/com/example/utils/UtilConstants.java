package com.example.utils;

import java.time.format.DateTimeFormatter;

public class UtilConstants {

  public static final String INSTRUCTION_TEXT =
      """
            === Генератор коротких ссылок ===
            1. Создать короткую ссылку.
            2. Перейти по короткой ссылке.
            3. Админ права.
            4. Выход.
            """;

  public static final DateTimeFormatter FORMATTER =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
}
