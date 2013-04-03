package de.showaddict.entity;

import java.util.ArrayList;
import java.util.List;

public class Season {
	
	private Integer id;
	private Integer season;
	private Integer percentage;
	private Integer aired;
	private Integer completed;
	private Integer left;
	private List<Episode> episodeList;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getSeason() {
		return season;
	}
	public void setSeason(Integer season) {
		this.season = season;
	}
	public Integer getPercentage() {
		return percentage;
	}
	public void setPercentage(Integer percentage) {
		this.percentage = percentage;
	}
	public Integer getAired() {
		return aired;
	}
	public void setAired(Integer aired) {
		this.aired = aired;
	}
	public Integer getCompleted() {
		return completed;
	}
	public void setCompleted(Integer completed) {
		this.completed = completed;
	}
	public Integer getLeft() {
		return left;
	}
	public void setLeft(Integer left) {
		this.left = left;
	}
	
	public List<Episode> getEpisodes() {
		return episodeList;
	}
	public void setEpisodes(List<Episode> episodes) {
		this.episodeList = episodes;
	}
	public void addEpisode(Episode episode) {
		if(episodeList == null) {
			this.episodeList = new ArrayList<Episode>();
		}
		this.episodeList.add(episode);
	}

}
