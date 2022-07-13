package com.learn.spark.config;

import java.io.File;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SparkConfig {

	@Value("${spark.app.name}")
	private String appName;
	@Value("${spark.master}")
	private String masterUri;
	// @Value("${spark.home}")
	// private String sparkHome;
	@Value("${cassandra.host}")
	private String cassandraIp;
	
	
	@Value("${cassandra.port}")
	private String cassandraPort;
	
	@Value("${app.support.libs.dir}")
	private String appJarsDir;

	@Bean
	public SparkConf conf() {
		SparkConf sparkConf = new SparkConf().setAppName(appName).setMaster(masterUri);
		sparkConf.set("spark.driver.maxResultSize", "200m");
		sparkConf.set("spark.driver.memory", "600m");
		sparkConf.set("spark.cassandra.connection.host", cassandraIp);
		sparkConf.set("spark.cassandra.connection.port", cassandraPort);
		return sparkConf;
	}

	@Bean
	public JavaSparkContext javaSparkContext() {
		JavaSparkContext sc = new JavaSparkContext(conf());
		
		addJarsFrom(sc,appJarsDir);
	/*	sc.addJar("E:\\Softwares\\spark-3.0.0-bin-hadoop3.2\\app-jars2\\spark-cassandra-connector_2.12-3.0.0.jar");
		sc.addJar("E:\\Softwares\\spark-3.0.0-bin-hadoop3.2\\app-jars2\\java-driver-core-4.13.0.jar");
		sc.addJar("E:\\Softwares\\spark-3.0.0-bin-hadoop3.2\\app-jars2\\jnr-posix-3.0.27.jar");
		sc.addJar(
				"E:\\Softwares\\spark-3.0.0-bin-hadoop3.2\\app-jars2\\spark-cassandra-connector-assembly_2.12-3.0.0.jar");
		sc.addJar("E:\\Softwares\\spark-3.0.0-bin-hadoop3.2\\app-jars2\\joda-time-2.9.4.jar");*/
		return sc;
	}
	
	public void addJarsFrom(JavaSparkContext sc, String appJarsDir) {
		File dir = new File(appJarsDir);
		for (File file : dir.listFiles()) {
			if(file.getName().endsWith(".jar"))
				sc.addJar(file.getAbsolutePath());
		}
	}

	@Bean
	public SparkSession sparkSession() {
		return SparkSession.builder().sparkContext(javaSparkContext().sc()).appName(appName).master(masterUri)
				.getOrCreate();
	}
}
