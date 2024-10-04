package com.banking.NttdataProyectoIAccountMs.util;

public class BlobUtil {
  public static String getResourceCustomers(String originalFileName) {
    return originalFileName.replace(Constants.DOUBLE_BACK_SLASH, Constants.SLASH);
  }
}
