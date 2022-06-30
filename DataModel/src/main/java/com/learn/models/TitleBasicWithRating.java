package com.learn.models;

import com.datastax.driver.mapping.annotations.ClusteringColumn;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Table(keyspace = "result", name = "basicwithrating1")
public class TitleBasicWithRating  {

	@Column(name = "titleType")
	@PartitionKey(value = 0)
	String titleType;

	
	@Column(name = "primaryTitle")
	String primaryTitle;

	@Column(name = "adult")
	boolean adult;
	
	@PartitionKey(value = 1)
	@Column(name = "startYear")
	int startYear;
	
	@ClusteringColumn
	@Column(name = "genres")
	String genres;

	@PartitionKey(value = 2)
	@Column(name="averageRating")
	double averageRating;
	
	@Column(name="numVotes")
	long numVotes;

	public TitleBasicWithRating() {
	}
	
	
	
	public TitleBasicWithRating(String titleType, String primaryTitle, boolean adult, int startYear, String genres,
			double averageRating, long numVotes) {
		super();
		this.titleType = titleType;
		this.primaryTitle = primaryTitle;
		this.adult = adult;
		this.startYear = startYear;
		this.genres = genres;
		this.averageRating = averageRating;
		this.numVotes = numVotes;
	}



	public String getTitleType() {
		return titleType;
	}

	public void setTitleType(String titleType) {
		this.titleType = titleType;
	}

	public String getPrimaryTitle() {
		return primaryTitle;
	}

	public void setPrimaryTitle(String primaryTitle) {
		this.primaryTitle = primaryTitle;
	}

	public boolean isAdult() {
		return adult;
	}

	public void setAdult(boolean adult) {
		this.adult = adult;
	}

	public int getStartYear() {
		return startYear;
	}

	public void setStartYear(int startYear) {
		this.startYear = startYear;
	}

	public String getGenres() {
		return genres;
	}

	public void setGenres(String genres) {
		this.genres = genres;
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
