package com.lankr.consumer;

import java.util.List;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.alibaba.rocketmq.common.protocol.heartbeat.MessageModel;
import com.lankr.dennisit.entity.process.ElasticSearchHandler;
import com.lankr.dennisit.util.JsonUtil;
import com.lankr.model.Hospital;
import com.lankr.producer.Producer;

public class Consumer implements Runnable{
	
	/** 
     * 当前例子是Consumer用法，使用方式给用户感觉是消息从RocketMQ服务器推到了应用客户端。<br> 
     * 但是实际Consumer内部是使用长轮询Pull方式从MetaQ服务器拉消息，然后再回调用户Listener方法<br> 
     *  
     * @throws MQClientException 
     */  
	private static ElasticSearchHandler esHandler = ElasticSearchHandler.instance();
	
	public static void main(String args[]) throws MQClientException {
		
//		 /** 
//         * 一个应用创建一个Consumer，由应用来维护此对象，可以设置为全局对象或者单例<br> 
//         * 注意：ConsumerGroupName需要由应用来保证唯一 ,最好使用服务的包名区分同一服务,一类Consumer集合的名称，这类Consumer通常消费一类消息，且消费逻辑一致 
//         * PushConsumer：Consumer的一种，应用通常向Consumer注册一个Listener监听器，Consumer收到消息立刻回调Listener监听器 
//         * PullConsumer：Consumer的一种，应用通常主动调用Consumer的拉取消息方法从Broker拉消息，主动权由应用控制 
//         */  
//		
//		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("ConsumerGroupName") ;
//		consumer.setNamesrvAddr("192.168.1.107:9876") ;
//		consumer.setInstanceName("Consumer") ;
//		consumer.setMessageModel(MessageModel.CLUSTERING);
//		//设置批量消费的个数
//		//consumer.setConsumeMessageBatchMaxSize(10);
//		/**
//		 * 订阅topic下tags分别等于tagA、tagC、tagD
//		 */
//		consumer.subscribe("TopicTest1", "*");
//		/**
//		 * 订阅topic下的所有消息
//		 * 注意：一个consumer对象可以订阅多个topic
//		 */
//		consumer.subscribe("TopicTest2", "*");
//		consumer.registerMessageListener(new MessageListenerConcurrently(){
//
//			@Override
//			public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
//				System.out.println(Thread.currentThread().getName() + "Receive New Massage:" + msgs.size());
//				MessageExt msg = msgs.get(0) ;
//				if (msg.getTopic().equals("TopicTest1")) {
//					if (msg.getTags() != null && msg.getTags().equals("TagA")) {
//						System.out.println(msg.getKeys());
//						QueryBuilder queryBuilder = QueryBuilders.queryStringQuery(msg.getKeys());
//						List<Hospital> result = esHandler.searcher(queryBuilder, "indexdemo", "typedemo");
//						List<Message> mesgs = null ; 
//				        for(int i=0; i<result.size(); i++){
//				            Hospital medicine = result.get(i);
//				            //System.out.println("(" + medicine.getId() + ")药品名称:" +medicine.getName() + "\t\t" + medicine.getFunction());
//				            Message mesg = new Message("Topic", "tags", "search", ("this is ok").getBytes()) ; 
//				            mesgs.add(mesg) ;
//				        }
//				        Producer.instance().send(mesgs) ;
//					} else if (msg.getTags() != null && msg.getTags().equals("tagC")) {
//						System.out.println(new String(msg.getBody())) ;
//					} else if (msg.getTags() != null && msg.getTags().equals("tagD")) {
//						System.out.println(new String(msg.getBody())) ;
//					}
//				} else if (msg.getTopic().equals("TopicTest2")) {
//					System.out.println(new String(msg.getBody())) ;
//				}
//				return ConsumeConcurrentlyStatus.CONSUME_SUCCESS ;
//			}
//			
//		});
//		
//		/** 
//         * Consumer对象在使用之前必须要调用start初始化，初始化一次即可<br> 
//         */ 
//		
//		consumer.start() ;
//		System.out.println("ConsumerStarted");
	}

	//消息直接以json的格式来传递
	
	public void run() {
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("ConsumerGroupName") ;
		consumer.setNamesrvAddr("139.129.116.189:9876") ;
		consumer.setInstanceName("Consumer") ;
		consumer.setMessageModel(MessageModel.CLUSTERING);
		try {
			consumer.subscribe("hospital", "*");
			consumer.subscribe("resource", "*");
		} catch (MQClientException e) {
			e.printStackTrace();
		}
		
		consumer.registerMessageListener(new MessageListenerConcurrently(){

			@Override
			public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
				
				System.out.println(Thread.currentThread().getName() + "Receive New Massage:" + msgs.size());
				for (MessageExt msg : msgs){
					if ("hospital".equals(msg.getTopic()) || "resource".equals(msg.getTopic())) {
						if ("add".equals(msg.getTags())) {
							System.out.println("消息类型add");
							System.out.println(new String(msg.getBody()));
							String uuid = msg.getKeys() ;
							byte[] temp = msg.getBody() ;
							String content = new String(temp) ;
							esHandler.createIndexResponse("zhiliao", msg.getTopic(), uuid, content) ;
							//Message message = new Message("hospital","add","reply",("OK").getBytes()) ;
							//Producer.instance().send(msgs);
						} else if ("update".equals(msg.getTags())) {
							System.out.println("消息类型update");
							System.out.println(new String(msg.getBody())) ;
							String uuid = msg.getKeys() ;
							byte[] temp = msg.getBody() ;
							String content = new String(temp) ;
							esHandler.update("zhiliao", msg.getTopic(), content) ;
						} else if ("delete".equals(msg.getTags())) {
							System.out.println("消息类型delete");
							System.out.println(new String(msg.getBody())) ;
							String uuid = msg.getKeys() ;
							byte[] temp = msg.getBody() ;
							String content = new String(temp) ;
							esHandler.delete("zhiliao", msg.getTopic(), content) ;
						}
					} else {
						return ConsumeConcurrentlyStatus.CONSUME_SUCCESS ;
					}
				}
				System.out.println("消费成功");
				return ConsumeConcurrentlyStatus.CONSUME_SUCCESS ;
			}
			
		});
		
		/** 
         * Consumer对象在使用之前必须要调用start初始化，初始化一次即可
         */ 
		
		try {
			consumer.start() ;
		} catch (MQClientException e) {
			e.printStackTrace();
		}
		System.out.println("ConsumerStarted");
	}
	
}
