package com.org.bytecoded.teleport.main;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.org.bytecoded.teleport.Teleport;
import com.org.bytecoded.teleport.domain.Port;
import com.org.bytecoded.teleport.domain.TelePortsAdjMap;

public class TeleportParser {

	private static final String REGEX_CITIES = "[a-zA-Z\\s]+ - [a-zA-Z\\s]+";
	private static final String REGEX_CITIES_IN_N_JUMPS = "cities from ([a-zA-Z\\s]+) in (\\d) jumps";
	private static final String REGEX_CAN_TELEPORT = "can I teleport from ([a-zA-Z\\s]+) to ([a-zA-Z\\s]+)";
	private static final String REGEX_IS_LOOP = "loop possible from ([a-zA-Z\\s]+)";
	
	private static Pattern pCities;
	private static Pattern pCitiesInNJumps;
	private static Pattern pCanTeleport;
	private static Pattern pIsLoop;
	
	@SuppressWarnings("rawtypes")
	static TelePortsAdjMap<Port> ports = new TelePortsAdjMap<Port>();
	
	static {
		pCities = Pattern.compile(REGEX_CITIES);
		pCitiesInNJumps = Pattern.compile(REGEX_CITIES_IN_N_JUMPS);
		pCanTeleport = Pattern.compile(REGEX_CAN_TELEPORT);
		pIsLoop = Pattern.compile(REGEX_IS_LOOP);
	}

	public static<T> void parseLine(String line) throws Exception {
		
		 Matcher mCities = pCities.matcher(line);
		 Matcher mCitiesInNJumps = pCitiesInNJumps.matcher(line);
		 Matcher mCanTeleport = pCanTeleport.matcher(line);
		 Matcher mIsLoop = pIsLoop.matcher(line);
		 
         if(mCities.matches()){
        	 parsePorts(line, ports);
         } else if (mCitiesInNJumps.matches()) {
        	 reset(ports.getPorts());
        	 String result = line + ":" + Teleport.getCitiesInNJumps(getPort(mCitiesInNJumps.group(1)), Integer.parseInt(mCitiesInNJumps.group(2)), ports.getPorts());
        	 System.out.println(result);
         } else if (mCanTeleport.matches()) {
        	 reset(ports.getPorts());
        	 String result = line + ":" + Teleport.canTeleport(getPort(mCanTeleport.group(1).trim()), getPort(mCanTeleport.group(2).trim()), ports.getPorts());
        	 System.out.println(result);
         } else if (mIsLoop.matches()) {
        	 reset(ports.getPorts());
        	 String result = line + ":" + Teleport.isInLoop(getPort(mIsLoop.group(1).trim()), ports.getPorts());
        	 System.out.println(result);
         }
		
	}

	@SuppressWarnings("rawtypes")
	private static Port getPort(String port) throws Exception {
		Iterator it = ports.getPorts().keySet().iterator();
		
	    while (it.hasNext()) {
	        Port p = (Port) it.next();
	        if(p.equals(new Port(port)))
	        	return p;
	    }
	    
	    throw new Exception("Port not found exception...");
	}
	
	@SuppressWarnings("rawtypes")
	private static void reset(LinkedHashMap<Port, List<Port>> linkedHashMap) {
		Iterator<Port> it = linkedHashMap.keySet().iterator();
		while (it.hasNext())
		{
			Port<String> port = it.next();
			port.setVisited(false);
			
			List<Port> portList = linkedHashMap.get(port);
			for(Port<String> p : portList){
				p.setVisited(false);
			}
		}
		
	}

	private static void parsePorts(String line, TelePortsAdjMap ports) {
		String[] cities = line.split("-");
		
		if(cities == null || cities.length < 2) {
			return;
		}
		
		ports.addTelePort(cities[0].trim(), cities[1].trim());
		
	}
	

}
