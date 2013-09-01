package model.service;

import java.util.ArrayList;
import java.util.List;

public class Service {
	private String name;
	private String index;
	private String path;
	private String function;
	private String isProcess;
	private String endFlag;
	private List<Service> subServices;
	
	public Service(String index, String name, String path, String function, String isProcess, String endFLag){
		this.name = name;
		this.index = index;
		this.path = path;
		this.function = function;
		this.isProcess = isProcess;
		this.endFlag = endFLag;
		this.subServices = new ArrayList<Service>();
	}
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public String getIsProcess() {
		return isProcess;
	}

	public void setIsProcess(String isProcess) {
		this.isProcess = isProcess;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getIndex() {
		return index;
	}
	
	public void setIndex(String index) {
		this.index = index;
	}
	
	public String getEndFlag() {
		return endFlag;
	}

	public void setEndFlag(String endFlag) {
		this.endFlag = endFlag;
	}
	
	public List<Service> getSubServices() {
		return subServices;
	}
	
	public void setSubServices(List<Service> subServices) {
		this.subServices = subServices;
	}
	
	public void addSubService(Service subService){
		this.subServices.add(subService);
	}
	
	public Service findServiceByName(String name){
		if(this.name.equals(name)){
			return this;
		}
		else if(!this.subServices.isEmpty()){
			Service subService = null;
			for(int i = 0; i < this.subServices.size(); i++){
				subService = this.subServices.get(i).findServiceByName(name);
				if(subService != null){
					return subService;
				}
			}
		}
		return null;
	}
	
	public void printService(){
		System.out.println("*****************Service***********************");
		System.out.println("Name:" + this.name + " Index:" + this.index);
		if(!this.subServices.isEmpty()){
			for(Service service : this.subServices){
				service.printService();
			}
		}
	}
}
