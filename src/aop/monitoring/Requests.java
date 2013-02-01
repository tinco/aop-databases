package aop.monitoring;

import java.util.HashMap;
import java.util.Map;

import aop.events.*;
import aop.events.Events.Result;

public class Requests {
	public static void installRequestPerformanceMonitor() {
		class RequestMonitor implements IListener<RequestEvent> {
			Map<RequestEvent,Long> requests = new HashMap<RequestEvent,Long>();
			public Result trigger(RequestEvent e) {
				if(requests.containsKey(e)) {
					long time = System.nanoTime() - requests.get(e);
					requests.remove(e);
					System.out.println("Request " + e.action + " took " + time + " nanoseconds.");					
				} else {
					requests.put(e, System.nanoTime());
				}
				
				return Result.CONTINUE;
			}
		}
		
		Events.register(new RequestMonitor());
	}
}
