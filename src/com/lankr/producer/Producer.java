package com.lankr.producer;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;

public class Producer {
	
	/** 
     * 一个应用创建一个Producer，由应用来维护此对象，可以设置为全局对象或者单例<br> 
     * 注意：ProducerGroupName需要由应用来保证唯一<br> 
     * ProducerGroup这个概念发送普通的消息时，作用不大，但是发送分布式事务消息时，比较关键， 
     * 因为服务器会回查这个Group下的任意一个Producer 
     */  
	private static Producer pro ;
	
	DefaultMQProducer producer ;
	
	private Producer() {
		producer = new DefaultMQProducer("ProducerGroupNama") ;
		producer.setNamesrvAddr("192.168.1.107:9876") ;
		producer.setInstanceName("Producer") ;
		try {
			producer.start() ;
		} catch (MQClientException e) {
			e.printStackTrace();
		}
	}
	
	public static Producer instance() {
		if (pro == null) {
			pro = new Producer() ;
		}
		return pro ;
	}
	
	public void send(List<Message> msgs) {
		for (Message msg : msgs) {
			 try {
				SendResult sendResult = producer.send(msg);
			} catch (MQClientException e) {
				e.printStackTrace();
			} catch (RemotingException e) {
				e.printStackTrace();
			} catch (MQBrokerException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			 try {
				TimeUnit.MILLISECONDS.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
		}
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {

			@Override
			public void run() {
				producer.shutdown() ;
			}
        	
        }));
        System.out.println("over");
	}
	
	public static void main(String args[]) throws MQClientException, InterruptedException {
		
		final DefaultMQProducer producer = new DefaultMQProducer("ProducerGroupNama") ;
		//nameserver服务,多个以;分开  
		producer.setNamesrvAddr("192.168.1.107:9876") ;
		producer.setInstanceName("Producer") ;
		
		/** 
         * Producer对象在使用之前必须要调用start初始化，初始化一次即可<br> 
         * 注意：切记不可以在每次发送消息时，都调用start方法 
         */  
		
		producer.start() ;
		
		/** 
         * 下面这段代码表明一个Producer对象可以发送多个topic，多个tag的消息。 
         * 注意：send方法是同步调用，只要不抛异常就标识成功。但是发送成功也可会有多种状态，<br> 
         * 例如消息写入Master成功，但是Slave不成功，这种情况消息属于成功，但是对于个别应用如果对消息可靠性要求极高，<br> 
         * 需要对这种情况做处理。另外，消息可能会存在发送失败的情况，失败重试由应用来处理。 
         */  
		
		for (int i = 0; i < 5; i++) {  
            try {  
                {      //通过topic订阅消息，tag过滤消息  
                    Message msg = new Message("TopicTest1",// topic  
                            "TagA",// tag 消息标签，只支持设置一个Tag（服务端消息过滤使用）  
                            "jkjgf",// key 消息关键词，多个Key用KEY_SEPARATOR隔开（查询消息使用）  
                            ("Hello MetaQA").getBytes());// body  
                    SendResult sendResult = producer.send(msg);  
                    System.out.println(sendResult); 
                }  
  
                {  
                    Message msg = new Message("TopicTest2",// topic  
                            "TagB",// tag  
                            "OrderID0034",// key  
                            ("Hello MetaQB").getBytes());// body  
                    SendResult sendResult = producer.send(msg);  
                    System.out.println(sendResult);  
                }  
  
                {  
                    Message msg = new Message("TopicTest3",// topic  
                            "TagC",// tag  
                            "OrderID061",// key  
                            ("Hello MetaQC").getBytes());// body  
                    SendResult sendResult = producer.send(msg);  
                    System.out.println(sendResult);  
                }  
            } catch (Exception e) {  
                e.printStackTrace();  
                
            }  
            
            TimeUnit.MILLISECONDS.sleep(1000);  
		}
            
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {

			@Override
			public void run() {
				producer.shutdown() ;
			}
        	
        }));
        System.out.println("over");
       // System.exit(0) ;
        
	}
}
