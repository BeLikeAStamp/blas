package com.androtailored.belikeastampuser.util;

import android.app.Application;

public class ProjectData extends Application {
	private String projectTheme;
	private String projectType;
	private String projectStyle;
	private String projectName;
	private String numberOfCards;
	private String submitDate;
	private String orderDate;
	private int[] colorPanel;
	private PersoSubject perso;
	
	public ProjectData() {
		super();
		colorPanel = new int[]{-1,-1,-1};
	}
	public int[] getColorPanel() {
		return colorPanel;
	}
	public void setColorPanel(int[] colorPanel) {
		this.colorPanel = colorPanel;
	}
	public void setColor1(int i) {
		this.colorPanel[0] = i;
	}
	public void setColor2(int i) {
		this.colorPanel[1] = i;
	}
	public void setColor3(int i) {
		this.colorPanel[2] = i;
	}
	public int getColor1() {
		return this.colorPanel[0];
	}
	public int getColor2() {
		return this.colorPanel[1];
	}
	public int getColor3() {
		return this.colorPanel[2];
	}
	public String getProjectTheme() {
		return projectTheme;
	}
	public void setProjectTheme(String projectTheme) {
		this.projectTheme = projectTheme;
	}
	public String getProjectType() {
		return projectType;
	}
	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getNumberOfCards() {
		return numberOfCards;
	}
	public void setNumberOfCards(String numberOfCards) {
		this.numberOfCards = numberOfCards;
	}
	public String getSubmitDate() {
		return submitDate;
	}
	public void setSubmitDate(String submitDate) {
		this.submitDate = submitDate;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public PersoSubject getPerso() {
		return perso;
	}
	public void setPerso(PersoSubject perso) {
		this.perso = perso;
	}
	public String getProjectStyle() {
		return projectStyle;
	}
	public void setProjectStyle(String projectStyle) {
		this.projectStyle = projectStyle;
	}

}
