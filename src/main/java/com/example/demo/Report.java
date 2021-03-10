package com.example.demo;

import java.util.Date;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Report {
	private int id;
	private String firstname;
	private String lastname;
	private Date Date;
	@XmlAttribute(name="id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@XmlElement(name="firstname")
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	@XmlElement(name="lastname")
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	@XmlElement(name="dob")
	public Date getDate() {
		return Date;
	}
	public void setDate(Date date) {
		Date = date;
	}
	@Override
	public String toString() {
		return "Report [id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + ", Date=" + Date + "]";
	}

}
