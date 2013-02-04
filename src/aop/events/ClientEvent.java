package aop.events;

import org.apache.cassandra.thrift.ThriftClientState;

public class ClientEvent {
	public ThriftClientState client;
	public ClientEvent(ThriftClientState client) {
		this.client = client;
	}
}
