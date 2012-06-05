package cn.rushmedia.jay.tvshow;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

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
import cn.rushmedia.jay.tvshow.util.ImageDownloder;

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
	private Post home;
	private HttpResponse response;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.topicdetail);
		init();
		AppData ap =(AppData) getApplication();
		ap.addActivity(this);
	    Intent i = getIntent();
	   home = (Post) i.getSerializableExtra("topic");
	    final Topic topic = home.getTopic();
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
				Intent MyReviewIntent = new Intent(TopicDetialActivity.this,NewFile_topic_Activity.class);
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
					int topicid =home.getTopic().getId();
					int userid =home.getTopic().getUser().getId();
					String json="{\"id\":\""+topicid+"\"}";
					String url="http://tvsrv.webhop.net:8080/api/users/"+userid+"/favorite-topics";
					DefaultHttpClient httpClient = new DefaultHttpClient();
					HttpPost request = new HttpPost(url);
					// 绑定到请求 Entry 
							// 发送请求 
							try {
								StringEntity entity = new StringEntity(json);
								request.setEntity(entity); 
								request.setHeader("Content-Type", "application/json");
								response = httpClient.execute(request); 
								String result = EntityUtils.toString(response.getEntity());
								System.out.println(result+"============>>");
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
								Toast.makeText(getApplicationContext(), "提交成功", 1).show();
								tv_sametopic_ctopic.setText("已收藏");
								tv_sametopic_ctopic.setClickable(false);
							}
				}
			});
	    
	    	
	}
	
	private void init() {
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
		tv_sametopic_ctopic = (Button)findViewById(R.id.tv_sametopic_ctopic);
		tv_sametopic_topicpost=(Button) findViewById(R.id.tv_sametopic_topicpost);
	}
}
