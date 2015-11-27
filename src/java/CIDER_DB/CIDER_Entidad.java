/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CIDER_DB;

import org.json.simple.JSONObject;

/**
 *
 * @author laptop
 */
public class CIDER_Entidad extends CIDER_DB_Entity{

 
 public CIDER_Entidad() {
 }
 public CIDER_Entidad(String entityName) {
  super(entityName);
 }
 @Override
 public String toJSON() {
  JSONObject jsonObject=new JSONObject();
  //make object
  jsonObject.put("nombre",getEntityName());
  return jsonObject.toJSONString();
 }
}
