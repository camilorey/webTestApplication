/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CIDER_statPlots;

import CIDER_DB.CIDER_DB;
import CIDER_DB.CIDER_Variable;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import javax.imageio.ImageIO;
import org.json.simple.parser.ParseException;

/**
 *
 * @author laptop
 */
public class CIDER_tortaPlot extends CIDER_singleVariableStatPlot{
 HashMap<String,Float> tortaContent;
 public CIDER_tortaPlot(CIDER_DB parentDB,String dataPath ,int w, int h) {
  super(parentDB, dataPath,w, h);
  tortaContent = null;
 }
 @Override
 public void makeSingleVariableQuery(String variableName, String filters) throws NullPointerException{
  try{
     ArrayList<String> arrayOfFilters = new ArrayList<String>();
     if(filters != null){
      arrayOfFilters = parseJSONArray(filters);
     }
     CIDER_Variable var = parentDB.getVariable(variableName);
     if(var == null){
      throw new NullPointerException("Variable not registered");
     }else{
      tortaContent = parentDB.makeSingleVariableStatPercentQuery(var, arrayOfFilters);
     }
    }catch(ParseException pe){
     throw new NullPointerException("parsing problem: "+pe.getMessage());
    }
 }
 public void plot(Graphics2D g){
  if(tortaContent != null){
   g.setColor(Color.white);
   g.fillRect(0, 0, width, height);
   g.setColor(Color.red);
   g.fillOval(0,0, width, height);
   g.setColor(Color.yellow);
   g.drawString("Te odio CIDER", width/2, height/2);
  }
 }
 @Override
 public void saveToFile(){
  try{
   File fileReference = new File(dataPath+"\\pruebaTorta.png");
   ImageIO.write(plot, "png", fileReference);
  }catch(Exception ex){
   System.out.println("something went wrong: "+ex);
  }
 }
}
