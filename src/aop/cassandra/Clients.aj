package aop.cassandra;

import org.apache.cassandra.thrift.AuthenticationException;
import org.apache.cassandra.thrift.AuthenticationRequest;
import org.apache.cassandra.thrift.CassandraServer;
import org.apache.cassandra.thrift.ThriftClientState;

import aop.cassandra.events.ClientLogin;
import aop.events.ClientEvent;
import aop.events.Events;
import aop.events.Events.Result;

public aspect Clients {
	pointcut clientLogin(AuthenticationRequest authRequest):
		call(* CassandraServer+.login(AuthenticationRequest)) && args(authRequest);
	
	pointcut clientConnected(ThriftClientState client):
		initialization(ThriftClientState.new()) && this(client);
	
	after(ThriftClientState client) : clientConnected(client){
		ClientEvent e = new ClientEvent(client);
		Events.trigger(e);
	}
	
	void around(AuthenticationRequest authRequest) throws AuthenticationException: clientLogin(authRequest) {
		ThriftClientState client = ((CassandraServer)thisJoinPoint.getThis()).state();
		ClientEvent e = new ClientLogin(client, authRequest);
		if(Events.trigger(e) != Result.STOP) {
			proceed(authRequest);
			Events.trigger(e);
		} else {
			throw new AuthenticationException("Denied by AspectJ");
		}
	}
}
