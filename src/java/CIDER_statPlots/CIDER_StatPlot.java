/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CIDER_statPlots;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import javax.imageio.ImageIO;

/**
 *
 * @author laptop
 */
public class CIDER_StatPlot {
 protected final String dataPath = "C:\\desarrollo\\desarrolloCIDER\\webTestApplication\\Data";
 int width;
 int height;
 BufferedImage plot; 
 Graphics2D g2D;
 public CIDER_StatPlot() {
 
 }
 public CIDER_StatPlot(int w, int h){
  width = w;
  height = h;
  plot = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
 }
 public void update(){
  g2D = plot.createGraphics();
  plot(g2D);
  g2D.dispose();
 }
 public void plot(Graphics2D g){
  g.setColor(Color.white);
  g.fillRect(0, 0, width, height);
  g.setColor(Color.red);
  g.fillOval(0,0, width, height);
  g.setColor(Color.yellow);
  g.drawString("Te odio CIDER", width/2, height/2);
 }
 public BufferedImage getPlot(){
  return plot;
 }
 public void saveToFile(){
  try{
   File fileReference = new File(dataPath+"\\prueba.png");
   ImageIO.write(plot, "png", fileReference);
  }catch(Exception ex){
   System.out.println("something went wrong: "+ex);
  }
 }
 public byte[] getByteArray(){
  byte[] asBytes = null;
  ByteArrayOutputStream output = new ByteArrayOutputStream();
  try{
  ImageIO.write(plot, "png", output);
  asBytes = output.toByteArray();
  }catch(Exception ex){
    System.out.println("something went wrong");
  }
  return asBytes;
 }
}
