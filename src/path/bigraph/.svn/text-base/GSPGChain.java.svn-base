package path.bigraph;

import java.util.List;

import model.efsm.Transition;
import model.efsm.State;
import model.service.Service;

public class GSPGChain {
	private Transition getTran = null;
	private Transition serviceTran = null;
	private Transition putTran = null;
	private Transition getCheckTran = null;
	private List<State> statesChain = null;
	private Service service = null;

	public GSPGChain(Transition getTran, Transition serviceTran, Transition putTran, Transition getCheckTran, List<State> states, Service service){
		this.getTran = getTran;
		this.serviceTran = serviceTran;
		this.putTran = putTran;
		this.getCheckTran = getCheckTran;
		this.statesChain = states;
		this.service = service;
	}
	
	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public Transition getGetTran() {
		return getTran;
	}
	
	public void setGetTran(Transition getTran) {
		this.getTran = getTran;
	}
	
	public Transition getServiceTran() {
		return serviceTran;
	}
	
	public void setServiceTran(Transition serviceTran) {
		this.serviceTran = serviceTran;
	}
	
	public Transition getPutTran() {
		return putTran;
	}
	
	public void setPutTran(Transition putTran) {
		this.putTran = putTran;
	}
	
	public Transition getGetCheckTran() {
		return getCheckTran;
	}

	public void setGetCheckTran(Transition getCheckTran) {
		this.getCheckTran = getCheckTran;
	}

	public List<State> getStatesChain() {
		return statesChain;
	}

	public void setStatesChain(List<State> statesChain) {
		this.statesChain = statesChain;
	}
	
	public void addState(State state){
		this.statesChain.add(state);
	}
}
