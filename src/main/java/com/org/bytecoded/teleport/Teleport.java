package com.org.bytecoded.teleport;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.org.bytecoded.teleport.domain.Port;

public class Teleport {

	private static final Logger LOGGER = Logger.getLogger(Teleport.class.getName());

	public static Set<Port> getCitiesInNJumps(Port cityPort, int noOfJumps, LinkedHashMap<Port, List<Port>> ports) {
		Set<Port> teleportableCities = new LinkedHashSet<Port>();

		int i = 0;
		while(i < noOfJumps){

			if(i == 0){
				cityPort.setVisited(true);
				teleportableCities.addAll(ports.get(cityPort));
			}
			else{
				Set<Port> nextTeleportableCities = new LinkedHashSet<Port>();
				Iterator<Port> iter = teleportableCities.iterator();
				while (iter.hasNext()) {
					Port adjacentPort = iter.next();
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

	public static boolean canTeleport(Port fromCity, Port toCity, LinkedHashMap<Port, List<Port>> ports){

		Set<Port> teleportableCities = new HashSet<Port>(ports.get(fromCity));
		
		System.out.println(fromCity + " - " + teleportableCities);

		if(teleportableCities.contains(toCity)){
			return true;
		} else {
			fromCity.setVisited(true);
			Iterator<Port> iter = teleportableCities.iterator();
			while (iter.hasNext()) {
				Port adjacentPort = iter.next();
				if(!adjacentPort.isVisited())
					return canTeleport(adjacentPort, toCity, ports);
			}
		}

		return false;
	}

	public static boolean isInLoop(Port originCity, LinkedHashMap<Port, List<Port>> ports){

		Set<Port> adjacentCities = new LinkedHashSet<Port>(ports.get(originCity));

		Iterator<Port> iter = adjacentCities.iterator();

		Port parent = originCity;

		while (iter.hasNext()) {

			//DFS Depth First Search for (city)
			if(recursive(iter.next(), parent, originCity, ports)) {
				return true;
			}

		}

		return false;

	}


	private static boolean recursive(Port next, Port parent, Port origin, LinkedHashMap<Port, List<Port>> ports) {
		
		LOGGER.setLevel(Level.WARNING);

		LOGGER.info( "NEXT:" + next + " PARENT:" + parent + " ORIGIN:" + origin);

		if (!next.isVisited()) {
			next.setVisited(true);
		}

		Set<Port> nextAdjacentCities = new LinkedHashSet<Port>(ports.get(next));

		if(null != nextAdjacentCities && nextAdjacentCities.contains(parent)){
			nextAdjacentCities.remove(parent);
			if(nextAdjacentCities.contains(origin)){
				return true;
			}
		}

		Iterator<Port> itr = nextAdjacentCities.iterator();
		while (itr.hasNext()) {
			parent = next;
			Port nextcity = itr.next();
			if(!nextcity.isVisited()) {
				if(recursive(nextcity, parent, origin, ports)) {
					return true;
				}
			}
		}

		return false;

	}


}
