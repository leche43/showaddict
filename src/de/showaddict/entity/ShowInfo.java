package de.showaddict.entity;

import java.util.List;

public class ShowInfo {
	private int id;
	private String title;
    private int year;
    private String imdb_id;
    private int tvdb_id;
    private int tvrage_id;
    private int plays;
    private String url;
    private Images images;
    private List<String> genres;

    public ShowInfo() {
    	
    }
    
    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * Don't use this method. It is only for parsing. Use {@link Show#getTitle() Show.getTitle()} instead.
	 * @return
	 */
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getImdb_id() {
		return imdb_id;
	}
	public void setImdb_id(String imdb_id) {
		this.imdb_id = imdb_id;
	}
	public int getTvdb_id() {
		return tvdb_id;
	}
	public void setTvdb_id(int tvdb_id) {
		this.tvdb_id = tvdb_id;
	}
	public int getTvrage_id() {
		return tvrage_id;
	}
	public void setTvrage_id(int tvrage_id) {
		this.tvrage_id = tvrage_id;
	}
	public int getPlays() {
		return plays;
	}
	public void setPlays(int plays) {
		this.plays = plays;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	public Images getImages() {
		return images;
	}

	public void setImages(Images images) {
		this.images = images;
	}

	public List<String> getGenres() {
		return genres;
	}

	public void setGenres(List<String> genres) {
		this.genres = genres;
	}

	@Override
	public String toString() {
		return title;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((genres == null) ? 0 : genres.hashCode());
		result = prime * result + ((images == null) ? 0 : images.hashCode());
		result = prime * result + ((imdb_id == null) ? 0 : imdb_id.hashCode());
		result = prime * result + plays;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + tvdb_id;
		result = prime * result + tvrage_id;
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		result = prime * result + year;
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
		ShowInfo other = (ShowInfo) obj;
		if (genres == null) {
			if (other.genres != null)
				return false;
		} else if (!genres.equals(other.genres))
			return false;
		if (images == null) {
			if (other.images != null)
				return false;
		} else if (!images.equals(other.images))
			return false;
		if (imdb_id == null) {
			if (other.imdb_id != null)
				return false;
		} else if (!imdb_id.equals(other.imdb_id))
			return false;
		if (plays != other.plays)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (tvdb_id != other.tvdb_id)
			return false;
		if (tvrage_id != other.tvrage_id)
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		if (year != other.year)
			return false;
		return true;
	}



	/**
	 * Klasse um urls zu Bildern einer Folge zu halten. Ist nur so gebaut, da man es so am besten parsen kann.
	 * 
	 * @author Nico
	 *
	 */
	public class Images {
		
		private String poster;
		private String fanart;
		private String banner;
		
		public String getPoster() {
			return poster;
		}
		public void setPoster(String poster) {
			this.poster = poster;
		}
		public String getFanart() {
			return fanart;
		}
		public void setFanart(String fanart) {
			this.fanart = fanart;
		}
		public String getBanner() {
			return banner;
		}
		public void setBanner(String banner) {
			this.banner = banner;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result
					+ ((banner == null) ? 0 : banner.hashCode());
			result = prime * result
					+ ((fanart == null) ? 0 : fanart.hashCode());
			result = prime * result
					+ ((poster == null) ? 0 : poster.hashCode());
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
			Images other = (Images) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (banner == null) {
				if (other.banner != null)
					return false;
			} else if (!banner.equals(other.banner))
				return false;
			if (fanart == null) {
				if (other.fanart != null)
					return false;
			} else if (!fanart.equals(other.fanart))
				return false;
			if (poster == null) {
				if (other.poster != null)
					return false;
			} else if (!poster.equals(other.poster))
				return false;
			return true;
		}
		private ShowInfo getOuterType() {
			return ShowInfo.this;
		}
		
	}
	
	
}
