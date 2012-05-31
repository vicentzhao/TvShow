package cn.rushmedia.jay.tvshow;

import cn.rushmedia.jay.tvshow.domain.AppData;
import cn.rushmedia.jay.tvshow.domain.Post2;
import cn.rushmedia.jay.tvshow.util.TimeDifference;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class ProgramReviewDetailActivity extends BaseActivity {
	
	     private TextView tv_tvshow_topicdetial_title;
	     private TextView tv_topicdetial_comment;
	     private TextView tv_topicdetial_current;
	     private Button tv_topicdetia_sametopic;
	     private Button tv_topicdetial_isay;
	     private Button tv_topicdetial_disc;
	     private  int programId;
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.programreviewdetail);
			AppData appData = (AppData) getApplication();
			appData.addActivity(this);
		    Intent it =getIntent();
		    Post2  homeLineDiscu = (Post2) it.getSerializableExtra("sametopicdetail");
		    String username = homeLineDiscu.getUser().getName();
		    String comment = homeLineDiscu.getC();
		    long created_time = homeLineDiscu.getCreated_at();
		    TimeDifference timeDifference = new TimeDifference();
		    programId=homeLineDiscu.getTopic().getProgramid();
		    ImageButton backtohome = (ImageButton) findViewById(R.id.backtohome);
	    	backtohome.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent i = new Intent(getApplicationContext(),TableActivity.class);
					startActivity(i);
				}
			});
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
			
			tv_topicdetia_sametopic.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
				Intent sameSameProgramIntent = new Intent(ProgramReviewDetailActivity.this,SameProgramListActivity.class);
				sameSameProgramIntent.putExtra("programid", programId);
				startActivity(sameSameProgramIntent);
				}
			});
//			tv_topicdetial_isay.setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					Intent MyReviewIntent = new Intent(SameTopicDetailActivity.this,MyReviewActivity.class);
//					
//				}
//			});
	  }
//		/**
//		 * ²¶×½»ØÍË¼ü
//		 */
//		public boolean onKeyDown(int keyCode, KeyEvent event) {
//			if(keyCode==KeyEvent.KEYCODE_BACK && event.getRepeatCount()==0){
//				showTips();
//			return false;
//			}
//			return false;
//			}
	
		private void initData() {
			tv_tvshow_topicdetial_title=(TextView) findViewById(R.id.tv_tvshow_topicdetial_title);
			tv_topicdetial_comment=(TextView) findViewById(R.id.tv_topicdetial_comment);
			tv_topicdetial_current=(TextView) findViewById(R.id.tv_topicdetial_current);
			tv_topicdetia_sametopic=(Button) findViewById(R.id.tv_topicdetia_sametopic);
			tv_topicdetial_isay=(Button) findViewById(R.id.tv_topicdetial_isay);
			tv_topicdetial_disc=(Button) findViewById(R.id.tv_topicdetial_disc);
			
		}

}
