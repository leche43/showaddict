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
	
	private String title;
//	private NextEpisode nextEpisode;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
//	public NextEpisode getNextEpisode() {
//		return nextEpisode;
//	}
//	public void setNextEpisode(NextEpisode nextEpisode) {
//		this.nextEpisode = nextEpisode;
//	}

}
