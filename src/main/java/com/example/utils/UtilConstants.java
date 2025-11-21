package com.example.utils;

import java.time.format.DateTimeFormatter;

public class UtilConstants {

	public static final String INSTRUCTION_TEXT = """
			=== Генератор коротких ссылок ===
			1. Создать короткую ссылку.
			2. Перейти по короткой ссылке.
			3. Админ права.
			4. Выход.
			""";

    public static final String ADMIN_INSTRUCTION_TEXT = """
            === Действия администратора: ===
			1. Обновить лимит переходов по ссылке.
			2. Обновить время жизни короткой ссылки.
			3. Удалить ссылку.
			4. Удалить акканунт.
			5. Назад.
            """;

	public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
}
