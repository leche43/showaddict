package de.showaddict.entity;

public class Episode {
	
	private Integer id;
	private Integer episode;
	private Boolean watched;
	
	public Episode() {
		super();
	}
	
	public Episode(Integer episode, Boolean watched) {
		this.episode = episode;
		this.watched = watched;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getEpisode() {
		return episode;
	}
	public void setEpisode(Integer episode) {
		this.episode = episode;
	}
	public Boolean getWatched() {
		return watched;
	}
	public void setWatched(Boolean watched) {
		this.watched = watched;
	}

}
