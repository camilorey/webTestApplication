/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CIDER_statPlots;

import CIDER_DB.CIDER_DB;

/**
 *
 * @author laptop
 */
public class CIDER_doubleVariableStatPlot extends CIDER_StatPlot{

 public CIDER_doubleVariableStatPlot(CIDER_DB parentDB,String dataPath, int w, int h) {
  super(parentDB, dataPath,w, h);
 }
 
 public void makeDoubleVariableQuery(String variable1Name,String variable2Name, String filters) throws NullPointerException{
 
 }
}
