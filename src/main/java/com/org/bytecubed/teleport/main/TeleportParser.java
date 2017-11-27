package com.org.bytecubed.teleport.main;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.org.bytecubed.teleport.TeleportAPI;
import com.org.bytecubed.teleport.domain.Port;
import com.org.bytecubed.teleport.domain.TeleportsAdjMap;

/**
 * @description Parses the input file and calls the appropriate API with the constructed domain object.
 * @author Deepti
 *
 */
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
	static TeleportsAdjMap<Port> ports = new TeleportsAdjMap<Port>();

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
		String result = "";

		if(mCities.matches()){
			parsePorts(line, ports);
		} else if (mCitiesInNJumps.matches()) {
			reset(ports.getPorts());
			result = line + ": " 
					+ TeleportAPI.getCitiesInNJumps(getPort(mCitiesInNJumps.group(1)), Integer.parseInt(mCitiesInNJumps.group(2)), ports.getPorts());
			System.out.println(result);
		} else if (mCanTeleport.matches()) {
			reset(ports.getPorts());
			result = line + ": " 
					+ (TeleportAPI.canTeleport(getPort(mCanTeleport.group(1).trim()), getPort(mCanTeleport.group(2).trim()), ports.getPorts()) ? "yes" : "no");
			System.out.println(result);
		} else if (mIsLoop.matches()) {
			reset(ports.getPorts());
			result = line + ": " 
					+ (TeleportAPI.isInLoop(getPort(mIsLoop.group(1).trim()), ports.getPorts()) ? "yes" : "no");
			System.out.println(result);
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static Port getPort(String port) throws Exception {
		Iterator it = ports.getPorts().keySet().iterator();

		while (it.hasNext()) {
			Port p = (Port) it.next();
			if(p.equals(new Port(port)))
				return p;
		}

		throw new Exception("Port not found exception...");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static void reset(LinkedHashMap<Port, List<Port>> linkedHashMap) throws Exception {
		
		if(null == linkedHashMap || linkedHashMap.isEmpty()) {
			throw new Exception("No ports provided, please provide the ports in the PortA - PortB format...");
		}
		
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

	@SuppressWarnings("rawtypes")
	private static void parsePorts(String line, TeleportsAdjMap ports) {
		String[] cities = line.split("-");

		if(cities == null || cities.length < 2) {
			return;
		}

		ports.addTelePort(cities[0].trim(), cities[1].trim());

	}


}
