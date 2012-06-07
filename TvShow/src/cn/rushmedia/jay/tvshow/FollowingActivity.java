package cn.rushmedia.jay.tvshow;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.rushmedia.jay.tvshow.domain.AppData;
import cn.rushmedia.jay.tvshow.domain.User;
import cn.rushmedia.jay.tvshow.util.ImageDownloder;
import cn.rushmedia.jay.tvshow.util.JsonUtil;

public class FollowingActivity extends BaseActivity {
	private ImageView tv_follower_image;
	private TextView tv_follower_name;
	private List<User> userList;
	Button tv_following_follow;
	private String loginInfo;
	private ListView listview;
	int id;
	private RelativeLayout rl;
	private MyAdapter myAdapter;
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.subject_1_following);
	userList = new ArrayList<User>();
	tv_follower_image =(ImageView) findViewById(R.id.tv_follower_image);
	tv_follower_name=(TextView) findViewById(R.id.tv_follower_name);
	rl=(RelativeLayout) findViewById(R.id.loading);
	listview = (ListView) findViewById(R.id.subjectlist);
	 AppData appl = (AppData)getApplication();
	 appl.addActivity(this);
	  loginInfo = appl.getLoginInfo();
	   myAdapter = new MyAdapter(this);
	 new AsyncTask<Void, Void, List>(){
 
		@Override
		protected void onPreExecute() {
		   showProgress(rl);
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(List result) {
			 hideProgress(rl);
			 if(result!=null){
			 myAdapter.notifyDataSetChanged();
			super.onPostExecute(result);}
			 else{
				 super.onPostExecute(result);
				 Toast.makeText(getApplicationContext(), "你没有关注的朋友", 1).show();
				 Intent i  = new Intent(getApplicationContext(),MyHomeActivity.class);
				 startActivity(i);
				 finish();
			 }
			 
		}

		@Override
		protected List doInBackground(Void... params) {
			 try {
					JSONObject infojs=new JSONObject(loginInfo);
					id =infojs.getInt("id");
					 String path ="http://tvsrv.webhop.net:8080/api/users/"+id+"/followings";
					 JsonUtil js= new JsonUtil();
					 JSONArray followerinfo = js.getSource(path);
					 if(followerinfo!=null&!"[]".equals("followerinfo")){
					 for (int i = 0; i < followerinfo.length(); i++) {
						 JSONObject  userjs=followerinfo.getJSONObject(i);
						 String imagepath = userjs.getString("image");
						 String username= userjs.getString("name");
						 long updated_at =userjs.getLong("updated-at");
						 int userid=userjs.getInt("id");
						 User user = new User();
						 user.setImage(imagepath);
						 user.setUpdated_at(updated_at);
						 user.setId(userid);
						 user.setName(username);
						 userList.add(user);
					 }
					}else{
						return null;
					}
					 return userList;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}
		}
		}.execute();
	
		ImageButton backtohome = (ImageButton) findViewById(R.id.backtohome);
    	backtohome.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),TableActivity.class);
				startActivity(i);
			}
		});
	listview.setAdapter(myAdapter);
//	listview.setOnItemClickListener(new OnItemClickListener() {
//
//		@Override
//		public void onItemClick(AdapterView<?> parent, View view, int position,
//				long id) {
//			Intent i = new Intent(FollowingActivity.this,UserDetailActivity.class);
//			i.putExtra("userinfo", userList.get(position));
//			startActivity(i);
//			
//			
//		}
//	});
	ImageButton tv_reprogram_button = (ImageButton) findViewById(R.id.tv_reprogram_button);
	tv_reprogram_button.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
            Intent i = new Intent(FollowingActivity.this,MyHomeActivity.class);			
            startActivity(i);
		}
	});
	
	}
//	/**
//	 * 捕捉回退键
//	 */
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		if(keyCode==KeyEvent.KEYCODE_BACK && event.getRepeatCount()==0){
//			
//			showTips();
//			
//		return false;
//		}
//		return false;
//		}
	 class MyAdapter extends BaseAdapter{
		   private LayoutInflater mInflater;
			public MyAdapter(Context context){
				this.mInflater = LayoutInflater.from(context);
			}
		@Override
		public int getCount() {
			return userList.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(final int position, View view, ViewGroup parent) {
			view =mInflater.inflate(R.layout.follower, null);
			tv_following_follow  =(Button)view.findViewById(R.id.tv_following_follow);
			tv_following_follow.setVisibility(View.VISIBLE);
			final int followuserid =userList.get(position).getId();
			tv_following_follow.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					System.out.println("wozhixingguo");
					new AsyncTask<Void, Void, List>(){
						@Override
						protected List doInBackground(Void... params) {
							 String json="{\"id\":\""+id+"\",\"followingid\":\""+followuserid+"\"}";
								String url= "http://tvsrv.webhop.net:8080/api/unfollow";
								DefaultHttpClient httpClient = new DefaultHttpClient();
								HttpPost request = new HttpPost(url);
								// 绑定到请求 Entry 
									try {
										StringEntity entity = new StringEntity(json);
										request.setEntity(entity); 
										request.setHeader("Content-Type", "application/json");
										// 发送请求 
										HttpResponse response = httpClient.execute(request); 
										String result = EntityUtils.toString(response.getEntity());
										if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
											Toast.makeText(getApplicationContext(), "提交成功", 1).show();
										}
										System.out.println(result+"====================>>>");
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
							return userList;
						}

						@Override
						protected void onPreExecute() {
							showProgress(rl);
							super.onPreExecute();
						}

						@Override
						protected void onPostExecute(List result) {
							userList.remove(position);//选择行的位置
							if(userList!=null){
							myAdapter.notifyDataSetChanged();
							listview.invalidate();
							hideProgress(rl);
							}else
							{
								hideProgress(rl);
								Intent i = new Intent(FollowingActivity.this,MyHomeActivity.class);
								startActivity(i);
								
							}
							super.onPostExecute(result);
						}}.execute();
					tv_following_follow.setText("unFollowing");
					
//							userList.remove(position);//选择行的位置
//							listview.set
//							listview.invalidate();
					
				}
			});
			
			tv_follower_image =(ImageView) view.findViewById(R.id.tv_follower_image);
			tv_follower_name=(TextView) view.findViewById(R.id.tv_follower_name);
			tv_follower_name.setText(userList.get(position).getName());

			ImageDownloder imageDownloder = new ImageDownloder();
			String imagepath=userList.get(position).getImage();
			try {
				Bitmap bitmap = imageDownloder.imageDownloder(imagepath);
				tv_follower_image.setImageBitmap(bitmap);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return view;
		
		}
	}

}
