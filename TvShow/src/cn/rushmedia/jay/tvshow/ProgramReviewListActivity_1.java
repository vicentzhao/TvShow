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
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
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

public class ProgramReviewListActivity_1 extends BaseActivity {
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
    private MyHomeLineDiscu discu;
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
	private User userInfo;
	ArrayList<MyHomeLineDiscu> intiData;
	RelativeLayout rl;
	int page = 1;
	int count = 8;
	private LinearLayout mSubjectFooter;
	private ProgressBar progressBar;
		public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.subject_1_post);
		 repostList = new ArrayList<Repost>();
		 LinearLayout layout = new LinearLayout(this);
		 listView = (ListView) this.findViewById(R.id.subjectlist);
			
			/**
		   	  * ��һҳ
		   	  */
		   	
		   	 Button tv_prepage=(Button) findViewById(R.id.tv_prepage);
		   	 tv_prepage.setOnClickListener(new OnClickListener() {
		   		
		   		@Override
		   		public void onClick(View v) {
		   			if(page==1){
		   				 Toast.makeText(getApplicationContext(), "�Ѿ��ǵ�һҳ", 1).show();
		   			}else
		   				page =page-1;
		   				intiData(page, count);
		   				adapter.notifyDataSetChanged();
		   		}
		   	});
		   		/**
		   		 * ��һҳ
		   		 */
		   	 Button tv_nextpage=(Button) findViewById(R.id.tv_nextpage);
		   	 tv_nextpage.setOnClickListener(new OnClickListener() {
		   		@Override
		   		public void onClick(View v) {
		   		   page =page+1;
		   		   intiData(page, count);
		   			adapter.notifyDataSetChanged();
		   		}
		   	});
		 Intent i =getIntent();
		 discu= (MyHomeLineDiscu) i.getSerializableExtra("saydetial");
			rl = (RelativeLayout) this.findViewById(R.id.loading);
			intiData = intiData(page,count);
			if(intiData==null){
				Toast.makeText(getApplicationContext(), "û����Ӧ������", 1).show();
				finish();
			}else
			listView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					System.out.println("==========>>>>>>���ѱ�ִ��");
					Intent intent = new Intent(getApplicationContext(),PostsDetialActivity.class);
					Log.i("system", "��������¼�");
					MyHomeLineDiscu myHomeLineDiscu =intiData.get(position);
					intent.putExtra("saydetial",myHomeLineDiscu);
					startActivity(intent);
					
				}
			});
			ImageButton backtohome = (ImageButton) findViewById(R.id.backtohome);
			backtohome.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent i = new Intent(getApplicationContext(),TableActivity.class);
					startActivity(i);
				}
			});
			ImageButton tv_reprogram_button = (ImageButton) findViewById(R.id.tv_reprogram_button);
			tv_reprogram_button.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent i = new Intent(getApplicationContext(),NewFileActivity.class);
					i.putExtra("saydetial", discu);
					startActivity(i);
					
				}
			});
			

	   	
	}
		/**
		 * Ϊadapter��ʼ������
		 * @param page  ҳ��
		 * @param count  ÿҳ��ʾ�ĸ���
		 */
	private ArrayList<MyHomeLineDiscu> intiData(final int page,final int count) {
		final int programid;
		final ArrayList<MyHomeLineDiscu>	myHomeLine =new ArrayList<MyHomeLineDiscu>();
					Intent userIntent = getIntent();
					MyHomeLineDiscu home;
					home= (MyHomeLineDiscu) userIntent.getSerializableExtra("saydetial");
					programid =home.getTopic().getProgramid();
					new AsyncTask<Void, Void, ArrayList>(){
						
						@Override
						protected void onPreExecute() {
							showProgress(rl);
							super.onPreExecute();
						}

						@Override
						protected ArrayList doInBackground(Void... params) {
							String path ="http://tvsrv.webhop.net:8080/api/programs/"+programid+"/posts?"+"page="+page+"&count="+count+"";
							JsonUtil jsut = new JsonUtil();
							String jsstring = null;
							try {
								jsstring = jsut.getStringSource(path);
								 array = jsut.getSource(path);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							 if(array!=null&&!"[]".equals(jsstring)){
                       for (int i = 0; i < array.length(); i++) {
							 User user = null;
							int p = 0;
							int t = 0;
							Topic topic = null;
							MyHomeLineDiscu myHomeLineDisc = null;
							long datelong = 0;
							try {
								js =array.getJSONObject(i);
								  JSONObject jsUser=js.getJSONObject("user");
								   userImagePath=jsUser.getString("image");
								   userName=jsUser.getString("name");
								   int userid =jsUser.getInt("id");
								   user = new User();
								   user.setImage(userImagePath);
								   user.setName(userName);
								   user.setId(userid);
								   JSONObject jsTopic=js.getJSONObject("topic");
								   jsProgram =jsTopic.getJSONObject("program");
								   filmImagePath=jsProgram.getString("image");
								   desc =jsProgram.getString("description");
								   if(!jsProgram.isNull("title")){
								   filmName=jsProgram.getString("title");
								   }else{
									   filmName="��Ʒ��Ӱ";
								   }
								   Program program =new Program();
								   program.setImagePath(filmImagePath);
								   program.setTitle(filmName);
								   program.setDescription(desc);
//						   program.setId(filmid);
								   if(!js.isNull("c")){
									   c =js.getString("c");
								   }else{
									   c="δ֪��";
								   }
								   int u =js.getInt("u");
								   p = js.getInt("p");
								   t = js.getInt("t");
								   rtitle=jsTopic.getString("name");
								  int programeid=jsTopic.getInt("programid");
								   topic = new Topic();
								   topic.setTopic_name(rtitle);
								   topic.setProgramid(programid);
								   topic.setProgram(program);
								   topic.setUser(user);
								   myHomeLineDisc = new MyHomeLineDiscu();
								   MyHomeLineDiscu repostmyHomeLineDiscu =  new MyHomeLineDiscu();
								   datelong = js.getLong("ct");
								   myHomeLineDisc.setU(u);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							   myHomeLineDisc.setT(t);
							   myHomeLineDisc.setP(p);
							   myHomeLineDisc.setC(c);
							   myHomeLineDisc.setTopic(topic);
							   myHomeLineDisc.setCreated_at(datelong);
							   myHomeLineDisc.setUser(user);
							   myHomeLineDisc.setTopic(topic);
							   myHomeLine.add(myHomeLineDisc);
 }
                        return myHomeLine;
							
						}else{
						 return null;	
						}
						}

						@Override
						protected void onPostExecute(ArrayList result) {
							if(result!=null){
								 adapter = new MyAdapter(ProgramReviewListActivity_1.this,array);
			                     listView.setAdapter(adapter);
			                     hideProgress(rl);
							}
							super.onPostExecute(result);
						}
						
						
					}.execute();
					return myHomeLine;
						
							
                   	
                      
                   	 
							

			
	
	}
	 /**
	  * ��ʼ���ؼ�
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
	  * �Զ���adapter
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
					   filmName="��Ʒ��Ӱ";
					   }
					   if(!repostjs.isNull("c")){
						   repostcomment =repostjs.getString("c");
					   }else{
						   repostcomment="δ֪��";
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
			Bitmap bitmapFromCache3 = cash.getBitmapFromCache(intiData.get(position).getUser().getImage());
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
							String imagePath=intiData.get(position).getUser().getImage();
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
			  Bitmap bitmapforcash4=cash.getBitmapFromCache(intiData.get(position).getTopic().getProgram().getImagePath());
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
							String filmpath=intiData.get(position).getTopic().getProgram().getImagePath();
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
				holder.tv_homeline_username.setText(intiData.get(position).getUser().getName());
				holder.tv_homeline_comment.setText(intiData.get(position).getC());
				holder.tv_homeline_filmname.setText(intiData.get(position).getTopic().getProgram().getTitle());
				holder.tv_homeline_title.setText(intiData.get(position).getTopic().getTopic_name());
				long create_time=intiData.get(position).getCreated_at();
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