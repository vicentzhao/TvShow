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
import org.json.JSONException;
import org.json.JSONObject;

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
import cn.rushmedia.jay.tvshow.util.SourceFromResponse;
import cn.rushmedia.jay.tvshow.util.SourcefromService;
public class SearchPeopleActivity extends BaseActivity {
	private ImageView tv_follower_image;
	private TextView tv_follower_name;
	private List<User> reuserList;
	private List<User> userList;
	Button tv_following_follow;
	private ListView userlistview;
	private List<User> reemailList;
	private RelativeLayout	r1;
	String jsonemail;
	int id;
	MyAdapter myAdapter;
	String jsonuser;
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.subject_1_test);
	reuserList = new ArrayList<User>();
	reemailList = new ArrayList<User>();
	tv_follower_image =(ImageView) findViewById(R.id.tv_follower_image);
	tv_follower_name=(TextView) findViewById(R.id.tv_follower_name);
	userlistview = (ListView) findViewById(R.id.subjectlist);
	AppData ap = (AppData) getApplication();
	ap.addActivity(this);
	r1=(RelativeLayout) findViewById(R.id.loading);
	ImageButton backtohome = (ImageButton) findViewById(R.id.backtohome);
	backtohome.setOnClickListener(new OnClickListener() {
		public void onClick(View v) {
			Intent i = new Intent(getApplicationContext(),TableActivity.class);
			startActivity(i);
		}
	});
	new AsyncTask<Void, Void,List>(){
		
		@Override
		protected List doInBackground(Void... params) {
			try {
				Intent i  =getIntent();
				String keyWord = i.getStringExtra("compath");
				 jsonuser="{\"key\":\""+keyWord+"\"}"; 
				 jsonemail= "{\"key\":\""+keyWord+"\"}";
				String userpath ="http://tvsrv.webhop.net:8080/api/search/users/name";
				String emailpath ="http://tvsrv.webhop.net:8080/api/search/users/email";
//				 JsonUtil js= new JsonUtil();
//				 followerinfo = js.getSource(path);
				SourceFromResponse sr = new SourceFromResponse();
				String usersource = sr.getSource(jsonuser,userpath );
				String emailsc = sr.getSource(jsonemail, emailpath);
				if("[]".equals(usersource)&"[]".equals(emailsc)){
					SearchPeopleActivity.this.finish();
					Toast.makeText(SearchPeopleActivity.this, "没有相应的话题", 1).show();
				}
				else if("[]".equals(usersource)&!"[]".equals(emailsc)){
				reemailList=getList(emailsc);
				userList =reemailList;
				}
				else if("[]".equals(emailsc)&!"[]".equals(usersource)){
					reuserList =getList(usersource);
					userList =reuserList;
				}else if(!"[]".equals(usersource)&!"[]".equals(emailsc)){
					userList = uniteList(reuserList, reemailList);
				}
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
			return userList;
		}
		@Override
		protected void onPreExecute() {
			showProgress(r1);
			super.onPreExecute();
		}
		@Override
		protected void onPostExecute(List result) {
			hideProgress(r1);
			if(result!= null){
			super.onPostExecute(result);
			//后台方法执行完后通知数据源变更
			myAdapter = new MyAdapter(SearchPeopleActivity.this,userList);
    			 myAdapter.notifyDataSetChanged();
    			 userlistview.setAdapter(myAdapter);
		}
			else {
				super.onPostExecute(result);
				Toast.makeText(SearchPeopleActivity.this, "sorry,你所搜索的用户不存在", 1).show();
				Intent  i =  new  Intent(SearchPeopleActivity.this,MySearchActivity.class);
				startActivity(i);
				finish();
			}
		}
		}.execute();
	
		
		ImageButton tv_reprogram_button;
		tv_reprogram_button=(ImageButton) findViewById(R.id.tv_reprogram_button);
		tv_reprogram_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(SearchPeopleActivity.this,MySearchActivity.class);
				startActivity(i);
				
			}
		});
	}
	public List<User> getList(String sc){
		try {
		if(sc!=null&!"[]".equals(sc)){
			JSONArray  js=  new JSONArray(sc);
		List  l =new ArrayList<User>();
			for (int i = 0; i < js.length(); i++) {
				 JSONObject  userjs=js.getJSONObject(i);
				 String imagepath = userjs.getString("image");
				 String username= userjs.getString("name");
				 int userid=userjs.getInt("id");
				 User user = new User();
				 user.setImage(imagepath);
				 user.setId(userid);
				 user.setName(username);
				 l.add(user);
}
			return l;
		 
		} else{
			return null;
		}
		}catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	public List uniteList(List l1,List l2){
		// 存放两者共同的数据
		List temp = new ArrayList<User>(l1);
		temp.retainAll(l2);
		l1.remove(temp);
		l2.remove(temp);
		List r3 = new ArrayList<User>();
		r3.addAll(l1);
		r3.addAll(l2);
		return r3;
	};
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

	 class MyAdapter extends BaseAdapter{
		   private LayoutInflater mInflater;
		   private List<User> userList;
			public MyAdapter(Context context,List<User> l){
				this.mInflater = LayoutInflater.from(context);
				this.userList=l;
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
			tv_following_follow.setText("following");
			final int followuserid =userList.get(position).getId();
			tv_following_follow.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					//tv_following_follow.setText("unFollowing");
					
					 String json="{\"id\":\""+id+"\",\"followingid\":\""+followuserid+"\"}";
						String url= "http://tvsrv.webhop.net:8080/api/follow";
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
								e.printStackTrace();
							}
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
