package oocl.ita.keyboardman.parkinglotmanagerhelperbackend.utils;

import sun.misc.BASE64Encoder;

import java.security.MessageDigest;

public class Md5Utils {

  public static String encoderByMd5(String str) {
    String encodedStr = null;
    try {
      MessageDigest md5 = MessageDigest.getInstance("MD5");
      BASE64Encoder base64Encoder = new BASE64Encoder();
      encodedStr = base64Encoder.encode(md5.digest(str.getBytes("utf-8")));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return encodedStr;
  }

  public static boolean checkPassword(String newPassword, String oldPassword) {
    try {
      if (encoderByMd5(newPassword).equals(oldPassword)) {
        return true;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }
}
