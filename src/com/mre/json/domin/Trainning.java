package com.mre.json.domin;

public class Trainning implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7424988472062733135L;
	
	private int ServMethod;
	private Data data;
	
	
	public Trainning() {
		super();
	}


	public Trainning(int servMethod, Data data) {
		super();
		ServMethod = servMethod;
		this.data = data;
	}


	public int getServMethod() {
		return ServMethod;
	}


	public void setServMethod(int servMethod) {
		ServMethod = servMethod;
	}


	public Data getData() {
		return data;
	}


	public void setData(Data data) {
		this.data = data;
	}


	@Override
	public String toString() {
		return "Trainning [ServMethod=" + ServMethod + ", data=" + data + "]";
	}
}
