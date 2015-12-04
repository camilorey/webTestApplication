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
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import javax.imageio.ImageIO;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

/**
 *
 * @author laptop
 */
public class CIDER_multiBarrasPlot extends CIDER_doubleVariableStatPlot{
 HashMap<String,HashMap<String,Integer>> multibarrasContent;
 int total;
 float[] colorFactors;
 public CIDER_multiBarrasPlot(CIDER_DB parentDB,String dataPath,int w, int h) {
  super(parentDB,dataPath,w, h);
  multibarrasContent = null;
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
      multibarrasContent = parentDB.makeDoubleVariableStatNumberQuery(var1, var2, arrayOfFilters);
      if(arrayOfFilters.isEmpty()){
       total = parentDB.numCentros();
      }else{
       total = parentDB.numCentrosWithFilter(arrayOfFilters);
      }
      colorFactors = new float[10];
    }
   }catch(ParseException pe){
    throw new NullPointerException("parsing problem: "+pe.getMessage());
   }
 }
 @Override
 public void plot(Graphics2D g){
  if(multibarrasContent != null){
   g.setColor(Color.white);
   g.fillRect(0, 0, width, height);
   int barThickness = Math.round(height*0.95f) / multibarrasContent.keySet().size();
   int currentY = 0;
   int xOffset = Math.round(width*0.2f);
   for(String key: multibarrasContent.keySet()){
    HashMap<String,Integer> subBarContent = multibarrasContent.get(key);
    int index = 0;
    int subBarThickness = Math.round((float) barThickness / (float) subBarContent.keySet().size());
    int subCurrentY = currentY;
    for(String subKey: subBarContent.keySet()){
     float u = (float) index / (float) subBarContent.keySet().size();
     float v = (float) subBarContent.get(subKey) / (float) total;
     int thisBarWidth = Math.round(width*v);
     Color barColor = new Color(u,u,u);
     Color textColor = new Color(1-u,1-u,1-u);
     g.setPaint(barColor);
     g.fill(new Rectangle2D.Double(xOffset, subCurrentY,thisBarWidth, subBarThickness));
     g.setPaint(Color.black);
     g.drawRect(xOffset, subCurrentY,thisBarWidth, subBarThickness);
     index++;
     subCurrentY += subBarThickness;
    }
    g.setPaint(Color.black);
    g.drawString(key,0,currentY);
    currentY += barThickness;
   }
  }
 }
 @Override
 public void saveToFile(){
  try{
   File fileReference = new File(dataPath+"\\pruebaMultiBarras.png");
   ImageIO.write(plot, "png", fileReference);
  }catch(Exception ex){
   System.out.println("something went wrong: "+ex);
  }
 }
}
