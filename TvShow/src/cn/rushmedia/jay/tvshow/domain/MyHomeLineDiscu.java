package cn.rushmedia.jay.tvshow.domain;

import java.io.Serializable;

public class MyHomeLineDiscu implements Serializable{
	private String timeDiffence;
    private Comment comment;
	private String c;
    private User user;
    private int fs;
    private Topic topic;
    private int post;
    private Repost repost;
	private long created_at;
    private int topicid;
    private int userid;
    private int valid;
    private int id;
    private boolean favorite;
    private int p;
    private int t;
    private int u;
   
    public Repost getRepost() {
		return repost;
	}
	public void setRepost(Repost repost) {
		this.repost = repost;
	}
	  public String getTimeDiffence() {
			return timeDiffence;
		}
		public void setTimeDiffence(String timeDiffence) {
			this.timeDiffence = timeDiffence;
		}

	public Comment getComment() {
			return comment;
		}
		public void setComment(Comment comment) {
			this.comment = comment;
		}
		public String getC() {
			return c;
		}
		public void setC(String c) {
			this.c = c;
		}
		public int getP() {
			return p;
		}
		public void setP(int p) {
			this.p = p;
		}
		public int getT() {
			return t;
		}
		public void setT(int t) {
			this.t = t;
		}
		public int getU() {
			return u;
		}
		public void setU(int u) {
			this.u = u;
		}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public int getFs() {
		return fs;
	}
	public void setFs(int fs) {
		this.fs = fs;
	}
	public Topic getTopic() {
		return topic;
	}
	public void setTopic(Topic topic) {
		this.topic = topic;
	}
	public int getPost() {
		return post;
	}
	public void setPost(int post) {
		this.post = post;
	}
	public long getCreated_at() {
		return created_at;
	}
	public void setCreated_at(long created_at) {
		this.created_at = created_at;
	}
	public int getTopicid() {
		return topicid;
	}
	public void setTopicid(int topicid) {
		this.topicid = topicid;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public int getValid() {
		return valid;
	}
	public void setValid(int valid) {
		this.valid = valid;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isFavorite() {
		return favorite;
	}
	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}
} 
