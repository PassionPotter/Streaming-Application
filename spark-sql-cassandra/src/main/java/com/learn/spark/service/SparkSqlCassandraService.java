package com.learn.spark.service;

import java.util.List;

import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import scala.Tuple2;

@Service
public class SparkSqlCassandraService {

	@Autowired
	JavaSparkContext javaSparkContext;

	@Autowired
	SparkSession sparkSession;
	

	public List<String> readTitleData() {
		Dataset<Row> tbasicSet = sparkSession.read().format("org.apache.spark.sql.cassandra")
				.option("keyspace", "example").option("table", "tbasic1").load().filter("startyear=2004");

		Dataset<Row> tratingSet = sparkSession.read().format("org.apache.spark.sql.cassandra")
				.option("keyspace", "example").option("table", "trating1").load();

		Dataset<Tuple2<Row, Row>> resultRDD = tbasicSet.joinWith(tratingSet,
				tbasicSet.col("tconst").equalTo(tratingSet.col("tconst")));
		Dataset<String> jsonData = resultRDD.toJSON();

		return jsonData.collectAsList();
	}
	
	public List<String> readTitleBasicData() {
		Dataset<Row> tbasicSet = sparkSession.read().format("org.apache.spark.sql.cassandra")
				.option("keyspace", "example").option("table", "tbasic1").load().filter("startyear=2004");

		Dataset<String> jsonData = tbasicSet.toJSON();

		return jsonData.collectAsList();
	}
	
	
	
public List<String> readTitleBasicDataWithQuery(String sqlQuery) {
		
		sparkSession.read().format("org.apache.spark.sql.cassandra")
		.option("keyspace", "example").option("table", "tbasic1").load().createOrReplaceTempView("tbasic1");
	
		Dataset<Row> results=sparkSession.
				sql(sqlQuery);
				Dataset<String> jsonData = results.toJSON();

		return jsonData.collectAsList();
	}


	public List<String> readTitleDataWithQuery(String sqlQuery) {
		
		sparkSession.read().format("org.apache.spark.sql.cassandra")
		.option("keyspace", "example").option("table", "tbasic1").load().createOrReplaceTempView("tbasic1");
		
		sparkSession.read().format("org.apache.spark.sql.cassandra")
		.option("keyspace", "example").option("table", "trating1").load().createOrReplaceTempView("trating1");
		
		Dataset<Row> results=sparkSession.
				sql(sqlQuery);
				Dataset<String> jsonData = results.toJSON();

		return jsonData.collectAsList();
	}
	
	public List<String> writeQueryResultToCassandra(String sqlQuery) {
		
		sparkSession.read().format("org.apache.spark.sql.cassandra")
		.option("keyspace", "example").option("table", "tbasic1").load().createOrReplaceTempView("tbasic1");
		
		sparkSession.read().format("org.apache.spark.sql.cassandra")
		.option("keyspace", "example").option("table", "trating1").load().createOrReplaceTempView("trating1");
		
		Dataset<Row> results=sparkSession.
				sql(sqlQuery);
				Dataset<String> jsonData = results.toJSON();

		return jsonData.collectAsList();
	}
}
