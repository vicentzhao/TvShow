package cn.rushmedia.jay.tvshow.domain;

import java.io.Serializable;

public class Repost implements Serializable{
    
	private Post discu;
   	public Post getDiscu() {
		return discu;
	}
	public void setDiscu(Post discu) {
		this.discu = discu;
	}
}
