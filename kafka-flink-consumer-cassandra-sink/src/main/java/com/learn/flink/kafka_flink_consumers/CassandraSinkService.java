package com.learn.flink.kafka_flink_consumers;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.connectors.cassandra.CassandraSink;
import org.apache.flink.streaming.connectors.cassandra.ClusterBuilder;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Cluster.Builder;
import com.datastax.driver.mapping.Mapper;

public class CassandraSinkService {
	public final static void sinkToCassandraDB(final DataStream<?> sinkTitleBasicStream, String cassandraHost,
			int cassandraPort, String cassandraUname, String cassandraPass) throws Exception {

		try {
			CassandraSink.addSink(sinkTitleBasicStream)
					.setMapperOptions(() -> new Mapper.Option[] { Mapper.Option.saveNullFields(true) })
					.setClusterBuilder(new ClusterBuilder() {

						/**
						 * 
						 */
						private static final long serialVersionUID = 1L;

						@Override
						protected Cluster buildCluster(Builder builder) {

							return builder.addContactPoints(cassandraHost).withPort(cassandraPort)
									.withCredentials(cassandraUname, cassandraPass).build();
						}
					}).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
