package oocl.ita.keyboardman.parkinglotmanagerhelperbackend.utils;

import java.util.Random;

public class GeneratePassword {

  public String createPassWord(int len){
    Random rd = new Random();
    final int maxNum = 62;
    StringBuffer sb = new StringBuffer();
    int index;
    char[] str = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
        'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
        'x', 'y', 'z', 'A','B','C','D','E','F','G','H','I','J','K',
        'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
        'X', 'Y' ,'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
    int count=0;
    while(count < len){
      index = Math.abs(rd.nextInt(maxNum));
      if (index >= 0 && index < str.length) {
        sb.append(str[index]);
        count ++;
      }
    }
    return sb.toString();
  }
}
