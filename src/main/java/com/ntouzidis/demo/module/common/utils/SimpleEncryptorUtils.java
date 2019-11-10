package com.ntouzidis.demo.module.common.utils;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class SimpleEncryptorUtils {

  private static final String PASSWORD = "kobines";
  private static StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();

  private SimpleEncryptorUtils() {
    encryptor.setPassword(PASSWORD);
  }

  public static String encrypt(String text) {
    return encryptor.encrypt(text);
  }

  public static String decrypt(String text) {
    return encryptor.decrypt(text);
  }
}
