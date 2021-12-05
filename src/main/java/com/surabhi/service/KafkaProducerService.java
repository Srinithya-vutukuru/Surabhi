package com.surabhi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.surabhi.persistence.model.Message;

@Service
public class KafkaProducerService {
	@Autowired
	private KafkaTemplate<String, Message> kafkaTemplate;

	public void sendMessage(Message message, String topicName) {
        
	    ListenableFuture<SendResult<String, Message>> future = 
	      kafkaTemplate.send(topicName, message);
		
	    future.addCallback(new ListenableFutureCallback<SendResult<String, Message>>() {

	        @Override
	        public void onSuccess(SendResult<String, Message> result) {
	            System.out.println("Sent message=[" + message + 
	              "] with offset=[" + result.getRecordMetadata().offset() + "]");
	        }
	        @Override
	        public void onFailure(Throwable ex) {
	            System.out.println("Unable to send message=[" 
	              + message + "] due to : " + ex.getMessage());
	        }
	    });
	}

}
