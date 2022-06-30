package com.learn.models;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Table(keyspace = "example", name = "trating1")
public class TitleRating  {
	
	@Column(name="tconst")
	@PartitionKey
	String tconst;
	
	@Column(name="averageRating")
	double averageRating;
	
	@Column(name="numVotes")
	long numVotes;

	public TitleRating(String tconst, double averageRating, long numVotes) {
		super();
		this.tconst = tconst;
		this.averageRating = averageRating;
		this.numVotes = numVotes;
	}
	
	public TitleRating() {
	}

	public String getTconst() {
		return tconst;
	}

	public void setTconst(String tconst) {
		this.tconst = tconst;
	}

	public double getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(double averageRating) {
		this.averageRating = averageRating;
	}

	public long getNumVotes() {
		return numVotes;
	}

	public void setNumVotes(long numVotes) {
		this.numVotes = numVotes;
	}
	
	@Override
	public String toString() {
		try {
			return new ObjectMapper().writeValueAsString(this);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
