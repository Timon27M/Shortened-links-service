package com.example.utils;

public class ColorPrint {
  private static final String RED = "\u001B[31m";
  private static final String RESET = "\u001B[0m";
  private static final String GREEN = "\u001B[32m";
  private static final String BLUE = "\u001B[34m";

  public static void printlnRed(String text) {
    System.out.println(RED + text + RESET);
  }
  public static void printlnGreen(String text) {System.out.println(GREEN + text + RESET);}
  public static void printlnBlue(String text) {System.out.println(BLUE + text + RESET);}

  public static void printRed(String text) {System.out.print(RED + text + RESET);}
}
