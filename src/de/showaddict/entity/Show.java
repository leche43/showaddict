package de.showaddict.entity;

import java.util.List;

public class Show {
	
	private Integer id;
	private String title;
	private String bannerUri;
	private ShowInfo show;
	private Progress progress;
	private List<Season> seasons;
	private NextEpisode next_episode;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public ShowInfo getShowInfo() {
		return show;
	}
	public void setShowInfo(ShowInfo showInfo) {
		this.show = showInfo;
	}
	public Progress getProgress() {
		return progress;
	}
	public void setProgress(Progress progress) {
		this.progress = progress;
	}
	public List<Season> getSeasons() {
		return seasons;
	}
	public void setSeasons(List<Season> seasons) {
		this.seasons = seasons;
	}
	public NextEpisode getNextEpisode() {
		return next_episode;
	}
	public void setNextEpisode(NextEpisode nextEpisode) {
		this.next_episode = nextEpisode;
	}

}
