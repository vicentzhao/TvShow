package cn.rushmedia.jay.tvshow;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import cn.rushmedia.jay.tvshow.domain.AppData;
import cn.rushmedia.jay.tvshow.domain.Post2;
import cn.rushmedia.jay.tvshow.util.TimeDifference;

public class PostsDetialActivity_1 extends BaseActivity {
     private TextView tv_tvshow_topicdetial_title;
     private TextView tv_topicdetial_comment;
     private TextView tv_topicdetial_current;
     private Button tv_topicdetia_reviewprogram;
     private Button tv_topicdetial_programdetail;
     private Button tv_topicdetial_sametopic;
    
     private Button tv_sametopic_backtohome;
     private Button tv_sametopic_idicupost;
     private  int programId;
     private Post2  homeLineDiscu;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.postdetail);
	    Intent it =getIntent();
	    homeLineDiscu = (Post2) it.getSerializableExtra("saydetial");
	    String username = homeLineDiscu.getUser().getName();
	    String comment = homeLineDiscu.getC();
	    long created_time = homeLineDiscu.getCreated_at();
	    TimeDifference timeDifference = new TimeDifference();
	    programId=homeLineDiscu.getTopic().getProgramid();
		AppData  appData =(AppData) getApplication();
		appData.addActivity(this);
		
		String timeDiffence;
		try {
			timeDiffence = timeDifference.getTimeDiffence(created_time);
		
	    initData();
	    tv_tvshow_topicdetial_title.setText(homeLineDiscu.getTopic().getTopic_name());
	    tv_topicdetial_comment.setText(homeLineDiscu.getC());
	    tv_topicdetial_current.setText(timeDiffence);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tv_topicdetia_reviewprogram.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			Intent sameTopicIntent = new Intent(PostsDetialActivity_1.this,TableActivity.class);
			
			startActivity(sameTopicIntent);
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
		tv_topicdetial_programdetail.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent MyReviewIntent = new Intent(PostsDetialActivity_1.this,NewFileActivity.class);
				
				MyReviewIntent.putExtra("saydetial",homeLineDiscu);
				startActivity(MyReviewIntent);
				
			}
		});
		tv_topicdetial_sametopic.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			    Intent sameTopicIntent = new Intent(PostsDetialActivity_1.this,SameTopicListActivity.class);
			    sameTopicIntent.putExtra("saydetial",homeLineDiscu);
			    startActivity(sameTopicIntent);
			}
		});
	/**
	 * 当前发言评论列表
	 */
	
		tv_sametopic_backtohome.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(PostsDetialActivity_1.this,DiscPostActivity.class);
				i.putExtra("saydetial",homeLineDiscu);
				startActivity(i);
				
			}
		});
		tv_sametopic_idicupost.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(PostsDetialActivity_1.this,PostOtherPostAcitvity.class);
				i.putExtra("saydetial",homeLineDiscu);
				startActivity(i);
				
			}
		});
		ImageButton back_button =(ImageButton) findViewById(R.id.backtohome);
		back_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),TableActivity.class);
				startActivity(i);
				finish();
				
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

	private void initData() {
		tv_tvshow_topicdetial_title=(TextView) findViewById(R.id.tv_tvshow_topicdetial_title);
		tv_topicdetial_comment=(TextView) findViewById(R.id.tv_topicdetial_comment);
		tv_topicdetial_current=(TextView) findViewById(R.id.tv_topicdetial_current);
		tv_topicdetia_reviewprogram= (Button) findViewById(R.id.tv_topicdetia_reviewprogram);
		tv_topicdetial_programdetail=(Button) findViewById(R.id.tv_topicdetial_programdetail);
		tv_topicdetial_sametopic=(Button) findViewById(R.id.tv_topicdetial_sametopic);
		tv_sametopic_backtohome=(Button) findViewById(R.id.tv_sametopic_backtohome);
		tv_sametopic_idicupost=(Button) findViewById(R.id.tv_sametopic_idicupost);
		
	}
}
