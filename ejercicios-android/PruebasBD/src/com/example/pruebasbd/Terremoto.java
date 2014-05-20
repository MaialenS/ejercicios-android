package com.example.pruebasbd;

import android.R.integer;

public class Terremoto {

	private int id;
	private String place;
	private int time;
	private String detail;
	private Float magnitude;
	private Float lat;
	private Float lon;
	private String url;
	private int updated_at;

	public Terremoto(int id, String place, int time, String detail,
			Float magnitude, Float lat, Float lon, String url,
			int updated_at) {

		super();
		this.id=id;
		this.place=place;
		this.time = time;
		this.detail = detail;
		this.magnitude = magnitude;
		this.lat = lat;
		this.lon = lon;
		this.url = url;
		
		this.updated_at = updated_at;
	}


	

	public int getId() {
		return id;
	}
	public String getPlace() {
		return place;
	}
	public int getTime() {
		return time;
	}
	public String getDetail() {
		return detail;
	}
	public float getMagnitude(){
		return magnitude;
	}
	public float getLatitude(){
		return lat;
	}
	public float getLongitude(){
		return lon;
	}
	public String getUrl(){
		return url;
	}

	public int getUpdated(){
		return updated_at;
	}
	
	
}
