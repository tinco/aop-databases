package aop.cassandra;

public aspect cassandra {
	pointcut main(): 
		execution(void *.main(..));
	
	before(): main() {
		System.out.println("Installing aspects");
		aop.monitoring.Requests.installRequestPerformanceMonitor();
	}
}
