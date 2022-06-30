package com.learn.models;

import com.datastax.driver.mapping.annotations.ClusteringColumn;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Table(keyspace = "example", name = "tbasic1")

public class TitleBasic {
	
	@Column(name = "tconst")
	String tconst;

	@PartitionKey(value = 0)
	@Column(name = "titleType")
	String titleType;

	@ClusteringColumn(value = 0)
	@Column(name = "primaryTitle")
	String primaryTitle;

	@Column(name = "originalTitle")
	String originalTitle;

	@Column(name = "adult")
	boolean adult;
	
	@ClusteringColumn(value = 1)
	@Column(name = "startYear")
	int startYear;

	@Column(name = "endYear")
	String endYear;

	@Column(name = "runtimeMinutes")
	int runtimeMinutes;

	@PartitionKey(value = 1)
	@Column(name = "genres")
	String genres;

	public String getTconst() {
		return tconst;
	}

	public void setTconst(String tconst) {
		this.tconst = tconst;
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

	public String getOriginalTitle() {
		return originalTitle;
	}

	public void setOriginalTitle(String originalTitle) {
		this.originalTitle = originalTitle;
	}

	public boolean getAdult() {
		return adult;
	}

	public void setIsAdult(boolean isAdult) {
		this.adult = isAdult;
	}

	public void setAdult(boolean isAdult) {
		this.adult = isAdult;
	}

	public int getRuntimeMinutes() {
		return runtimeMinutes;
	}

	public void setRuntimeMinutes(int runtimeMinutes) {
		this.runtimeMinutes = runtimeMinutes;
	}

	public String getGenres() {
		return genres;
	}

	public void setGenres(String genres) {
		this.genres = genres;
	}

	public TitleBasic() {
	}

	public TitleBasic(String tconst, String titleType, String primaryTitle, String originalTitle, boolean isAdult,
			int startYear, String endYear, int runtimeMinutes, String genres) {
		super();
		this.tconst = tconst;
		this.titleType = titleType;
		this.primaryTitle = primaryTitle;
		this.originalTitle = originalTitle;
		this.adult = isAdult;
		this.startYear = startYear;
		this.endYear = endYear;
		this.runtimeMinutes = runtimeMinutes;
		this.genres = genres;
	}

	public int getStartYear() {
		return startYear;
	}

	public void setStartYear(int startYear) {
		this.startYear = startYear;
	}

	public String getEndYear() {
		return endYear;
	}

	public void setEndYear(String endYear) {
		this.endYear = endYear;
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
