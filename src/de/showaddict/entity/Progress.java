package de.showaddict.entity;

import java.io.Serializable;

public class Progress implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7911726711163603697L;
	
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aired == null) ? 0 : aired.hashCode());
		result = prime * result
				+ ((completed == null) ? 0 : completed.hashCode());
		result = prime * result + ((left == null) ? 0 : left.hashCode());
		result = prime * result
				+ ((percentage == null) ? 0 : percentage.hashCode());
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
		Progress other = (Progress) obj;
		if (aired == null) {
			if (other.aired != null)
				return false;
		} else if (!aired.equals(other.aired))
			return false;
		if (completed == null) {
			if (other.completed != null)
				return false;
		} else if (!completed.equals(other.completed))
			return false;
		if (left == null) {
			if (other.left != null)
				return false;
		} else if (!left.equals(other.left))
			return false;
		if (percentage == null) {
			if (other.percentage != null)
				return false;
		} else if (!percentage.equals(other.percentage))
			return false;
		return true;
	}
	
	
}
