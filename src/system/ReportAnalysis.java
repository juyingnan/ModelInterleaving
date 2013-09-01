package system;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReportAnalysis {
	
	public ArrayList<ArrayList<String>> readPathFile(String fileName, String sheetName) throws FileNotFoundException, IOException{
		ArrayList<ArrayList<String>> paths = new ArrayList<ArrayList<String>>();
		XSSFWorkbook  wb = new XSSFWorkbook(new FileInputStream(new File(fileName)));
		XSSFSheet sheet = wb.getSheet(sheetName);
		for(int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++){
			ArrayList<String> path = new ArrayList<String>();
			XSSFRow row = sheet.getRow(i);
			for(int j = 0; j < row.getLastCellNum(); j++){
				XSSFCell cell = row.getCell(j);
				String s = cell.getStringCellValue();
				if(s.trim().length() > 0){
					path.add(cell.getStringCellValue());															
				}
			}
			paths.add(path);
		}
		
		return paths;
	}
	
	public ArrayList<String> readTermFile(String fileName, String sheetName) throws FileNotFoundException, IOException{
		ArrayList<String> terms = new ArrayList<String>();
		XSSFWorkbook  wb = new XSSFWorkbook(new FileInputStream(new File(fileName)));
		XSSFSheet sheet = wb.getSheet(sheetName);
		for(int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++){
			XSSFRow row = sheet.getRow(i);
			XSSFCell cell = row.getCell(0);
			terms.add(cell.getStringCellValue());
		}	
		return terms;
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException{
		ReportAnalysis ra = new ReportAnalysis();
		String fileName = "D:\\Info.xlsx";
		
		ArrayList<ArrayList<String>> paths = ra.readPathFile(fileName, "Pattern");
		//ArrayList<ArrayList<String>> paths = ra.readPathFile(fileName, "TestingPaths");
		int i = 0;
		for(ArrayList<String> path : paths){
			System.out.print("path" + i + ": ");
			for(String c : path){
				System.out.print(c + "  ");
			}
			System.out.print("\n");
			i++;
		}
		
		ArrayList<String> terms = ra.readTermFile(fileName, "StateTerm");
		int j = 0;
		for(String term : terms){
			System.out.println("term" + j + ": " + term);
			j++;
		}
		
		File file = new File("D:\\BigraphModelGen(Pattern).txt");
		if(file.exists()){
			file.delete();
		}
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		FileOutputStream fos = null;  
		try {
			fos = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		PrintWriter out = new PrintWriter(fos);
		out.flush();	
		
		ArrayList<String> BigraphPath = new ArrayList<String>();
		int id = 0;
		for(ArrayList<String> path : paths){
			String s = id + "{" + "\n";
			for(int m = 0; m < path.size() - 1; m++){
				if(path.get(m).contains("C")){				
					int index = Integer.parseInt(path.get(m).trim().substring(1));					
					s += terms.get(index - 1);
					s += "\n";					
				}
			}
			BigDecimal big =   new BigDecimal(Double.parseDouble(path.get(path.size() - 1).trim().substring(1)));  
			String coverage = big.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue() +"%";
			s += "}" + coverage + "\n";
			BigraphPath.add(s);
			System.out.print(s);
			out.print(s);
			out.flush();
			id ++;
		}
		out.close();
		
		ra.generateRandomTestCases(fileName, "FFAllDef");
	}
	
	public void generateRandomTestCases(String fileName, String sheetName) throws FileNotFoundException, IOException{
		File file = new File("d:\\AllDef.txt");
		if(file.exists()){
			file.delete();
		}
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		FileOutputStream fos = null;  
		try {
			fos = new FileOutputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		PrintWriter out = new PrintWriter(fos);
		out.flush();	
		
		
		int[][] fr = new int[38][21];
		XSSFWorkbook  wb = new XSSFWorkbook(new FileInputStream(new File(fileName)));
		XSSFSheet sheet = wb.getSheet(sheetName);
		for(int i = sheet.getFirstRowNum(); i <= 37; i++){
			XSSFRow row = sheet.getRow(i);
			for(int j = 0; j < row.getLastCellNum(); j++){
				XSSFCell cell = row.getCell(j);
				fr[i][j] = (int)cell.getNumericCellValue();
			}
		}
		
		for(int time = 0; time < 10; time++){
			int[] sum = new int[21];
			double[] rate = new double[21];
			Random r = new Random();
			ArrayList<Integer> row = new ArrayList<Integer>();
			while(row.size() < 15){
				int x = r.nextInt(38);
				if(row.indexOf(x) == -1){
					row.add(x);
				}
			}
			for(int i = 0; i < 21; i++){
				for(int h : row){
					sum[i] += fr[h][i];					
				}
				rate[i] = sum[i]/15.0;
				out.print(rate[i] + "\t");
				out.flush();
			}
			out.println("");
			out.flush();
		}
		out.close();
	}
}
