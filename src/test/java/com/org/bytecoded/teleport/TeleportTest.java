package com.org.bytecoded.teleport;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.org.bytecoded.teleport.domain.Port;
import com.org.bytecoded.teleport.domain.TelePortsAdjMap;

public class TeleportTest {

	//@Test
	public final void testGetCitiesFrom() {
		LinkedHashMap<Port, ArrayList<Port>> ports = createNewMockData().getPorts();
		
		Set<Port> excpected = new LinkedHashSet<Port>();
		excpected.add(new Port("New York"));
		excpected.add(new Port("Baltimore"));
		
		//Set<Port> resultSet = Teleport.getCitiesInNJumps(new Port("Seattle"), 1, ports);
		
		//assertEquals(2, resultSet.size());
		//assertEquals(excpected, resultSet);
		
		//System.out.println(resultSet);
		
		//resultSet = Teleport.getCitiesInNJumps(new Port("Seattle"), 2, ports);
		
		excpected.add(new Port("Philadelphia"));
		excpected.add(new Port("Washington"));
		//assertEquals(4, resultSet.size());
		//assertEquals(excpected, resultSet);
		
		//System.out.println(resultSet);
	}
	
	
	@Test
	public final void testCanTeleport() {
		LinkedHashMap<Port, List<Port>> ports = createNewMockData().getPorts();
		
		System.out.println(ports);
		
		Iterator it = ports.keySet().iterator();
		Port fromCity = null;
		Port toCity = null;
	    while (it.hasNext()) {
	        Port p = (Port) it.next();
	        if(p.equals(new Port("New York")))
	        	fromCity = p;
	        if(p.equals(new Port("Atlanta")))
	        	toCity = p;
	    }
		
		boolean result = Teleport.canTeleport(fromCity, toCity, ports);
		
		System.out.println(result);
		
		assertEquals(true, result);
		
		//assertEquals(false, Teleport.canTeleport(new Port("Oakland"), new Port("Atlanta"), ports));

	}

	//@Test
	public final void testIsInLoop() {
		LinkedHashMap<Port, List<Port>> ports = createNewMockData().getPorts();
		
		//assertEquals(false, Teleport.isInLoop(new Port("Washington"), ports));
//		assertEquals(false, Teleport.isInLoop(new Port("Atlanta"), ports));
		//assertEquals(true, Teleport.isInLoop(new Port("Oakland"), ports));
//		assertEquals(true, Teleport.isInLoop(la, ports.getPorts()));
//		assertEquals(true, Teleport.isInLoop(sfo, ports.getPorts()));
//		assertEquals(true, Teleport.isInLoop(baltimore, ports.getPorts()));
//		assertEquals(true, Teleport.isInLoop(philadelphia, ports.getPorts()));
//		assertEquals(true, Teleport.isInLoop(ny, ports.getPorts()));
//		assertEquals(true, Teleport.isInLoop(seattle, ports.getPorts()));
	}
	
	private TelePortsAdjMap createNewMockData() {
		TelePortsAdjMap ports = new TelePortsAdjMap();

		Port washington = new Port("Washington");
		Port baltimore =  new Port("Baltimore");
		Port atlanta =  new Port("Atlanta");
		Port philadelphia = new Port("Philadelphia");
		Port ny = new Port("New York");
		Port la = new Port("Los Angeles");
		Port sfo = new Port("San Fransisco");
		Port oak = new Port("Oakland");
		Port seattle = new Port("Seattle");


		/*Washington - Baltimore
		Washington - Atlanta
		Baltimore - Philadelphia
		Philadelphia - New York
		Los Angeles - San Fransisco
		San Fransisco - Oakland
		Los Angeles - Oakland
		Seattle - New York
		Seattle - Baltimore*/


		ports.addTelePort(washington, baltimore);
		ports.addTelePort(washington, atlanta);
		ports.addTelePort(baltimore, philadelphia);
		ports.addTelePort(philadelphia, ny);
		ports.addTelePort(la, sfo);
		ports.addTelePort(sfo, oak);
		ports.addTelePort(la, oak);
		ports.addTelePort(seattle, ny);
		ports.addTelePort(seattle, baltimore);
		
		return ports;
	}

}
