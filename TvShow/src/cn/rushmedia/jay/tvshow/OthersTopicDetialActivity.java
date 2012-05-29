package cn.rushmedia.jay.tvshow;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import cn.rushmedia.jay.tvshow.domain.AppData;
import cn.rushmedia.jay.tvshow.domain.MyHomeLineDiscu;
import cn.rushmedia.jay.tvshow.domain.Topic;
import cn.rushmedia.jay.tvshow.domain.User;
import cn.rushmedia.jay.tvshow.util.ImageDownloder;

public class OthersTopicDetialActivity extends BaseActivity {
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
	private MyHomeLineDiscu home;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.topicdetail);
		init();
		AppData ap = (AppData) getApplication();
		ap.addActivity(this);
	    Intent i = getIntent();
	   home = (MyHomeLineDiscu) i.getSerializableExtra("topic");
	    final Topic topic = home.getTopic();
	    back_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(OthersTopicDetialActivity.this,OthersTopicListActivity.class);
				User userinfo = home.getUser();
				i.putExtra("userinfo",userinfo);
				startActivity(i);
				
			}
		});
	    ImageButton backtohome = (ImageButton) findViewById(R.id.backtohome);
    	backtohome.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),TableActivity.class);
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
	    tv_topicdetia_reviewprogram.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent sameTopicIntent = new Intent(OthersTopicDetialActivity.this,ProgramReviewListActivity_2.class);
				sameTopicIntent.putExtra("saydetial",home);
				startActivity(sameTopicIntent);
								
			}
		});
	    	tv_topicdetial_programdetail.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				MyHomeLineDiscu homeLineDiscu  = new MyHomeLineDiscu();
				homeLineDiscu.setTopic(topic);
				Intent MyReviewIntent = new Intent(OthersTopicDetialActivity.this,NewFile_topic_Activity.class);
				MyReviewIntent.putExtra("saydetial",homeLineDiscu);
				startActivity(MyReviewIntent);
				
			}
		});
	   
	    	tv_sametopic_isaytopic.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
				  Intent i = new Intent(OthersTopicDetialActivity.this,NewTopicActivity.class);
				  i.putExtra("home", home);
				  startActivity(i);
					
				}
			});
	    	tv_sametopic_backtohome.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent i = new Intent(OthersTopicDetialActivity.this,TableActivity.class);
					startActivity(i);
					finish();
				}
			
	    	});
	    	
	    	/**
	    	 * 相关话题的帖子
	    	 */
	    	tv_sametopic_topicpost.setOnClickListener(new OnClickListener() {
	    		
	    		@Override
	    		public void onClick(View v) {
	    			Intent  i = new Intent(OthersTopicDetialActivity.this,SameTopicPostListActivity.class);
	    			i.putExtra("home", home);
	    			startActivity(i);
	    		}
	    	});
	}
//	/**
//	 * 捕捉回退键
//	 */
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		if(keyCode==KeyEvent.KEYCODE_BACK && event.getRepeatCount()==0){
//			showTips();
//		return false;
//		}
//		return false;
//		}


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
		tv_sametopic_topicpost=(Button) findViewById(R.id.tv_sametopic_topicpost);
	}
	
	
}
