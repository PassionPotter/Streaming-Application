package com.learn.kafkadataproducer.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class ApplicationConstants {
	
	
	public static  String KAFKA_LOCAL_SERVER_CONFIG;
	
	@Value("${kafka.common.bootstrap.servers}")
	public void  setKafkaLocalServerConfig(String value) {
		ApplicationConstants.KAFKA_LOCAL_SERVER_CONFIG=value;
	}
	
	
	
	public static  String TITLE_BASIC_GROUP_ID;
	
	@Value("${kafka.topic.title.basic.groupId.prefix}")
	public void  setTitleBasicGroupIdPrefix(String value) {
		ApplicationConstants.TITLE_BASIC_GROUP_ID=value;
	}
	
	
	
	public static  String TITLE_BASIC_TOPIC_NAME;
	
	@Value("${kafka.topic.title.basic.name}")
	public void  setTitleBasicTopicName(String value) {
		ApplicationConstants.TITLE_BASIC_TOPIC_NAME=value;
	}
	
	
	
	public static String TITLE_BASIC_TSV_FILE_SOURCE_PATH;
	
	@Value("${kafka.topic.title.basic.tsvFile.Path}")
	public void  setTitleBasicTsvFilePath(String value) {
		ApplicationConstants.TITLE_BASIC_TSV_FILE_SOURCE_PATH=value;
	}
	
	
	
	public static final String TITLE_BASIC_KAFKA_LISTENER_CONTAINER_FACTORY="tBasicKafkaListenerContainerFactory";
	public static final String KAFKA_LISTENER_CONTAINER_FACTORY="titleBasicKafkaListenerContainerFactory";
	
}
