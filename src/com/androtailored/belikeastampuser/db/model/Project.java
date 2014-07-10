package com.androtailored.belikeastampuser.db.model;

import java.io.Serializable;

public class Project implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8213396978710850752L;

	private String project_name;
	private String sub_date;
	private int project_status;
	private String theme;
	private String type;
	private String order_date;
	private int nbr_cards;
	private int remoteId;

	public Project() {}



	public Project(String project_name, String sub_date, int project_status,
			String theme, String type, String order_date, int nbr_cards) {
		super();
		this.project_name = project_name;
		this.sub_date = sub_date;
		this.project_status = project_status;
		this.theme = theme;
		this.type = type;
		this.order_date = order_date;
		this.nbr_cards = nbr_cards;
	}



	public String getProject_name() {
		return project_name;
	}
	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}
	public String getSub_date() {
		return sub_date;
	}
	public void setSub_date(String sub_date) {
		this.sub_date = sub_date;
	}
	public int getProject_status() {
		return project_status;
	}
	public void setProject_status(int project_status) {
		this.project_status = project_status;
	}
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getOrder_date() {
		return order_date;
	}
	public void setOrder_date(String order_date) {
		this.order_date = order_date;
	}
	public int getNbr_cards() {
		return nbr_cards;
	}
	public void setNbr_cards(int nbr_cards) {
		this.nbr_cards = nbr_cards;
	}
	public int getRemoteId() {
		return remoteId;
	}
	public void setRemoteId(int remoteId) {
		this.remoteId = remoteId;
	}



}
