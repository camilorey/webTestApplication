/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CIDER_DB;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author laptop
 */
public class CIDER_EntityHandler {
 protected HashMap<String, CIDER_DB_Entity> entities;
 public CIDER_EntityHandler() {
  entities = new HashMap<String, CIDER_DB_Entity>();
 }
 public void addEntity(CIDER_DB_Entity ent){
  if(entities.containsKey(ent.getEntityName())){
   System.out.println("name conflict for entity: "+ ent.getEntityName());
  }else{
   entities.put(ent.getEntityName(),ent);
  }
 }
 public int size(){
  return entities.size();
 }
 public CIDER_DB_Entity get(int i){
  return entities.get(i);
 }
 public CIDER_DB_Entity get(String entName){
  if(entities.containsKey(entName)){
   return entities.get(entName);
  }else{
   return null;
  }
 }
 public ArrayList<CIDER_DB_Entity> getFilterQueryResult(CIDER_Variable var, String filter){
  ArrayList<CIDER_DB_Entity> queryResult = new ArrayList<CIDER_DB_Entity>();
  for(Map.Entry mapEntry: entities.entrySet()){
   if(mapEntry instanceof CIDER_Centro){
    CIDER_Centro entAsCentro = (CIDER_Centro) mapEntry;
    if(entAsCentro.respondToQuery(var.getVariableName(), filter)){
     queryResult.add(entAsCentro);
    }
   }
  }
  return queryResult;
 }
 public void replace(CIDER_DB_Entity entity, CIDER_DB_Entity newEntity){
  entities.put(entity.getEntityName(),newEntity);
 }
 public boolean isIn(String entName){
  return entities.containsKey(entName);
 }
 public boolean isIn(CIDER_DB_Entity ent){
  return entities.containsValue(ent);
 }
 public int connectionBetweenEntities(String entity1Name, String entity2Name){
  CIDER_DB_Entity ent1 = get(entity1Name);
  CIDER_DB_Entity ent2 = get(entity2Name);
  if(ent1 == null || ent2 == null ){
   return -3;
  }else{
    if(ent1 instanceof CIDER_Entidad && ent2 instanceof CIDER_Entidad){
     return -2;
    }else{
     if(ent1 instanceof CIDER_Centro){
      CIDER_Centro ent1AsCentro = (CIDER_Centro) ent1;
      if(ent1AsCentro.isAliado(ent2, 'p'))
       return 2;
      else if(ent1AsCentro.isAliado(ent2,'s'))
       return 1;
      else
       return 0;
     }else{
      CIDER_Centro ent2AsCentro = (CIDER_Centro) ent2;
      if(ent2AsCentro.isAliado(ent1, 'p'))
       return 2;
      else if(ent2AsCentro.isAliado(ent1,'s'))
       return 1;
      else
       return 0;
     }
    }
  }
 }
 public HashMap<String,ArrayList<CIDER_DB_Entity>> getSingleVariableQueryResult(CIDER_Variable var){
  HashMap<String,ArrayList<CIDER_DB_Entity>> queryResult = new HashMap<String,ArrayList<CIDER_DB_Entity>>();
  ArrayList<String> varOptions = var.getVariableOptions();
  for(int i=0;i<varOptions.size();i++){
   queryResult.put(varOptions.get(i),getFilterQueryResult(var, varOptions.get(i)));
  }
  return queryResult;
 }
 public HashMap<String, HashMap<String, ArrayList<CIDER_DB_Entity>>> getDoubleVariableQueryResult(CIDER_Variable var1, CIDER_Variable var2){
  return null;
 }
}
