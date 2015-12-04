/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CIDER_DB;

import CIDER_DB.CIDER_ExcelHandler;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author laptop
 */
public class CIDER_DB {
 protected String filePath;
 protected String fileName;
 protected CIDER_CentrosHandler centros;
 protected CIDER_VariableHandler variables;
 protected CIDER_ExcelHandler excelHandler;
 public CIDER_DB(){
  centros = null;
  variables = null;
  excelHandler = null;
 }
 public CIDER_DB(String filePath, String fileName){
  this.filePath = filePath;
  this.fileName = fileName;
 }
 public void createDB() throws Exception{
  try{
   System.out.println("Excel file known: "+filePath+"\\"+fileName);
   excelHandler = new CIDER_ExcelHandler(filePath, fileName);
   System.out.println("opening Excel File");
   excelHandler.openFile();
   variables = excelHandler.createVariableSet();
   centros = excelHandler.createCentrosHandler(variables);
  }catch(Exception e){
   throw new Exception("could not create DB due to: " +e);
  }
 }
 public PrintWriter createDB(PrintWriter responseMonitor){
  responseMonitor.println("starting Data Base Creation process<br>");
  try{
   excelHandler = new CIDER_ExcelHandler(filePath, fileName);
   responseMonitor.println("Excel Reader created, now opening file <br>");
   responseMonitor = excelHandler.openFile(responseMonitor);
   responseMonitor.println("<h1> Creating Variable Set </h1>");
   variables = excelHandler.createVariableSet(responseMonitor);
   responseMonitor.println("<h1> Creating Centros for CIDER App</h1>");
   centros = excelHandler.createCentrosHandler(variables,responseMonitor);
  }catch(Exception e){
   responseMonitor.println("Opening file failed due to: "+e.toString()+"<br>");
  }
  return responseMonitor;
 }
 public CIDER_Variable getVariable(String varName){
  return variables.getVariable(varName);
 }
 public CIDER_DB_Entity get(String entName){
  return centros.get(entName);
 }
 public int numCentrosWithFilter(ArrayList<String> filters){
  return centros.getNumCentrosWithFilter(filters);
 }
 public ArrayList<CIDER_DB_Entity> getFilterQueryResult(CIDER_Variable var, String filter){
  return centros.getFilterQueryResult(var, filter);
 }
 public HashMap<String,ArrayList<CIDER_DB_Entity>> getSingleVariableQueryResult(CIDER_Variable var){
  return centros.getSingleVariableQueryResult(var);
 }
 public HashMap<String, HashMap<String, ArrayList<CIDER_DB_Entity>>> getDoubleVariableQueryResult(CIDER_Variable var1, CIDER_Variable var2){
  return centros.getDoubleVariableQueryResult(var1, var2);
 }
 public HashMap<String,Float> makeSingleVariableStatPercentQuery(CIDER_Variable var,ArrayList<String> filters){
  return centros.makeSingleVariableStatPercentQuery(var,filters);
 }
 public HashMap<String, Integer> makeSingleVariableStatNumberQuery(CIDER_Variable var, ArrayList<String> filters){
  return centros.makeSingleVariableStatNumberQuery(var,filters);
 }
 public HashMap<String, HashMap<String,Float>> makeDoubleVariableStatPercentQuery(CIDER_Variable var1, CIDER_Variable var2, ArrayList<String> filters){
  return centros.makeDoubleVariableStatPercentQuery(var1, var2, filters);
 }
 public HashMap<String, HashMap<String,Integer>> makeDoubleVariableStatNumberQuery(CIDER_Variable var1, CIDER_Variable var2, ArrayList<String> filters){
  return centros.makeDoubleVariableStatNumberQuery(var1, var2, filters);
 }
 public void replace(CIDER_DB_Entity entity, CIDER_DB_Entity newEntity){
  centros.replace(entity, newEntity);
 }
 public boolean isIn(String entName){
  return centros.isIn(entName);
 }
 public boolean isIn(CIDER_DB_Entity ent){
  return centros.isIn(ent);
 }
 public int numCentros(){
  return centros.getNumCentros();
 }
 public int numEntidades(){
  return centros.getNumEntidades();
 }
 public int connectionBetweenEntities(String entity1Name, String entity2Name){
  return centros.connectionBetweenEntities(entity1Name, entity2Name);
 }
 public ArrayList<String> getCentrosRegistrados(){
  return centros.getCentroNames();
 }
 public ArrayList<String> getEntidadesRegistradas(){
  return centros.getEntidadNames();
 }
 public ArrayList<String> getVariablesRegistradas(){
  return variables.getVariableNames();
 }
 public ArrayList<String> getVariableOptions(String varName){
  return variables.getVariableOptions(varName);
 }
}
