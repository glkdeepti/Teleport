package com.org.bytecubed.teleport;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.org.bytecubed.teleport.TeleportAPI;
import com.org.bytecubed.teleport.domain.Port;
import com.org.bytecubed.teleport.domain.TeleportsAdjMap;

public class TeleportAPITest {

	@Test
	public final void testGetCitiesFrom() throws Exception {
		LinkedHashMap<Port, List<Port>> ports = createNewMockData().getPorts();

		Set<Port> excpected = new LinkedHashSet<Port>();
		excpected.add(new Port("New York"));
		excpected.add(new Port("Baltimore"));

		Set<Port> resultSet = TeleportAPI.getCitiesInNJumps(getPort("Seattle", ports), 1, ports);

		assertEquals(2, resultSet.size());
		assertEquals(excpected, resultSet);

		//System.out.println(resultSet);

		resultSet = TeleportAPI.getCitiesInNJumps(getPort("Seattle", ports), 2, ports);

		excpected.add(new Port("Philadelphia"));
		excpected.add(new Port("Washington"));
		assertEquals(4, resultSet.size());
		assertEquals(excpected, resultSet);

		//System.out.println(resultSet);
	}


	@Test
	public final void testCanTeleport() throws Exception {
		LinkedHashMap<Port, List<Port>> ports = createNewMockData().getPorts();

		assertEquals(true, TeleportAPI.canTeleport(getPort("New York", ports), getPort("Atlanta", ports), ports));

		assertEquals(false, TeleportAPI.canTeleport(getPort("Oakland", ports), getPort("Atlanta", ports), ports));

	}

	@Test
	public final void testIsInLoop() throws Exception {
		LinkedHashMap<Port, List<Port>> ports = createNewMockData().getPorts();

		assertEquals(false, TeleportAPI.isInLoop(getPort("Washington", ports), ports));
		assertEquals(true, TeleportAPI.isInLoop(getPort("Oakland", ports), ports));
		assertEquals(false, TeleportAPI.isInLoop(getPort("Washington", ports), ports));
		assertEquals(false, TeleportAPI.isInLoop(getPort("Atlanta", ports), ports));
		assertEquals(true, TeleportAPI.isInLoop(getPort("Los Angeles", ports), ports));
	}

	private TeleportsAdjMap createNewMockData() {
		TeleportsAdjMap ports = new TeleportsAdjMap();

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

	private static Port getPort(String port, LinkedHashMap<Port, List<Port>> ports ) throws Exception {
		Iterator it = ports.keySet().iterator();

		while (it.hasNext()) {
			Port p = (Port) it.next();
			if(p.equals(new Port(port)))
				return p;
		}

		throw new Exception("Port not found exception...");
	}

}
