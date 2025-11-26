package com.example.utils;

import java.util.Scanner;
import java.util.UUID;

public class ScannerUtil {

	private static Scanner sc = new Scanner(System.in);

	private static <T> T scannerRun(String text, Class<T> type) {
		System.out.print(text);
		if (type == Integer.class) {
			String number = sc.nextLine();
			if (number.isEmpty())
				return null;
			try {
				return type.cast(Integer.parseInt(number)); // преобразуем в число
			} catch (NumberFormatException e) {
				System.out.println("Введите число!");
				return null;
			}
		} else if (type == String.class) {
			return type.cast(sc.nextLine());
		} else if (type == UUID.class) {
			return type.cast(UUID.fromString(sc.nextLine()));
		}
		return null;
	}

	public static Integer readInt(String text) {
		return scannerRun(text, Integer.class);
	}

	public static String readString(String text) {
		return scannerRun(text, String.class);
	}

	public static UUID readUuid(String text) {
		return scannerRun(text, UUID.class);
	}

	public static void closeScanner() {
		sc.close();
	}
}
