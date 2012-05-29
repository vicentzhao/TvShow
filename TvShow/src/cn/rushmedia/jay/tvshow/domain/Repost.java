package cn.rushmedia.jay.tvshow.domain;

import java.io.Serializable;

public class Repost implements Serializable{
    
	private MyHomeLineDiscu discu;
   	public MyHomeLineDiscu getDiscu() {
		return discu;
	}
	public void setDiscu(MyHomeLineDiscu discu) {
		this.discu = discu;
	}
}
