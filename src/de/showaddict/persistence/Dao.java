package de.showaddict.persistence;

import java.util.List;
import java.util.logging.Logger;

import android.content.Context;
import de.showaddict.entity.Episode;
import de.showaddict.entity.MockShow;
import de.showaddict.entity.NextEpisode;
import de.showaddict.entity.Progress;
import de.showaddict.entity.Season;
import de.showaddict.entity.Show;
import de.showaddict.entity.ShowInfo;

/**
 * Klasse um Zugriffe auf die Datenbank zu koordinieren
 * 
 * @author Nico
 *
 */
public class Dao {
	
	public static Logger LOGGER = Logger.getLogger(Dao.class.getName());
	
	
	private Context context;
	
	
	public Dao(Context context) {
		super();
		this.context = context;
	}

	public void createShows(List<Show> shows) {
		LOGGER.info("START CREATING SHOWS");
		
		for(Show show : shows) {
			//FIXME Ist das hier wirklich noetig? Ich muss einer show daten geben, sonst wird sie nicht erstellt
			show.setTitle(show.getShowInfo().getTitle());
			long showId = createShow(show);
			createProgress(show.getProgress(), showId);
//			createNextEpisode(show.getNextEpisode(), showId);
			createShowInfo(show.getShowInfo(), showId);
			for(Season season : show.getSeasons()) {
				long seasonId = createSeason(season, showId);
				for(Episode episode : season.getEpisodes()) {
					createEpisode(episode, seasonId);
				}
			}
			LOGGER.info("CREATED SHOW: " + show.getTitle());
		}
		LOGGER.info("FINISHED CREATING SHOWS");
	}
	
	public List<Show> getAllShows() {
		LOGGER.info("STARTED GET ALL SHOWS");
		ShowDbAdapter showDbAdapter = new ShowDbAdapter(context);
		showDbAdapter.open();
		List<Show> shows = showDbAdapter.getAllShows();
		showDbAdapter.close();
		for(Show show : shows) {
			Progress progress = getProgressForShow(show.getId());
			show.setProgress(progress);
			
//			NextEpisode nextEpisode = getNextEpisodeForShow(show.getId());
//			show.setNextEpisode(nextEpisode);
			
			ShowInfo showInfo = getShowInfoForShow(show.getId());
			show.setShowInfo(showInfo);
			
			List<Season> seasons = getSeasonsForShow(show.getId());
			for(Season season : seasons) {
				List<Episode> episodes = getEpisodesForSeason(season.getId());
				season.setEpisodes(episodes);
			}
			show.setSeasons(seasons);
		}
		LOGGER.info("FINISHED GET ALL SHOWS");
		return shows;
	}
	
	public List<MockShow> getAllMockShows() {
		ShowDbAdapter showDbAdapter = new ShowDbAdapter(context);
		showDbAdapter.open();
		List<MockShow> mockShows = showDbAdapter.getAllMockShows();
		showDbAdapter.close();
		
		return mockShows;
	}
	
	private List<Episode> getEpisodesForSeason(Integer seasonId) {
		EpisodeDbAdapter episodeDbAdapter = new EpisodeDbAdapter(context);
		episodeDbAdapter.open();
		List<Episode> episodes = episodeDbAdapter.getEpisodesFromSeason(seasonId);
		episodeDbAdapter.close();
		return episodes;
	}

	private List<Season> getSeasonsForShow(Integer showId) {
		SeasonDbAdapter seasonDbAdapter = new SeasonDbAdapter(context);
		seasonDbAdapter.open();
		List<Season> seasons = seasonDbAdapter.getSeasonsFromShow(showId);
		seasonDbAdapter.close();
		return seasons;
	}

	private ShowInfo getShowInfoForShow(Integer showId) {
		ShowInfoDbAdapter showInfoDbAdapter = new ShowInfoDbAdapter(context);
		showInfoDbAdapter.open();
		ShowInfo showInfo = showInfoDbAdapter.getShowInfoFromShow(showId);
		showInfoDbAdapter.close();
		return showInfo;
	}

	private NextEpisode getNextEpisodeForShow(Integer showId) {
		NextEpisodeDbAdapter nextEpisodeDbAdapter = new NextEpisodeDbAdapter(context);
		nextEpisodeDbAdapter.open();
		NextEpisode nextEpisode = nextEpisodeDbAdapter.getNextEpisodeFromShow(showId);
		nextEpisodeDbAdapter.close();
		return nextEpisode;
	}

	private Progress getProgressForShow(Integer showId) {
		ProgressDbAdapter progressDbAdapter = new ProgressDbAdapter(context);
		progressDbAdapter.open();
		Progress progress = progressDbAdapter.getProgressFromShow(showId);
		progressDbAdapter.close();
		return progress;
	}

	private void createEpisode(Episode episode, long seasonId) {
		EpisodeDbAdapter episodeDbAdapter = new EpisodeDbAdapter(context);
		episodeDbAdapter.open();
		episodeDbAdapter.createEpisode(episode, seasonId);
		episodeDbAdapter.close();
	}

	private long createSeason(Season season, long showId) {
		SeasonDbAdapter seasonDbAdapter = new SeasonDbAdapter(context);
		seasonDbAdapter.open();
		long seasonId = seasonDbAdapter.createSeason(season, showId);
		seasonDbAdapter.close();
		
		return seasonId;
	}

	private void createShowInfo(ShowInfo showInfo, long showId) {
		ShowInfoDbAdapter showInfoDbAdapter = new ShowInfoDbAdapter(context);
		showInfoDbAdapter.open();
		showInfoDbAdapter.createShowInfo(showInfo, showId);
		showInfoDbAdapter.close();
	}

	private void createNextEpisode(NextEpisode nextEpisode, long showId) {
		NextEpisodeDbAdapter nextEpisodeDbAdapter = new NextEpisodeDbAdapter(context);
		nextEpisodeDbAdapter.open();
		nextEpisodeDbAdapter.createNextEpisode(nextEpisode, showId);
		nextEpisodeDbAdapter.close();
	}

	private void createProgress(Progress progress, long showId) {
		ProgressDbAdapter progresseDbAdapter = new ProgressDbAdapter(context);
		progresseDbAdapter.open();
		progresseDbAdapter.createProgress(progress, showId);
		progresseDbAdapter.close();
	}

	private long createShow(Show show) {
		ShowDbAdapter showDbAdapter = new ShowDbAdapter(context);
		showDbAdapter.open();
		long showId = showDbAdapter.createShow(show);
		showDbAdapter.close();
		
		return showId;
	}

}
