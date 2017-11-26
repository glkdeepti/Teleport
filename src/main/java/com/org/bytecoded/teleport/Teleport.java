package com.org.bytecoded.teleport;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import com.org.bytecoded.teleport.domain.Port;

public class Teleport {
	
	private static final Logger LOGGER = Logger.getLogger(Teleport.class.getName());

	public static<T> boolean canTeleport(Port<T> fromCity, Port<T> toCity, LinkedHashMap<Port<T>, List<Port<T>>> ports){

		Set<Port<T>> teleportableCities = new HashSet<Port<T>>(ports.get(fromCity));

		if(teleportableCities.contains(toCity)){
			return true;
		} else {
			fromCity.setVisited(true);
			Iterator<Port<T>> iter = teleportableCities.iterator();
			while (iter.hasNext()) {
				Port<T> adjacentPort = iter.next();
				if(!adjacentPort.isVisited())
					return canTeleport(adjacentPort, toCity, ports);
			}
		}

		return false;

	}

	public static<T> Set<Port<T>> getCitiesFrom(Port<T> cityPort, int noOfJumps, LinkedHashMap<Port<T>, List<Port<T>>> ports){

		Set<Port<T>> teleportableCities = new LinkedHashSet<Port<T>>();

		int i = 0;
		while(i < noOfJumps){

			if(i == 0){
				cityPort.setVisited(true);
				teleportableCities.addAll(ports.get(cityPort));
			}
			else{
				Set<Port<T>> nextTeleportableCities = new LinkedHashSet<Port<T>>();
				Iterator<Port<T>> iter = teleportableCities.iterator();
				while (iter.hasNext()) {
					Port<T> adjacentPort = iter.next();
					if (!adjacentPort.isVisited()) {
						adjacentPort.setVisited(true);
						nextTeleportableCities.addAll(ports.get(adjacentPort));
					}
				}

				teleportableCities.addAll(nextTeleportableCities);
			}
			i++;
		}

		if(teleportableCities.contains(cityPort)){ //Remove the originating city it could have added as we can travers too and fro.
			teleportableCities.remove(cityPort);
		}
		return teleportableCities;
	}
	
	public static<T> boolean isInLoop(Port<T> originCity, LinkedHashMap<Port<T>, List<Port<T>>> ports){
		
		Set<Port<T>> adjacentCities = new LinkedHashSet<Port<T>>(ports.get(originCity));
		
		Iterator<Port<T>> iter = adjacentCities.iterator();

		Port<T> parent = originCity;
		
		while (iter.hasNext()) {

			//DFS Depth First Search for (city)
			if(recursive(iter.next(), parent, originCity, ports)) {
				return true;
			}

		}

		return false;
		
	}
	

	private static<T> boolean recursive(Port<T> next, Port<T> parent, Port<T> origin, LinkedHashMap<Port<T>, List<Port<T>>> ports) {

		LOGGER.info( "NEXT:" + next + " PARENT:" + parent + " ORIGIN:" + origin);
		
		if (!next.isVisited()) {
			next.setVisited(true);
		}
		
		Set<Port<T>> nextAdjacentCities = new LinkedHashSet<Port<T>>(ports.get(next));
		
		if(null != nextAdjacentCities && nextAdjacentCities.contains(parent)){
			nextAdjacentCities.remove(parent);
			if(nextAdjacentCities.contains(origin)){
				return true;
			}
		}
		
		Iterator<Port<T>> itr = nextAdjacentCities.iterator();
		while (itr.hasNext()) {
			parent = next;
			Port<T> nextcity = itr.next();
			if(!nextcity.isVisited()) {
				if(recursive(nextcity, parent, origin, ports)) {
					return true;
				}
			}
		}
		
		return false;

	}
}
