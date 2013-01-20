package aop.cassandra;

import org.apache.thrift.ProcessFunction;

public aspect queries {
	pointcut testCut(): 
		execution(void *.main(..));
	
	before(): testCut() {
		System.out.println("aspects installed!");
	}
	
	pointcut processQuery():
		execution(* ProcessFunction+.getResult(..));
	
	before(): processQuery() {
		String method = ((ProcessFunction)thisJoinPoint.getThis()).getMethodName();
		System.out.println("processing query of type ["+method+"]");
	}
}
