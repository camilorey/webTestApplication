/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CIDER_DB;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author laptop
 */
public class CIDER_ExcelHandler {
 protected String filePath;
 protected String fileName;
 
 protected XSSFWorkbook CIDER_workBook;
 protected XSSFSheet CIDER_variableSetSheet;
 protected XSSFSheet CIDER_dataBaseSheet;
 
 protected HashMap<String, Integer> variableToDBMap;
 
 public CIDER_ExcelHandler(String filePath, String fileName) {
  this.filePath = filePath;
  this.fileName = fileName;
  variableToDBMap = new HashMap<String, Integer>();
 }
 public PrintWriter openFile(PrintWriter responseMonitor){
  responseMonitor.println("trying to open Excel File <br>");
  try {
      File fileReference = new File(filePath+"\\"+fileName);
      FileInputStream fileOpener = new FileInputStream(fileReference);
      responseMonitor.println("file "+ fileName +" opened <br>");
      responseMonitor.println("file size to read: " + fileOpener.available()+"<br>");
      CIDER_workBook = new XSSFWorkbook(fileOpener);
      CIDER_variableSetSheet = CIDER_workBook.getSheetAt(0);
      CIDER_dataBaseSheet = CIDER_workBook.getSheetAt(1);
      responseMonitor.println("--------------FILE OPENDED-------------------"+"<br>");
      createVariableToDBMap();
    }
    catch(IOException e) {
      responseMonitor.println("File open failed due to: <br>");
      responseMonitor.println(e.toString());
      responseMonitor.println("<br>");
    }
  return responseMonitor;
 }
 public void openFile() throws IOException{
  try {
      File fileReference = new File(filePath+"\\"+fileName);
      FileInputStream fileOpener = new FileInputStream(fileReference);
      CIDER_workBook = new XSSFWorkbook(fileOpener);
      CIDER_variableSetSheet = CIDER_workBook.getSheetAt(0);
      CIDER_dataBaseSheet = CIDER_workBook.getSheetAt(1);
      createVariableToDBMap();
    }
    catch(IOException e) {
      throw new IOException("file not opened due to: "+e.getMessage());
    }
 }
 public ArrayList<String> getColumnContent(XSSFSheet sheet, int colNumber){
  ArrayList<String> column = new ArrayList<String>();
    Iterator<Row> rowIterator = sheet.iterator();
    boolean reachedEmptyCell = false;
    while (rowIterator.hasNext () && !reachedEmptyCell) {
      Row currentRow = rowIterator.next();
      Cell currentCell = currentRow.getCell(colNumber);
      if(currentCell != null){
       if(!currentCell.toString().equals("")){
        if (currentCell.getCellType() == Cell.CELL_TYPE_STRING) {
         column.add(currentCell.getStringCellValue());
        }
       }else{
        reachedEmptyCell = true;
       }
      }
    }
    return column;
 }
 public CIDER_VariableHandler createVariableSet(PrintWriter responseMonitor){
  CIDER_VariableHandler varHandler = new CIDER_VariableHandler();
  int variableCount = CIDER_variableSetSheet.getRow(0).getPhysicalNumberOfCells();
  boolean reachedEmptyColumn = false;
  int loadedVariables = 0;
  for(int i=0;i<variableCount && !reachedEmptyColumn;i++){
   ArrayList<String> variableColumn = getColumnContent(CIDER_variableSetSheet, i);
   if(variableColumn.isEmpty()){
    reachedEmptyColumn = true;
   }else{
    if(varHandler.isVariableIn(variableColumn.get(0))){
     responseMonitor.println("variable already added"+"<br>");
    }else{
      CIDER_Variable variable = new CIDER_Variable(variableColumn.get(0).toUpperCase());
      for(int j=1;j<variableColumn.size();j++){
       if(!variable.isInOptions(variableColumn.get(j))){
        variable.addVariableOption(variableColumn.get(j));
       }else{
        responseMonitor.println("variable option already defined"+"<br>");
       }
      }
      varHandler.addVariable(variable);
      loadedVariables++;
    }
   }
  }
  
  responseMonitor.println("========================================================"+"<br>");
  responseMonitor.println("===============VARIABLE SET INFO========================"+"<br>");
  responseMonitor.println("variables read: "+loadedVariables+"<br>");
  varHandler.printVariableSetInfo(responseMonitor);
  return varHandler;
 }
 public CIDER_VariableHandler createVariableSet(){
  CIDER_VariableHandler varHandler = new CIDER_VariableHandler();
  int variableCount = CIDER_variableSetSheet.getRow(0).getPhysicalNumberOfCells();
  boolean reachedEmptyColumn = false;
  int loadedVariables = 0;
  for(int i=0;i<variableCount && !reachedEmptyColumn;i++){
   ArrayList<String> variableColumn = getColumnContent(CIDER_variableSetSheet, i);
   if(variableColumn.isEmpty()){
    reachedEmptyColumn = true;
   }else{
    if(varHandler.isVariableIn(variableColumn.get(0))){
     
    }else{
      CIDER_Variable variable = new CIDER_Variable(variableColumn.get(0).toUpperCase());
      for(int j=1;j<variableColumn.size();j++){
       if(!variable.isInOptions(variableColumn.get(j))){
        variable.addVariableOption(variableColumn.get(j));
       }
      }
      varHandler.addVariable(variable);
      loadedVariables++;
    }
   }
  }
  return varHandler;
 }
 public void createVariableToDBMap(){
  Iterator<Row> rowIterator = CIDER_dataBaseSheet.iterator(); 
  int rowCounter = 0;
  int columnIndex = 0;
  variableToDBMap = new HashMap<String, Integer>();
  while (rowIterator.hasNext ()) {
   Row currentRow = rowIterator.next();
   if (rowCounter == 0) {
    Iterator<Cell> columnIterator = currentRow.cellIterator();
    columnIndex = 0;
    while (columnIterator.hasNext ()) {
    Cell currentCell = columnIterator.next();
     if (currentCell.getCellType() == Cell.CELL_TYPE_STRING) {
      variableToDBMap.put(currentCell.getStringCellValue().toUpperCase(), new Integer(columnIndex));
     }
     columnIndex++;
    }
    rowCounter++;
   }
  }
  
 }
 public CIDER_Centro fillUniqueFields(Row r, CIDER_Centro centro){
  String[] variableNames = new String[]{"NOMBRE","ENTIDAD ADSCRITA A","DIRECCIÓN","CONTACTO TOP","E-MAIL CONTACTO TOP",
                                        "TELÉFONO 1", "COORDENADAS DE UBICACIÓN","PRODUCCIÓN","TAMAÑO",
                                        "NATURALEZA","CIUDAD/MUNICIPIO","DEPARTAMENTO",
                                        "ALCANCE TERRITORIAL","PRIORIDAD PARA EL CIDER",
                                        "ROL EN SU ENTORNO","REGIÓN","ROL FRENTE AL CIDER","PÁGINA(S) WEB","OFERTA ACADÉMICA","E-MAIL INSTITUCIONAL"};
  for(int i=0;i<variableNames.length;i++){
   int variableNumber = variableToDBMap.get(variableNames[i]);
   if(variableNumber != -1){
    Cell currentCell = r.getCell(variableNumber);
    if(currentCell != null){
     String cellContent;
    if(currentCell.getCellType() == Cell.CELL_TYPE_NUMERIC){
     cellContent = currentCell.getNumericCellValue()+"";
    }else{
     cellContent = currentCell.getStringCellValue();
    }
    centro.makeSingleVariable(variableNames[i],cellContent);
    }else{
     System.out.println("null cell value asking for: "+variableToDBMap.get(variableNames[i]));
    }
   }
  }
  return centro;
 }
 public CIDER_Centro fillMultiVariableFields(Row r, CIDER_Centro centro,CIDER_VariableHandler variables){
  String[] multiVariableNames = new String[]{"TEMÁTICA DE ÉNFASIS:5","DIFERENCIADOR:4",
                                             "CLIENTE FINAL:5","ACTIVIDAD ECONÓMICA:3"};
  for(int i=0;i<multiVariableNames.length;i++){
   String[] tokens = multiVariableNames[i].split(":");
   String varName = tokens[0];
   int numOptions = Integer.parseInt(tokens[1]);
   ArrayList<String> varValues = new ArrayList<String>();
   for(int j=1;j<=numOptions;j++){
    String columnName = tokens[0]+" "+j;
    String cellValue = r.getCell(variableToDBMap.get(columnName)).getStringCellValue();
    if(!cellValue.equals("NA") && !cellValue.equals("No hay información disponible")){
     varValues.add(cellValue);
    }
    centro.makeMultivariable(varName, varValues);
   }
  }
  centro.reportMultiVariables();
  return centro;
 }
 public CIDER_Centro createAliadosForCentro(Row r, CIDER_Centro centro, CIDER_CentrosHandler centrosHandler){
  for(int i=1;i<=10;i++){
   String principalResult = r.getCell(variableToDBMap.get("ALIADO PRINCIPAL "+i)).getStringCellValue();
   String secundarioResult = r.getCell(variableToDBMap.get("ALIADO SECUNDARIO "+i)).getStringCellValue();
   if(!principalResult.equals("NA")){
    if(!principalResult.equals("No hay información disponible")){
     int entityType = centrosHandler.entityType(principalResult);
     if(entityType==-1){
      CIDER_Entidad aliadoP = new CIDER_Entidad(principalResult);
      centrosHandler.addEntity(aliadoP);
      centro.addToAliados(aliadoP, 'p');
     }else{
       CIDER_DB_Entity aliadoP = centrosHandler.get(principalResult);
       centro.addToAliados(aliadoP,'p');
     }
    }
   }   
   if(!secundarioResult.equals("NA")){
    if(!secundarioResult.equals("No hay información disponible")){
     int entityType = centrosHandler.entityType(secundarioResult);
     if(entityType==-1){
      CIDER_Entidad aliadoS = new CIDER_Entidad(secundarioResult);
      centrosHandler.addEntity(aliadoS);
      centro.addToAliados(aliadoS, 's');
     }else{
       CIDER_DB_Entity aliadoS = centrosHandler.get(secundarioResult);
       centro.addToAliados(aliadoS,'s');
     }
    }
   }
  }
  return centro;
 }
 public CIDER_CentrosHandler createAliados(CIDER_CentrosHandler centrosHandler){
  Iterator<Row> rowIterator = CIDER_dataBaseSheet.iterator();
  int rowCounter = 0;
  int numCentrosConAliados = 0;
  boolean reachedEmptyRow = false;
  while(rowIterator.hasNext() && !reachedEmptyRow){
   if(rowCounter !=0){
    Row currentRow = rowIterator.next();
    String centroName = currentRow.getCell(variableToDBMap.get("NOMBRE")).getStringCellValue();
    if(centroName.equals("")){
     reachedEmptyRow = true;
    }else{
     CIDER_DB_Entity entity = centrosHandler.get(centroName);
     if(entity!= null){
      if(entity instanceof CIDER_Centro){
       CIDER_Centro centro = (CIDER_Centro) centrosHandler.get(centroName);
       CIDER_Centro centroConAliados = createAliadosForCentro(currentRow, centro, centrosHandler);
       centrosHandler.replace(centro, centroConAliados);
       numCentrosConAliados++;
      }
     }
    }
   }
   rowCounter++;
  }
  System.out.println("Excel rows read for aliados creation: "+rowCounter+" aliados created for: "+numCentrosConAliados+" centros");
  return centrosHandler;
 }
 public CIDER_CentrosHandler createAliados(CIDER_CentrosHandler centrosHandler, PrintWriter responseMonitor){
  Iterator<Row> rowIterator = CIDER_dataBaseSheet.iterator();
  int rowCounter = 0;
  int numCentrosConAliados = 0;
  boolean reachedEmptyRow = false;
  while(rowIterator.hasNext() && !reachedEmptyRow){
   if(rowCounter !=0){
    Row currentRow = rowIterator.next();
    String centroName = currentRow.getCell(variableToDBMap.get("NOMBRE")).getStringCellValue();
    if(centroName.equals("")){
     reachedEmptyRow = true;
    }else{
     CIDER_DB_Entity entity = centrosHandler.get(centroName);
     if(entity!= null){
      if(entity instanceof CIDER_Centro){
       CIDER_Centro centro = (CIDER_Centro) centrosHandler.get(centroName);
       CIDER_Centro centroConAliados = createAliadosForCentro(currentRow, centro, centrosHandler);
       centrosHandler.replace(centro, centroConAliados);
       numCentrosConAliados++;
      }
     }
    }
   }
   rowCounter++;
  }
  responseMonitor.println("Excel rows read for aliados creation: "+rowCounter+" aliados created for: "+numCentrosConAliados+" centros"+"<br>");
  return centrosHandler;
 }
 public CIDER_CentrosHandler createCentros(CIDER_VariableHandler variables){
  CIDER_CentrosHandler centrosHandler = new CIDER_CentrosHandler();
  Iterator<Row> rowIterator = CIDER_dataBaseSheet.iterator();
  int rowCounter = 0;
  int numCentrosCreados = 0;
  boolean reachedEmptyRow = false;
  while(rowIterator.hasNext() && !reachedEmptyRow){
   Row currentRow = rowIterator.next();
   if(rowCounter !=0){
    String centroName = currentRow.getCell(variableToDBMap.get("NOMBRE")).getStringCellValue();
    if(centroName.equals("")){
     reachedEmptyRow = true;
    }else{
     String adscritoA = currentRow.getCell(variableToDBMap.get("ENTIDAD ADSCRITA A")).getStringCellValue();
     CIDER_Centro centro = new CIDER_Centro(centroName);
     centro = fillUniqueFields(currentRow, centro);
     centro = fillMultiVariableFields(currentRow, centro, variables);
     centrosHandler.addEntity(centro);
     numCentrosCreados++;
    }
   }
   rowCounter++;
  }
  System.out.println("Excel rows read for centros creation: "+rowCounter+ " centros created "+ numCentrosCreados);
  return centrosHandler;
 }
 public CIDER_CentrosHandler createCentros(CIDER_VariableHandler variables, PrintWriter responseMonitor){
  CIDER_CentrosHandler centrosHandler = new CIDER_CentrosHandler();
  Iterator<Row> rowIterator = CIDER_dataBaseSheet.iterator();
  int rowCounter = 0;
  int numCentrosCreados = 0;
  boolean reachedEmptyRow = false;
  while(rowIterator.hasNext() && !reachedEmptyRow){
   Row currentRow = rowIterator.next();
   if(rowCounter !=0){
    String centroName = currentRow.getCell(variableToDBMap.get("NOMBRE")).getStringCellValue();
    if(centroName.equals("")){
     reachedEmptyRow = true;
    }else{
     String adscritoA = currentRow.getCell(variableToDBMap.get("ENTIDAD ADSCRITA A")).getStringCellValue();
     CIDER_Centro centro = new CIDER_Centro(centroName);
     centro = fillUniqueFields(currentRow, centro);
     centro = fillMultiVariableFields(currentRow, centro, variables);
     centrosHandler.addEntity(centro);
     numCentrosCreados++;
    }
   }
   rowCounter++;
  }
  responseMonitor.println("Excel rows read for centros creation: "+(rowCounter-1)+ ", centros created "+ numCentrosCreados+"<br>");
  return centrosHandler;
 }
 public CIDER_CentrosHandler createCentrosHandler(CIDER_VariableHandler variables){
  System.out.println("=============EXCEL REPORT DATA=======================================");
  System.out.println("-------------------creating centros-----------------------------------");
  CIDER_CentrosHandler centros = createCentros(variables);
  System.out.println("-------------------creating aliados-----------------------------------");
  CIDER_CentrosHandler centrosConAliados = createAliados(centros);
  return centrosConAliados;
 }
 public CIDER_CentrosHandler createCentrosHandler(CIDER_VariableHandler variables, PrintWriter responseMonitor){
  responseMonitor.println("=============EXCEL REPORT DATA======================================="+"<br>");
  responseMonitor.println("-------------------creating centros-----------------------------------"+"<br>");
  CIDER_CentrosHandler centros = createCentros(variables,responseMonitor);
  responseMonitor.println("-------------------creating aliados-----------------------------------"+"<br>");
  CIDER_CentrosHandler centrosConAliados = createAliados(centros);
  return centrosConAliados;
 }
}
