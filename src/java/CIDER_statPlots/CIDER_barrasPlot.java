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
import org.json.simple.parser.ParseException;

/**
 *
 * @author laptop
 */
public class CIDER_barrasPlot extends CIDER_singleVariableStatPlot{
 HashMap<String,Integer> barrasContent;
 int total;
 float[] colorFactors;
 public CIDER_barrasPlot(CIDER_DB parentDB,String dataPath,int w, int h) {
  super(parentDB, dataPath,w, h);
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
      barrasContent = parentDB.makeSingleVariableStatNumberQuery(var, arrayOfFilters);
      colorFactors = new float[barrasContent.keySet().size()];
      for(int i=0;i<colorFactors.length;i++){
       colorFactors[i] = (float) i / (float) colorFactors.length;
      }
      if(arrayOfFilters.isEmpty()){
       total = parentDB.numCentros();
      }else{
       total = parentDB.numCentrosWithFilter(arrayOfFilters);
      }
     }
    }catch(ParseException pe){
     throw new NullPointerException("parsing problem: "+pe.getMessage());
    }
 }
 @Override
 public void plot(Graphics2D g){
  if(barrasContent != null){
   g.setColor(Color.white);
   g.fillRect(0, 0, width, height);
   int barThickness = Math.round(height*0.95f) / barrasContent.keySet().size();
   int currentY = 0;
   int index = 0;
   int cX = 0;
   int cY = 0;
   for(String key: barrasContent.keySet()){
    float u = (float) barrasContent.get(key) / (float) total;
    int thisBarWidth = Math.round(u*width);
    Color barColor = new Color(colorFactors[index],colorFactors[index],colorFactors[index]);
    Color textColor = new Color(1-colorFactors[index],1-colorFactors[index],1-colorFactors[index]);
    g.setPaint(barColor);
    g.fill(new Rectangle2D.Double(0, currentY,thisBarWidth, barThickness));
    g.setPaint(Color.black);
    g.drawRect(0, currentY,thisBarWidth, barThickness);
    g.setPaint(textColor);
    g.drawString(key+"("+barrasContent.get(key)+")",0,currentY-barThickness/2);
    currentY += barThickness;
    index++;
   }
  }
 }
 @Override
 public void saveToFile(){
  try{
   File fileReference = new File(dataPath+"\\pruebaBarras.png");
   ImageIO.write(plot, "png", fileReference);
  }catch(Exception ex){
   System.out.println("something went wrong: "+ex);
  }
 }
}
