package aop.cassandra.events;

import org.apache.cassandra.thrift.ThriftClientState;

public class ClientEvent extends aop.events.ClientEvent {
	public ClientEvent(ThriftClientState client) {
		super(client);
	}
}
