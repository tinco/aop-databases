package aop.monitoring;

import java.util.HashMap;
import java.util.Map;

import org.apache.cassandra.thrift.ThriftClientState;

import aop.events.Events;
import aop.events.IListener;
import aop.events.ClientEvent;
import aop.events.Events.Result;

public class Clients {
	public static void installClientConnectionsMonitor() {
		class ClientMonitor implements IListener<ClientEvent> {
			Map<ThriftClientState,Long> connectedClients = new HashMap<ThriftClientState,Long>();
			public Result trigger(ClientEvent e) {
				if(connectedClients.containsKey(e.client)) {
					long time = System.nanoTime() - connectedClients.get(e.client);
					connectedClients.remove(e.client);
					System.out.println("Client " + e.client.hashCode() + " disconnected after " + time + " nanoseconds.");					
				} else {
					connectedClients.put(e.client, System.nanoTime());
					System.out.println("Client " + e.client.hashCode() + " connected");
				}
				
				return Result.CONTINUE;
			}
		}
		
		Events.register(new ClientMonitor());
	}
}