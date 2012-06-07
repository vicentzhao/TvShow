package cn.rushmedia.jay.tvshow;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.rushmedia.jay.tvshow.domain.AppData;
import cn.rushmedia.jay.tvshow.domain.User;
import cn.rushmedia.jay.tvshow.util.ImageDownloder;
import cn.rushmedia.jay.tvshow.util.JsonUtil;

public class HotPeopleActivity extends BaseActivity {
	private ImageView tv_follower_image;
	private TextView tv_follower_name;
	private List<User> userList;
	private ListView listview;
	private String path;
	 MyAdapter myAdapter;
	String loginInfo;
	RelativeLayout rl;
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.subject_1_people);
	userList = new ArrayList<User>();
	tv_follower_image =(ImageView) findViewById(R.id.tv_follower_image);
	tv_follower_name=(TextView) findViewById(R.id.tv_follower_name);
	 AppData appl = (AppData)getApplication();
	 appl.addActivity(this);
	 loginInfo= appl.getLoginInfo();
	 rl=(RelativeLayout) findViewById(R.id.loading);
	 Intent i = getIntent();
	  path = i.getStringExtra("path");
	 listview = (ListView) findViewById(R.id.subjectlist);
	 new AsyncTask<Void, Void, List>(){
		@Override
		protected void onPreExecute() {
			showProgress(rl);
			super.onPreExecute();
		}
		@Override
		protected void onPostExecute(List result) {
			hideProgress(rl);
			super.onPostExecute(result);
			myAdapter = new MyAdapter(HotPeopleActivity.this);
			  listview.setAdapter(myAdapter);
			myAdapter.notifyDataSetChanged();
		}
		@Override
		protected List doInBackground(Void... params) {
			try {
				 JsonUtil js= new JsonUtil();
				 JSONArray followerinfo = js.getSource(path);
				 for (int i = 0; i < followerinfo.length(); i++) {
					 JSONObject  userjs=followerinfo.getJSONObject(i);
					 String imagepath = userjs.getString("image");
					 String username= userjs.getString("name");
					 User user = new User();
					 user.setImage(imagepath);
					 user.setName(username);
					 userList.add(user);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return userList;
		}
		 
	 }.execute();
	ImageButton tv_reprogram_button = 
		(ImageButton) findViewById(R.id.tv_reprogram_button);
	tv_reprogram_button.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent i = new Intent(HotPeopleActivity.this,MySearchActivity.class);
			startActivity(i);
		}
	});
//	listview.setOnItemClickListener(new OnItemClickListener() {
//
//		@Override
//		public void onItemClick(AdapterView<?> parent, View view, int position,
//				long id) {
//		Intent i = new Intent(HotPeopleActivity.this,UserDetailActivity.class);
//		i.putExtra("userinfo", userList.get(position));
//		startActivity(i);
//		}
//	});
	ImageButton backtohome = (ImageButton) findViewById(R.id.backtohome);
	backtohome.setOnClickListener(new OnClickListener() {
		public void onClick(View v) {
			Intent i = new Intent(getApplicationContext(),TableActivity.class);
			startActivity(i);
		}
	});
	}

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
