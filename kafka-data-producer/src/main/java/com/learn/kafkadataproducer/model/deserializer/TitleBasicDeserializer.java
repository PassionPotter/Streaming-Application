package com.learn.kafkadataproducer.model.deserializer;

import java.io.IOException;

import org.apache.kafka.common.serialization.Deserializer;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.models.TitleBasic;

public class TitleBasicDeserializer implements Deserializer<TitleBasic> {

	@Override
	public TitleBasic deserialize(String topic, byte[] data) {
		
		TitleBasic titleBasic=null;

		ObjectMapper mapper=new ObjectMapper();
		try {
			titleBasic=mapper.readValue(data, TitleBasic.class);
		} catch (StreamReadException e) {
			e.printStackTrace();
		} catch (DatabindException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return titleBasic;
	}

}
