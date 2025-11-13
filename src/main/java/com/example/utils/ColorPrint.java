package com.example.utils;

public class ColorPrint {
  public static final String RED = "\u001B[31m";
  public static final String RESET = "\u001B[0m";

  public static void printlnRed(String text) {
    System.out.println(RED + text + RESET);
  }

  public static void printRed(String text) {
    System.out.print(RED + text + RESET);
  }
}
