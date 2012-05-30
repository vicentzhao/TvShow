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
import cn.rushmedia.jay.tvshow.domain.MyHomeLineDiscu;
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
     private MyHomeLineDiscu  homeLineDiscu;
     private ImageButton backtohome; 
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.postdetial_1);
	    Intent it =getIntent();
	    AppData ap = (AppData) getApplication();
	    ap.addActivity(this);
	    homeLineDiscu = (MyHomeLineDiscu) it.getSerializableExtra("saydetial");
	    String username = homeLineDiscu.getUser().getName();
	    String comment = homeLineDiscu.getC();
	    long created_time = homeLineDiscu.getCreated_at();
	    TimeDifference timeDifference = new TimeDifference();
	    programId=homeLineDiscu.getTopic().getProgramid();
	     p = homeLineDiscu.getP();
		String timeDiffence;
		try {
			timeDiffence = timeDifference.getTimeDiffence(created_time);
	    initData();
	    tv_tvshow_topicdetial_title.setText(homeLineDiscu.getTopic().getTopic_name());
	    tv_topicdetial_comment.setText(homeLineDiscu.getC());
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
			Intent sameTopicIntent = new Intent(PostsDetialActivity.this,ProgramReviewListActivity_1.class);
			sameTopicIntent.putExtra("saydetial",homeLineDiscu);
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
				MyReviewIntent.putExtra("saydetial",homeLineDiscu);
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
			    sameTopicIntent.putExtra("saydetial",homeLineDiscu);
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
		 * 当前发言评论列表
		 */
//		tv_sametopic_backtohome.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				
//				//http://tvsrv.webhop.net:8080/api/posts/1/comments
//				final ProgressDialog pd = new ProgressDialog(PostsDetialActivity.this) ;
//				pd.setMessage("正在登陆");
//				pd.show();
//				new AsyncTask<Void, Void, String>(){
//
//					@Override
//					protected void onPostExecute(String result) {
//						pd.dismiss();
//						if(result!=null){
//							Intent i = new Intent(PostsDetialActivity.this,DiscPostActivity.class);
//							i.putExtra("data", result);
//							startActivity(i);
//						}else{
//							return;
//						}
//						super.onPostExecute(result);
//					}
//
//					@Override
//					protected String doInBackground(Void... params) {
//						try {
//							JsonUtil js = new JsonUtil();
//							String path ="http://tvsrv.webhop.net:8080/api/posts/"+p+"/comments";
//							String source = js.getStringSource(path);
//							return source;
//						} catch (Exception e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//							return null;
//						}
//					}
//					
//				}.execute();
//			}
//		});
		/**
		 * 评论相关帖子
		 */
		tv_sametopic_idicupost.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(PostsDetialActivity.this,PostOtherPostAcitvity.class);
				i.putExtra("saydetial",homeLineDiscu);
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
