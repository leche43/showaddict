package de.showaddict.entity;

import java.io.Serializable;

/**
 * 
 * enthaelt genaue Informationen zur naechsten Episode einer Show
 * die der User noch nicht gesehen hat
 * 
 * @author Nico
 *
 */
public class NextEpisode implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2133482208359870959L;
	
	private Integer id;
	private Integer season;
	private Integer num;
	private String title;
	private long first_aired;
	private Images images;
	
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
	
	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public long getFirst_aired() {
		return first_aired;
	}

	public void setFirst_aired(long first_aired) {
		this.first_aired = first_aired;
	}

	public Images getImages() {
		return images;
	}

	public void setImages(Images images) {
		this.images = images;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (first_aired ^ (first_aired >>> 32));
		result = prime * result + ((num == null) ? 0 : num.hashCode());
		result = prime * result + ((season == null) ? 0 : season.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		NextEpisode other = (NextEpisode) obj;
		if (first_aired != other.first_aired)
			return false;
		if (num == null) {
			if (other.num != null)
				return false;
		} else if (!num.equals(other.num))
			return false;
		if (season == null) {
			if (other.season != null)
				return false;
		} else if (!season.equals(other.season))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}


	/**
	 * Klasse images wird bisher nur hier verwendet.
	 * 
	 * Kann aber auch herausgezogen werden, falls sie woanders verwendet wird.
	 * 
	 * Nur zum Parsen so gebaut.
	 * 
	 * @author Nico
	 *
	 */
	//TODO anders loesen
	public class Images {
		private String screen;

		public Images(String screen) {
			this.screen = screen;
		}

		public String getScreen() {
			return screen;
		}

		public void setScreen(String screen) {
			this.screen = screen;
		}
	}



}
