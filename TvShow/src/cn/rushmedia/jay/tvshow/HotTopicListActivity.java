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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import cn.rushmedia.jay.tvshow.MyTopicListActivity.MyAdapter;
import cn.rushmedia.jay.tvshow.MyTopicListActivity.ViewHolder;
import cn.rushmedia.jay.tvshow.domain.AppData;
import cn.rushmedia.jay.tvshow.domain.Post;
import cn.rushmedia.jay.tvshow.domain.Program;
import cn.rushmedia.jay.tvshow.domain.Topic;
import cn.rushmedia.jay.tvshow.domain.User;
import cn.rushmedia.jay.tvshow.util.ImageCash;
import cn.rushmedia.jay.tvshow.util.ImageDownloder;
import cn.rushmedia.jay.tvshow.util.ImageFileCache;
import cn.rushmedia.jay.tvshow.util.JsonUtil;

public class HotTopicListActivity extends BaseActivity {
	private AppData appData;
//	private int programeid;
	ViewHolder holder ;
	public ImageView tv_sametopiclist_movie_img;
	public TextView tv_sametopiclist_movie_title;
	public TextView tv_sametopiclist_movie_actor;
	public TextView tv_sametopiclist_movie_key;
	public TextView tv_sametopiclist_movie_desc;
	private Button tv_mytopic_previewpage;
	private Button tv_mytopic_nextpage;
	LinearLayout mSubjectFooter;
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
	private int id;
	private RelativeLayout r1;
	MyAdapter myAdapter;
	private ListView listview;
	private  User userinfo;
	Bitmap bitmap;
	private int programeid;
	int page = 1;
	int count = 10;
	private String path;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sametopiclist_1);
		AppData ap = (AppData) getApplication();
		ap.addActivity(this);
		Intent i = getIntent();
		path =i.getStringExtra("path");
		Log.d("search", "path is:" + path);
		imageList= new ArrayList<Bitmap>();
		r1=(RelativeLayout) findViewById(R.id.loading);
		initView();
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i = new Intent(getApplicationContext(),TopicDetialActivity.class);
				Topic topic =topicArraylist.get(position);
				Post home = new Post();
				home.setTopic(topic);
				i.putExtra("topic", home);
				startActivity(i);
					
			}
		});
		ImageButton  tv_topic_back_button =(ImageButton) findViewById(R.id.tv_topic_back_button);
		tv_topic_back_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),MySearchActivity.class);
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
    	/**
	   	  * ��һҳ
	   	  */
	   	
		tv_mytopic_previewpage.setOnClickListener(new OnClickListener() {
	   		@Override
	   		public void onClick(View v) {
	   			if(page==1){
	   				 Toast.makeText(getApplicationContext(), "�Ѿ��ǵ�һҳ", 1).show();
	   			}else{
	   				page =page-1;
	   				String realPath =path+"page="+page+"&count="+count+"";
	   				ArrayList<Topic> initData =intiData(realPath);
	   				listview.setAdapter(new MyAdapter(HotTopicListActivity.this, initData));
	   			}
	   		}
	   	});
	   		/**
	   		 * ��һҳ
	   		 */
		tv_mytopic_nextpage.setOnClickListener(new OnClickListener() {
	   		@Override
	   		public void onClick(View v) {
	   				page =page+1;
	   				String realPath =path+"page="+page+"&count="+count+"";
	   				ArrayList<Topic> initData =intiData(realPath);
	   				if(initData!=null&&"[]".equals(initData)){
	   				listview.setAdapter(new MyAdapter(HotTopicListActivity.this, initData));
	   				}
	   				else{
	   					Toast.makeText(HotTopicListActivity.this, "���Ѿ������һҳ", 1).show();
	   				}
	   		}
	   	});
	   	intiData(path);
		
		
	}
	private ArrayList<Topic> intiData(final String downpath) {
		topicArraylist = new ArrayList<Topic>();
		 new AsyncTask<Void, Void, List>(){
				@Override
				protected void onPreExecute() {
					showProgress(r1);
					super.onPreExecute();
				}
				@Override
				protected void onPostExecute(List result) {
					if(result!=null&&result.size()!=0){
					hideProgress(r1);
					super.onPostExecute(result);
					myAdapter= new MyAdapter(getApplicationContext(),result);
					myAdapter.notifyDataSetChanged();
					listview.setAdapter(myAdapter);
					}else{
						hideProgress(r1);
						super.onPostExecute(result);
					}
				}
				@Override
				protected List doInBackground(Void... params) {
					try {
						JsonUtil js = new JsonUtil();
						String sameTopicPath=downpath+"?"+"page="+page+"&count="+count+"";
						Log.d("search", "sameTopicPath is:" + sameTopicPath);
						sameTopic = js.getSource(sameTopicPath);
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
					return topicArraylist;
				}
			 }.execute();
				return (ArrayList<Topic>) topicArraylist;
	}
			public final class ViewHolder{
				public ImageView tv_sametopiclist_userimage;
				public TextView tv_sametopiclist_username;
				public TextView tv_sametopiclist_topicname;
			}
		   class MyAdapter extends BaseAdapter{
			   private LayoutInflater mInflater;
			   private List<Topic> topics;
				public MyAdapter(Context context, List result){
					this.mInflater = LayoutInflater.from(context);
					this.topics=result;
				}
			@Override
			public int getCount() {
				return topics.size();
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
				ImageFileCache cache =ImageFileCache.getCashInstance();
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
				cache = ImageFileCache.getCashInstance();
				Bitmap bitmapFromCache = cache.getImage(topicArraylist.get(position).getUser().getImage());
							Bitmap bitmap;
								ImageDownloder imageDownloder = new ImageDownloder();
								String imagepath=topicArraylist.get(position).getUser().getImage();
								try {
									bitmap = imageDownloder.imageDownloder(imagepath);
									holder.tv_sametopiclist_userimage.setImageBitmap(bitmap);
									cache.saveBmpToSd(bitmap, topicArraylist.get(position).getUser().getImage());
								} catch (Exception e) {
									e.printStackTrace();
								}
				return convertView;
			}
		}
		   void initView(){
				listview = (ListView) this.findViewById(R.id.androidlist);
				LayoutInflater inflater = LayoutInflater.from(this);
				LinearLayout mSubjectFooter = (LinearLayout) inflater.inflate(
						R.layout.subject_footer, null);
				tv_mytopic_previewpage = (Button) mSubjectFooter
						.findViewById(R.id.tv_mytopic_previewpage);
				tv_mytopic_nextpage = (Button) mSubjectFooter
						.findViewById(R.id.tv_mytopic_nextpage);
				listview.addFooterView(mSubjectFooter);
			}
	
}
