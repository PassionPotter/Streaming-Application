package com.learn.kafkadataproducer.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.learn.kafkadataproducer.constants.ApplicationConstants;
import com.learn.models.TitleBasic;

@Component
@PropertySource("classpath:application.properties")
public class KafkaConsumer {
	private static final Logger logger= LoggerFactory.getLogger(KafkaConsumer.class);

	@KafkaListener(groupId=("${kafka.topic.title.basic.groupId.prefix}"),containerFactory=ApplicationConstants.KAFKA_LISTENER_CONTAINER_FACTORY,topics=("${kafka.topic.title.basic.name}"))
	public void receiveMessage(TitleBasic msg) {
		logger.info("Message Received using Kafka Listener " + msg);
	}
}
