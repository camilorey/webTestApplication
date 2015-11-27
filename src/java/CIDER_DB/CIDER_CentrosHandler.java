/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CIDER_DB;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author laptop
 */
public class CIDER_CentrosHandler extends CIDER_EntityHandler{
 public CIDER_CentrosHandler() {
  super();
 }
 public int entityType(String entName){
  if(!entities.containsKey(entName)){
   return -1;
  }else{
   if(entities.get(entName) instanceof CIDER_Entidad){
    return 0;
   }else{
    return 1;
   }
  }
 }
 public ArrayList<String> getCentroNames(){
  ArrayList<String> centroNames = new ArrayList<String>();
  for(String key: entities.keySet()){
   if(entities.get(key) instanceof CIDER_Centro)
    centroNames.add(key);
  }
  return centroNames;
 }
 public ArrayList<String> getEntidadNames(){
  ArrayList<String> entidadNames = new ArrayList<String>();
  for(String key: entities.keySet()){
   if(entities.get(key) instanceof CIDER_Entidad)
    entidadNames.add(key);
  }
  return entidadNames;
 }
 public int getNumCentros(){
  int numCentros = 0;
  for(String name: entities.keySet()){
   if(entities.get(name) instanceof CIDER_Centro){
    numCentros++;
   }
  }
  return numCentros;
 }
 public int getNumEntidades(){
  int numEntidades = 0;
  for(String name: entities.keySet()){
   if(entities.get(name) instanceof CIDER_Entidad){
    numEntidades++;
   }
  }
  return numEntidades;
 }
 public int getNumCentrosWithOption(String varName, String varOption,ArrayList<String> filters){
  int numCentrosWithOption = 0;
  for(String name: entities.keySet()){
   if(entities.get(name) instanceof CIDER_Centro){
    CIDER_Centro centro = (CIDER_Centro) entities.get(name);
    if(centro.respondToQuery(varName, varOption)){
     if(filters == null){
      numCentrosWithOption++;
     }else{
      if(centro.respondToFilters(filters)){
       numCentrosWithOption++;
      }
     }
    }
   }
  }
  return numCentrosWithOption;
 }
 public int getNumCentrosWithFilter(ArrayList<String> filters){
  int numCentrosWithFilter = 0;
  for(String name: entities.keySet()){
   if(entities.get(name) instanceof CIDER_Centro){
    CIDER_Centro centro = (CIDER_Centro) entities.get(name);
    if(centro.respondToFilters(filters)){
       numCentrosWithFilter++;
    }
   }
  }
  return numCentrosWithFilter;
 }
 
 public HashMap<String,Float> makeSingleVariableStatPercentQuery(CIDER_Variable var, ArrayList<String> filters){
  int numCentros = getNumCentros();
  int numCentrosWithFilter = 0;
  if(filters!=null){
   numCentrosWithFilter = getNumCentrosWithFilter(filters);
  }
  HashMap<String,Float> queryResult = new HashMap<String,Float>();
  ArrayList<String> varOptions = var.getVariableOptions();
  int total = 0;
  float percentageTotal = 0;
  for(int i=0;i<varOptions.size();i++){
   int numCentrosWithOption = 0;
   float percentageValue = 0.0f;
   numCentrosWithOption = getNumCentrosWithOption(var.getVariableName(),varOptions.get(i),filters);
   total += numCentrosWithOption;
   if(filters == null){
    percentageValue = (float) numCentrosWithOption / (float) numCentros;
   }else{
    percentageValue = (float) numCentrosWithOption / (float) numCentrosWithFilter;
   }
   percentageTotal += percentageValue;
   queryResult.put(varOptions.get(i), new Float(percentageValue));
  }
  return queryResult;
 }
 public HashMap<String,Integer> makeSingleVariableStatNumberQuery(CIDER_Variable var, ArrayList<String> filters){
  int numCentros = getNumCentros();
  int numCentrosWithFilter = 0;
  if(filters!=null){
   numCentrosWithFilter = getNumCentrosWithFilter(filters);
  }
  HashMap<String,Integer> queryResult = new HashMap<String,Integer>();
  ArrayList<String> varOptions = var.getVariableOptions();
  int total = 0;
  for(int i=0;i<varOptions.size();i++){
   int numCentrosWithOption = 0;
   float percentageValue = 0.0f;
   numCentrosWithOption = getNumCentrosWithOption(var.getVariableName(),varOptions.get(i),filters);
   total += numCentrosWithOption;
   if(filters == null){
    percentageValue = (float) numCentrosWithOption / (float) numCentros;
   }else{
    percentageValue = (float) numCentrosWithOption / (float) numCentrosWithFilter;
   }
   queryResult.put(varOptions.get(i), new Integer(numCentrosWithOption));
  }
  return queryResult;
 }
 public HashMap<String,HashMap<String,Float>> makeDoubleVariableStatPercentQuery(CIDER_Variable var1, CIDER_Variable var2, ArrayList<String> filters){
  HashMap<String,HashMap<String,Float>> queryResult = new HashMap<String,HashMap<String,Float>>();
  ArrayList<String> var1Options = var1.getVariableOptions();
  for(int i=0;i<var1Options.size();i++){
   ArrayList<String> filtersWithOption = new ArrayList<String>();
   filtersWithOption.add(var1Options.get(i));
   if(filters !=null){
    for(int j=0;j<filters.size();j++){
     filtersWithOption.add(filters.get(j));
    }
   }
   HashMap<String,Float> subQueryResult = makeSingleVariableStatPercentQuery(var2,filtersWithOption);
   queryResult.put(var1Options.get(i),subQueryResult);
  }
  return queryResult;
 }
 public HashMap<String,HashMap<String,Integer>> makeDoubleVariableStatNumberQuery(CIDER_Variable var1, CIDER_Variable var2, ArrayList<String> filters){
  HashMap<String,HashMap<String,Integer>> queryResult = new HashMap<String,HashMap<String,Integer>>();
  ArrayList<String> var1Options = var1.getVariableOptions();
  for(int i=0;i<var1Options.size();i++){
   ArrayList<String> filtersWithOption = new ArrayList<String>();
   filtersWithOption.add(var1Options.get(i));
   if(filters !=null){
    for(int j=0;j<filters.size();j++){
     filtersWithOption.add(filters.get(j));
    }
   }
   HashMap<String,Integer> subQueryResult = makeSingleVariableStatNumberQuery(var2,filtersWithOption);
   queryResult.put(var1Options.get(i),subQueryResult);
  }
  return queryResult;
 }
}
