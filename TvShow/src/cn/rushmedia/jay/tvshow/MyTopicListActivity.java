package cn.rushmedia.jay.tvshow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.rushmedia.jay.tvshow.domain.AppData;
import cn.rushmedia.jay.tvshow.domain.Post;
import cn.rushmedia.jay.tvshow.domain.Program;
import cn.rushmedia.jay.tvshow.domain.Topic;
import cn.rushmedia.jay.tvshow.domain.User;
import cn.rushmedia.jay.tvshow.util.ImageCash;
import cn.rushmedia.jay.tvshow.util.ImageDownloder;
import cn.rushmedia.jay.tvshow.util.JsonUtil;

public class MyTopicListActivity extends BaseActivity {
	private AppData appData;
//	private int programeid;
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
	private List<Topic> topicArraylist;
	private List<Bitmap> imageList;
	private RelativeLayout r1;
	MyAdapter myAdapter;
	private ListView listview;
	private  User userinfo;
	Bitmap bitmap;
	private int programeid;
	int page = 1;
	int count = 10;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sametopiclist_1);
		AppData ap = (AppData) getApplication();
		ap.addActivity(this);
		
		imageList= new ArrayList<Bitmap>();
		r1=(RelativeLayout) findViewById(R.id.loading);
		listview =(ListView) findViewById(R.id.androidlist);
		
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				 Intent i = new Intent(getApplicationContext(),TopicDetialActivity.class);
				 Post home = new Post();
				 Topic topic =topicArraylist.get(position);
				 home.setTopic(topic);
				 i.putExtra("topic", home);
				 startActivity(i);
					
			}
		});
		ImageButton  tv_topic_back_button =(ImageButton) findViewById(R.id.tv_topic_back_button);
		tv_topic_back_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),MyHomeActivity.class);
				startActivity(i);
			}
		});
		ImageButton backtohome = (ImageButton) findViewById(R.id.backtohome);
    	backtohome.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),TableActivity.class);
				startActivity(i);
			}
		});
    	intiData(page,count);
    	/**
	   	  * 上一页
	   	  */
	   	
	   	 Button tv_prepage=(Button) findViewById(R.id.tv_prepage);
	   	 tv_prepage.setOnClickListener(new OnClickListener() {
	   		public void onClick(View v) {
	   			if(page==1){
	   				 Toast.makeText(getApplicationContext(), "已经是第一页", 1).show();
	   			}else
	   				page =page-1;
	   				intiData(page, count);
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
	   		   intiData(page, count);
	   		myAdapter.notifyDataSetChanged();
	   		}
	   	});
	}
	private void intiData(final int page, final int count) {
		 new AsyncTask<Void, Void, List>(){
				@Override
				protected void onPreExecute() {
					showProgress(r1);
					super.onPreExecute();
				}
				protected void onPostExecute(List result) {
					hideProgress(r1);
					myAdapter= new MyAdapter(getApplicationContext(),result);
					super.onPostExecute(result);
					listview.setAdapter(myAdapter);
					myAdapter.notifyDataSetChanged();
				}
				protected List doInBackground(Void... params) {
					try {
						AppData ap =(AppData) getApplication();
						String loginInfo = ap.getLoginInfo();
						JSONObject os = new JSONObject(loginInfo);
						int userid = os.getInt("id");
						JsonUtil js = new JsonUtil();
						String sameTopicPath="http://tvsrv.webhop.net:8080/api/users/"+userid+"/topics?"+"page="+page+"&count="+count+"";
						sameTopic = js.getSource(sameTopicPath);
						topicArraylist = new ArrayList<Topic>();
						for (int i = 0; i < sameTopic.length(); i++) {
							 topic = new 	Topic();
								JSONObject  sametopicjb=sameTopic.getJSONObject(i);
								topic_name=sametopicjb.getString("name");
								programeid=sametopicjb.getInt("programid");
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
						} catch (Exception e) {
							e.printStackTrace();
						}
						String imagepath=topicArraylist.get(0).getUser().getImage();
						ImageDownloder imageDownloder = new ImageDownloder();
						 try {
							bitmap = imageDownloder.imageDownloder(imagepath);
						} catch (Exception e) {
							e.printStackTrace();
						}
					return topicArraylist;
				}
			 }.execute();
	}

			public final class ViewHolder{
				public ImageView tv_sametopiclist_userimage;
				public TextView tv_sametopiclist_username;
				public TextView tv_sametopiclist_topicname;
			}
		   class MyAdapter extends BaseAdapter{
			   private List<Topic> topicAList;
			   private LayoutInflater mInflater;
				public MyAdapter(Context context, List result){
					this.mInflater = LayoutInflater.from(context);
					this.topicAList=result;
				}
			@Override
			public int getCount() {
				return topicAList.size();
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
				holder.tv_sametopiclist_userimage.setImageBitmap(bitmap);
				holder.tv_sametopiclist_username.setText(topicArraylist.get(position).getUser().getName());
				holder.tv_sametopiclist_topicname.setText(topicArraylist.get(position).getTopic_name());
				return convertView;
			}
		}
		}
	
	


