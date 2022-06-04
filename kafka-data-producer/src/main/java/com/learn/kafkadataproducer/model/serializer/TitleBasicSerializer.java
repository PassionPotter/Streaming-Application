package com.learn.kafkadataproducer.model.serializer;

import org.apache.kafka.common.serialization.Serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.models.TitleBasic;

public class TitleBasicSerializer implements Serializer<TitleBasic> {

	@Override
	public byte[] serialize(String topic, TitleBasic data) {

		byte[] bytes=null ;
		ObjectMapper om=new ObjectMapper();
		try {
			 bytes = om.writeValueAsBytes(data);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bytes;
	
	}

}
