package com.learn.kafkadataproducer.producer.config;

import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import com.learn.kafkadataproducer.model.serializer.TitleBasicSerializer;
import com.learn.models.TitleBasic;


@Configuration
@EnableKafka
public class TitleBasicProducerConfig implements BasicProducerConfig<TitleBasic>  {

	@Bean
	public ProducerFactory<String, TitleBasic> titleBasicProducerFactory() {
		Map<String, Object> configs=getBasicProducerConfigs();
		configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, TitleBasicSerializer.class);
		
		ProducerFactory<String, TitleBasic> producerFactory = new DefaultKafkaProducerFactory<String, TitleBasic>(configs);
		return producerFactory;

	}

	@Bean
	public KafkaTemplate<String, TitleBasic> titleBasicProducerKafkaTemplate() {
		KafkaTemplate<String, TitleBasic> kafkaTemplate=new KafkaTemplate<String, TitleBasic>(titleBasicProducerFactory());
		return kafkaTemplate;
	}

/*	@Bean
	public ProducerFactory<String, TitleBasic> customerProducerFactory() {
		Map<String, Object> configs = new HashMap<String, Object>();
		configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, ApplicationConstants.KAFKA_LOCAL_SERVER_CONFIG);
		//configs.put(ProducerConfig.t);
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, TitleBasicSerializer.class);
        configs.put(ProducerConfig.ACKS_CONFIG, "all");
        configs.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        configs.put(ProducerConfig.RETRIES_CONFIG, 0);
        configs.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, 300);
        configs.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        configs.put(ProducerConfig.MAX_BLOCK_MS_CONFIG,120000);
		ProducerFactory<String, TitleBasic> producerFactory = new DefaultKafkaProducerFactory<String, TitleBasic>(configs);
		return producerFactory;

	}
	
	@Bean
	public KafkaTemplate<String, TitleBasic> tBasicProducerKafkaTemplate(){
		KafkaTemplate<String, TitleBasic> kafkaTemplate=new KafkaTemplate<String, TitleBasic>(customerProducerFactory());
		return kafkaTemplate;
		
	} */
}
