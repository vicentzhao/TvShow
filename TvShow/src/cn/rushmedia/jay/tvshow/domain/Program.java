package cn.rushmedia.jay.tvshow.domain;

import java.io.Serializable;

public class Program implements Serializable {
    	
    /**
     *  topics: "34", 
      keyword: "����;����;����;���", 
      created_at: "1322632191789", 
      be_favorited: "1", 
      director: "����˹�и���ŵ��", 
      posts: "226", 
      actor: "�ǡ�Ƥ��˹;����-����Ī˹;�ǡ���������ŵ;Russ Fega;���š�����˹", 
      title: "������Ƭ", 
      id: "223", 
      image: "http://tvsrv.webhop.net:8080/upload/53127a05-c1b0-4105-bc6c-9e3c40c5fe06.jpg", 
      favorite: "false", 
      description: "������֧����������������������Ƭ�����˹������������������顣
���Ɂ6�1л���ȣ������6�1Ƥ��˹ �Σ��ڼ��⵽��ͽ��Ϯ�������ӱ����̵ļ�ɱ���Լ��Բ�Ҳ�ܵ����ص��˺���
�������������Լ����˺����ġ����ڼ���ɥʧ֢������ֻ�ܼ�סʮ������ǰ���������飬Ϊ�������������ȥ����Ϊ������������ӱ�����ƾ������ֽ�������������յ������С������������䣬�ռ�������չ���˼��ѵĵ��顣
�����У��������ϴ��׵ľư�Ů�д��������(�����6�1���݁6�1Ī˹ ��)�����ƺ�֪��һЩ�����ɶ����Ȥ���£�����̩��(�ǁ6�1��������ŵ ��)���Գ�������ǰ�����ѣ�������ȥ�������ģ��������⡣����˭�����ţ�������櫣�̩�ϣ��������Լ���
��˿����֮�������֮��������֮��ȴ�ֲп��ޱȣ��������Ƿ��������һ�е��������0�8����", 
      valid: "1"

     */
	private User user;
     private int topics;
     private String key;
     private  long created_at;
     private String director;
     private int posts;
     private String actor;
     private String title;
     private int id;
     private String imagePath;
     private boolean favorite;
     private String description;
     
	public Program() {
		super();
	}
	public Program(User user, int topics, String key, long created_at,
			String director, int posts, String actor, String title, int id,
			String imagePath, boolean favorite, String description) {
		super();
		this.user = user;
		this.topics = topics;
		this.key = key;
		this.created_at = created_at;
		this.director = director;
		this.posts = posts;
		this.actor = actor;
		this.title = title;
		this.id = id;
		this.imagePath = imagePath;
		this.favorite = favorite;
		this.description = description;
	}
	public int getTopics() {
		return topics;
	}
	public void setTopics(int topics) {
		this.topics = topics;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public long getCreated_at() {
		return created_at;
	}
	public void setCreated_at(long created_at) {
		this.created_at = created_at;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public int getPosts() {
		return posts;
	}
	public void setPosts(int posts) {
		this.posts = posts;
	}
	public String getActor() {
		return actor;
	}
	public void setActor(String actor) {
		this.actor = actor;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public boolean isFavorite() {
		return favorite;
	}
	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
     
     

}
