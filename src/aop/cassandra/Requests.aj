package aop.cassandra;

import org.apache.cassandra.thrift.InvalidRequestException;
import org.apache.thrift.ProcessFunction;
import org.apache.thrift.TException;

import aop.cassandra.events.RequestEvent;
import aop.events.*;
import aop.events.Events.Result;

public aspect Requests {
	pointcut processRequest():
		execution(* ProcessFunction+.getResult(..));
	
	Object around() throws TException: processRequest() {
		String method = ((ProcessFunction)thisJoinPoint.getThis()).getMethodName();
		RequestEvent e = new RequestEvent(method);
		if(Events.trigger(e) != Result.STOP) {
			Object result = proceed();
			Events.trigger(e);
			return result;
		} else {
			throw new InvalidRequestException("Denied by AspectJ");
		}
	}
}
