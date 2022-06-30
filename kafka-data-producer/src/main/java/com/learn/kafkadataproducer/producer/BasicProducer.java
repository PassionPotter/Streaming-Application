package com.learn.kafkadataproducer.producer;

import org.supercsv.cellprocessor.ift.BoolCellProcessor;

public interface BasicProducer<T> {
	public String sendMessage(T requestBody);
	public String sendAllMessage();
	public BoolCellProcessor[] getCellProcessor();
}
