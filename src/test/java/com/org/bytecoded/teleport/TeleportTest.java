package com.org.bytecoded.teleport;

import static org.junit.Assert.assertEquals;

import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Test;

import com.org.bytecoded.teleport.domain.Port;
import com.org.bytecoded.teleport.domain.TelePortsAdjMap;

public class TeleportTest {

	@Test
	public final<T> void testGetCitiesFrom() {
		TelePortsAdjMap<Port<T>> ports = new TelePortsAdjMap<Port<T>>();

		Port<T> washington = new Port("Washington");
		Port<T> baltimore =  new Port("Baltimore");
		Port<T> atlanta =  new Port("Atlanta");
		Port<T> philadelphia = new Port("Philadelphia");
		Port<T> ny = new Port("New York");
		Port<T> la = new Port("Los Angeles");
		Port<T> sfo = new Port("San Fransisco");
		Port<T> oak = new Port("Oakland");
		Port<T> seattle = new Port("Seattle");

		ports.addTelePort(washington, baltimore);
		ports.addTelePort(washington, atlanta);
		ports.addTelePort(baltimore, philadelphia);
		ports.addTelePort(philadelphia, ny);
		ports.addTelePort(la, sfo);
		ports.addTelePort(sfo, oak);
		ports.addTelePort(la, oak);
		ports.addTelePort(seattle, ny);
		ports.addTelePort(seattle, baltimore);
		
		Set<Port<T>> excpected = new LinkedHashSet<Port<T>>();
		excpected.add(ny);
		excpected.add(baltimore);
		
		
		Set<Port<T>> resultSet = Teleport.getCitiesFrom(seattle, 1, ports.getPorts());
		
		assertEquals(2, resultSet.size());
		assertEquals(excpected, resultSet);
		
		resultSet = Teleport.getCitiesFrom(seattle, 2, ports.getPorts());
		
		excpected.add(philadelphia);
		excpected.add(washington);
		assertEquals(4, resultSet.size());
		assertEquals(excpected, resultSet);
	}
	
	
	@Test
	public final <T> void testCanTeleport() {
		TelePortsAdjMap<Port<T>> ports = new TelePortsAdjMap<Port<T>>();

		Port<T> washington = new Port("Washington");
		Port<T> baltimore =  new Port("Baltimore");
		Port<T> atlanta =  new Port("Atlanta");
		Port<T> philadelphia = new Port("Philadelphia");
		Port<T> ny = new Port("New York");
		Port<T> la = new Port("Los Angeles");
		Port<T> sfo = new Port("San Fransisco");
		Port<T> oak = new Port("Oakland");
		Port<T> seattle = new Port("Seattle");

		ports.addTelePort(washington, baltimore);
		ports.addTelePort(washington, atlanta);
		ports.addTelePort(baltimore, philadelphia);
		ports.addTelePort(philadelphia, ny);
		ports.addTelePort(la, sfo);
		ports.addTelePort(sfo, oak);
		ports.addTelePort(la, oak);
		ports.addTelePort(seattle, ny);
		ports.addTelePort(seattle, baltimore);
		
		assertEquals(true, Teleport.canTeleport(ny, atlanta, ports.getPorts()));
		
		assertEquals(false, Teleport.canTeleport(oak, atlanta, ports.getPorts()));

		//System.out.println(Teleport.canTeleport(seattle, washington, ports.getPorts()));

		//System.out.println(Teleport.canTeleport(ny, atlanta, ports.getPorts()));

		//System.out.println(Teleport.canTeleport(oak, atlanta, ports.getPorts()));

		//System.out.println(Teleport.getCitiesFrom(seattle, 1, ports.getPorts()));

		//System.out.println(Teleport.getCitiesFrom(seattle, 2, ports.getPorts()));

		// System.out.println(Teleport.getCitiesFrom(oak, 3, ports.getPorts()));

		//System.out.println(Teleport.getCitiesFrom(washington, 2, ports.getPorts()));
		//		 
		//		 System.out.println(Teleport.getCitiesFrom(baltimore, 1, ports.getPorts()));
		//		 
		//System.out.println(Teleport.getCitiesFrom(oak, 1, ports.getPorts()));

		//System.out.println(Teleport.isLoopFinal(washington, ports.getPorts()));
		//fail("Not yet implemented"); // TODO
	}

	@Test
	public final <T> void testIsInLoop() {
		TelePortsAdjMap<Port<T>> ports = new TelePortsAdjMap<Port<T>>();

		Port<T> washington = new Port("Washington");
		Port<T> baltimore =  new Port("Baltimore");
		Port<T> atlanta =  new Port("Atlanta");
		Port<T> philadelphia = new Port("Philadelphia");
		Port<T> ny = new Port("New York");
		Port<T> la = new Port("Los Angeles");
		Port<T> sfo = new Port("San Fransisco");
		Port<T> oak = new Port("Oakland");
		Port<T> seattle = new Port("Seattle");


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
		
		assertEquals(false, Teleport.isInLoop(washington, ports.getPorts()));
		assertEquals(false, Teleport.isInLoop(atlanta, ports.getPorts()));
//		assertEquals(true, Teleport.isInLoop(oak, ports.getPorts()));
//		assertEquals(true, Teleport.isInLoop(la, ports.getPorts()));
//		assertEquals(true, Teleport.isInLoop(sfo, ports.getPorts()));
//		assertEquals(true, Teleport.isInLoop(baltimore, ports.getPorts()));
//		assertEquals(true, Teleport.isInLoop(philadelphia, ports.getPorts()));
//		assertEquals(true, Teleport.isInLoop(ny, ports.getPorts()));
//		assertEquals(true, Teleport.isInLoop(seattle, ports.getPorts()));
		

		//System.out.println(Teleport.canTeleport(seattle, washington, ports.getPorts()));

		//System.out.println(Teleport.canTeleport(ny, atlanta, ports.getPorts()));

		//System.out.println(Teleport.canTeleport(oak, atlanta, ports.getPorts()));

		//System.out.println(Teleport.getCitiesFrom(seattle, 1, ports.getPorts()));

		//System.out.println(Teleport.getCitiesFrom(seattle, 2, ports.getPorts()));

		// System.out.println(Teleport.getCitiesFrom(oak, 3, ports.getPorts()));

		//System.out.println(Teleport.getCitiesFrom(washington, 2, ports.getPorts()));
		//		 
		//		 System.out.println(Teleport.getCitiesFrom(baltimore, 1, ports.getPorts()));
		//		 
		//System.out.println(Teleport.getCitiesFrom(oak, 1, ports.getPorts()));

		//System.out.println(Teleport.isLoopFinal(washington, ports.getPorts()));
		//fail("Not yet implemented"); // TODO
	}
	

	//@Test
	public<T> TelePortsAdjMap<Port<T>> createNewMockData() {
		TelePortsAdjMap<Port<T>> ports = new TelePortsAdjMap<Port<T>>();

		Port<T> washington = new Port("Washington");
		Port<T> baltimore =  new Port("Baltimore");
		Port<T> atlanta =  new Port("Atlanta");
		Port<T> philadelphia = new Port("Philadelphia");
		Port<T> ny = new Port("New York");
		Port<T> la = new Port("Los Angeles");
		Port<T> sfo = new Port("San Fransisco");
		Port<T> oak = new Port("Oakland");
		Port<T> seattle = new Port("Seattle");


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
