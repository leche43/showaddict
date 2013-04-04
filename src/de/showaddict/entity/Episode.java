package de.showaddict.entity;

import java.io.Serializable;

public class Episode implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1906889129523720466L;
	
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((episode == null) ? 0 : episode.hashCode());
		result = prime * result + ((watched == null) ? 0 : watched.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Episode other = (Episode) obj;
		if (episode == null) {
			if (other.episode != null)
				return false;
		} else if (!episode.equals(other.episode))
			return false;
		if (watched == null) {
			if (other.watched != null)
				return false;
		} else if (!watched.equals(other.watched))
			return false;
		return true;
	}

}
