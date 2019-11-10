package com.ntouzidis.demo.module.common.converter.attribute_converter;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.security.Key;
import java.util.Base64;

@Converter
public class CryptoConverter implements AttributeConverter<String, String> {

  private static final String ALGORITHM = "AES/ECB/PKCS5Padding";
  private static final byte[] KEY = "MySuperSecretKey".getBytes();

  @Override
  public String convertToDatabaseColumn(String s) {
    if (s == null) return null;
    Key key = new SecretKeySpec(KEY, "AES");
    try {
      Cipher c = Cipher.getInstance(ALGORITHM);
      c.init(Cipher.ENCRYPT_MODE, key);
      return Base64.getEncoder().encodeToString(c.doFinal(s.getBytes()));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public String convertToEntityAttribute(String s) {
    if (s == null) return null;
    Key key = new SecretKeySpec(KEY, "AES");
    try {
      Cipher c = Cipher.getInstance(ALGORITHM);
      c.init(Cipher.DECRYPT_MODE, key);
      return new String(c.doFinal(Base64.getDecoder().decode(s)));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
