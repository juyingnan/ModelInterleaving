package model.service;

import java.util.ArrayList;
import java.util.List;

public class ServiceModel {
	private String ip;
	private String path;
	private String namespace;
	private List<Service> services;
	
	public ServiceModel(String ip, String path, String namespace){
		this.ip = ip;
		this.path = path;
		this.namespace = namespace;
		this.services = new ArrayList<Service>();
	}
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public List<Service> getServices() {
		return services;
	}

	public void setServices(List<Service> services) {
		this.services = services;
	}
	
	public void addService(Service service){
		this.services.add(service);
	}
	
	public Service findServiceByName(String name){
		Service service = null;
		for(int i = 0; i < this.services.size(); i++){
			service = this.services.get(i).findServiceByName(name);
			if(service != null){
				return service;
			}
		}
		return service;
	}
	
	public Service findServiceByIndex(String index){
		Service service = null;
		for(Service s : this.services){
			if(s.getIndex().equals(index)){
				service = s;
			}
		}
		return service;
	}
	
	public void printServiceModel(){
		System.out.println("*****************ServiceModel***********************");
		for(Service service : this.services){
			service.printService();
		}
	}
}
