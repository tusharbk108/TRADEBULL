package com.atyeti.tradingApp.models;

import javax.persistence.*;

@Entity
@Table(name = "company")
public class CompanyModel {
	
	@Column(name="name",nullable = false)
	private String name;


	@Column(name="open",nullable = false)
	private int open_rate;
	
	@Column(name="close",nullable = false)
	private int close_rate;
	
	@Column(name="peak",nullable = false)
	private int peak_rate;
	
	@Column(name="least" ,nullable = false)
	private int least_rate;
	
	@Column(name="current" ,nullable = false)
	private int current_rate;
	
	@Id
	@Column(name="companyid",nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int company_id;
	
	@Column(name="yearlow",nullable = false)
	private int year_low;
	
	@Column(name="yearhigh",nullable = false)
	private int year_high;
	
	@Column(name="marketcap",nullable = false)
	private float market_cap;
	
	@Column(name="peratio",nullable = false)
	private float p_e_ratio;
	
	@Column(name="volume",nullable = true)
	private int volume;



	public CompanyModel(String name, int open_rate, int close_rate, int peak_rate, int least_rate, int current_rate,
			int company_id, int year_low, int year_high, float market_cap, float p_e_ratio, int volume) {
		super();
		this.name = name;
		this.open_rate = open_rate;
		this.close_rate = close_rate;
		this.peak_rate = peak_rate;
		this.least_rate = least_rate;
		this.current_rate = current_rate;
		this.company_id = company_id;
		this.year_low = year_low;
		this.year_high = year_high;
		this.market_cap = market_cap;
		this.p_e_ratio = p_e_ratio;
		this.volume = volume;
	}

	@Override
	public String toString() {
		return "CompanyModel{" +
				"name='" + name + '\'' +
				", open_rate=" + open_rate +
				", close_rate=" + close_rate +
				", peak_rate=" + peak_rate +
				", least_rate=" + least_rate +
				", current_rate=" + current_rate +
				", company_id=" + company_id +
				", year_low=" + year_low +
				", year_high=" + year_high +
				", market_cap=" + market_cap +
				", p_e_ratio=" + p_e_ratio +
				", volume=" + volume +
				'}';
	}

	public CompanyModel()
	{
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getOpen_rate() {
		return open_rate;
	}

	public void setOpen_rate(int open_rate) {
		this.open_rate = open_rate;
	}

	public int getClose_rate() {
		return close_rate;
	}

	public void setClose_rate(int close_rate) {
		this.close_rate = close_rate;
	}

	public int getPeak_rate() {
		return peak_rate;
	}

	public void setPeak_rate(int peak_rate) {
		this.peak_rate = peak_rate;
	}

	public int getLeast_rate() {
		return least_rate;
	}

	public void setLeast_rate(int least_rate) {
		this.least_rate = least_rate;
	}

	public int getCurrent_rate() {
		return current_rate;
	}

	public void setCurrent_rate(int current_rate) {
		this.current_rate = current_rate;
	}

	public int getCompany_id() {
		return company_id;
	}

	public void setCompany_id(int company_id) {
		this.company_id = company_id;
	}

	public int getYear_low() {
		return year_low;
	}

	public void setYear_low(int year_low) {
		this.year_low = year_low;
	}

	public int getYear_high() {
		return year_high;
	}

	public void setYear_high(int year_high) {
		this.year_high = year_high;
	}

	public float getMarket_cap() {
		return market_cap;
	}

	public void setMarket_cap(float market_cap) {
		this.market_cap = market_cap;
	}

	public float getP_e_ratio() {
		return p_e_ratio;
	}

	public void setP_e_ratio(float p_e_ratio) {
		this.p_e_ratio = p_e_ratio;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}


}
