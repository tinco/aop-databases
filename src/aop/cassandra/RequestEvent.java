package aop.cassandra;
import aop.events.*;

public class RequestEvent extends aop.events.RequestEvent {
	public RequestEvent(String action) {
		super(action);
	}
}
