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
import cn.rushmedia.jay.tvshow.domain.MyHomeLineDiscu;
import cn.rushmedia.jay.tvshow.domain.Topic;
import cn.rushmedia.jay.tvshow.domain.User;
import cn.rushmedia.jay.tvshow.util.ImageCash;
import cn.rushmedia.jay.tvshow.util.ImageDownloder;

public class MyHomeActivity extends BaseActivity {
	private JSONObject js;
	private ImageView tv_home_user_img;
	private TextView tv_username;
	private TextView tv_useremail;
	private TextView tv_curtime;
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
	private int DIALOG_LOGOUT_ID = 10;
	private String loginInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myhome);
		intiData();
		// 获取当前的日期格式
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		AppData appl = (AppData) getApplication();
		loginInfo = appl.getLoginInfo();
		try {
			js = new JSONObject(loginInfo);
			long updatedtime = js.getLong("updated-at");
			Date date = new Date(updatedtime);
			String datereg = sDateFormat.format(date);
			tv_username.setText(js.getString("name"));
			tv_useremail.setText(js.getString("email"));
			tv_curtime.setText(datereg);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			String imagePath = js.getString("image");
			ImageDownloder downloder = new ImageDownloder();
			Bitmap bitmap = downloder.imageDownloder(imagePath);
			tv_home_user_img.setImageBitmap(bitmap);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		tv_home_collecttopic.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
			}
		});
		tv_home_fans.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent fansIntent = new Intent(MyHomeActivity.this,
						FollowerActivity.class);
				startActivity(fansIntent);

			}
		});
		/**
		 * 我关注的朋友
		 */
		tv_home_formyfriend.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent followingIntent = new Intent(MyHomeActivity.this,
						FollowingActivity.class);
				startActivity(followingIntent);

			}
		});
		/**
		 * 用户列表
		 */
		tv_home_userslist.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MyHomeActivity.this,
						UsersActivity.class);
				startActivity(intent);

			}
		});
		/**
		 * 话题列表
		 */
		tv_home_topicslist.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent topicIntent = new Intent(MyHomeActivity.this,
						TopicListActivity.class);
				startActivity(topicIntent);
			}
		});
		/**
		 * 回到主菜单
		 */
		ImageButton backtohome = (ImageButton) findViewById(R.id.backtohome);
		backtohome.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),
						TableActivity.class);
				startActivity(i);
			}
		});
		/**
		 * 我发表的话题
		 */
		tv_home_writetopic.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent myTopicListIntent = new Intent(MyHomeActivity.this,
						MyTopicListActivity.class);
				startActivity(myTopicListIntent);
			}
		});
		/**
		 * 我发表的帖子
		 */
		tv_home_join.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent myPostListIntent = new Intent(MyHomeActivity.this,
						MyPostListActivity_1.class);
				MyHomeLineDiscu home = new MyHomeLineDiscu();
				try {
					JSONObject js = new JSONObject(loginInfo);
					String username = js.getString("name");
					String email = js.getString("email");
					int userid = js.getInt("id");
					String image = js.getString("image");
					long create_date = js.getLong("created-at");
					User user = new User();
					user.setEmail(email);
					user.setId(userid);
					user.setCreated_at(create_date);
					user.setName(username);
					user.setImage(image);
					Topic topic = new Topic();
					topic.setUser(user);
					home.setTopic(topic);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				myPostListIntent.putExtra("topic", home);
				startActivity(myPostListIntent);
				finish();
			}
		});
		/**
		 * 我收藏的节目
		 */
		tv_home_collectprogram.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent myPostListIntent = new Intent(MyHomeActivity.this,
						MyFavProgramActivity.class);
				try {
					JSONObject js = new JSONObject(loginInfo);
					String username = js.getString("name");
					String email = js.getString("email");
					int userid = js.getInt("id");
					String image = js.getString("image");
					long create_date = js.getLong("created-at");
					User user = new User();
					user.setEmail(email);
					user.setId(userid);
					user.setCreated_at(create_date);
					user.setName(username);
					user.setImage(image);
					myPostListIntent.putExtra("userinfo", user);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				startActivity(myPostListIntent);
			}
		});
		/**
		 * 我收藏的话题
		 */
		tv_home_collecttopic.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent myPostListIntent = new Intent(MyHomeActivity.this,
						MyFavTopicsActivity_1.class);
				try {
					JSONObject js = new JSONObject(loginInfo);
					String username = js.getString("name");
					String email = js.getString("email");
					int userid = js.getInt("id");
					String image = js.getString("image");
					long create_date = js.getLong("created-at");
					User user = new User();
					user.setEmail(email);
					user.setId(userid);
					user.setCreated_at(create_date);
					user.setName(username);
					user.setImage(image);
					myPostListIntent.putExtra("userinfo", user);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				startActivity(myPostListIntent);
			}
		});
		/**
		 * 我发表过的评论
		 */
		tv_home_saysomething.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),
						MyPostListActivity_1.class);
				MyHomeLineDiscu home = new MyHomeLineDiscu();
				try {
					JSONObject js = new JSONObject(loginInfo);
					String username = js.getString("name");
					String email = js.getString("email");
					int userid = js.getInt("id");
					String image = js.getString("image");
					long create_date = js.getLong("created-at");
					User user = new User();
					user.setEmail(email);
					user.setId(userid);
					user.setCreated_at(create_date);
					user.setName(username);
					user.setImage(image);
					Topic topic = new Topic();
					topic.setUser(user);
					home.setTopic(topic);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				i.putExtra("topic", home);
				startActivity(i);

			}
		});
	}

	/**
	 * 
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			showTips();
			return false;
		}
		return false;
	}

	private void intiData() {
		tv_home_user_img = (ImageView) findViewById(R.id.tv_home_user_img);
		tv_username = (TextView) findViewById(R.id.tv_home_user_name);
		tv_useremail = (TextView) findViewById(R.id.tv_home_user_emali);
		tv_curtime = (TextView) findViewById(R.id.tv_home_user_currtime);
		// tv_home_forme=(Button) findViewById(R.id.tv_home_forme);
		// tv_home_followme=(Button) findViewById(R.id.tv_home_followme);
		tv_home_writetopic = (Button) findViewById(R.id.tv_home_writetopic);
		tv_home_join = (Button) findViewById(R.id.tv_home_join);
		tv_home_fans = (Button) findViewById(R.id.tv_home_fans);
		tv_home_saysomething = (Button) findViewById(R.id.tv_home_saysomething);
		tv_home_formyfriend = (Button) findViewById(R.id.tv_home_formyfriend);
		tv_home_collectprogram = (Button) findViewById(R.id.tv_home_collectprogram);
		tv_home_collecttopic = (Button) findViewById(R.id.tv_home_collecttopic);
		tv_home_userslist = (Button) findViewById(R.id.tv_home_userslist);
		tv_home_topicslist = (Button) findViewById(R.id.tv_home_topicslist);
	}
}
