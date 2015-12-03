/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.nio.charset.Charset;

/**
 *
 * @author laptop
 */
public class StringHandler {
 protected Charset systemCharset;
 protected Charset utf8Charset;
 public StringHandler() {
  systemCharset = Charset.forName(Charset.defaultCharset().name());
  utf8Charset = Charset.forName("UTF-8");
  System.out.println("========================================================");
  System.out.println("============System ENCODING ISSUES=======================");
  System.out.println("========================================================");
  System.out.println("System charset: "+systemCharset.name());
  System.out.println("UTF-8 charset: "+utf8Charset.name());
  String testStringInSystem = "á é í ó ú Á É Í Ó Ú ñ Ñ ( ) [ ] , . : ;";
  String testStringInUTF8 = new String(testStringInSystem.getBytes(utf8Charset),utf8Charset);
  System.out.println("Test string in "+ systemCharset.name()+" encoding: "+ testStringInSystem);
  System.out.println("Test string in UTF-8: "+testStringInUTF8);
  System.out.println("encoding results:");
  System.out.println("from system to utf8: "+new String(utf8Charset.encode(testStringInSystem).array(),utf8Charset));
  System.out.println("from utf8 to system: "+new String(systemCharset.encode(testStringInUTF8).array(),systemCharset));
 }
 public String encodeToSystemFromUTF8(String utf8String){
  if(utf8String.equals("")){
   return "";
  }else{
   String inSystemUTF = new String(utf8String.getBytes(utf8Charset),utf8Charset);
   String decoded = new String(systemCharset.encode(inSystemUTF).array(),systemCharset);
   System.out.println("received string: "+utf8String);
   System.out.println("translated to system UTF-8: "+inSystemUTF);
   System.out.println("decoded version from system: "+decoded);
   return decoded;
  }
 }
 public String encodeToUTF8FromSystem(String systemString){
  if(systemString.equals("")){
   return "";
  }else{
   String decoded = new String(systemString.getBytes(systemCharset),utf8Charset);
   System.out.println("recieved string: "+ systemString+ " decoded string: "+ decoded);
   return decoded;
  }
 }
}
