package aop.cassandra.events;
import aop.events.*;

public class RequestEvent extends aop.events.RequestEvent {
	public RequestEvent(String action) {
		super(action);
	}
}
