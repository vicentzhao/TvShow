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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import cn.rushmedia.jay.tvshow.SameTopicListActivity.MyAdapter;
import cn.rushmedia.jay.tvshow.SameTopicListActivity.ViewHolder;
import cn.rushmedia.jay.tvshow.domain.AppData;
import cn.rushmedia.jay.tvshow.domain.Post2;
import cn.rushmedia.jay.tvshow.domain.Program;
import cn.rushmedia.jay.tvshow.domain.Topic;
import cn.rushmedia.jay.tvshow.domain.User;
import cn.rushmedia.jay.tvshow.util.ImageCash;
import cn.rushmedia.jay.tvshow.util.ImageDownloder;
import cn.rushmedia.jay.tvshow.util.JsonUtil;

public class SearchSameTopicListActivity extends BaseActivity {
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
	private List<Topic> topicArraylist;
	private ListView listview;
	private Post2 homeLineDiscu;
	private ImageButton tv_topic_back_button;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sametopiclist_1);
		ProgressDialog pd = new ProgressDialog(SearchSameTopicListActivity.this) ;
		pd.setMessage("正在登陆");
		appData =(AppData) getApplication();
		appData.addActivity(this);
		pd.show();
		topicArraylist = new ArrayList<Topic>();
			Intent it =getIntent();
			try {
				homeLineDiscu = (Post2) it.getSerializableExtra("saydetial");
			programeid = homeLineDiscu.getTopic().getProgramid();
			JsonUtil js = new JsonUtil();
			String sameTopicPath="http://tvsrv.webhop.net:8080/api/programs/"+programeid+"/topics";
			sameTopic = js.getSource(sameTopicPath);
			if(sameTopic!=null){
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
			  }else{
				  pd.dismiss();
				  Toast.makeText(SearchSameTopicListActivity.this, "沒有相關的話題", 1).show();
				  Intent i = new Intent(SearchSameTopicListActivity.this,NewFileActivity.class);
				  i.putExtra("saydetial",homeLineDiscu);
				  startActivity(i);
				  finish();
			  }
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			pd.dismiss();
			MyAdapter myAdapter= new MyAdapter(this);
			listview =(ListView) findViewById(R.id.androidlist);
			listview.setAdapter(myAdapter);
			listview.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
						Intent i  = new Intent(SearchSameTopicListActivity.this,TopicDetialActivity.class);
						Topic topic =topicArraylist.get(position);
						Post2 home = new Post2();
						home.setTopic(topic);
						i.putExtra("topic", home);
						startActivity(i);
											
				}
			});
			tv_topic_back_button=(ImageButton) findViewById(R.id.tv_topic_back_button);
			tv_topic_back_button.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent i = new Intent(SearchSameTopicListActivity.this,PostsDetialActivity.class);
					i.putExtra("saydetial",homeLineDiscu);
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
			   private LayoutInflater mInflater;
				public MyAdapter(Context context){
					this.mInflater = LayoutInflater.from(context);
				}
			@Override
			public int getCount() {
				return sameTopic.length();
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
				Bitmap bitmapFromCache = cash.getBitmapFromCache(topicArraylist.get(position).getUser().getImage());
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
								String imagepath=topicArraylist.get(position).getUser().getImage();
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
				holder.tv_sametopiclist_username.setText(topicArraylist.get(position).getUser().getName());
				holder.tv_sametopiclist_topicname.setText(topicArraylist.get(position).getTopic_name());
				return convertView;
			}
		}
}
