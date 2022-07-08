package com.learn.flink.kafka_flink_consumers;

import java.util.Date;
import java.util.Properties;

import org.apache.flink.api.common.functions.FlatJoinFunction;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;
import org.apache.flink.streaming.util.serialization.SimpleStringSchema;
import org.apache.flink.util.Collector;

import com.learn.models.TitleBasic;
import com.learn.models.TitleBasicWithRating;
import com.learn.models.TitleRating;

public class TitlesCassandraSink {

	public static void main(String[] args) throws Exception {
		// create execution environment
		StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
		// StreamExecutionEnvironment
		// env=StreamExecutionEnvironment.createRemoteEnvironment("172.27.16.1", 6123);

		env.setParallelism(1);

		Properties properties = new Properties();
		Date newDate = new Date();

		
		String bootStrapServers = System.getenv("KAFKA_SERVER_IP") + ":" + System.getenv("KAFKA_SERVER_PORT");
		String topic1 = System.getenv("KAFKA_TOPIC_TITLE_BASIC");
		String topic2 = System.getenv("KAFKA_TOPIC_TITLE_RATING");

		String cassandraHost = System.getenv("CASSANDRA_HOST");
		int cassandraPort = Integer.parseInt(System.getenv("CASSANDRA_PORT"));
		String cassandraUname = System.getenv("CASSANDRA_UNAME");
		String cassandraPass = System.getenv("CASSANDRA_PASS");

		
		
	/*	String bootStrapServers = System.getProperty("KAFKA_SERVER_IP", "localhost") + ":"
				+ System.getProperty("KAFKA_SERVER_PORT", "9092");
		String topic1 = System.getProperty("KAFKA_TOPIC1", "tbasic4");
		String topic2 = System.getProperty("KAFKA_TOPIC2", "topic2");

		String cassandraHost = System.getProperty("CASSANDRA_HOST", "localhost");
		int cassandraPort = Integer.parseInt(System.getProperty("CASSANDRA_PORT", "9042"));
		String cassandraUname = System.getProperty("CASSANDRA_UNAME", "cassandra");
		String cassandraPass = System.getProperty("CASSANDRA_PASS", "cassandra");
	*/	
		properties.setProperty("bootstrap.servers", bootStrapServers);
		properties.setProperty("group.id", "title-consumer");
		properties.setProperty("auto.offset.reset", "earliest");

	

		
		DataStream<String> stream = env
				.addSource(new FlinkKafkaConsumer011<>(topic1, new SimpleStringSchema(), properties));
		
		DataStream<TitleBasic> resultStream = stream.flatMap(new FlatMapFunction<String, TitleBasic>() {
			@Override
			public void flatMap(String value, Collector<TitleBasic> out) throws Exception {
				ObjectMapper om = new ObjectMapper();
				TitleBasic tBasic = om.readValue(value, TitleBasic.class);
				out.collect(tBasic);
			}
		})
		/*.timeWindowAll(Time.seconds(15)).reduce(new ReduceFunction<TitleBasic>() {

			@Override
			public TitleBasic reduce(TitleBasic value1, TitleBasic value2) throws Exception {
				return value2;
			}
		});*/
		.keyBy(TitleBasic::getTconst).window(TumblingProcessingTimeWindows.of(Time.seconds(15)))
			.reduce(new ReduceFunction<TitleBasic>() {
			
			@Override
			public TitleBasic reduce(TitleBasic value1, TitleBasic value2) throws Exception {
				return value2;
			}
		});

		DataStream<String> stream2 = env
				.addSource(new FlinkKafkaConsumer011<>(topic2, new SimpleStringSchema(), properties));

		

		DataStream<TitleRating> resultStream2 = stream2.flatMap(new FlatMapFunction<String, TitleRating>() {
			@Override
			public void flatMap(String value, Collector<TitleRating> out) throws Exception {
				ObjectMapper om = new ObjectMapper();
				TitleRating tBasic = om.readValue(value, TitleRating.class);
				out.collect(tBasic);
			}
		}).keyBy(TitleRating::getTconst).window(TumblingProcessingTimeWindows.of(Time.seconds(15)))
		.reduce(new ReduceFunction<TitleRating>() {

			@Override
			public TitleRating reduce(TitleRating value1, TitleRating value2) throws Exception {
				return value2;
			}
		});

		// result.print();

		// result.writeAsText("src/main/resources/out3.txt",WriteMode.OVERWRITE);
		// result.writeAsText("src/main/resources/out4.txt",WriteMode.OVERWRITE);

		DataStream<TitleBasicWithRating> joinedStream = resultStream.join(resultStream2)
				.where(value -> value.getTconst()).equalTo(value2 -> value2.getTconst())
				.window(TumblingProcessingTimeWindows.of(Time.seconds(60)))
				.with(new FlatJoinFunction<TitleBasic, TitleRating, TitleBasicWithRating>() {

					@Override
					public void join(TitleBasic tBasic, TitleRating tRating, Collector<TitleBasicWithRating> out)
							throws Exception {

						out.collect(new TitleBasicWithRating(tBasic.getTitleType(), tBasic.getPrimaryTitle(),
								tBasic.getAdult(), tBasic.getStartYear(), tBasic.getGenres(),
								tRating.getAverageRating(), tRating.getNumVotes()));
					}

				});

		CassandraSinkService.sinkToCassandraDB(resultStream, cassandraHost, cassandraPort, cassandraUname,
				cassandraPass);

		CassandraSinkService.sinkToCassandraDB(resultStream2, cassandraHost, cassandraPort, cassandraUname,
				cassandraPass);

		CassandraSinkService.sinkToCassandraDB(joinedStream, cassandraHost, cassandraPort, cassandraUname,
				cassandraPass);

		env.execute();
	}
}
