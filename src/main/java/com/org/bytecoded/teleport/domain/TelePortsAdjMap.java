package com.org.bytecoded.teleport.domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

public class TelePortsAdjMap<T> implements Iterable<T> {
	
	private LinkedHashMap<T, List<T>> ports = new LinkedHashMap<T, List<T>>();
	
	public boolean addPort(T port){
		if (port == null) {
            throw new NullPointerException("The input port cannot be null.");
        }
		
        if (ports.containsKey(port)) return false;

        ports.put(port, new ArrayList<T>());
        return true;
	}
	
	public boolean addTelePort(T port, T telePort){
		if (port == null || telePort == null) {
			throw new NullPointerException("The input port and teleport cannot be null.");
		}

		if (!ports.containsKey(port)) {
			this.addPort(port);
		}

		ports.get(port).add(telePort);

		//As its bidirectional,add its port and adjacent ports
		this.addPort(telePort);
		ports.get(telePort).add(port);

		return true;
	}
	
	public LinkedHashMap<T, List<T>> getPorts(){
		return this.ports;
	}

	public Iterator<T> iterator() {
		// TODO Auto-generated method stub
		 return ports.keySet().iterator();
	}

}