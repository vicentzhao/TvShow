package cn.rushmedia.jay.tvshow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;
import cn.rushmedia.jay.tvshow.MyPostActivity_1.MyAdapter;
import cn.rushmedia.jay.tvshow.MyPostActivity_1.ViewHolder;
import cn.rushmedia.jay.tvshow.domain.AppData;
import cn.rushmedia.jay.tvshow.domain.MyHomeLineDiscu;
import cn.rushmedia.jay.tvshow.domain.Program;
import cn.rushmedia.jay.tvshow.domain.Repost;
import cn.rushmedia.jay.tvshow.domain.Topic;
import cn.rushmedia.jay.tvshow.domain.User;
import cn.rushmedia.jay.tvshow.util.ImageCash;
import cn.rushmedia.jay.tvshow.util.ImageDownloder;
import cn.rushmedia.jay.tvshow.util.JsonUtil;
import cn.rushmedia.jay.tvshow.util.TimeDifference;

public class MyPostListActivity_1 extends BaseActivity {
	int upid;
	private AppData appData;
	ViewHolder holder ;
	private List<Program> mData;
	private JSONObject js;
	private int userid;
	private ListView listview;
	private String userImagePath;
	private String userName;
	private JSONObject jsProgram;
	private String filmImagePath;
	private String filmName;
	private String c;
	private String rtitle;
    private List<MyHomeLineDiscu> myHomeLineDiscList;
    private LinearLayout captchaLayout;
	private HashMap<String, Bitmap> mHardBitmapCache;
	private ImageCash  cash;
	private String logininfo;
	private List<Repost>  repostList;
	private String  repostcomment;
	private String  repostfilmName;
	private JSONObject repostjs;
	private JSONArray array;
	private int filmid;
	private  String repostUserImagePath;
	private  String repostFilmImagePath;
	private static final String TAG = "MainActivity";
	private MyAdapter adapter ;
	private int lastItem = 0;
	private ListView listView;
	private LinearLayout loadingLayout;
	//private int beginnum=0;
	boolean isloading = false;
	private Button tv_mytopic_previewpage;
	private Button tv_mytopic_nextpage;
	private String desc;
	/**     * 设置布局显示属性     */   
	private LayoutParams mLayoutParams = new LinearLayout.LayoutParams(      
			LinearLayout.LayoutParams.WRAP_CONTENT,   
			LinearLayout.LayoutParams.WRAP_CONTENT);  
	/**     * 设置布局显示目标最大化属性     */   
	private LayoutParams FFlayoutParams = new LinearLayout.LayoutParams(    
			LinearLayout.LayoutParams.FILL_PARENT,       
			LinearLayout.LayoutParams.FILL_PARENT);   
	//private ProgressBar progressBar;
	RelativeLayout rl;
	int page = 1;
	int count = 8;
	private ProgressBar progressBar;
	
		public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.subject_posts);
		tv_mytopic_previewpage=(Button) findViewById(R.id.tv_mytopic_previewpage);
		tv_mytopic_nextpage=(Button) findViewById(R.id.tv_mytopic_nextpage);
		 repostList = new ArrayList<Repost>();
		 LinearLayout layout = new LinearLayout(this);
		 listView = (ListView) this.findViewById(R.id.androidlist);
		 /**
		  * 设置监听事件，监听listview的改变
		  */
			listView.setOnScrollListener(new OnScrollListener() {
				 public int oldFirstVisibleItem;
				@Override
				public void onScrollStateChanged(AbsListView view, int scrollState) {

					System.out.println("scroll 状态改变");;
			//		if(scrollState==SCROLL_STATE_IDLE){
						System.out.println("最后一个 "+ view.getLastVisiblePosition());
						System.out.println("总数 "+ view.getCount());
listView.setSelection(lastItem - 1);//设置listview的当前位置，如果不设置每次加载完后都会返回到list的第一项。   
				}
				@Override
				public void onScroll(AbsListView view, int firstVisibleItem,
						int visibleItemCount, int totalItemCount) {
					   // TODO Auto-generated method stub
		            if(firstVisibleItem > this.oldFirstVisibleItem){
		            	 upid=firstVisibleItem - this.oldFirstVisibleItem;
		                    System.out.println("Scroll down!");
		                    System.out.println("firstVisibleItem"+"========"+firstVisibleItem);
		                    System.out.println("visibleItemCount"+"========"+visibleItemCount);
		                    System.out.println(totalItemCount);
		                    if(totalItemCount!=0){
								   if(firstVisibleItem + visibleItemCount == totalItemCount){
							   // listView.addFooterView(loadingLayout);
								tv_mytopic_nextpage.setVisibility(View.VISIBLE);
								tv_mytopic_nextpage.setOnClickListener(new OnClickListener() {
									
									@Override
									public void onClick(View v) {
										loadmoreItem();
										listView.removeFooterView(loadingLayout);
										adapter.notifyDataSetChanged();
									}
								});
								   }}
		            }
		            if(firstVisibleItem < this.oldFirstVisibleItem){
		                    System.out.println("Scroll up!");
		                    System.out.println("firstVisibleItem"+"========>>>>"+firstVisibleItem);
		                    System.out.println("visibleItemCount"+"========>>>>"+visibleItemCount);
		                    System.out.println("totalItemCount"+"======>>"+totalItemCount);
		                    if(firstVisibleItem==0){
		                    if(totalItemCount!=0){
								 
							tv_mytopic_previewpage.setVisibility(View.VISIBLE);
							tv_mytopic_previewpage.setOnClickListener(new OnClickListener() {
								
								@Override
								public void onClick(View v) {
									loadlessItem();
									adapter.notifyDataSetChanged();
								}
							});
						}
		            }
		            }
	            this.oldFirstVisibleItem = firstVisibleItem;

					lastItem = firstVisibleItem + visibleItemCount - 1;
				System.out.println("lastItem:" + lastItem);
					Log.i(TAG, "lastItem:" + lastItem);
					}
			});
			rl = (RelativeLayout) this.findViewById(R.id.loading);
			Intent i = getIntent();
			final MyHomeLineDiscu home=(MyHomeLineDiscu) i.getSerializableExtra("topic");
			 userid = home.getTopic().getUser().getId();
			intiData(page,count);
			AppData  appData =(AppData) getApplication();
			appData.addActivity(this);
			listView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					System.out.println("==========>>>>>>我已被执行");
					Intent intent = new Intent(MyPostListActivity_1.this,PostsDetialActivity.class);
					Log.i("system", "触发点击事件");
					MyHomeLineDiscu myHomeLineDiscu =myHomeLineDiscList.get(position);
					intent.putExtra("saydetial",myHomeLineDiscu);
					startActivity(intent);
					
				}
			});
			ImageButton tv_reprogram_button =(ImageButton) findViewById(R.id.tv_reprogram_button);
			tv_reprogram_button.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent i = new Intent(getApplicationContext(),UserDetailActivity.class);
					i.putExtra("userinfo", home.getTopic().getUser());
					startActivity(i);
				}
			});
			ImageButton backtohome =(ImageButton) findViewById(R.id.backtohome);
			backtohome.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent i = new Intent(getApplicationContext(),TableActivity.class);
					startActivity(i);
				}
			});
	}
//		/**
//		 * 捕捉回退键
//		 */
//		public boolean onKeyDown(int keyCode, KeyEvent event) {
//			if(keyCode==KeyEvent.KEYCODE_BACK && event.getRepeatCount()==0){
//				showTips();
//			return false;
//			}
//			return false;
//			}
		/**
		 * 为adapter初始化数据
		 * @param page  页数
		 * @param count  每页显示的个数
		 */
	private void intiData(final int page,final int count) {
		
		myHomeLineDiscList =new ArrayList<MyHomeLineDiscu>();
		isloading = true;
		new AsyncTask<Void, Void, JSONArray>(){
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				// 显示进度条
				showProgress(rl);
				super.onPreExecute();
			}
			protected void onPostExecute(JSONArray result) {
				// TODO Auto-generated method stub
				// 隐藏进度条
				isloading = false;
				hideProgress(rl);
				tv_mytopic_previewpage.setVisibility(View.GONE);
				 tv_mytopic_nextpage.setVisibility(View.GONE);
				if (result !=null) {
					 adapter = new MyAdapter(MyPostListActivity_1.this,result);
						listView.setAdapter(adapter);
				}else{
					Toast.makeText(getApplicationContext(), "没有相关的信息", 1)
					.show();
				}
				super.onPostExecute(result);
			}
			@Override
			protected JSONArray doInBackground(Void... params) {
				try {
					 
						
					//	String path ="http://tvsrv.webhop.net:8080/api/users/"+userid+"/homeline";
					//http://tvsrv.webhop.net:8080/api/users/1/posts
						String path ="http://tvsrv.webhop.net:8080/api/users/"+userid+"/posts?page="+page+"&count="+count+"";
						JsonUtil jsut = new JsonUtil();
						 array = jsut.getSource(path);
						 if(array!=null){
					 for (int i = 0; i < array.length(); i++) {
						 js =array.getJSONObject(i);
						  JSONObject jsUser=js.getJSONObject("user");
						   userImagePath=jsUser.getString("image");
						   userName=jsUser.getString("name");
						   User user = new User();
						   user.setImage(userImagePath);
						   user.setName(userName);
						   JSONObject jsTopic=js.getJSONObject("topic");
						   jsProgram =jsTopic.getJSONObject("program");
						   filmImagePath=jsProgram.getString("image");
						   desc =jsProgram.getString("description");
						   if(!jsProgram.isNull("title")){
						   filmName=jsProgram.getString("title");
		//				   filmid=jsProgram.getInt("id");
						   }else{
							   filmName="极品电影";
						   }
						   Program program =new Program();
						   program.setImagePath(filmImagePath);
						   program.setTitle(filmName);
						   program.setDescription(desc);
//						   program.setId(filmid);
						   if(!js.isNull("c")){
							   c =js.getString("c");
						   }else{
							   c="未知的";
						   }
						   int u =js.getInt("u");
						   int p=js.getInt("p");
						   int t=js.getInt("t");
						   rtitle=jsTopic.getString("name");
						  int programid=jsTopic.getInt("programid");
						   Topic topic = new Topic();
						   topic.setTopic_name(rtitle);
						   topic.setProgramid(programid);
						   topic.setProgram(program);
						   MyHomeLineDiscu myHomeLineDisc = new MyHomeLineDiscu();
						   MyHomeLineDiscu repostmyHomeLineDiscu =  new MyHomeLineDiscu();
						   long datelong = js.getLong("ct");
						   myHomeLineDisc.setU(u);
						   myHomeLineDisc.setT(t);
						   myHomeLineDisc.setP(p);
						   myHomeLineDisc.setC(c);
						   myHomeLineDisc.setTopic(topic);
						   myHomeLineDisc.setCreated_at(datelong);
						   myHomeLineDisc.setUser(user);
						   myHomeLineDisc.setTopic(topic);
						   myHomeLineDiscList.add(myHomeLineDisc);
					 }
					 return array;
						 
						 }else{
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
	public final class ViewHolder{
		public ImageView tv_homeline_userimage;
		public TextView tv_homeline_username;
		public TextView tv_homeline_comment;
		public  ImageView tv_homeline_filmimage;
		public TextView tv_homeline_filmname;
		public TextView tv_homeline_title;
		public TextView tv_homeline_currenttime;
		public Button tv_homeline_topic_turnother;
		public ImageView tv_homeline_repost_userimage;
		public TextView tv_homeline_repost_username;
		public TextView  tv_homeline_repost_comment;
		public ImageView tv_homeline_repost_filmimage;
		public TextView tv_homeline_repost_filmname;
		public TextView tv_homeline_repost_title;
		public TextView tv_homeline_repost_currenttime;
	}
	 /**
	  * 自定义adapter
	  * @author Administrator
	  *
	  */
	public class MyAdapter extends BaseAdapter{
		
		private LayoutInflater mInflater;
		private JSONArray array_list;
		public MyAdapter(Context context,JSONArray array){
			this.mInflater = LayoutInflater.from(context);
			this.array_list=array;
		}
		@Override
		public int getCount() {
			return myHomeLineDiscList.size();
		}
		public Object getItem(int arg0) {
			return null;
		}
		@Override
		public long getItemId(int arg0) {
			return 0;
		}
		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			holder=new ViewHolder();  
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.mytopic_1, null);
				holder.tv_homeline_userimage = (ImageView)convertView.findViewById(R.id.tv_homeline_userimage);
				holder.tv_homeline_username = (TextView)convertView.findViewById(R.id.tv_homeline_username);
				holder.tv_homeline_comment = (TextView)convertView.findViewById(R.id.tv_homeline_comment);
				holder.tv_homeline_filmimage=(ImageView)convertView.findViewById(R.id.tv_homeline_filmimage);
				holder.tv_homeline_filmname =(TextView) convertView.findViewById(R.id.tv_homeline_filmname);
				holder.tv_homeline_title=(TextView)convertView.findViewById(R.id.tv_homeline_title);
				holder.tv_homeline_currenttime =(TextView)convertView.findViewById(R.id.tv_homeline_currenttime);
				holder.tv_homeline_repost_comment=(TextView) convertView.findViewById(R.id.tv_homeline_repost_comment);
				holder.tv_homeline_repost_currenttime=(TextView) convertView.findViewById(R.id.tv_homeline_repost_currenttime);
				holder.tv_homeline_repost_filmimage =(ImageView) convertView.findViewById(R.id.tv_homeline_repost_filmimage);
				holder.tv_homeline_repost_filmname=(TextView) convertView.findViewById(R.id.tv_homeline_repost_filmname);
				holder.tv_homeline_repost_title =(TextView) convertView.findViewById(R.id.tv_homeline_repost_title);
				holder.tv_homeline_repost_userimage =(ImageView) convertView.findViewById(R.id.tv_homeline_repost_userimage);
				holder.tv_homeline_repost_username=(TextView) convertView.findViewById(R.id.tv_homeline_repost_username);
				holder.tv_homeline_topic_turnother=(Button) convertView.findViewById(R.id.tv_homeline_topic_turnother);
				convertView.setTag(holder);
			}else {
				holder = (ViewHolder)convertView.getTag();
			}try {
				if(!( array_list.getJSONObject(position)).isNull("repost")){
					captchaLayout = (LinearLayout) convertView.findViewById(R.id.tv_homeline_repost_main);
					captchaLayout.setVisibility(View.VISIBLE);
					  repostjs=array_list.getJSONObject(position).getJSONObject("repost");
				      JSONObject repostjsUser=repostjs.getJSONObject("user");
				     repostUserImagePath =repostjsUser.getString("image");
					   String repostUserName=repostjsUser.getString("name");
				       JSONObject repostjsTopic=repostjs.getJSONObject("topic");
					   JSONObject repostjsProgram =repostjsTopic.getJSONObject("program");
					   repostFilmImagePath=repostjsProgram.getString("image");
					   if(!repostjsProgram.isNull("title")){
					   repostfilmName=repostjsProgram.getString("title");
					   }else{
					   filmName="极品电影";
					   }
					   if(!repostjs.isNull("c")){
						   repostcomment =repostjs.getString("c");
					   }else{
						   repostcomment="未知的";
					   }
					   String repostrtitle=repostjsTopic.getString("name");
					   long repostdatelong= repostjs.getLong("ct");
					appData =(AppData) getApplication(); 
					cash = new ImageCash();
					Bitmap bitmapFromCache = cash.getBitmapFromCache(repostUserImagePath);
					if(bitmapFromCache==null){
						new AsyncTask<Void, Void, Bitmap>(){
							@Override
							protected void onPostExecute(Bitmap result) {
								holder.tv_homeline_repost_userimage.setImageBitmap(result);
								super.onPostExecute(result);
							}
							@Override
							protected Bitmap doInBackground(Void... params) {
								try {
									ImageDownloder imageDownloder = new ImageDownloder();
									
									Bitmap repostuserimage = imageDownloder.imageDownloder(repostUserImagePath);
									 mHardBitmapCache = appData.getmHardBitmapCache();
									mHardBitmapCache.put(repostUserImagePath, repostuserimage);
								appData.setmHardBitmapCache(mHardBitmapCache);
									return repostuserimage;
								} catch (Exception e) {
									e.printStackTrace();
									return null;
								}	
								
							}
						}.execute();
					}else{
						holder.tv_homeline_repost_userimage.setImageBitmap(bitmapFromCache);
					}
					  Bitmap bitmapforcash2=cash.getBitmapFromCache(repostFilmImagePath);
				  if(bitmapforcash2==null){
						new AsyncTask<Void, Void, Bitmap>(){
							@Override
							protected void onPostExecute(Bitmap result) {
								if("".equals(result)){
								
								holder.tv_homeline_repost_filmimage.setImageResource(R.drawable.icon);
								}else{
									holder.tv_homeline_repost_filmimage.setImageBitmap(result);
								}
								super.onPostExecute(result);
								
							}

							@Override
							protected Bitmap doInBackground(Void... params) {
								try {
									ImageDownloder imageDownloder = new ImageDownloder();
									if(!"".equals(repostFilmImagePath)){
									Bitmap repostfilmImage = imageDownloder.imageDownloder(repostFilmImagePath);
								 mHardBitmapCache = appData.getmHardBitmapCache();
				             mHardBitmapCache.put(repostFilmImagePath, repostfilmImage);
								return repostfilmImage;
									}else{
										return null;
									}
								} catch (Exception e) {
									e.printStackTrace();
									return null;
								}	
							}
						}.execute();
					  }else{
						  holder.tv_homeline_repost_filmimage.setImageBitmap(bitmapforcash2);
				  }
						holder.tv_homeline_repost_username.setText(repostUserName);
						holder.tv_homeline_repost_comment.setText(repostcomment);
						holder.tv_homeline_repost_filmname.setText(repostfilmName);
						holder.tv_homeline_repost_title.setText(repostrtitle);						
						TimeDifference timeDifference = new TimeDifference();
						try {
							String timeDiffence = timeDifference.getTimeDiffence(repostdatelong);
							holder.tv_homeline_repost_currenttime.setText(timeDiffence);
						} catch (Exception e) {
							e.printStackTrace();
						}
				}
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			appData =(AppData) getApplication(); 
			cash = new ImageCash();
			Bitmap bitmapFromCache3 = cash.getBitmapFromCache(myHomeLineDiscList.get(position).getUser().getImage());
			if(bitmapFromCache3==null){
				new AsyncTask<Void, Void, Bitmap>(){
					@Override
					protected void onPostExecute(Bitmap result) {
						holder.tv_homeline_userimage.setImageBitmap(result);
						super.onPostExecute(result);
					}
					@Override
					protected Bitmap doInBackground(Void... params) {
						try {
							ImageDownloder imageDownloder = new ImageDownloder();
							String imagePath=myHomeLineDiscList.get(position).getUser().getImage();
							Bitmap userimage = imageDownloder.imageDownloder(imagePath);
							 mHardBitmapCache = appData.getmHardBitmapCache();
							mHardBitmapCache.put(imagePath, userimage);
							appData.setmHardBitmapCache(mHardBitmapCache);
							return userimage;
						} catch (Exception e) {
							e.printStackTrace();
							return null;
						}	
						
					}
				}.execute();
			}else{
				holder.tv_homeline_userimage.setImageBitmap(bitmapFromCache3);
			}
			  Bitmap bitmapforcash4=cash.getBitmapFromCache(myHomeLineDiscList.get(position).getTopic().getProgram().getImagePath());
			  if(bitmapforcash4==null){
				new AsyncTask<Void, Void, Bitmap>(){
					@Override
					protected void onPostExecute(Bitmap result) {
						if("".equals(result)){
						
						holder.tv_homeline_filmimage.setImageResource(R.drawable.icon);
						}else{
							holder.tv_homeline_filmimage.setImageBitmap(result);
						}
						super.onPostExecute(result);
						
					}

					@Override
					protected Bitmap doInBackground(Void... params) {
						try {
							ImageDownloder imageDownloder = new ImageDownloder();
							String filmpath=myHomeLineDiscList.get(position).getTopic().getProgram().getImagePath();
							if(!"".equals("path")){
							Bitmap filmimage = imageDownloder.imageDownloder(filmpath);
							 mHardBitmapCache = appData.getmHardBitmapCache();
							mHardBitmapCache.put(filmpath, filmimage);
							return filmimage;
							}else{
								return null;
							}
						} catch (Exception e) {
							e.printStackTrace();
							return null;
						}	
					}
				}.execute();
			  }else{
				  holder.tv_homeline_filmimage.setImageBitmap(bitmapforcash4);
			  }
				holder.tv_homeline_username.setText(myHomeLineDiscList.get(position).getUser().getName());
				holder.tv_homeline_comment.setText(myHomeLineDiscList.get(position).getC());
				holder.tv_homeline_filmname.setText(myHomeLineDiscList.get(position).getTopic().getProgram().getTitle());
				holder.tv_homeline_title.setText(myHomeLineDiscList.get(position).getTopic().getTopic_name());
				long create_time=myHomeLineDiscList.get(position).getCreated_at();
				TimeDifference timeDifference = new TimeDifference();
				try {
					String timeDiffence = timeDifference.getTimeDiffence(create_time);
					holder.tv_homeline_currenttime.setText(timeDiffence);
				} catch (Exception e) {
					e.printStackTrace();
				}
			return convertView;
		}
	}

	private void loadmoreItem() {
		page = page + 1;
		count = 8;
       intiData(page, count);

		}
	private void loadlessItem() {
		
		page = page - 1;
		if(page<=0){
		 count = 8;
		 page=1;
		 intiData(page, count);
		}
	}
	
}
