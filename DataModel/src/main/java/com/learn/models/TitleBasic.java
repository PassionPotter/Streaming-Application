package com.learn.models;

import java.io.Serializable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TitleBasic implements Serializable {
	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;
	String tconst, titleType, primaryTitle, originalTitle;
	boolean adult;
	int startYear;
	String endYear;
	int runtimeMinutes;
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
