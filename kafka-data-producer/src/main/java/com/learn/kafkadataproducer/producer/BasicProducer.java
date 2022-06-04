package com.learn.kafkadataproducer.producer;

public interface BasicProducer<T> {
	public String sendMessage(T requestBody);
	public String sendAllMessage();
}
