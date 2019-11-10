package com.ntouzidis.demo.module.common.utils;

import org.slf4j.LoggerFactory;

public class LogUtil {

  public static org.slf4j.Logger log(Class<?> tClass) {
    return LoggerFactory.getLogger(tClass);
  }

  private LogUtil() {
  }

}
