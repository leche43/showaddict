package de.showaddict.entity;

public class ShowInfo {
	private int id;
	private String title;
    private int year;
    private String imdb_id;
    private int tvdb_id;
    private int tvrage_id;
    private int plays;
    private String url;
//    "images": {
//        "poster": "http://trakt.us/images/posters/735.8.jpg",
//        "fanart": "http://trakt.us/images/fanart/735.8.jpg",
//        "banner": "http://trakt.us/images/banners/735.8.jpg"
//    },
//    "genres": ["Animation", "Comedy"]
    public ShowInfo() {
    	
    }
    
    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
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

	@Override
	public String toString() {
		return title;
	}
}
