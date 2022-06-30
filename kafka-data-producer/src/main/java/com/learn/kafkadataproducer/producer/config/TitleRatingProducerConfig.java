package com.learn.kafkadataproducer.producer.config;

import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import com.learn.kafkadataproducer.model.serializer.TitleRatingSerializer;
import com.learn.models.TitleRating;

@Configuration
@EnableKafka
public class TitleRatingProducerConfig implements BasicProducerConfig<TitleRating> {
	
	@Bean
	public ProducerFactory<String, TitleRating> titleRatingProducerFactory() {
		Map<String, Object> configs=getBasicProducerConfigs();
		configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, TitleRatingSerializer.class);
		
		ProducerFactory<String, TitleRating> producerFactory=new DefaultKafkaProducerFactory<String, TitleRating>(configs);
		return producerFactory;
	}

	@Bean
	public KafkaTemplate<String, TitleRating> titleRatingProducerKafkaTemplate() {
		 KafkaTemplate<String, TitleRating> kafkaTemplate=new KafkaTemplate<String, TitleRating>(titleRatingProducerFactory());
		 
		return kafkaTemplate;
	}

}
