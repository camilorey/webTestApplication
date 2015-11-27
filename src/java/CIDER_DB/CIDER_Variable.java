/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CIDER_DB;

import java.io.PrintWriter;
import java.util.ArrayList;
import org.json.simple.*;
/**
 *
 * @author laptop
 */
public class CIDER_Variable {
 protected String variableName;
 protected ArrayList<String> variableOptions;
 
 public CIDER_Variable() {
  variableName = "variable";
  variableOptions = new ArrayList<String>();
 }
 public CIDER_Variable(String variableName) {
  this.variableName = variableName;
  variableOptions = new ArrayList<String>();
 }
 public CIDER_Variable(String variableName, ArrayList<String> variableOptions) {
  this.variableName = variableName;
  this.variableOptions = variableOptions;
 }
 public String getVariableName() {
  return variableName;
 }
 public void setVariableName(String variableName) {
  this.variableName = variableName;
 }
 public int getNumOptions(){
  return variableOptions.size();
 }
 public ArrayList<String> getVariableOptions() {
  return variableOptions;
 }
 public void setVariableOptions(ArrayList<String> variableOptions) {
  this.variableOptions = variableOptions;
 }
 public void addVariableOption(String option){
  variableOptions.add(option);
 }
 public boolean isInOptions(String value){
  boolean isResponse = false;
  for(int i=0;i<variableOptions.size();i++){
   isResponse = isResponse || variableOptions.equals(value);
  }
  return isResponse;
 }
 public void printVariable(){
  System.out.println("==============================================");
  System.out.println("Variable: "+variableName);
  System.out.println("----------------------------------------------");
  for(int i=0;i<variableOptions.size();i++){
   System.out.println(variableOptions.get(i));
  }
  System.out.println("==============================================");
  System.out.println();
 }
 
 public PrintWriter printVariable(PrintWriter monitorResponse){
  monitorResponse.println("<p>");
  monitorResponse.println("=============================================="+"<br>");
  monitorResponse.println("Variable: "+variableName);
  monitorResponse.println("----------------------------------------------"+"<br>");
  for(int i=0;i<variableOptions.size();i++){
   monitorResponse.println(variableOptions.get(i)+"<br>");
  }
  monitorResponse.println("=============================================="+"<br>");
  monitorResponse.println("</p>");
  return monitorResponse;
 }
 public String createQueryString(){
  String optionsQuery = "";
  for(int i=0;i<variableOptions.size();i++){
   optionsQuery += variableOptions.get(i);
   if(i<variableOptions.size()-1){
    optionsQuery+= ",";
   }
  }
  return variableName+"|"+optionsQuery;
 }
 public String toJSON(){
  JSONObject jsonObject=new JSONObject();
  jsonObject.put("variableName",variableName);
  JSONArray options = new JSONArray();
  for(int i=0;i<variableOptions.size();i++){
   options.add(variableOptions.get(i));
  }
  jsonObject.put("variableOptions",options.toJSONString());
  return jsonObject.toJSONString();
 }
}
