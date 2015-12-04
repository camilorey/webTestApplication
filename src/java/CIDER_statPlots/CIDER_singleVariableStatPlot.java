/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CIDER_statPlots;

import CIDER_DB.CIDER_DB;
import CIDER_DB.CIDER_Variable;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

/**
 *
 * @author laptop
 */
public class CIDER_singleVariableStatPlot extends CIDER_StatPlot{

 public CIDER_singleVariableStatPlot(CIDER_DB parentDB, int w, int h) {
  super(parentDB, w, h);
 }
 public void makeSingleVariableQuery(String variableName, String filters) throws NullPointerException{
 
 }
}
