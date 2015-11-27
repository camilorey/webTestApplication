/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CIDER_DB;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author laptop
 */
public class CIDER_VariableHandler {
 protected HashMap<String, CIDER_Variable> variables;
 
 public CIDER_VariableHandler() {
  variables = new HashMap<String, CIDER_Variable>();
 }
 public int getNumVariables(){
  return variables.size();
 }
 public ArrayList<CIDER_Variable> getVariables(){
  ArrayList<CIDER_Variable> varsAsArrayList = new ArrayList<CIDER_Variable>();
  for(Map.Entry mEntry: variables.entrySet()){
   varsAsArrayList.add((CIDER_Variable) mEntry.getValue());
  }
  return varsAsArrayList;
 }
 public void addVariable(CIDER_Variable var){
  variables.put(var.getVariableName(),var);
 }
 public boolean isVariableIn(String varName){
  CIDER_Variable var = getVariable(varName);
  return var != null;
 }
 public CIDER_Variable getVariable(String varName){
  return variables.get(varName);
 }
 public ArrayList<String> getVariableNames(){
  ArrayList<String> variableNames = new ArrayList<String>();
  for(String key: variables.keySet()){
   variableNames.add(key);
  }
  return variableNames;
 }
 public ArrayList<String> getVariableOptions(String varName){
  return ((CIDER_Variable) variables.get(varName)).getVariableOptions();
 }
 public void printVariableSetInfo(){
  for(Map.Entry mapEntry: variables.entrySet()){
   ((CIDER_Variable)mapEntry.getValue()).printVariable();
  }
 }
 public PrintWriter printVariableSetInfo(PrintWriter monitorResponse){
  for(Map.Entry mapEntry: variables.entrySet()){
   monitorResponse = ((CIDER_Variable)mapEntry.getValue()).printVariable(monitorResponse);
  }
  return monitorResponse;
 }
}
