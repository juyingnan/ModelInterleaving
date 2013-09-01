package model.bigraph.parser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.bigraph.Define;

public class DataflowDefineParser {
	
	public List<Define> readDefines(String fileName){
		List<Define> defineList = new ArrayList<Define>();
		String defines = readtxt(fileName);
		if(defines.contains("}")){
			String[] defs = defines.split("}");
			for(String def : defs){
				if(def.contains("{")){
					String[] defTerms = def.split("[{]");
					if(defTerms.length == 2){
						String term = defTerms[0];
						Define define = new Define(term);
						if(defTerms[1].contains(", ")){
							String[] pathIndexs = defTerms[1].split(", ");
							for(String index : pathIndexs){
								define.addPathIndex(Integer.parseInt(index));
							}
						}
						defineList.add(define);
					}
				}
			}
		}
		return defineList;
	}
	
	private String readtxt(String fileName){
		String result = "";	
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(fileName));
			String r;
			r = br.readLine(); 
			while(r != null){
				result += r;
				r = br.readLine();
			}
			br.close();
		} catch (FileNotFoundException e) {
			System.out.println("can not open the file:"+e);
		} catch (IOException e) {
			System.out.println("can not read the file:"+e);
		}			
		return result;
	}
	
	public static void main(String[] args){
		DataflowDefineParser ddp = new DataflowDefineParser();
		List<Define> defineList = ddp.readDefines("doc/dpath.txt");
		for(Define def : defineList){
			System.out.print("Term: " + def.getTerm() + " Path Index: ");
			for(int i : def.getPathIndex()){
				System.out.print(i + " ");
			}
			System.out.println("");
		}
	}
}
