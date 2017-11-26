package com.org.bytecoded.teleport.domain;

public class Port<T> {

	T portName;
	
	boolean visited;
	
	public Port(T portNm){
		this.portName = portNm;
	}
	
	
	public String toString(){
		return (String) this.portName;
	}


	public boolean isVisited() {
		return visited;
	}


	public void setVisited(boolean visited) {
		this.visited = visited;
	}
	
	
}
