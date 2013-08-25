package com.evilkittyrpg.dataobjects;

import java.util.Date;

public class MediaElement {

	private int 	index;
	private String 	uri;
	private String 	comment;
	private int		position;
	private String 	status;
	private int		featured;
	private String 	owner_uuid;
	private String 	type;
	private Date 	creation_date;

	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @param index the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
	}


	/**
	 * @return the uuid
	 */
	public String getUri() {
		return uri;
	}

	/**
	 * @param uuid the uuid to set
	 */
	public void setUri(String uri) {
		this.uri = uri;
	}

	/**
	 * @return the name_legal
	 */
	public void setComment(String _comment) {
		comment = _comment;
	}

	public String getComment() {
		return comment;
	}

	/**
	 * @param name_legal the name_legal to set
	 */
	public void setPosition(int position) {
		this.position = position;
	}
	
	public int getPosition() {
		return position;
	}

	
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	
	/**
	 * @return the featured
	 */
	public int getFeatured() {
		return featured;
	}

	/**
	 * @param status the featured to set
	 */
	public void setFeatured(int featured) {
		this.featured = featured;
	}

	/**
	 * @return the uuid_agency
	 */
	public String getOwner_uuid() {
		return owner_uuid;
	}

	/**
	 * @param uuid_agency the uuid_agency to set
	 */
	public void setOwner_uuid(String uuid) {
		this.owner_uuid = uuid;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
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

	public MediaElement copy()
	{
		MediaElement c = new MediaElement();
		
		c.uri = uri;
		c.comment = comment;
		c.position = position;
		c.status = status;
		c.featured = featured;
		c.owner_uuid = owner_uuid;
		c.type = type;
		c.creation_date = creation_date;
		return (c);
	}
}

