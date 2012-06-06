package cn.rushmedia.jay.tvshow;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Application;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.rushmedia.jay.tvshow.domain.AppData;
import cn.rushmedia.jay.tvshow.domain.Post;
import cn.rushmedia.jay.tvshow.domain.Topic;
import cn.rushmedia.jay.tvshow.domain.User;
import cn.rushmedia.jay.tvshow.util.ImageDownloder;
import cn.rushmedia.jay.tvshow.util.JSONObject2Topic;
import cn.rushmedia.jay.tvshow.util.JSONObject2User;
import cn.rushmedia.jay.tvshow.util.JsonUtil;

public class TopicDetialActivity extends BaseActivity {
	private ImageButton back_button;
	private ImageView tv_topic_userimage;
	private ImageView tv_homeline_programimage;
	private TextView tv_topic_username;
	private TextView tv_topic_comment;
	private TextView tv_homeline_programname;
	private TextView tv_homeline_programdirector;
	private TextView tv_homeline_programactor;
	private TextView tv_homeline_desc;
	private Button tv_topicdetia_reviewprogram;
	private Button tv_topicdetial_programdetail;
	private Button tv_sametopic_isaytopic;
	private Button tv_sametopic_backtohome;
	private Button tv_sametopic_disctopic;
	private Button tv_sametopic_topicpost;
	private Button tv_sametopic_ctopic;
	private boolean isCollected=false;
	private Post home;
	private HttpResponse response;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.topicdetail);
	    Intent i = getIntent();
	   home = (Post) i.getSerializableExtra("topic");
	   int topicId = home.getTopic().getId();
	   isCollected(topicId);
	    final Topic topic = home.getTopic();
	    initView();
	    back_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(TopicDetialActivity.this,OthersTopicDetialActivity.class);
				i.putExtra("topic",home);
				startActivity(i);
				
			}
		});
	    ImageDownloder id = new ImageDownloder();
	    String userimagepath = topic.getUser().getImage();
	    String programimagepath =topic.getProgram().getImagePath();
	    try {
			tv_topic_userimage.setImageBitmap(id.imageDownloder(userimagepath));
			tv_homeline_programimage.setImageBitmap(id.imageDownloder(programimagepath));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    tv_topic_username.setText(topic.getUser().getName());
	    tv_topic_comment.setText(topic.getTopic_name());
	    tv_homeline_programname.setText(topic.getProgram().getTitle());
	    tv_homeline_programdirector.setText(topic.getProgram().getDirector());
	    tv_homeline_desc.setText(topic.getProgram().getDescription());
	    tv_homeline_desc.setText(topic.getProgram().getDescription());
	    /**
	     * 电影评论
	     */
	    tv_topicdetia_reviewprogram.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent sameTopicIntent = new Intent(TopicDetialActivity.this,MyPostActivity.class);
				sameTopicIntent.putExtra("saydetial",home);
				startActivity(sameTopicIntent);
			}
		});
	    /**
	     * 电影详情
	     */
	    	tv_topicdetial_programdetail.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Post homeLineDiscu  = new Post();
				homeLineDiscu.setTopic(topic);
				Intent MyReviewIntent = new Intent(TopicDetialActivity.this,NewFileActivity.class);
				MyReviewIntent.putExtra("saydetial",homeLineDiscu);
				startActivity(MyReviewIntent);
			}
		});
	    	/**
	    	 * 发表话题
	    	 */
	    	tv_sametopic_isaytopic.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
				  Intent i = new Intent(TopicDetialActivity.this,NewTopicActivity.class);
				  i.putExtra("saydetial", home);
				  startActivity(i);
					
				}
			});
	    	
	    	tv_sametopic_backtohome.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent i = new Intent(TopicDetialActivity.this,TableActivity.class);
					startActivity(i);
					finish();
				}
			
	    	});
	    	ImageButton backtohome = (ImageButton) findViewById(R.id.backtohome);
	    	backtohome.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent i = new Intent(getApplicationContext(),TableActivity.class);
					startActivity(i);
				}
			});
	    	/**
	    	 * 相关话题的帖子
	    	 */
	    	tv_sametopic_topicpost.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent  i = new Intent(TopicDetialActivity.this,SameTopicPostListActivity.class);
					i.putExtra("home", home);
					startActivity(i);
				}
			});
	    	/**
	    	 * 收藏话题
	    	 */
	    	tv_sametopic_ctopic.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					User loginUser = getLoginUser();
					 int userid =loginUser.getId();
					if(isCollected){
						String unPath ="http://tvsrv.webhop.net:8080/api/users/"+userid+"/unfavorite-topics";
						int code = collectTopic(unPath);
						if(code==HttpStatus.SC_OK){
							Toast.makeText(getApplicationContext(), "取消收藏成功", 1).show();
							tv_sametopic_ctopic.setText("收藏");
							isCollected=false;
						}else{
							Toast.makeText(getApplicationContext(), "取消收藏失败，请重试", 1).show();
						}
					}else{
					 String collectPath="http://tvsrv.webhop.net:8080/api/users/"+userid+"/favorite-topics";
	                     int collectcode = collectTopic(collectPath);
								if (collectcode == HttpStatus.SC_OK){
									Toast.makeText(getApplicationContext(), "收藏成功", 1).show();
									tv_sametopic_ctopic.setText("取消收藏");
									isCollected=true;
								}else{
									Toast.makeText(getApplicationContext(), "收藏失败，请重试", 1).show();
								}
				}
				}
			});
	}
	private void initView() {
		back_button = (ImageButton) findViewById(R.id.back_button);
		tv_homeline_programimage = (ImageView) findViewById(R.id.tv_homeline_programimage);
		tv_topic_userimage =(ImageView) findViewById(R.id.tv_topic_userimage);
		tv_topic_username=(TextView) findViewById(R.id.tv_topic_username);
		tv_topic_comment=(TextView) findViewById(R.id.tv_topic_comment);
		tv_homeline_programname=(TextView) findViewById(R.id.tv_homeline_programname);
		tv_homeline_programdirector=(TextView) findViewById(R.id.tv_homeline_programdirector);
		tv_homeline_desc=(TextView) findViewById(R.id.tv_homeline_desc);
		tv_topicdetia_reviewprogram=(Button) findViewById(R.id.tv_topicdetia_reviewprogram);
		tv_topicdetial_programdetail=(Button) findViewById(R.id.tv_topicdetial_programdetail);
		tv_sametopic_isaytopic=(Button) findViewById(R.id.tv_sametopic_isaytopic);
		tv_sametopic_backtohome=(Button) findViewById(R.id.tv_sametopic_backtohome);
		tv_sametopic_topicpost=(Button) findViewById(R.id.tv_sametopic_topicpost);
		tv_sametopic_ctopic =(Button) findViewById(R.id.tv_sametopic_ctopic);
		 if(isCollected){
			 tv_sametopic_ctopic.setText("取消收藏");
		 }else{
			 tv_sametopic_ctopic.setText("收藏话题");
		 }
	}
	public User getLoginUser(){
		AppData userInfo = (AppData) getApplication();
		String loginInfo = userInfo.getLoginInfo();
		JSONObject2User ju = new JSONObject2User();
		User user = null;
		try {
			user = ju.getUser(new JSONObject(loginInfo));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return user;
	}
	 public boolean isCollected(int topicId){
		 try {
			User loginUser = getLoginUser();
			 int userid =loginUser.getId();
			 String path ="http://tvsrv.webhop.net:8080/api/users/"+userid+"/favorite-topics";
			 JsonUtil ju = new JsonUtil();
			 JSONArray favtopicArray = ju.getSource(path);
			 for (int i = 0; i < favtopicArray.length(); i++) {
				JSONObject jsTopic  =favtopicArray.getJSONObject(i);
				JSONObject2Topic jt = new JSONObject2Topic();
				Topic topic = jt.getTopic(jsTopic);
				if(topicId==topic.getId()){
					isCollected=true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return false;
	 }
	  public int collectTopic(String url){
		  HttpResponse response;
		  try {
			    int topicId = home.getTopic().getId();
			    String json="{\"id\":\""+topicId+"\"}";
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpPost request = new HttpPost(url);
				StringEntity entity = new StringEntity(json,"utf-8");
				request.setEntity(entity); 
				request.setHeader("Content-Type", "application/json");
				response = httpClient.execute(request); 
				String result = EntityUtils.toString(response.getEntity());
				System.out.println(result+"============>>");
				int statusCode = response.getStatusLine().getStatusCode();
				return statusCode;
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
	  }
}
