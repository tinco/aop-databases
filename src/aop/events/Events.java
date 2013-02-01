package aop.events;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author tinco
 *
 * So the idea of this class is that databases trigger events here
 * and listeners can register to certain events. 
 */
public class Events {
	public enum Result {CONTINUE, STOP};
	
	private static Map<Class, List<IListener>> listenerMap = new HashMap<Class, List<IListener>>();
	
	public static <C> void register(IListener<C> listener) {
		Method[] methods = listener.getClass().getMethods();
		for(Method m : methods) {
			if(m.getName() == "trigger") {
				Class klass = m.getParameterTypes()[0];
				if(!listenerMap.containsKey(klass)) {
					listenerMap.put(klass, new ArrayList<IListener>(1));
				}
				listenerMap.get(klass).add(listener);
				return;
			}
		}
		throw new RuntimeException("Unreachable code reached.");
	}
	
	public static Result trigger(Object event) {
		Class klass = event.getClass();
		Result r = trigger(event, klass);
		
		// Run triggers for interfaces
		for(Class k : klass.getInterfaces()) {
			Result newR = trigger(event, k);
			if(newR == Result.STOP) { r = Result.STOP; }
		}
		
		// Recursively run triggers for super classes
		Class k = klass.getSuperclass();
		while(k != klass) {
			Result newR = trigger(event, klass.getSuperclass());
			if(newR == Result.STOP) { r = Result.STOP; }
		}
		
		return trigger(event, klass);
	}
	
	private static Result trigger(Object event, Class klass) {
		Result r = Result.CONTINUE;
		if(listenerMap.containsKey(klass)) {
			for(IListener l : listenerMap.get(klass)) {
				Result newR = l.trigger(event);
				if(newR == Result.STOP) { r = Result.STOP; }
			}
		}
		return r;
	}
}
