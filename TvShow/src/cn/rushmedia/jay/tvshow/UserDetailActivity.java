package cn.rushmedia.jay.tvshow;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import cn.rushmedia.jay.tvshow.domain.AppData;
import cn.rushmedia.jay.tvshow.domain.User;
import cn.rushmedia.jay.tvshow.util.ImageCash;
import cn.rushmedia.jay.tvshow.util.ImageDownloder;

public class UserDetailActivity extends BaseActivity {
	
	private JSONObject js;
	private ImageView tv_home_user_img;
	private TextView tv_username;
	private TextView tv_useremail;
	private TextView  tv_curtime;
	private Button tv_home_forme;
	private Button tv_home_followme;
	private Button tv_home_writetopic;
	private Button tv_home_join;
	private Button tv_home_fans;
	private Button tv_home_saysomething;
	private Button tv_home_formyfriend;
	private Button tv_home_collectprogram;
	private Button tv_home_collecttopic;
	private Button tv_home_userslist;
	private Button tv_home_topicslist;
	private AppData appData;
	private ImageCash cash;
	private Bitmap bitmapFromCache;
	private HashMap<String, Bitmap> mHardBitmapCache;
	private User userinfo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.otheruserhome);
		intiData();
		//��ȡ��ǰ�����ڸ�ʽ
	SimpleDateFormat    sDateFormat    =   new    SimpleDateFormat("yyyy-MM-dd");     
//		 //��ȡһ����������
//		Calendar dateAndTime = Calendar.getInstance(Locale.CHINA);
		   	Intent i = getIntent();
		   	userinfo=(User) i.getSerializableExtra("userinfo");
	    long updatedtime = userinfo.getUpdated_at();
	    Date date = new Date(updatedtime);
	    String datereg = sDateFormat.format(date);
		tv_username.setText(userinfo.getName());
		tv_useremail.setText(userinfo.getEmail());
		tv_curtime.setText(datereg);
		ImageButton backtohome = (ImageButton) findViewById(R.id.backtohome);
    	backtohome.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),TableActivity.class);
				startActivity(i);
			}
		});
			appData =(AppData) getApplication(); 
			appData.addActivity(this);
			cash = new ImageCash();
			
			bitmapFromCache = cash.getBitmapFromCache(userinfo.getImage());
			if(bitmapFromCache==null){
				try {
					String imagePath = userinfo.getImage();
					ImageDownloder ig = new ImageDownloder();
					Bitmap bitmap = ig.imageDownloder(imagePath);
					mHardBitmapCache = appData.getmHardBitmapCache();
					mHardBitmapCache.put(imagePath, bitmap);
					tv_home_user_img.setImageBitmap(bitmap);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}else{
				tv_home_user_img.setImageBitmap(bitmapFromCache);
			}
//			ImageButton tv_reprogram_button =(ImageButton) findViewById(R.id.tv_reprogram_button);
//			tv_reprogram_button.setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					Intent i = new Intent(UserDetailActivity.this,FollowerActivity.class);
//					startActivity(i);
//					
//				}
//			});
			/**
			 * fans
			 */
			tv_home_fans.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
				Intent fansIntent = new Intent(UserDetailActivity.this,OtherFollowerActivity.class);
				fansIntent.putExtra("userinfo", userinfo);
				startActivity(fansIntent);
				
				}
			});
			/**
			 * ta��ת��,����
			 */
			tv_home_saysomething.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					// TODO Auto-generated method stub
				Intent  i = new Intent(UserDetailActivity.this,MyMoveActivity.class);
				i.putExtra("userinfo", userinfo);
				startActivity(i);
				
				}
			});
			/**
			 * ��ע������
			 */
			tv_home_formyfriend.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent followingIntent = new Intent(UserDetailActivity.this,OtherFollowingActivity.class);
					followingIntent.putExtra("userinfo", userinfo);
					startActivity(followingIntent);
					
				}
			});
			/**
			 * ta���������
			 */
			tv_home_userslist.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(UserDetailActivity.this,OtherDiscPostActivity.class);
					intent.putExtra("userinfo", userinfo);
					startActivity(intent);
					
				}
			});
			/**
			 * ta������
			 */
			tv_home_topicslist.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent topicIntent = new  Intent(UserDetailActivity.this,OtherDiscPostActivity_1.class);
					topicIntent.putExtra("userinfo", userinfo);
					startActivity(topicIntent);
				}
			});
			/**
			 * ta����Ļ���
			 */
			tv_home_writetopic.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent myTopicListIntent = new Intent(UserDetailActivity.this,OthersTopicListActivity.class);
					myTopicListIntent.putExtra("userinfo", userinfo);
					startActivity(myTopicListIntent);
					
				}
			});
			/**
			 * ta���������
			 */
			tv_home_join.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent  i = new Intent(UserDetailActivity.this,OtherUsersPostActivity.class);
					i.putExtra("userinfo", userinfo);
					startActivity(i);
				
				}
			});
			/**
			 * ta�ղصĽ�Ŀ
			 */
			tv_home_collectprogram.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent myPostListIntent = new Intent(UserDetailActivity.this,MyFavProgramActivity.class);
					myPostListIntent.putExtra("userinfo", userinfo);
					startActivity(myPostListIntent);
					
				}
			});
			/**
			 * ta�ղصĻ���
			 */
			tv_home_collecttopic.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent myPostListIntent = new Intent(UserDetailActivity.this,SameTopicListActivity.class);
					myPostListIntent.putExtra("userinfo", userinfo);
					startActivity(myPostListIntent);
					
				}
			});
	}
//	/**
//	 * ��׽���˼�
//	 */
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		if(keyCode==KeyEvent.KEYCODE_BACK && event.getRepeatCount()==0){
//			showTips();
//		return false;
//		}
//		return false;
//		}
	private void intiData() {
		tv_home_user_img=(ImageView) findViewById(R.id.tv_home_user_img);
		tv_username=(TextView) findViewById(R.id.tv_home_user_name);
		tv_useremail=(TextView) findViewById(R.id.tv_home_user_emali);
		tv_curtime=(TextView) findViewById(R.id.tv_home_user_currtime);
		tv_home_writetopic=(Button) findViewById(R.id.tv_home_writetopic);
		tv_home_join=(Button) findViewById(R.id.tv_home_join);
		tv_home_fans=(Button) findViewById(R.id.tv_home_fans);
	    tv_home_saysomething=(Button) findViewById(R.id.tv_home_saysomething);
		tv_home_formyfriend=(Button) findViewById(R.id.tv_home_formyfriend);
		tv_home_collectprogram=(Button) findViewById(R.id.tv_home_collectprogram);
		tv_home_collecttopic=(Button) findViewById(R.id.tv_home_collecttopic);
		tv_home_userslist=(Button) findViewById(R.id.tv_home_userslist);
		tv_home_topicslist=(Button) findViewById(R.id.tv_home_topicslist);
	}

}
