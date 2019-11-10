package com.ntouzidis.demo.module.common.converter.enum_converter;


import com.ntouzidis.demo.module.common.enumeration.Gender;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class GenderEnumConverter implements Converter<String, Gender> {

  @Override
  public Gender convert(String name) {
    try {
      return Gender.valueOf(name.toUpperCase());
    } catch(Exception e) {
      throw new IllegalArgumentException("The Gender values are " + Arrays.toString(Gender.values()));
    }
  }
}
