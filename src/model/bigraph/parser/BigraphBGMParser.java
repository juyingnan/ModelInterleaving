package model.bigraph.parser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.bigraph.BigraphElement;

import org.bigraph.bigmc.Paraller;
import org.bigraph.bigmc.Prefix;
import org.bigraph.bigmc.Term;
import org.bigraph.bigmc.TermType;

public class BigraphBGMParser {
	List<String> ruleCoverages = new ArrayList<String>();
		
	public boolean checkAinB(BigraphElement a, BigraphElement b){
		boolean result = false;
		if(b.isDescendant(a)){
			result = true;
		}
		return result;
	}
	
	public boolean checkAlinkB(BigraphElement a, BigraphElement b){
		boolean result = false;
		for(int i = 0; i < a.getLinks().size(); i++){
			String t = a.getLinks().get(i);
			if(t.startsWith("Name:(idle")){// idle 表示port没有连边
				continue;
			}
			if(b.getLinks().contains(a.getLinks().get(i))){
				result = true;
			}
		}
		return result;
	} 
	
	public BigraphElement checkA(BigraphElement e, String a){
		BigraphElement result = null;
		if(e.getName().equals(a)){
			result = e;
			return result;
		}else{
			for(int i = 0; i < e.getChildren().size(); i++){
				result = checkA(e.getChildren().get(i), a);
				if(result != null)
					return result;
			}
			return result;
		}
	}
	
	public BigraphElement TranslateTerm(Term term){
		BigraphElement result = new BigraphElement("root");
		TermToElement(term, result);
		return result;
	}
	
	public void TermToElement(Term term, BigraphElement parent){
		if(term.termType() == TermType.TPREF()){
			BigraphElement element = new BigraphElement(((Prefix)term).ctrl().name());
			for(int i = 0; i < ((Prefix)term).port().length(); i++){
				element.addLink(((Prefix)term).port().apply(i).toString());
			}
			parent.addChildren(element);
			TermToElement(((Prefix)term).suffix(), element);
		}else if(term.termType() == TermType.TPAR()){
			TermToElement(((Paraller)term).leftTerm(), parent);
			TermToElement(((Paraller)term).rightTerm(), parent);
		}else{
			return;
		}
	}
	
	public List<String[]> readContextModels(String fileName){
		List<String[]> contextModels = new ArrayList<String[]>();
		String models = readtxt(fileName);
		if(models.contains("%")){
			String[] cModels = models.split("%");
			for(String cModel : cModels){
				if(cModel.contains("{")){
					String[] indexPathCoverage = cModel.split("[{]");
					if(indexPathCoverage.length == 2){
						if(indexPathCoverage[1].contains("}")){
							String[] pathCoverage = indexPathCoverage[1].split("}");
							if(pathCoverage.length == 2){
								String[] contextM = readContextModel(pathCoverage[0]);
								contextModels.add(contextM);
								ruleCoverages.add(pathCoverage[1]);
//								ruleCoverages.set(Integer.parseInt(indexPathCoverage[0]),
//										pathCoverage[1]);
							}
						}
					}
				}
			}
		}
		return contextModels;
	}
	
	private String[] readContextModel(String contextModel){
		String[] result = null;
		if(contextModel.contains(";")){
			result = contextModel.split(";");
		}		
		return result;
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

	public List<String> getRuleCoverages() {
		return ruleCoverages;
	}
	
	public static void main(String[] args){
		BigraphBGMParser bp = new BigraphBGMParser();
		bp.readContextModels("doc/BigraphModel.txt");
		List<String> ruleCoverages = bp.getRuleCoverages();
		for(int i = 0; i < ruleCoverages.size(); i++){
			String coverage = ruleCoverages.get(i);
			System.out.println("Index: " + i + " Coverage: " + coverage);
		}
	}
}
