package model.service.petrinet;

public class Place {
	private String id;
	private String serviceName;
	private String path;
	private String function;
	
	public Place(String id, String serviceName, String path, String function){
		this.id = id;
		this.serviceName = serviceName;
		this.path = path;
		this.function = function;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
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

}
