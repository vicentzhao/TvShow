package cn.rushmedia.jay.tvshow;

import org.json.JSONArray;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import cn.rushmedia.jay.tvshow.domain.AppData;
import cn.rushmedia.jay.tvshow.domain.Post;
import cn.rushmedia.jay.tvshow.util.JsonUtil;
import cn.rushmedia.jay.tvshow.util.TimeDifference;

public class PostsDetialActivity extends BaseActivity {
     private TextView tv_tvshow_topicdetial_title;
     private TextView tv_topicdetial_comment;
     private TextView tv_topicdetial_current;
     private Button tv_topicdetia_reviewprogram;
     private Button tv_topicdetial_programdetail;
     private Button tv_topicdetial_sametopic;
     private Button tv_sametopic_isay;
     private Button tv_sametopic_backtohome;
     private Button tv_sametopic_idicupost;
     private int programId;
     private int p;
     private ImageButton back_button;
     private Post  post;
     private ImageButton backtohome; 
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.postdetial_1);
	    Intent it =getIntent();
	    AppData ap = (AppData) getApplication();
	    ap.addActivity(this);
	    post = (Post) it.getSerializableExtra("saydetial");
	    String username = post.getUser().getName();
	    String comment = post.getC();
	    long created_time = post.getCt();
	    TimeDifference timeDifference = new TimeDifference();
	    programId=post.getTopic().getProgramid();
	     p = post.getP();
		String timeDiffence;
		try {
			timeDiffence = timeDifference.getTimeDiffence(created_time);
	    initData();
	    tv_tvshow_topicdetial_title.setText(post.getTopic().getTopic_name());
	    tv_topicdetial_comment.setText(post.getC());
	    tv_topicdetial_current.setText(timeDiffence);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//回到主页
		backtohome.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(PostsDetialActivity.this,TableActivity.class);
				startActivity(i);
				finish();
			}
		});
		/**
		 * 电影评论监听
		 */
		tv_topicdetia_reviewprogram.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				 ProgressDialog pd = new ProgressDialog(PostsDetialActivity.this) ;
				pd.show();
			Intent sameTopicIntent = new Intent(PostsDetialActivity.this,MyPostActivity.class);
			sameTopicIntent.putExtra("saydetial",post);
			startActivity(sameTopicIntent);
			pd.dismiss();
			}
		});
		/**
		 * 电影详情
		 */
		tv_topicdetial_programdetail.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent MyReviewIntent = new Intent(PostsDetialActivity.this,NewFileActivity.class);
				MyReviewIntent.putExtra("saydetial",post);
				startActivity(MyReviewIntent);
			}
		});
		/**
		 * 类似话题
		 */
		tv_topicdetial_sametopic.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			    Intent sameTopicIntent = new Intent(PostsDetialActivity.this,SameTopicList_postActivity.class);
			    sameTopicIntent.putExtra("saydetial",post);
			    startActivity(sameTopicIntent);
			    
			}
		});
		
		/**
		 * 返回上一层
		 */
		back_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			Intent i  = new Intent (PostsDetialActivity.this,TableActivity.class);
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
		 * 评论相关帖子
		 */
		tv_sametopic_idicupost.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(PostsDetialActivity.this,PostOtherPostAcitvity.class);
				i.putExtra("saydetial",post);
				startActivity(i);
			}
		});
  }
	private void initData() {
		backtohome=(ImageButton)findViewById(R.id.backtohome);
		tv_tvshow_topicdetial_title=(TextView) findViewById(R.id.tv_tvshow_topicdetial_title);
		tv_topicdetial_comment=(TextView) findViewById(R.id.tv_topicdetial_comment);
		tv_topicdetial_current=(TextView) findViewById(R.id.tv_topicdetial_current);
		tv_topicdetia_reviewprogram= (Button) findViewById(R.id.tv_topicdetia_reviewprogram);
		tv_topicdetial_programdetail=(Button) findViewById(R.id.tv_topicdetial_programdetail);
		tv_topicdetial_sametopic=(Button) findViewById(R.id.tv_topicdetial_sametopic);
//		tv_sametopic_backtohome=(Button) findViewById(R.id.tv_sametopic_backtohome);
		tv_sametopic_idicupost=(Button) findViewById(R.id.tv_sametopic_idicupost);
		back_button=(ImageButton)findViewById(R.id.back_button);
	}
}
