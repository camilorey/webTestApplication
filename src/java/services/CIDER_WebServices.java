/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import CIDER_DB.CIDER_Centro;
import CIDER_DB.CIDER_DB;
import CIDER_DB.CIDER_DB_Entity;
import CIDER_DB.CIDER_Entidad;
import CIDER_DB.CIDER_Variable;
import java.util.ArrayList;
import java.util.HashMap;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author admCIDER
 */
@WebService(serviceName = "CIDER_WebServices")
public class CIDER_WebServices {
 protected CIDER_DB dataBase;
 protected final String dataPath = "C:\\Users\\admCIDER\\Desktop\\HerramientaWeb_CIDER\\Data\\excelFiles";
 protected final String fileName = "baseDeDatos_emailInstitucional.xlsx";
 
 public CIDER_WebServices() {
   if(dataBase == null){
    dataBase = new CIDER_DB(dataPath,fileName);
    try{
     dataBase.createDB();
    }catch(Exception e){
     System.out.println("could not create DataBase due to: "+e.getMessage());
    }
   }
 }
  /**
  * Web service operation
  */
 @WebMethod(operationName = "numCentros")
 public String numCentros() throws NullPointerException {
  //TODO write your implementation code here:
  if(dataBase == null){
   throw new NullPointerException("Data Base is not defined yet");
  }else{
    return dataBase.numCentros()+""; 
  }
 }

 /**
  * Web service operation
  */
 @WebMethod(operationName = "numEntidades")
 public String numEntidades() throws NullPointerException {
  if(dataBase == null){
   throw new NullPointerException("Data Base is not defined yet");
  }else{
    return dataBase.numEntidades()+""; 
  }
 }

 /**
  * Web service operation
  */
 @WebMethod(operationName = "getCentro")
 public String getCentro(@WebParam(name = "centroName") String centroName) throws NullPointerException {
  String response ="";
  if(dataBase == null){
   throw new NullPointerException("Data Base is not defined yet");
  }else{
   CIDER_DB_Entity result = dataBase.get(centroName);
    if(result == null){
     throw new NullPointerException("Entity not registered in DB");
    }else if(!(result instanceof CIDER_Centro)){
     throw new NullPointerException("Entity is not centro");
    }else{
     response = ((CIDER_Centro) result).toJSON();
    }
  }
  return response;
 }

 /**
  * Web service operation
  */
 @WebMethod(operationName = "getEntidad")
 public String getEntidad(@WebParam(name = "entidadName") String entidadName) throws NullPointerException {
  String response ="";
  if(dataBase == null){
   throw new NullPointerException("Data Base is not defined yet");
  }else{
   CIDER_DB_Entity result = dataBase.get(entidadName);
    if(result == null){
     throw new NullPointerException("Entity not registered in DB");
    }else if(!(result instanceof CIDER_Entidad)){
     throw new NullPointerException("Entity is not entidad");
    }else{
     response = ((CIDER_Entidad) result).toJSON();
    }
  }
  return response;
 }

 /**
  * Web service operation
  */
 @WebMethod(operationName = "getEntidadesLabels")
 public String getEntidadesLabels() throws NullPointerException {
  String response ="";
  if(dataBase == null){
   throw new NullPointerException("Data Base is not defined yet");
  }else{
    response = ArrayListToJSONArray(dataBase.getEntidadesRegistradas()).toJSONString();
   
  }
  return response;
 }

 /**
  * Web service operation
  */
 @WebMethod(operationName = "getCentroLabels")
 public String getCentroLabels() throws NullPointerException {
  String response ="";
  if(dataBase == null){
   throw new NullPointerException("Data Base is not defined yet");
  }else{
    response = ArrayListToJSONArray(dataBase.getCentrosRegistrados()).toJSONString();
   
  }
  return response;
 }

 /**
  * Web service operation
  */
 @WebMethod(operationName = "getVariableLabels")
 public String getVariableLabels() throws NullPointerException {
  String response ="";
  if(dataBase == null){
   throw new NullPointerException("Data Base is not defined yet");
  }else{
   response = ArrayListToJSONArray(dataBase.getVariablesRegistradas()).toJSONString();
  }
  return response;
 }

 /**
  * Web service operation
  */
 @WebMethod(operationName = "getVariableOptions")
 public String getVariableOptions(@WebParam(name = "variableName") String variableName) throws NullPointerException {
  String response ="";
  if(dataBase == null){
   throw new NullPointerException("Data Base is not defined yet");
  }else{
    try{
        //String correctedVariableName = new String(variableName.getBytes("UTF-8"), "ISO-8859-1");
        
        CIDER_Variable var = dataBase.getVariable(variableName);
        response = ArrayListToJSONArray(var.getVariableOptions()).toJSONString();
    }catch(Exception e){
       System.out.println("charset encoding exception: "+e.toString()); 
    }  
  }
  return response;
 }

 /**
  * Web service operation
  */
 @WebMethod(operationName = "getVariable")
 public String getVariable(@WebParam(name = "variableName") String variableName) throws NullPointerException {
  String response ="";
  if(dataBase == null){
   throw new NullPointerException("Data Base is not defined yet");
  }else{
   CIDER_Variable result = dataBase.getVariable(variableName);
    if(result == null){
     throw new NullPointerException("Variable not registered in DB");
    }else{
     response = ((CIDER_Variable) result).toJSON();
    }
  }
  return response;
 }
 
 /**
  * Web service operation
  */
 /*
 @WebMethod(operationName = "getVariableQuery")
 public String getVariableQuery(@WebParam(name = "variableName") String variableName) throws NullPointerException {
  String response ="";
  if(dataBase == null){
   throw new NullPointerException("Data Base is not defined yet");
  }else{
   CIDER_Variable var = dataBase.getVariable(variableName);
   if(var == null){
    throw new NullPointerException("variable is not defined");
   }else{
    HashMap<String,ArrayList<CIDER_DB_Entity>> queryResult = dataBase.getSingleVariableQueryResult(var);
    JSONObject queryAsJSON = new JSONObject();
    for(String varOption: queryResult.keySet()){
     ArrayList<CIDER_DB_Entity> optionResult = queryResult.get(varOption);
     JSONArray entities = new JSONArray();
     for(int i=0;i<optionResult.size();i++){
      entities.add(optionResult.get(i).getEntityName());
     }
     queryAsJSON.put(varOption, entities.toJSONString());
    }
    response = queryAsJSON.toJSONString();
   }
  }
  return response;
 }
 */
 /**
  * Web service operation
  */
 /*
 @WebMethod(operationName = "getSingleVariableNumberQuery")
 public String getSingleVariableNumberQuery(@WebParam(name = "variableName") String variableName, @WebParam(name = "filters") String filters) throws NullPointerException {
  String response ="";
  if(dataBase == null){
   throw new NullPointerException("Data Base is not defined yet");
  }else{
    try{
     ArrayList<String> arrayOfFilters = parseJSONArray(filters);
     CIDER_Variable var = dataBase.getVariable(variableName);
     if(var == null){
      throw new NullPointerException("Variable not registered");
     }else{
      HashMap<String,Integer> result = dataBase.makeSingleVariableStatNumberQuery(var, arrayOfFilters);
      JSONObject queryAsObject = HashMapToJSONObject(result);
      response = queryAsObject.toJSONString();
     }
    }catch(ParseException pe){
     throw new NullPointerException("parsing problem: "+pe.getMessage());
    }
  }
  return response;
 }
 */
 /**
  * Web service operation
  */
 /*
 @WebMethod(operationName = "getSingleVariablePercentQuery")
 public String getSingleVariablePercentQuery(@WebParam(name = "variableName") String variableName, @WebParam(name = "filters") String filters) throws NullPointerException {
  String response ="";
  if(dataBase == null){
   throw new NullPointerException("Data Base is not defined yet");
  }else{
    try{
     ArrayList<String> arrayOfFilters = parseJSONArray(filters);
     CIDER_Variable var = dataBase.getVariable(variableName);
     if(var == null){
      throw new NullPointerException("Variable not registered");
     }else{
      HashMap<String,Float> result = dataBase.makeSingleVariableStatPercentQuery(var, arrayOfFilters);
      JSONObject queryAsObject = HashMapToJSONObject(result);
      response = queryAsObject.toJSONString();
     }
    }catch(ParseException pe){
     throw new NullPointerException("parsing problem: "+pe.getMessage());
    }
  }
  return response;
 }
 */
 /**
  * Web service operation
  */
 /*
 @WebMethod(operationName = "getDoubleVariableNumberQuery")
 public String getDoubleVariableNumberQuery(@WebParam(name = "variable1Name") String variable1Name, @WebParam(name = "variable2Name") String variable2Name, @WebParam(name = "filters") String filters) throws NullPointerException {
  String response ="";
  if(dataBase == null){
   throw new NullPointerException("Data Base is not defined yet");
  }else{
   try{
    ArrayList<String> arrayOfFilters = parseJSONArray(filters);
    CIDER_Variable var1 = dataBase.getVariable(variable1Name);
    CIDER_Variable var2 = dataBase.getVariable(variable2Name);
    if(var1 == null || var2 == null){
     String nullVar = "";
     if(var1 == null){
       nullVar = "variable: "+variable1Name+" is null";
     }
     if(var2 == null){
      nullVar = "variable: "+variable2Name+" is null";
     }
     throw new NullPointerException("Variable null: "+nullVar);
    }else{
      HashMap<String,HashMap<String,Integer>> result = dataBase.makeDoubleVariableStatNumberQuery(var1, var2, arrayOfFilters);
      JSONObject resultAsJSON = new JSONObject();
      for(String key: result.keySet()){
       JSONObject subQuery = HashMapToJSONObject(result.get(key));
       resultAsJSON.put(key, subQuery.toJSONString());
      }
      response = resultAsJSON.toJSONString();
    }
   }catch(ParseException pe){
    throw new NullPointerException("parsing problem: "+pe.getMessage());
   }
  }
  return response;
 }
 */
 /**
  * Web service operation
  */
 /*
 @WebMethod(operationName = "getDoubleVariablePercentQuery")
 public String getDoubleVariablePercentQuery(@WebParam(name = "variable1Name") String variable1Name, @WebParam(name = "variable2Name") String variable2Name, @WebParam(name = "filters") String filters) throws NullPointerException {
  String response ="";
  if(dataBase == null){
   throw new NullPointerException("Data Base is not defined yet");
  }else{
   try{
    ArrayList<String> arrayOfFilters = parseJSONArray(filters);
    CIDER_Variable var1 = dataBase.getVariable(variable1Name);
    CIDER_Variable var2 = dataBase.getVariable(variable2Name);
    if(var1 == null || var2 == null){
     String nullVar = "";
     if(var1 == null){
       nullVar = "variable: "+variable1Name+" is null";
     }
     if(var2 == null){
      nullVar = "variable: "+variable2Name+" is null";
     }
     throw new NullPointerException("Variable null: "+nullVar);
    }else{
      HashMap<String,HashMap<String,Float>> result = dataBase.makeDoubleVariableStatPercentQuery(var1, var2, arrayOfFilters);
      JSONObject resultAsJSON = new JSONObject();
      for(String key: result.keySet()){
       JSONObject subQuery = HashMapToJSONObject(result.get(key));
       resultAsJSON.put(key, subQuery.toJSONString());
      }
      response = resultAsJSON.toJSONString();
    }
   }catch(ParseException pe){
    throw new NullPointerException("parsing problem: "+pe.getMessage());
   }
  }
  return response;
 }
 */
 /**
  * Web service operation
  */
 @WebMethod(operationName = "getConnectionBetweenEntities")
 public String getConnectionBetweenEntities(@WebParam(name = "entity1Name") String entity1Name, @WebParam(name = "entity2Name") String entity2Name) throws NullPointerException {
  String response ="";
  if(dataBase == null){
   throw new NullPointerException("Data Base is not defined yet");
  }else{
    int queryResponse = dataBase.connectionBetweenEntities(entity1Name, entity2Name);
    switch(queryResponse){
     case 2:
      response = "ALIADO_PRINCIPAL";
      break;
     case 1:
      response = "ALIADO_SECUNDARIO";
      break;
     case 0:
      response = "NO_CONNECTION";
      break;
     case -2:
      response = "ENTITIES_ARE_BOTH_ENTIDAD";
      break;
     default:
      response = "ENTITIES_NOT_REGISTERED";
      break;
    }
  }
  return response;
 }

 public ArrayList<String> parseJSONArray(String array) throws ParseException{
  ArrayList<String> arrayList = new ArrayList<String>();
  try{
    JSONParser parser = new JSONParser();
    Object obj=parser.parse(array);
    JSONArray parsedArray =(JSONArray)obj;
    for(int i=0;i<parsedArray.size();i++){
     arrayList.add((String) parsedArray.get(i));
    }
  }
  catch(ParseException pe){
    throw new ParseException(pe.getPosition(), pe.getErrorType(), pe);
  }
  return arrayList;
 }
 public JSONArray ArrayListToJSONArray(ArrayList list){
  JSONArray asJSON = new JSONArray();
  for(int i=0;i<list.size();i++){
   asJSON.add(list.get(i));
  }
  return asJSON;
 }
 /*
 public JSONObject HashMapToJSONObject(HashMap<String,?> map){
  JSONObject asJSON = new JSONObject();
  for(String key: map.keySet()){
   asJSON.put(key, map.get(key));
  }
  return asJSON;
 }
*/
}
