package com.lankr.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

/**
 * 
 * 使用cglib来动态代理
 * 
 * @author Lankr
 *
 */
public class TestCglib{
	public static void main(String[] args) {
		BookFacadeCglib cglib = new BookFacadeCglib() ;
		BookImpl book = (BookImpl) cglib.getInstance(new BookImpl()) ;
		book.request();
		
	}
}

interface BookCglib{
	void request();
}

/**
 * 
 * @author Lankr
 * 此类是没有实现接口的
 * Spring中就是使用的cglib代理
 *
 */
class BookImpl{

	public void request() {
		System.out.println("=========request============");
	}
}

class BookFacadeCglib implements MethodInterceptor{

	private Object target ;
	
	/**
	 * 
	 * 创建代理对象
	 * 
	 * @param target
	 * @return
	 */
	public Object getInstance(Object target) {
		this.target = target ;
		Enhancer enhancer = new Enhancer() ;
		enhancer.setSuperclass(this.target.getClass()) ;
		//设置回调方法
		enhancer.setCallback(this) ;
		//创建代理对象
		return enhancer.create() ;
	}
	
	//定义拦截器
	@Override
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		System.out.println("===========before===========") ;
		proxy.invokeSuper(obj, args) ;
		System.out.println("===========after===========");
		return null ;
	}
	
	
	
}
