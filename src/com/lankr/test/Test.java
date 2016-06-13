package com.lankr.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 
 * 使用java实现动态代理   还有一个静态代理
 * 
 * @author Lankr
 *
 */
public class Test{
	public static void main(String[] args) {
		TestJava testJava = new TestJava() ;
		ProxyHandler handler = new ProxyHandler(testJava) ;
		//必须实现接口才能实现代理
		//取得代理对象
		TestA proxyTest = (TestA) Proxy.newProxyInstance(TestJava.class.getClassLoader(), TestJava.class.getInterfaces(), handler) ;
		proxyTest.request();
	}
}

interface TestA{
	void request();
}

class TestJava implements TestA{

	@Override
	public void request() {
		System.out.println("=========request============");
	}
}

class ProxyHandler implements InvocationHandler{
	
	private TestA testA ;
	
	public ProxyHandler(TestA testA){
		this.testA = testA ;
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("================before===============");
		Object result = method.invoke(testA, args) ;
		System.out.println("============after=============");
		return result;
	}
	
}
