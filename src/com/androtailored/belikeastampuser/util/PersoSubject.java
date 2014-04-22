package com.androtailored.belikeastampuser.util;

public class PersoSubject {
	private String name;
	private short sexe;
	private int age;
	private String style;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public short getSexe() {
		return sexe;
	}
	public void setSexe(short sexe) {
		this.sexe = sexe;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	@Override
	public String toString() {
		return name + ", " + sexe + ", =" + age
				+ ", " + style;
	}
	
}
