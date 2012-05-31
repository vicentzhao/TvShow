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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import cn.rushmedia.jay.tvshow.domain.AppData;
import cn.rushmedia.jay.tvshow.domain.Post2;
import cn.rushmedia.jay.tvshow.domain.Program;
import cn.rushmedia.jay.tvshow.domain.Repost;
import cn.rushmedia.jay.tvshow.domain.Topic;
import cn.rushmedia.jay.tvshow.domain.User;
import cn.rushmedia.jay.tvshow.util.ImageCash;
import cn.rushmedia.jay.tvshow.util.ImageDownloder;
import cn.rushmedia.jay.tvshow.util.JsonUtil;
import cn.rushmedia.jay.tvshow.util.TimeDifference;

	public class MyTopicActivity extends BaseActivity {
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
	    private List<Post2> myHomeLineDiscList;
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
		
 		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.subject);
			myHomeLineDiscList =new ArrayList<Post2>();
			 repostList = new ArrayList<Repost>();
			
			 AppData  appData =(AppData) getApplication();
			 logininfo = appData.getLoginInfo();
			 appData.addActivity(this);
				try {
					JSONObject  js= new JSONObject(logininfo);
					userid=js.getInt("id");
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String path ="http://tvsrv.webhop.net:8080/api/users/"+userid+"/homeline";
				JsonUtil jsut = new JsonUtil();
				 try {
					 array = jsut.getSource(path);
					intiData(array);
					MyAdapter adapter = new MyAdapter(this,array);
					ListView listView =(ListView) findViewById(R.id.androidlist);
					listView.setAdapter(adapter);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		private void intiData(JSONArray jsonArray) {
			try {
				 for (int i = 0; i < jsonArray.length(); i++) {
					 js =jsonArray.getJSONObject(i);
					  JSONObject jsUser=js.getJSONObject("user");
					   userImagePath=jsUser.getString("image");
					   userName=jsUser.getString("name");
					   User user = new User();
					   user.setImage(userImagePath);
					   user.setName(userName);
					   JSONObject jsTopic=js.getJSONObject("topic");
					   jsProgram =jsTopic.getJSONObject("program");
					   filmImagePath=jsProgram.getString("image");
					   if(!jsProgram.isNull("title")){
					   filmName=jsProgram.getString("title");
					   }else{
						   filmName="极品电影";
					   }
					   Program program =new Program();
					   program.setImagePath(filmImagePath);
					   program.setTitle(filmName);
//					   program.setId(filmid);
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
					   Post2 myHomeLineDisc = new Post2();
					   Post2 repostmyHomeLineDiscu =  new Post2();
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
					 
				 
			} catch (Exception e) {
				e.printStackTrace();
			}
			ImageButton backtohome = (ImageButton) findViewById(R.id.backtohome);
	    	backtohome.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent i = new Intent(getApplicationContext(),TableActivity.class);
					startActivity(i);
				}
			});
		}
		
		protected void onListItemClick(ListView l, View v, int position, long id) {
			Intent intent = new Intent(MyTopicActivity.this,PostsDetialActivity.class);
			//AppData.getInstance().setMoviesEntity(mData);
			//Program movie = mData.get(position);
			Post2 myHomeLineDiscu =myHomeLineDiscList.get(position);
			intent.putExtra("saydetial",myHomeLineDiscu);
			startActivity(intent);
		}
		/**
		 * listview中点击按键弹出对话框
		 */
//		public void showInfo(){
//			new AlertDialog.Builder(this)
//			.setTitle("我的listview")
//			.setMessage("介绍...")
//			.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//				@Override
//				public void onClick(DialogInterface dialog, int which) {
//				}
//			})
//			.show();
//		}
		
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

		public class MyAdapter extends BaseAdapter{
			private LayoutInflater mInflater;
			private JSONArray array_list;
			public MyAdapter(Context context,JSONArray array){
				this.mInflater = LayoutInflater.from(context);
				this.array_list=array;
			}
			@Override
			public int getCount() {
				return array_list.length();
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
	}


