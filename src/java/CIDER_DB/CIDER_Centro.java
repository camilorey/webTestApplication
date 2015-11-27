/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CIDER_DB;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.json.simple.*;
/**
 *
 * @author laptop
 */
public class CIDER_Centro extends CIDER_DB_Entity{

 
 protected HashMap<String,String> singleVariables;
 protected HashMap<String,ArrayList<String>> multiVariables;
 protected ArrayList<CIDER_DB_Entity> aliadosPrincipales;
 protected ArrayList<CIDER_DB_Entity> aliadosSecundarios;
 public CIDER_Centro() {
  aliadosPrincipales = new ArrayList<CIDER_DB_Entity>();
  aliadosSecundarios = new ArrayList<CIDER_DB_Entity>();
  singleVariables = new HashMap<String,String>();
  multiVariables = new HashMap<String,ArrayList<String>>();
 }
 public CIDER_Centro(String entityName) {
  super(entityName);
  singleVariables = new HashMap<String,String>();
  multiVariables = new HashMap<String,ArrayList<String>>();
  aliadosPrincipales = new ArrayList<CIDER_DB_Entity>();
  aliadosSecundarios = new ArrayList<CIDER_DB_Entity>();
 }
 public void makeSingleVariable(String variableName, String variableContent){
  singleVariables.put(variableName,variableContent);
 }
 public void makeMultivariable(String variableName, ArrayList<String> variableContent){
  multiVariables.put(variableName, variableContent);
 }
 public void reportMultiVariables(){
  for(String multiVarName: multiVariables.keySet()){
   boolean repeated = repeatedValuesInMultivariable(multiVarName);
   if(repeated){
    System.out.println("repeated values in variable: "+multiVarName);
   }
  }
 }
 public boolean repeatedValuesInMultivariable(String multiVarName){
  boolean hasRepeated = false;
  ArrayList<String> var = multiVariables.get(multiVarName);
  for(int i=0;i<var.size();i++){
   String val = var.get(i);
   for(int j=0;j<var.size();j++){
    if(i!=j){
     hasRepeated = hasRepeated || val.equals(var.get(j));
    }
   }
   return hasRepeated;
  }
  return hasRepeated;
 }
 public ArrayList<CIDER_DB_Entity> getAliadosPrincipales() {
  return aliadosPrincipales;
 }
 public ArrayList<CIDER_DB_Entity> getAliadosSecundarios() {
  return aliadosSecundarios;
 }
 public void addToAliados(CIDER_DB_Entity entity, char type){
  if(type == 'p'){
   aliadosPrincipales.add(entity);
  }else{
   aliadosSecundarios.add(entity);
  }
 }
 public boolean isAliado(CIDER_DB_Entity entity, char type){
  boolean isAliado = singleVariables.get("ENTIDAD ADSCRITA A").equals(entity.getEntityName());
  if(type == 'p'){
   for(int i=0;i<aliadosPrincipales.size();i++){
    isAliado = isAliado || aliadosPrincipales.get(i).equals(entity);
   }
  }else{
   for(int i=0;i<aliadosSecundarios.size();i++){
    isAliado = isAliado || aliadosSecundarios.get(i).equals(entity);
   }
  }
  return isAliado;
 }
 public boolean respondToFilters(ArrayList<String> filters){
  boolean isInFilter = true;
  for(int i=0;i<filters.size();i++){
   isInFilter = isInFilter && respondToQuery(filters.get(i));
  }
  return isInFilter;
 }
 public boolean respondToQuery(String query){
  boolean response = false;
  //search for string in individual variables
  response = singleVariables.containsValue(query);
  if(response){
   return response;
  }else{
   //now search for string in multivariables
   for(Map.Entry multiVarEntry: multiVariables.entrySet()){
    ArrayList<String> multiVar = (ArrayList<String>) multiVarEntry.getValue();
    for(int i=0;i<multiVar.size();i++){
     response = response || multiVar.get(i).equals(query);
    }
   }
   return response;
  }
 }
 public boolean respondToQuery(String variableName, String query){
  if(isSingleVariable(variableName)){
   return singleVariables.get(variableName).equals(query);
  }else if(isMultiVariable(variableName)){
    ArrayList<String> varOptions = multiVariables.get(variableName);
    boolean satisfies = false;
    for(int i=0;i<varOptions.size();i++){
     satisfies = satisfies || varOptions.get(i).equals(query);
    }
    return satisfies;
  }else{
   return false;
  }
 }
 public boolean isSingleVariable(String variableName){
  return singleVariables.containsKey(variableName);
 }
 public boolean isMultiVariable(String variableName){
  return multiVariables.containsKey(variableName);
 }
 @Override
 public String toJSON() {
  JSONObject jsonObject=new JSONObject();
  //make object
  jsonObject.put("nombre",getEntityName());
  //get single variable content
  for(String varName: singleVariables.keySet()){
   jsonObject.put(varName,singleVariables.get(varName));
  }
  //get multivariable content
  for(String multiVarName: multiVariables.keySet()){
   ArrayList<String> varContent = multiVariables.get(multiVarName);
   JSONArray varAsJSON = new JSONArray();
   for(int i=0;i<varContent.size();i++){
    varAsJSON.add(varContent.get(i));
   }
   jsonObject.put(multiVarName,varAsJSON.toJSONString());
  }
  //make aliados
  JSONArray aliadosP = new JSONArray();
  JSONArray aliadosS = new JSONArray();
  for(int i=0;i<aliadosPrincipales.size();i++){
   aliadosP.add(aliadosPrincipales.get(i).getEntityName());
  }
  for(int i=0;i<aliadosSecundarios.size();i++){
   aliadosS.add(aliadosSecundarios.get(i).getEntityName());
  }
  jsonObject.put("aliados_principales", aliadosP.toJSONString());
  jsonObject.put("aliados_secundarios", aliadosS.toJSONString());
  return jsonObject.toJSONString();
 }
}
