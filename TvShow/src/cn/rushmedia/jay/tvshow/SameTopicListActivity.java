package cn.rushmedia.jay.tvshow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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
import android.widget.TextView;
import android.widget.Toast;
import cn.rushmedia.jay.tvshow.domain.AppData;
import cn.rushmedia.jay.tvshow.domain.MyHomeLineDiscu;
import cn.rushmedia.jay.tvshow.domain.Program;
import cn.rushmedia.jay.tvshow.domain.Topic;
import cn.rushmedia.jay.tvshow.domain.User;
import cn.rushmedia.jay.tvshow.util.ImageCash;
import cn.rushmedia.jay.tvshow.util.ImageDownloder;
import cn.rushmedia.jay.tvshow.util.JsonUtil;

public class SameTopicListActivity extends BaseActivity {
	private AppData appData;
	private int programeid;
	ViewHolder holder ;
	public ImageView tv_sametopiclist_movie_img;
	public TextView tv_sametopiclist_movie_title;
	public TextView tv_sametopiclist_movie_actor;
	public TextView tv_sametopiclist_movie_key;
	public TextView tv_sametopiclist_movie_desc;
	public Button  sayButton;
	public Button  abouybutton;
	public Button moreButton;
	public Button reButton;
	private ImageCash  cash;
	private Program movie;
	private String programtitle;
	private String programkey;
	private String programdesc;
	private String programactor;
	private String programPath;
	private HashMap<String, Bitmap> mHardBitmapCache;
	private JSONObject  jb ;
	private ListView lst ;
	private JSONArray sameTopic;
	private String topic_name;
	private Topic topic;
	private List<Topic> topiclist;
	private ListView listview;
	private MyHomeLineDiscu homeLineDiscu;
	private ImageButton tv_topic_back_button;
	private int page=1;
	private int count=8;
	 MyAdapter myAdapter;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sametopiclist_1);
		ProgressDialog pd = new ProgressDialog(SameTopicListActivity.this) ;
		appData =(AppData) getApplication();
		appData.addActivity(this);
		pd.setMessage("正在加载");
		pd.show();
		ImageButton backtohome = (ImageButton) findViewById(R.id.backtohome);
    	backtohome.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),TableActivity.class);
				startActivity(i);
			}
		});
           topiclist = initData(page,count);
    	
		if(topiclist==null){
    		 Toast.makeText(SameTopicListActivity.this, "沒有相關的話題", 1).show();
//			  Intent i = new Intent(SameTopicListActivity.this,NewFileActivity.class);
//			  i.putExtra("saydetial",homeLineDiscu);
//			  startActivity(i);
//			  SameTopicListActivity.this.finish();
			  finish();
    	}else{
			Intent i = getIntent();
			i.getSerializableExtra("saydetial");
			myAdapter= new MyAdapter(this,topiclist);
			listview =(ListView) findViewById(R.id.androidlist);
			listview.setAdapter(myAdapter);
			pd.dismiss();
    	
			listview.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
						Intent i  = new Intent(SameTopicListActivity.this,TopicDetialActivity.class);
						Topic topic =topiclist.get(position);
						MyHomeLineDiscu home = new MyHomeLineDiscu();
						home.setTopic(topic);
						i.putExtra("topic", home);
						startActivity(i);
											
				}
			});
			tv_topic_back_button=(ImageButton) findViewById(R.id.tv_topic_back_button);
			tv_topic_back_button.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent i = new Intent(SameTopicListActivity.this,PostsDetialActivity.class);
					i.putExtra("saydetial",homeLineDiscu);
					startActivity(i);
					
				}
			});
			/**
		   	  * 上一页
		   	  */
		   	
		   	 Button tv_prepage=(Button) findViewById(R.id.tv_prepage);
		   	 tv_prepage.setOnClickListener(new OnClickListener() {
		   		
		   		@Override
		   		public void onClick(View v) {
		   			if(page==1){
		   				 Toast.makeText(getApplicationContext(), "已经是第一页", 1).show();
		   			}else
		   				page =page-1;
		   				initData(page, count);
		   				myAdapter.notifyDataSetChanged();
		   		}
		   	});
		   		/**
		   		 * 下一页
		   		 */
		   	 Button tv_nextpage=(Button) findViewById(R.id.tv_nextpage);
		   	 tv_nextpage.setOnClickListener(new OnClickListener() {
		   		@Override
		   		public void onClick(View v) {
		   		   page =page+1;
		   		   initData(page, count);
		   		myAdapter.notifyDataSetChanged();
		   		}

				
		   	});
    	}
			
	}			
	private ArrayList<Topic> initData(int page2, int count2) {
		ArrayList<Topic> topicArraylist = new ArrayList<Topic>();
		Intent it =getIntent();
		try {
			homeLineDiscu = (MyHomeLineDiscu) it.getSerializableExtra("saydetial");
		programeid = homeLineDiscu.getTopic().getProgramid();
		JsonUtil js = new JsonUtil();
		String sameTopicPath="http://tvsrv.webhop.net:8080/api/programs/"+programeid+"/topics?page="+page2+"&count="+count2+"";
		Log.i("相关的话题===============>", sameTopicPath);
		String sametopicss =js.getStringSource(sameTopicPath);
		sameTopic = js.getSource(sameTopicPath);
		if(sameTopic!=null&&!"[]".equals(sametopicss)){
		for (int i = 0; i < sameTopic.length(); i++) {
			 topic = new 	Topic();
			JSONObject  sametopicjb=sameTopic.getJSONObject(i);
			topic_name=sametopicjb.getString("name");
			topic.setProgramid(programeid);
			topic.setTopic_name(topic_name);
			JSONObject userjsob = sametopicjb.getJSONObject("user");
			JSONObject programjsob = sametopicjb.getJSONObject("program");
			String programname=programjsob.getString("title");
			String imagePath = programjsob.getString("image");
			String desc =programjsob.getString("description");
			String programdirector = programjsob.getString("director");
			String actor = programjsob.getString("actor");
			Program  p = new Program();
			p.setTitle(programname);
			p.setActor(actor);
			p.setDirector(programdirector);
			p.setImagePath(imagePath);
			p.setDescription(desc);
			if(userjsob.isNull("id")){continue;}
			String username = userjsob.getString("name");
			String  userimagepath = userjsob.getString("image");
			User user = new User();
			user.setName(username);
			user.setImage(userimagepath);
			topic.setUser(user);
			topic.setProgram(p);
			topic.setProgramid(programeid);
			topicArraylist.add(topic);
		}
		return  topicArraylist;
		  }else{
			  return  null;
		  }
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
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

			public final class ViewHolder{
				public ImageView tv_sametopiclist_userimage;
				public TextView tv_sametopiclist_username;
				public TextView tv_sametopiclist_topicname;
			}
		   class MyAdapter extends BaseAdapter{
			   private List<Topic> tplist;
			   private LayoutInflater mInflater;
				public MyAdapter(Context context, List<Topic> topiclist){
					this.mInflater = LayoutInflater.from(context);
					this.tplist=topiclist;
				}
			@Override
			public int getCount() {
				return tplist.size();
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
			public View getView(final int position, View convertView, ViewGroup parent) {
				holder=new ViewHolder();  
				if (convertView == null) {
					convertView = mInflater.inflate(R.layout.sametopiclist_detail, null);
					holder.tv_sametopiclist_userimage = (ImageView)convertView.findViewById(R.id.tv_sametopiclist_userimage);
					holder.tv_sametopiclist_username = (TextView)convertView.findViewById(R.id.tv_sametopiclist_username);
					holder.tv_sametopiclist_topicname = (TextView)convertView.findViewById(R.id.tv_sametopiclist_topicname);
					convertView.setTag(holder);
				}else {
					holder = (ViewHolder)convertView.getTag();
				}
				appData =(AppData) getApplication(); 
				cash = new ImageCash();
				Bitmap bitmapFromCache = cash.getBitmapFromCache(tplist.get(position).getUser().getImage());
				if(bitmapFromCache==null){
					new AsyncTask<Void, Void, Bitmap>(){
						@Override
						protected void onPostExecute(Bitmap result) {
							holder.tv_sametopiclist_userimage.setImageBitmap(result);
							super.onPostExecute(result);
						}
						@Override
						protected Bitmap doInBackground(Void... params) {
							Bitmap bitmap;
							try {
								ImageDownloder imageDownloder = new ImageDownloder();
								String imagepath=tplist.get(position).getUser().getImage();
								bitmap = imageDownloder.imageDownloder(imagepath);
								mHardBitmapCache = appData.getmHardBitmapCache();
								mHardBitmapCache.put(imagepath, bitmap);
								return bitmap;
							} catch (Exception e) {
								e.printStackTrace();
								return null;
							}	
						}
					}.execute();
				}			
				holder.tv_sametopiclist_username.setText(tplist.get(position).getUser().getName());
				holder.tv_sametopiclist_topicname.setText(tplist.get(position).getTopic_name());
				return convertView;
			}
		}
		  }
		

