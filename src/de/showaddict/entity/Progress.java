package de.showaddict.entity;

public class Progress {
	
	private Integer id;
	private Integer percentage;
	private Integer aired;
	private Integer completed;
	private Integer left;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPercentage() {
		return percentage;
	}
	public void setPercentage(Integer percentage) {
		this.percentage = percentage;
	}
	public Integer getAired() {
		return aired;
	}
	public void setAired(Integer aired) {
		this.aired = aired;
	}
	public Integer getCompleted() {
		return completed;
	}
	public void setCompleted(Integer completed) {
		this.completed = completed;
	}
	public Integer getLeft() {
		return left;
	}
	public void setLeft(Integer left) {
		this.left = left;
	}
}
