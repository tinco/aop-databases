package aop.events;

public interface IListener<E> {
	public Events.Result trigger(E event);
}
