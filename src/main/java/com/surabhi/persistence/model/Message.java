package com.surabhi.persistence.model;

import java.util.Date;

import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
public class Message {
	private String sender;
	private Date date;
	private String reciver;
	private String message;	
}
