package com.maialen.datos;

public class Terremoto {

	private int id;
	private String idStr;
	private String place;
	private long time;
	private String detail;
	private Float magnitude;
	private Float lat;
	private Float lon;
	private String url;
//	private int updated_at;

	public Terremoto(int id, String idStr , String place, long time, String detail,
			Float magnitude, Float lat, Float lon, String url) {

		this(idStr, place, time, detail, magnitude, lat, lon, url);
		this.id=id;
	}
	
	public Terremoto(String idStr , String place, long time, String detail,
			Float magnitude, Float lat, Float lon, String url) {

		super();
		this.idStr=idStr;
		this.place=place;
		this.time = time;
		this.detail = detail;
		this.magnitude = magnitude;
		this.lat = lat;
		this.lon = lon;
		this.url = url;
	}

	public int getId() {
		return id;
	}
	public String getIdStr() {
		return idStr;
	}

	public void setIdStr(String idStr) {
		this.idStr = idStr;
	}

	public String getPlace() {
		return place;
	}
	public long getTime() {
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

	public String toString(){
		
		String res=id+"-> lugar: "+place+" hora: "+time+" detalle: "+detail+" magnitud: "+magnitude+" Lat: "+lat+" Log: "+lon+" "+url;
		return res;
	}
	
}
