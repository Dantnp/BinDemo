package com.ntouzidis.demo.module.common.utils;

import java.time.LocalDate;

import static com.google.common.base.Preconditions.checkArgument;

public class PinUtils {

  public static void verifyPin(String pin) {
    LocalDate today = LocalDate.now();
    String dailyPIN = String.valueOf(today.getDayOfMonth() + today.getMonthValue() + today.getYear());
    checkArgument(dailyPIN.equals(pin), "WRONG PIN");
  }

  private PinUtils() {
  }
}
