package de.showaddict.entity;

/**
 * 
 * enthaelt genaue Informationen zur naechsten Episode einer Show
 * die der User noch nicht gesehen hat
 * 
 * @author Nico
 *
 */
public class NextEpisode {
	
	
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

	
	/**
	 * Klasse images wird bisher nur hier verwendet
	 * 
	 * kann aber auch herausgezogen werden, falls sie woanders verwendet wird
	 * 
	 * @author Nico
	 *
	 */
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
