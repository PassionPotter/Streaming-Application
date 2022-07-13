package com.learn.spark.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.learn.spark.service.SparkSqlCassandraService;

@RestController
public class SparkSqlCassandraController {

	@Autowired
	SparkSqlCassandraService service;

	@RequestMapping(method = RequestMethod.GET, path = "/titleData")
	public List<String> readTitleData() {

		return service.readTitleData();

	}

	@RequestMapping(method = RequestMethod.GET, path = "/titleBasicData")
	public List<String> readTitleBasicData() {

		return service.readTitleBasicData();

	}

	@RequestMapping(method = RequestMethod.GET, path = "/titleBasicDataWithQuery")
	public List<String> readTitleBasicDataWithQuery(@RequestBody(required = true) String sqlQuery) {
		return service.readTitleBasicDataWithQuery(sqlQuery);

	}

	@RequestMapping(method = RequestMethod.GET, path = "/titleDataWithQuery")
	public List<String> readTitleDataWithQuery(@RequestBody(required = true) String sqlQuery) {

		return service.readTitleDataWithQuery(sqlQuery);

	}

	@RequestMapping(method = RequestMethod.POST, path = "/writeResultToCassandra")
	public List<String> writeQueryResultToCassandra(@RequestBody(required = true) String sqlQuery) {

		return service.writeQueryResultToCassandra(sqlQuery);

	}
}
