package cn.rushmedia.jay.tvshow.domain;

import java.io.Serializable;

public class Program implements Serializable {
    	
    /**
     *  topics: "34", 
      keyword: "犯罪;剧情;悬疑;惊悚", 
      created_at: "1322632191789", 
      be_favorited: "1", 
      director: "克里斯托弗·诺兰", 
      posts: "226", 
      actor: "盖·皮尔斯;凯瑞-安·莫斯;乔·潘托里亚诺;Russ Fega;乔雅·福克斯", 
      title: "记忆碎片", 
      id: "223", 
      image: "http://tvsrv.webhop.net:8080/upload/53127a05-c1b0-4105-bc6c-9e3c40c5fe06.jpg", 
      favorite: "false", 
      description: "当记忆支离破碎后，你该怎样面对生活？本片的主人公就遭遇了这样的事情。
莱纳•谢尔比（盖伊•皮尔斯 饰）在家遭到歹徒的袭击，妻子被残忍的奸杀，自己脑部也受到严重的伤害。
醒来后，他发现自己患了罕见的“短期记忆丧失症”，他只能记住十几分钟前发生的事情，为了让生活继续下去，更为了替惨死的妻子报仇，他凭借纹身、纸条、宝丽来快照等零碎的小东西，保存记忆，收集线索，展开了艰难的调查。
调查中，莱纳遇上粗俗的酒吧女招待娜塔莉娅(凯瑞•安妮•莫斯 饰)，她似乎知道一些莱昂纳多感兴趣的事；还有泰迪(乔•潘托利亚诺 饰)，自称是他以前好朋友，但看上去鬼鬼祟祟的，不怀好意。到底谁能相信？娜塔莉娅？泰迪？还是他自己？
抽丝剥茧之后，真相呼之欲出，简单之至却又残酷无比，而莱纳是否有面对这一切的勇气？©豆瓣", 
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
