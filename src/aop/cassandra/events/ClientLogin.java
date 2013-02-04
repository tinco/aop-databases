package aop.cassandra.events;

import org.apache.cassandra.thrift.AuthenticationRequest;
import org.apache.cassandra.thrift.ThriftClientState;

public class ClientLogin extends ClientEvent {
	public AuthenticationRequest authRequest;
	public ClientLogin(ThriftClientState client, AuthenticationRequest authRequest) {
		super(client);
		this.authRequest = authRequest;
	}
}
