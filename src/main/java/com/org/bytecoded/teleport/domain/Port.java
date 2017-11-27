package com.org.bytecoded.teleport.domain;

public class Port<T> {

	T portName;

	boolean visited;

	public Port(T portNm){
		this.portName = portNm;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((portName == null) ? 0 : portName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Port<T> other = (Port<T>) obj;
		if (portName == null) {
			if (other.portName != null)
				return false;
		} else if (!portName.equals(other.portName))
			return false;
		return true;
	}


	public String toString(){
		return "(" + this.portName + "," + this.visited + ")";
	}


	public boolean isVisited() {
		return visited;
	}


	public void setVisited(boolean visited) {
		this.visited = visited;
	}


}
