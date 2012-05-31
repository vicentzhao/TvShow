package cn.rushmedia.jay.tvshow.domain;

import java.io.Serializable;

public class Repost implements Serializable{
    
	private Post2 discu;
   	public Post2 getDiscu() {
		return discu;
	}
	public void setDiscu(Post2 discu) {
		this.discu = discu;
	}
}
