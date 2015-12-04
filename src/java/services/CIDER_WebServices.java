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
import CIDER_statPlots.CIDER_StatPlot;
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
 CIDER_DB_Wrapper DB;
 
 public CIDER_WebServices() {
   if(DB == null){
     DB = new CIDER_DB_Wrapper();
   }
 }
  /**
  * Web service operation
  */
 @WebMethod(operationName = "numCentros")
 public String numCentros() throws NullPointerException {
  //TODO write your implementation code here:
  if(DB == null){
   throw new NullPointerException("Data Base is not defined yet");
  }else{
    return DB.numCentros(); 
  }
 }

 /**
  * Web service operation
  */
 @WebMethod(operationName = "numEntidades")
 public String numEntidades() throws NullPointerException {
  if(DB == null){
   throw new NullPointerException("Data Base is not defined yet");
  }else{
    return DB.numEntidades(); 
  }
 }

 /**
  * Web service operation
  */
 @WebMethod(operationName = "getCentro")
 public String getCentro(@WebParam(name = "centroName") String centroName) throws NullPointerException {
  String response ="failed operation";
  if(DB == null){
   throw new NullPointerException("Data Base is not defined yet");
  }else{
    response = DB.getCentro(centroName);
  }
  return response;
 }

 /**
  * Web service operation
  */
 @WebMethod(operationName = "getEntidad")
 public String getEntidad(@WebParam(name = "entidadName") String entidadName) throws NullPointerException {
  String response ="failed operation";
  if(DB == null){
   throw new NullPointerException("Data Base is not defined yet");
  }else{
   response = DB.getEntidad(entidadName);
  }
  return response;
 }

 /**
  * Web service operation
  */
 @WebMethod(operationName = "getEntidadesLabels")
 public String getEntidadesLabels() throws NullPointerException {
  String response ="failed operation";
  if(DB == null){
   throw new NullPointerException("Data Base is not defined yet");
  }else{
    response = DB.getEntidadesLabels();
  }
  return response;
 }

 /**
  * Web service operation
  */
 @WebMethod(operationName = "getCentroLabels")
 public String getCentroLabels() throws NullPointerException {
  String response ="failed operation";
  if(DB == null){
   throw new NullPointerException("Data Base is not defined yet");
  }else{
    response = DB.getCentroLabels();
   
  }
  return response;
 }

 /**
  * Web service operation
  */
 @WebMethod(operationName = "getVariableLabels")
 public String getVariableLabels() throws NullPointerException {
  String response ="failed operation";
  if(DB == null){
   throw new NullPointerException("Data Base is not defined yet");
  }else{
   response = DB.getVariableLabels();
  }
  return response;
 }

 /**
  * Web service operation
  */
 @WebMethod(operationName = "getVariableOptions")
 public String getVariableOptions(@WebParam(name = "variableName") String variableName) throws NullPointerException {
  String response ="";
  if(DB == null){
   throw new NullPointerException("Data Base is not defined yet");
  }else{
    try{
        response = DB.getVariableOptions(variableName);
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
  if(DB == null){
   throw new NullPointerException("Data Base is not defined yet");
  }else{
   response = DB.getVariable(variableName);
  }
  return response;
 }
 
 /**
  * Web service operation
  */
 
 @WebMethod(operationName = "getVariableQuery")
 public String getVariableQuery(@WebParam(name = "variableName") String variableName) throws NullPointerException {
  String response ="";
  if(DB == null){
   throw new NullPointerException("Data Base is not defined yet");
  }else{
   response = DB.getVariableQuery(variableName);
  }
  return response;
 }
 /**
  * Web service operation
  */
 @WebMethod(operationName = "getSingleVariableNumberQuery")
 public String getSingleVariableNumberQuery(@WebParam(name = "variableName") String variableName, @WebParam(name = "filters") String filters) throws NullPointerException {
  String response ="";
  if(DB == null){
   throw new NullPointerException("Data Base is not defined yet");
  }else{
   response = DB.getSingleVariableNumberQuery(variableName, filters);
  }
  return response;
 }
 /**
  * Web service operation
  */
 @WebMethod(operationName = "getSingleVariablePercentQuery")
 public String getSingleVariablePercentQuery(@WebParam(name = "variableName") String variableName, @WebParam(name = "filters") String filters) throws NullPointerException {
  String response ="";
  if(DB == null){
   throw new NullPointerException("Data Base is not defined yet");
  }else{
    response = DB.getSingleVariablePercentQuery(variableName, filters);
  }
  return response;
 }
 /**
  * Web service operation
  */
 @WebMethod(operationName = "createTortaPlot")
 public byte[] createTortaPlot(@WebParam(name = "variableName") String variableName, @WebParam(name = "filters") String filters) throws NullPointerException {
  byte[] response = null;
  if(DB == null){
   throw new NullPointerException("Data Base is not defined yet");
  }else{
     CIDER_StatPlot tortaPlot = new CIDER_StatPlot(DB.getDB(),800,600);
     tortaPlot.update();
     tortaPlot.saveToFile();
     response = tortaPlot.getByteArray();
    //response = DB.getSingleVariablePercentQuery(variableName, filters);
  }
  return response;
 }
 /**
  * Web service operation
  */
 @WebMethod(operationName = "getDoubleVariableNumberQuery")
 public String getDoubleVariableNumberQuery(@WebParam(name = "variable1Name") String variable1Name, @WebParam(name = "variable2Name") String variable2Name, @WebParam(name = "filters") String filters) throws NullPointerException {
  String response ="";
  if(DB == null){
   throw new NullPointerException("Data Base is not defined yet");
  }else{
   response = DB.getDoubleVariableNumberQuery(variable1Name, variable2Name, filters);
  }
  return response;
 }
 /**
  * Web service operation
  */
 @WebMethod(operationName = "getDoubleVariablePercentQuery")
 public String getDoubleVariablePercentQuery(@WebParam(name = "variable1Name") String variable1Name, @WebParam(name = "variable2Name") String variable2Name, @WebParam(name = "filters") String filters) throws NullPointerException {
  String response ="";
  if(DB == null){
   throw new NullPointerException("Data Base is not defined yet");
  }else{
    response = DB.getDoubleVariablePercentQuery(variable1Name, variable2Name, filters);
  }
  return response;
 }
 /**
  * Web service operation
  */
 @WebMethod(operationName = "getConnectionBetweenEntities")
 public String getConnectionBetweenEntities(@WebParam(name = "entity1Name") String entity1Name, @WebParam(name = "entity2Name") String entity2Name) throws NullPointerException {
  String response ="";
  if(DB == null){
   throw new NullPointerException("Data Base is not defined yet");
  }else{
    response = DB.getConnectionBetweenEntities(entity1Name, entity2Name);
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
}
