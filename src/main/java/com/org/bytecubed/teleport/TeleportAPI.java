package com.org.bytecubed.teleport;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.org.bytecubed.teleport.domain.Port;

/**
 * @description Teleporting APIs
 * @author Deepti
 *
 */
public class TeleportAPI {

	private static final Logger LOGGER = Logger.getLogger(TeleportAPI.class.getName());

	static {
		LOGGER.setLevel(Level.WARNING); // Set it to INFO to be able to see the logs on console.
	}

	/**
	 * Suppress default constructor for noninstantiability     
	 */
	private TeleportAPI() {         
		// This constructor will never be invoked 
	}

	/**
	 * Gets all the cities reachable from cityPort upto N jumps.
	 * @param cityPort
	 * @param noOfJumps
	 * @param ports
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("rawtypes")
	public static Set<Port> getCitiesInNJumps(Port cityPort, int noOfJumps, LinkedHashMap<Port, List<Port>> ports) throws Exception {

		isEmpty(ports);

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

		if(teleportableCities.contains(cityPort)){ //Remove the originating city, it gets added as we travers to and fro.
			teleportableCities.remove(cityPort);
		}
		return teleportableCities;
	}


	/**
	 * Determines if the city is reachable from a given city.
	 * @param fromCity
	 * @param toCity
	 * @param ports
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("rawtypes")
	public static boolean canTeleport(Port fromCity, Port toCity, LinkedHashMap<Port, List<Port>> ports) throws Exception{

		isEmpty(ports);

		Set<Port> teleportableCities = new HashSet<Port>(ports.get(fromCity));

		LOGGER.info(fromCity + " - " + teleportableCities);

		if(teleportableCities.contains(toCity)){
			return true;     // Return true if toCity is part of the adjacent City
		} else {
			fromCity.setVisited(true);
			Iterator<Port> iter = teleportableCities.iterator();
			while (iter.hasNext()) {
				Port adjacentPort = iter.next();
				if(!adjacentPort.isVisited())
					return canTeleport(adjacentPort, toCity, ports);  // Keep checking if the adjacent cities of the adjacent city has toCity.
			}
		}

		return false;
	}

	/**
	 * Determines if a loop is possible from a given city.
	 * @param originCity
	 * @param ports
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isInLoop(Port originCity, LinkedHashMap<Port, List<Port>> ports) throws Exception{

		isEmpty(ports);

		Set<Port> adjacentCities = new LinkedHashSet<Port>(ports.get(originCity));

		Iterator<Port> iter = adjacentCities.iterator();

		Port parent = originCity;

		while (iter.hasNext()) {
			Port next = iter.next();

			//DFS Depth First Search for (city)
			if(recursiveTraversal(next, parent, originCity, ports)) {
				return true;
			}

		}

		return false;

	}


	@SuppressWarnings("rawtypes")
	private static boolean recursiveTraversal(Port next, Port parent, Port origin, LinkedHashMap<Port, List<Port>> ports) {

		LOGGER.info( "NEXT:" + next + " PARENT:" + parent + " ORIGIN:" + origin);

		if (!next.isVisited()) {
			next.setVisited(true);
		}

		Set<Port> nextAdjacentCities = new LinkedHashSet<Port>(ports.get(next));

		//validate the nextAdjacentCities list excluding parent, if the nextAdjacentCities contains origin return true.
		if(null != nextAdjacentCities && nextAdjacentCities.contains(parent)){
			nextAdjacentCities.remove(parent);
			if(nextAdjacentCities.contains(origin)){
				return true;
			}
		}

		//Iterate over nextAdjacentCities list, get the next further adjacent cities for each city in the nextAdjacentCities.
		Iterator<Port> itr = nextAdjacentCities.iterator();
		while (itr.hasNext()) {
			parent = next;
			Port nextcity = itr.next();
			if(!nextcity.isVisited()) {
				if(recursiveTraversal(nextcity, parent, origin, ports)) {
					return true;
				}
			}
		}

		return false;

	}

	@SuppressWarnings("rawtypes")
	private static void isEmpty(LinkedHashMap<Port, List<Port>> ports) throws Exception {
		if(null == ports || ports.isEmpty()) {
			LOGGER.info("No ports provided, please provide the ports in the PortA - PortB format...");
			throw new Exception("No ports provided, please provide the ports in the PortA - PortB format...");
		}
	}

}
