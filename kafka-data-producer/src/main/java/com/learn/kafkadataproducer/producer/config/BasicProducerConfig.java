package com.learn.kafkadataproducer.producer.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import com.learn.kafkadataproducer.constants.ApplicationConstants;

public interface BasicProducerConfig<T> {
	
	public default Map<String, Object> getBasicProducerConfigs() {
		Map<String, Object> configs = new HashMap<String, Object>();
		configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, ApplicationConstants.KAFKA_LOCAL_SERVER_CONFIG);
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configs.put(ProducerConfig.ACKS_CONFIG, "all");
        configs.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
        configs.put(ProducerConfig.RETRIES_CONFIG, 0);
        configs.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, 300);
        configs.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        configs.put(ProducerConfig.MAX_BLOCK_MS_CONFIG,120000);
        return configs;
	}
	
}
