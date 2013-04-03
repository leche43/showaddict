package de.showaddict.entity;

/**
 * Mock Object fuer eine Show.
 * Sollte benutzt werden, dass man nicht alle Informationen auf einmal aus der Datenbank laedt.
 * Kann um attribute erweitert werden.
 * 
 * Diese MockShow wird im Moment für die Anzeige der Liste der Shows verwendet.
 * 
 * @author Nico
 *
 */
public class MockShow {
	
	private Integer showId;
	private String title;
	private String bannerUri;
	private NextEpisode nextEpisode;
	
	public Integer getShowId() {
		return showId;
	}
	public void setShowId(Integer showId) {
		this.showId = showId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBannerUri() {
		return bannerUri;
	}
	public void setBannerUri(String bannerUri) {
		this.bannerUri = bannerUri;
	}
	public NextEpisode getNextEpisode() {
		return nextEpisode;
	}
	public void setNextEpisode(NextEpisode nextEpisode) {
		this.nextEpisode = nextEpisode;
	}

}
