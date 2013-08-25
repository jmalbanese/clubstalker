package com.evilkittyrpg.dataobjects;

import java.util.Date;

public class Rating 
{

	private int 	id;
	private String 	uuid_escort;
	private Date 	creation_date;
	private String	uuid_john;
	private int 	location_quality;
	private int		enthusiasm;
	private int 	technique;
	private int 	looks;
	private int		picture_authentic;
	private int		overall_satisfaction;
	private String	comments;
	

	public Rating copy()
	{
		Rating c = new Rating();
		
		c.id = id;
		c.uuid_escort = uuid_escort;
		c.creation_date = creation_date;
		c.uuid_john = uuid_john;
		c.location_quality = location_quality;
		c.enthusiasm = enthusiasm;
		c.technique = technique;
		c.looks = looks;
		c.picture_authentic = picture_authentic;
		c.overall_satisfaction = overall_satisfaction;
		c.comments = comments;
		return (c);
	}


	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}


	/**
	 * @return the uuid_escort
	 */
	public String getUuid_escort() {
		return uuid_escort;
	}


	/**
	 * @param uuid_escort the uuid_escort to set
	 */
	public void setUuid_escort(String uuid_escort) {
		this.uuid_escort = uuid_escort;
	}


	/**
	 * @return the creation_date
	 */
	public Date getCreation_date() {
		return creation_date;
	}


	/**
	 * @param creation_date the creation_date to set
	 */
	public void setCreation_date(Date creation_date) {
		this.creation_date = creation_date;
	}


	/**
	 * @return the uuid_john
	 */
	public String getUuid_john() {
		return uuid_john;
	}


	/**
	 * @param uuid_john the uuid_john to set
	 */
	public void setUuid_john(String uuid_john) {
		this.uuid_john = uuid_john;
	}


	/**
	 * @return the location_quality
	 */
	public int getLocation_quality() {
		return location_quality;
	}


	/**
	 * @param location_quality the location_quality to set
	 */
	public void setLocation_quality(int location_quality) {
		this.location_quality = location_quality;
	}


	/**
	 * @return the enthusiasm
	 */
	public int getEnthusiasm() {
		return enthusiasm;
	}


	/**
	 * @param enthusiasm the enthusiasm to set
	 */
	public void setEnthusiasm(int enthusiasm) {
		this.enthusiasm = enthusiasm;
	}


	/**
	 * @return the technique
	 */
	public int getTechnique() {
		return technique;
	}


	/**
	 * @param technique the technique to set
	 */
	public void setTechnique(int technique) {
		this.technique = technique;
	}


	/**
	 * @return the looks
	 */
	public int getLooks() {
		return looks;
	}


	/**
	 * @param looks the looks to set
	 */
	public void setLooks(int looks) {
		this.looks = looks;
	}


	/**
	 * @return the picture_authentic
	 */
	public int getPicture_authentic() {
		return picture_authentic;
	}


	/**
	 * @param picture_authentic the picture_authentic to set
	 */
	public void setPicture_authentic(int picture_authentic) {
		this.picture_authentic = picture_authentic;
	}


	/**
	 * @return the overall_satisfaction
	 */
	public int getOverall_satisfaction() {
		return overall_satisfaction;
	}


	/**
	 * @param overall_satisfaction the overall_satisfaction to set
	 */
	public void setOverall_satisfaction(int overall_satisfaction) {
		this.overall_satisfaction = overall_satisfaction;
	}


	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}


	/**
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}
}

