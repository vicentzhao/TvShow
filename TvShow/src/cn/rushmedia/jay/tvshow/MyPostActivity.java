package cn.rushmedia.jay.tvshow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.rushmedia.jay.tvshow.domain.AppData;
import cn.rushmedia.jay.tvshow.domain.Post;
import cn.rushmedia.jay.tvshow.domain.Repost;
import cn.rushmedia.jay.tvshow.domain.Topic;
import cn.rushmedia.jay.tvshow.util.ImageCash;
import cn.rushmedia.jay.tvshow.util.ImageDownloder;
import cn.rushmedia.jay.tvshow.util.ImageFileCache;
import cn.rushmedia.jay.tvshow.util.JSONObject2Post;
import cn.rushmedia.jay.tvshow.util.JsonUtil;
import cn.rushmedia.jay.tvshow.util.TimeDifference;
public class MyPostActivity extends BaseActivity implements OnClickListener {
	private AppData appData;
	private ViewHolder holder;
	private int userid;
	private List<Post> postList;
	private HashMap<String, Bitmap> mHardBitmapCache;
	private ImageCash cash;
	private String logininfo;
	private List<Repost> repostList;
	private String repostcomment;
	private String repostfilmName;
	private JSONObject repostjs;
	private JSONArray array;
	private int filmid;
	private String repostUserImagePath;
	private String repostFilmImagePath;
	private static final String TAG = "MainActivity";
	private MyAdapter adapter;
	private int lastItem = 0;
	private ListView listView;
	private LinearLayout loadingLayout;
	boolean isloading = false;
	private Button tv_mytopic_previewpage;
	private Button tv_mytopic_nextpage;
	private String desc;
	private ImageFileCache cache;
	private RelativeLayout rl;
	private String path;
	int page = 1;
	int count = 10;
	private LinearLayout mSubjectFooter;
	private boolean isFirstLoading = true;
	private ArrayList<Post> proPost;
	private boolean isPostProgram=false;
	private boolean isAllTopic=false;
	private int programid;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.subject);
		initView();
		proPost = new ArrayList<Post>();
		Intent it =getIntent();
		Post post =(Post)it.getSerializableExtra("saydetial");
		Post allTopic = (Post) it.getSerializableExtra("topic");
		if(post!=null){
			isPostProgram=true;
		 programid =post.getTopic().getProgram().getId();
	       path ="http://tvsrv.webhop.net:8080/api/programs/"+programid+"/posts?page="+page+"&count="+count+"";
		}else if(allTopic!=null){
			userid=allTopic.getTopic().getUser().getId();
			isAllTopic =true;
			path ="http://tvsrv.webhop.net:8080/api/users/"+userid+"/posts?page="+page+"&count="+count+"";
		}
		else{
			try {
				appData = (AppData) getApplication();
				logininfo = appData.getLoginInfo();
				JSONObject jsUserInfo = new JSONObject(logininfo);
				userid = jsUserInfo.getInt("id");
               path = "http://tvsrv.webhop.net:8080/api/users/"
					+ userid + "/homeline?page=" + page + "&count="
					+ count + "";
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		tv_mytopic_previewpage.setOnClickListener(this);
		tv_mytopic_nextpage.setOnClickListener(this);
		intiData(path);
		/**
		 * 设置监听事件，监听listview的改变
		 */
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(MyPostActivity.this,
						PostsDetialActivity.class);
				Post post = postList
						.get(position);
				intent.putExtra("saydetial", post);
				startActivity(intent);
			}
		});
	}

//	/**
//	 * 捕捉回退键
//	 */
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//         if(keyCode == KeyEvent.KEYCODE_BACK){
//        	 showTips();
//         }
//         return false;
//    }

	/**
	 * 为adapter初始化数据
	 * 
	 * @param page
	 *            页数
	 * @param count
	 *            每页显示的个数
	 */
	private void intiData(final String initPath) {
		postList = new ArrayList<Post>();
		isloading = true;
		new AsyncTask<Void, Void, JSONArray>() {

			protected void onPreExecute() {
				showProgress(rl);
				super.onPreExecute();
			}
			protected void onPostExecute(JSONArray result) {
				isloading = false;
				hideProgress(rl);
				if (result != null) {
					if (isFirstLoading) {
						adapter = new MyAdapter(MyPostActivity.this);
						mSubjectFooter.setVisibility(View.VISIBLE);
						listView.setAdapter(adapter);
						isFirstLoading = false;
					} else {
						mSubjectFooter.setVisibility(View.VISIBLE);
						adapter.setData(result);
						adapter.notifyDataSetChanged();
					}
				} else {
					Toast.makeText(getApplicationContext(), "获取用户数据失败", 1)
							.show();
				}
				super.onPostExecute(result);
			}
			@Override
			protected JSONArray doInBackground(Void... params) {
				try {
					JSONObject jsPost ;
					JsonUtil jsut = new JsonUtil();
					array = jsut.getSource(initPath);
					if (array != null) {
						for (int i = 0; i < array.length(); i++) {
							jsPost = array.getJSONObject(i);
							JSONObject2Post jp = new JSONObject2Post();
							Post post = jp.getPost(jsPost);
							postList.add(post);
						}
						return array;

					} else {
						return null;
					}
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			}

		}.execute();

	}

	/**
	 * 初始化控件
	 */
	public final class ViewHolder {
		public ImageView tv_homeline_userimage;
		public TextView tv_homeline_username;
		public TextView tv_homeline_comment;
		public ImageView tv_homeline_filmimage;
		public TextView tv_homeline_filmname;
		public TextView tv_homeline_title;
		public TextView tv_homeline_currenttime;
		public Button tv_homeline_topic_turnother;
		public ImageView tv_homeline_repost_userimage;
		public TextView tv_homeline_repost_username;
		public TextView tv_homeline_repost_comment;
		public ImageView tv_homeline_repost_filmimage;
		public TextView tv_homeline_repost_filmname;
		public TextView tv_homeline_repost_title;
		public TextView tv_homeline_repost_currenttime;
	}

	/**
	 * 自定义adapter
	 * 
	 * @author Administrator
	 * 
	 */
	public class MyAdapter extends BaseAdapter {

		private Context mContext;
		private LayoutInflater mInflater;
		private JSONArray array_list;

		public MyAdapter(Context context) {
			this.mContext = context;
			mInflater =LayoutInflater.from(context);
		}

		public void setData(JSONArray jArr) {
			this.array_list = jArr;
		}

		public JSONArray getData() {
			return this.array_list;
		}


		@Override
		public int getCount() {
			return postList.size();
		}

		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			holder = new ViewHolder();
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.mytopic_1, null);
				holder.tv_homeline_userimage = (ImageView) convertView
						.findViewById(R.id.tv_homeline_userimage);
				holder.tv_homeline_username = (TextView) convertView
						.findViewById(R.id.tv_homeline_username);
				holder.tv_homeline_comment = (TextView) convertView
						.findViewById(R.id.tv_homeline_comment);
				holder.tv_homeline_filmimage = (ImageView) convertView
						.findViewById(R.id.tv_homeline_filmimage);
				holder.tv_homeline_filmname = (TextView) convertView
						.findViewById(R.id.tv_homeline_filmname);
				holder.tv_homeline_title = (TextView) convertView
						.findViewById(R.id.tv_homeline_title);
				holder.tv_homeline_currenttime = (TextView) convertView
						.findViewById(R.id.tv_homeline_currenttime);
				holder.tv_homeline_repost_comment = (TextView) convertView
						.findViewById(R.id.tv_homeline_repost_comment);
				holder.tv_homeline_repost_currenttime = (TextView) convertView
						.findViewById(R.id.tv_homeline_repost_currenttime);
				holder.tv_homeline_repost_filmimage = (ImageView) convertView
						.findViewById(R.id.tv_homeline_repost_filmimage);
				holder.tv_homeline_repost_filmname = (TextView) convertView
						.findViewById(R.id.tv_homeline_repost_filmname);
				holder.tv_homeline_repost_title = (TextView) convertView
						.findViewById(R.id.tv_homeline_repost_title);
				holder.tv_homeline_repost_userimage = (ImageView) convertView
						.findViewById(R.id.tv_homeline_repost_userimage);
				holder.tv_homeline_repost_username = (TextView) convertView
						.findViewById(R.id.tv_homeline_repost_username);
				holder.tv_homeline_topic_turnother = (Button) convertView
						.findViewById(R.id.tv_homeline_topic_turnother);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
//			try {
//				if (!(array_list.getJSONObject(position)).isNull("repost")) {
//					captchaLayout = (LinearLayout) convertView
//							.findViewById(R.id.tv_homeline_repost_main);
//					captchaLayout.setVisibility(View.VISIBLE);
//					repostjs = array_list.getJSONObject(position)
//							.getJSONObject("repost");
//					JSONObject repostjsUser = repostjs.getJSONObject("user");
//					repostUserImagePath = repostjsUser.getString("image");
//					String repostUserName = repostjsUser.getString("name");
//					JSONObject repostjsTopic = repostjs.getJSONObject("topic");
//					JSONObject repostjsProgram = repostjsTopic
//							.getJSONObject("program");
//					repostFilmImagePath = repostjsProgram.getString("image");
//					if (!repostjsProgram.isNull("title")) {
//						repostfilmName = repostjsProgram.getString("title");
//					} else {
//						filmName = "极品电影";
//					}
//					if (!repostjs.isNull("c")) {
//						repostcomment = repostjs.getString("c");
//					} else {
//						repostcomment = "未知的";
//					}
//					String repostrtitle = repostjsTopic.getString("name");
//					long repostdatelong = repostjs.getLong("ct");
//					 cache= ImageFileCache.getCashInstance();
//					Bitmap UserImageCache = cache.getImage(repostUserImagePath);
//					if (UserImageCache == null) {
//						new AsyncTask<Void, Void, Bitmap>() {
//							@Override
//							protected void onPostExecute(Bitmap result) {
//								if(result!=null){
//								holder.tv_homeline_repost_userimage
//										.setImageBitmap(result);
//								cache.saveBmpToSd(result, repostUserImagePath);
//								}
//								super.onPostExecute(result);
//							}
//
//							@Override
//							protected Bitmap doInBackground(Void... params) {
//								try {
//									ImageDownloder imageDownloder = new ImageDownloder();
//									Bitmap repostuserimage = imageDownloder
//											.imageDownloder(repostUserImagePath);
//									mHardBitmapCache = appData
//											.getmHardBitmapCache();
//									mHardBitmapCache.put(repostUserImagePath,
//											repostuserimage);
//									appData.setmHardBitmapCache(mHardBitmapCache);
//									return repostuserimage;
//								} catch (Exception e) {
//									e.printStackTrace();
//									return null;
//								}
//
//							}
//						}.execute();
//					} else {
//						holder.tv_homeline_repost_userimage
//								.setImageBitmap(UserImageCache);
//					}
//					
//					Bitmap FileImageCache = cache.getImage(repostFilmImagePath);
//					if (FileImageCache == null) {
//						new AsyncTask<Void, Void, Bitmap>() {
//							@Override
//							protected void onPostExecute(Bitmap result) {
//								if ("".equals(result)) {
//									holder.tv_homeline_repost_filmimage
//											.setImageResource(R.drawable.icon);
//								} else {
//									holder.tv_homeline_repost_filmimage
//											.setImageBitmap(result);
//									cache.saveBmpToSd(result, repostFilmImagePath);
//								}
//								super.onPostExecute(result);
//							}
//							@Override
//							protected Bitmap doInBackground(Void... params) {
//								try {
//									ImageDownloder imageDownloder = new ImageDownloder();
//									if (!"".equals(repostFilmImagePath)) {
//										Bitmap repostfilmImage = imageDownloder
//												.imageDownloder(repostFilmImagePath);
//										mHardBitmapCache = appData
//												.getmHardBitmapCache();
//										mHardBitmapCache.put(
//												repostFilmImagePath,
//												repostfilmImage);
//										return repostfilmImage;
//									} else {
//										return null;
//									}
//								} catch (Exception e) {
//									e.printStackTrace();
//									return null;
//								}
//							}
//						}.execute();
//					} else {
//						holder.tv_homeline_repost_filmimage
//								.setImageBitmap(FileImageCache);
//					}
//					holder.tv_homeline_repost_username.setText(repostUserName);
//					holder.tv_homeline_repost_comment.setText(repostcomment);
//					holder.tv_homeline_repost_filmname.setText(repostfilmName);
//					holder.tv_homeline_repost_title.setText(repostrtitle);
//					TimeDifference timeDifference = new TimeDifference();
//					try {
//						String timeDiffence = timeDifference
//								.getTimeDiffence(repostdatelong);
//						holder.tv_homeline_repost_currenttime
//								.setText(timeDiffence);
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//			} catch (JSONException e1) {
//				e1.printStackTrace();
//			}
			appData = (AppData) getApplication();
			cash = new ImageCash();
			Bitmap bitmapFromCache3 = cash
					.getBitmapFromCache(postList.get(position)
							.getUser().getImage());
			cache = ImageFileCache.getCashInstance();
			final String userImagePath =postList.get(position).getUser().getImage();
			Bitmap userImaga = cache.getImage(userImagePath);
			if (userImaga == null) {
				new AsyncTask<Void, Void, Bitmap>() {
					@Override
					protected void onPostExecute(Bitmap result) {
						if(result!=null){
							cache.saveBmpToSd(result, userImagePath);
						holder.tv_homeline_userimage.setImageBitmap(result);
						}else{
						 Bitmap bit =BitmapFactory.decodeResource(getResources(), R.drawable.app_icon);
						 cache.saveBmpToSd(bit, userImagePath);
							holder.tv_homeline_userimage.setImageBitmap(bit);
						}
						super.onPostExecute(result);
					}

					@Override
					protected Bitmap doInBackground(Void... params) {
						try {
							ImageDownloder imageDownloder = new ImageDownloder();
							
							Bitmap userimage = imageDownloder
									.imageDownloder(userImagePath);
							return userimage;
						} catch (Exception e) {
							e.printStackTrace();
							return null;
						}

					}
				}.execute();
			} else {
				holder.tv_homeline_userimage.setImageBitmap(userImaga);
			}
			final String programPath =postList
			.get(position).getTopic().getProgram().getImagePath();
			Bitmap imageProgram = cache.getImage(programPath);
			if (imageProgram == null) {
				new AsyncTask<Void, Void, Bitmap>() {
					@Override
					protected void onPostExecute(Bitmap result) {
						if (result==null) {

							holder.tv_homeline_filmimage
									.setImageResource(R.drawable.icon);
						} else {
							holder.tv_homeline_filmimage.setImageBitmap(result);
							cache.saveBmpToSd(result, programPath);
						}
						super.onPostExecute(result);

					}
					@Override
					protected Bitmap doInBackground(Void... params) {
						try {
							ImageDownloder imageDownloder = new ImageDownloder();
						
								Bitmap filmimage = imageDownloder
										.imageDownloder(programPath);
								return filmimage;
						} catch (Exception e) {
							e.printStackTrace();
							return null;
						}
					}
				}.execute();
			} else {
				holder.tv_homeline_filmimage.setImageBitmap(imageProgram);
			}
			holder.tv_homeline_username.setText(postList
					.get(position).getUser().getName());
			holder.tv_homeline_comment.setText(postList.get(position)
					.getC());
			holder.tv_homeline_filmname.setText(postList
					.get(position).getTopic().getProgram().getTitle());
			holder.tv_homeline_title.setText(postList.get(position)
					.getTopic().getTopic_name());
			long create_time = postList.get(position).getCt();
			TimeDifference timeDifference = new TimeDifference();
			try {
				String timeDiffence = timeDifference
						.getTimeDiffence(create_time);
				holder.tv_homeline_currenttime.setText(timeDiffence);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return convertView;
		}
	}

	private void loadmoreItem() {
		page = page + 1;
		count = 10;
		if(isPostProgram){
			String postProgramPath ="http://tvsrv.webhop.net:8080/api/programs/"+programid+"/posts?page="+page+"&count="+count+"";
			intiData(postProgramPath);
		}else if(isAllTopic){
			String topicPath ="http://tvsrv.webhop.net:8080/api/users/"+userid+"/posts?page="+page+"&count="+count+"";
			intiData(topicPath);
		}
		else {
			String homePath ="http://tvsrv.webhop.net:8080/api/users/"
					+ userid + "/homeline?page=" + page + "&count="
					+ count + "";
			intiData(homePath);
		}
	}
	private void loadlessItem() {

		page = page - 1;
		if (page <= 0) {
			count = 10;
			page = 1;
			mSubjectFooter.setVisibility(View.VISIBLE);
			Toast.makeText(this, "已经到达首页", Toast.LENGTH_SHORT).show();
		} 
		else if(isAllTopic){
			String topicPath ="http://tvsrv.webhop.net:8080/api/users/"+userid+"/posts?page="+page+"&count="+count+"";
			intiData(topicPath);
		}
		else {
			if(isPostProgram){
				String postProgramPath ="http://tvsrv.webhop.net:8080/api/programs/"+programid+"/posts?page="+page+"&count="+count+"";
				intiData(postProgramPath);
			}else{
				String homePath ="http://tvsrv.webhop.net:8080/api/users/"
						+ userid + "/homeline?page=" + page + "&count="
						+ count + "";
				intiData(homePath);
			}
		}
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_mytopic_previewpage:
			mSubjectFooter.setVisibility(View.GONE);
			loadlessItem();
			break;
		case R.id.tv_mytopic_nextpage:
			mSubjectFooter.setVisibility(View.GONE);
			loadmoreItem();
			break;
		}
	}
	void initView(){
		listView = (ListView) this.findViewById(R.id.androidlist);
		LayoutInflater inflater = LayoutInflater.from(this);
		mSubjectFooter = (LinearLayout) inflater.inflate(
				R.layout.subject_footer, null);
		tv_mytopic_previewpage = (Button) mSubjectFooter
				.findViewById(R.id.tv_mytopic_previewpage);
		tv_mytopic_nextpage = (Button) mSubjectFooter
				.findViewById(R.id.tv_mytopic_nextpage);
		listView.addFooterView(mSubjectFooter);
		rl = (RelativeLayout) this.findViewById(R.id.loading);
	}
}
