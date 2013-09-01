package model.bigraph;

import java.util.ArrayList;
import java.util.List;

public class BigraphElement {
	private String name;
	private List<BigraphElement> children;
	private List<String> links;
	
	public BigraphElement(String name){
		this.name = name;
		this.children = new ArrayList<BigraphElement>();
		this.links = new ArrayList<String>();
	}
	
	public List<String> getElementNamesByControlName(String controlName){
		List<String> results = new ArrayList<String>();
		if(this.name.contains(controlName)){
			results.add(this.getName());
		}
		if(!this.children.isEmpty()){
			for(BigraphElement be : this.children){
				List<String> beNames = be.getElementNamesByControlName(controlName);
				if(!beNames.isEmpty()){
					for(String beName : beNames){
						results.add(beName);
					}
				}
			}
		}
		return results;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public List<BigraphElement> getChildren() {
		return children;
	}
	
	public List<BigraphElement> getDescendant() {
		List<BigraphElement> descendants = new ArrayList<BigraphElement>();
		descendants.addAll(this.children);
		return descendants;
	}
	
	public void setChildren(List<BigraphElement> children) {
		this.children = children;
	}
	
	public void addChildren(BigraphElement e) {
		this.children.add(e);
	}
	
	public List<String> getLinks() {
		return links;
	}
	
	public void setLinks(List<String> links) {
		this.links = links;
	}
	
	public void addLink(String l) {
		this.links.add(l);
	}
	
	public String toString(){
		String result = "";
		result += this.getName();
		result += "[";
		for(int i = 0; i < this.getLinks().size(); i++){
			result += this.getLinks().get(i).toString();
		}
		result += "]";
		result += "(";
		for(int i = 0; i < this.getChildren().size(); i++){
			result += this.getChildren().get(i).toString();
		}
		result += ")";
		return result;
	}
	
	public void printBigraphElement(){
		System.out.println("******************BigraphModel*********************");
		System.out.println(this.toString());
	}
	
	public boolean isDescendant(BigraphElement a){
		boolean result = false;
		
		if(this.getChildren().contains(a)){
			result = true;
		}else{
			for(int i = 0; i < this.getChildren().size(); i++){
				result = this.getChildren().get(i).isDescendant(a);
				if(result == true)
					return result;
			}
		}
		return result;
	}
}
