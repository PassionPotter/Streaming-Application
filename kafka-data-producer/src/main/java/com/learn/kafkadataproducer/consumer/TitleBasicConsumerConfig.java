package com.learn.kafkadataproducer.consumer;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.OffsetResetStrategy;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.learn.kafkadataproducer.constants.ApplicationConstants;
import com.learn.kafkadataproducer.model.deserializer.TitleBasicDeserializer;
import com.learn.models.TitleBasic;

@Configuration
@EnableKafka
public class TitleBasicConsumerConfig {

	

	//consumer
	@Bean
	public ConsumerFactory<String, TitleBasic> customerConsumerFactory() {
		Map<String, Object> configs = new HashMap<String, Object>();
		configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, ApplicationConstants.KAFKA_LOCAL_SERVER_CONFIG);
		//ConsumerConfig.
		configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, TitleBasicDeserializer.class);
	//	configs.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		configs.put(JsonDeserializer.TRUSTED_PACKAGES, "com.learn.deserializer.*");

		ConsumerFactory<String, TitleBasic> consumerFactory = new DefaultKafkaConsumerFactory<String, TitleBasic>(
				configs);
		return consumerFactory;

	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, TitleBasic> titleBasicKafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, TitleBasic> conccKafkaListContFac = new ConcurrentKafkaListenerContainerFactory<String, TitleBasic>();
		conccKafkaListContFac.setConsumerFactory(customerConsumerFactory());
		return conccKafkaListContFac;

	}
	
}
