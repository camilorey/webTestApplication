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
public class CIDER_comparativoPlot extends CIDER_doubleVariableStatPlot{
 HashMap<String,HashMap<String,Float>> comparativoContent;
 public CIDER_comparativoPlot(CIDER_DB parentDB, int w, int h) {
  super(parentDB, w, h);
  comparativoContent = null;
 }
 @Override
 public void makeDoubleVariableQuery(String variable1Name,String variable2Name, String filters) throws NullPointerException{
   try{
    ArrayList<String> arrayOfFilters = new ArrayList<String>();
    if(filters != null){
     arrayOfFilters = parseJSONArray(filters);
    }
    CIDER_Variable var1 = parentDB.getVariable(variable1Name);
    CIDER_Variable var2 = parentDB.getVariable(variable2Name);
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
      comparativoContent = parentDB.makeDoubleVariableStatPercentQuery(var1, var2, arrayOfFilters);
    }
   }catch(ParseException pe){
    throw new NullPointerException("parsing problem: "+pe.getMessage());
   }
 }
 @Override
 public void plot(Graphics2D g){
  if(comparativoContent != null){
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
   File fileReference = new File(dataPath+"\\pruebaComparativo.png");
   ImageIO.write(plot, "png", fileReference);
  }catch(Exception ex){
   System.out.println("something went wrong: "+ex);
  }
 }
}
