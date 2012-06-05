package cn.rushmedia.jay.tvshow;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import cn.rushmedia.jay.tvshow.domain.Post;
import cn.rushmedia.jay.tvshow.domain.Topic;
import cn.rushmedia.jay.tvshow.domain.User;
import cn.rushmedia.jay.tvshow.util.ImageDownloder;
import cn.rushmedia.jay.tvshow.util.ImageFileCache;
import cn.rushmedia.jay.tvshow.util.JSONObject2Topic;
import cn.rushmedia.jay.tvshow.util.JsonUtil;

public class SameTopicListActivity extends BaseActivity {
	private int programeid;
	ViewHolder holder ;
	public ImageView tv_sametopiclist_movie_img;
	public TextView tv_sametopiclist_movie_title;
	public TextView tv_sametopiclist_movie_actor;
	public TextView tv_sametopiclist_movie_key;
	public TextView tv_sametopiclist_movie_desc;
	private Button tv_mytopic_previewpage;
	private Button tv_mytopic_nextpage;
	public Button  sayButton;
	public Button  abouybutton;
	public Button moreButton;
	public Button reButton;
	private ImageFileCache  cache;
	private JSONArray sameTopic;
	private Topic topic;
	private List<Topic> topiclist;
	private ListView listview;
	private Post post;
	private ImageButton tv_topic_back_button;
	private int page=1;
	private int count=8;
	private MyAdapter myAdapter;
	private boolean isFavTopic =false;
	private int userId;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sametopiclist_1);
		initView();
		ImageButton backtohome = (ImageButton) findViewById(R.id.backtohome);
    	backtohome.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),TableActivity.class);
				startActivity(i);
			}
		}); 
			Intent i = getIntent();
			post= (Post) i.getSerializableExtra("saydetial");
			
			if(post!=null){
			programeid = post.getTopic().getProgram().getId();
			String sameTopicPath="http://tvsrv.webhop.net:8080/api/programs/"+programeid+"/topics?page="+page+"&count="+count+"";
			topiclist = initData(sameTopicPath);
			myAdapter= new MyAdapter(this,topiclist);
			listview.setAdapter(myAdapter);
			}else{
				Intent  iUser= getIntent();
				User userinfo = (User) iUser.getSerializableExtra("userinfo");
				userId =userinfo.getId();
				String sameTopicPath="http://tvsrv.webhop.net:8080/api/users/"+userId+"/favorite-topics?"+"page="+page+"&count="+count+"";
				topiclist = initData(sameTopicPath);
				myAdapter= new MyAdapter(this,topiclist);
				listview.setAdapter(myAdapter);
				isFavTopic=true;
			}
			listview.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
						Intent i  = new Intent(SameTopicListActivity.this,TopicDetialActivity.class);
						Topic topic =topiclist.get(position);
						Post home = new Post();
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
					i.putExtra("saydetial",post);
					startActivity(i);
					
				}
			});
			/**
		   	  * 上一页
		   	  */
		   	
			tv_mytopic_previewpage.setOnClickListener(new OnClickListener() {
		   		@Override
		   		public void onClick(View v) {
		   			if(page==1){
		   				 Toast.makeText(getApplicationContext(), "已经是第一页", 1).show();
		   			}else if(isFavTopic){
		   				page =page-1;
		   				String realPath ="http://tvsrv.webhop.net:8080/api/users/"+userId+"/favorite-topics?"+"page="+page+"&count="+count+"";
		   				ArrayList<Topic> initData =initData(realPath);
		   				listview.setAdapter(new MyAdapter(SameTopicListActivity.this, initData));
		   		}else{
		   			page=page-1;
		   			programeid = post.getTopic().getProgram().getId();
					String sameTopicPath="http://tvsrv.webhop.net:8080/api/programs/"+programeid+"/topics?page="+page+"&count="+count+"";
					ArrayList<Topic> initData =initData(sameTopicPath);
	   				listview.setAdapter(new MyAdapter(SameTopicListActivity.this, initData));
		   		}
		   			}
		   	});
		   		/**
		   		 * 下一页
		   		 */
			tv_mytopic_nextpage.setOnClickListener(new OnClickListener() {
		   		@Override
		   		public void onClick(View v) {
		   			if(isFavTopic){
		   				page =page+1;
		   				String realPath ="http://tvsrv.webhop.net:8080/api/users/"+userId+"/favorite-topics?"+"page="+page+"&count="+count+"";
		   				ArrayList<Topic> initData =initData(realPath);
		   				if(initData!=null&&"[]".equals(initData)){
		   				listview.setAdapter(new MyAdapter(SameTopicListActivity.this, initData));
		   				}
		   				else{
		   					Toast.makeText(SameTopicListActivity.this, "这已经是最后一页", 1).show();
		   				}
		   		}else{
		   			page=page+1;
		   			programeid = post.getTopic().getProgram().getId();
					String sameTopicPath="http://tvsrv.webhop.net:8080/api/programs/"+programeid+"/topics?page="+page+"&count="+count+"";
					ArrayList<Topic> initData =initData(sameTopicPath);
					if(initData!=null&&"[]".equals(initData)){
						listview.setAdapter(new MyAdapter(SameTopicListActivity.this, initData));
		   				}
		   				else{
		   					page=page-1;
		   					Toast.makeText(SameTopicListActivity.this, "这已经是最后一页", 1).show();
		   				}
	   				
		   		}
		   		}
		   	});
   	
	}			
	private ArrayList<Topic> initData(String downPath) {
		ArrayList<Topic> topicArraylist = new ArrayList<Topic>();
		Intent it =getIntent();
		try {
			JsonUtil js = new JsonUtil();
		String sametopicss =js.getStringSource(downPath);
		sameTopic = js.getSource(downPath);
		if(sameTopic!=null&&!"[]".equals(sametopicss)){
		for (int i = 0; i < sameTopic.length(); i++) {
			JSONObject  sametopicjb=sameTopic.getJSONObject(i);
			JSONObject2Topic jt = new JSONObject2Topic();
			topic = jt.getTopic(sametopicjb);
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
				cache = ImageFileCache.getCashInstance();
				Bitmap bitmapFromCache = cache.getImage(tplist.get(position).getUser().getImage());
							Bitmap bitmap;
								ImageDownloder imageDownloder = new ImageDownloder();
								String imagepath=tplist.get(position).getUser().getImage();
								try {
									bitmap = imageDownloder.imageDownloder(imagepath);
									holder.tv_sametopiclist_userimage.setImageBitmap(bitmap);
									cache.saveBmpToSd(bitmap, tplist.get(position).getUser().getImage());
								} catch (Exception e) {
									e.printStackTrace();
								}
				holder.tv_sametopiclist_username.setText(tplist.get(position).getUser().getName());
				holder.tv_sametopiclist_topicname.setText(tplist.get(position).getTopic_name());
				return convertView;
			}
		}
		   void initView(){
			   listview =(ListView) findViewById(R.id.androidlist);
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
		

