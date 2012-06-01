package cn.rushmedia.jay.tvshow;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
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
import cn.rushmedia.jay.tvshow.util.ImageDownloder;
import cn.rushmedia.jay.tvshow.util.SourceFromResponse;

public class SearchProgramActivity extends BaseActivity {
	List<Program> mData;
	ListView listview;
	ViewHolder holder ;
	private RelativeLayout rl;
	 String imagepath;
		String title;
		String actor;
		String desc;
		String keyword;
		 String director ;
		 JSONArray js;
		 int id;
		 String loginInfo ;
	
	String  jsonpro ;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.subject_1_program);
		rl=(RelativeLayout) findViewById(R.id.loading);
		listview=(ListView) findViewById(R.id.subjectlist);
		mData  = new ArrayList<Program>(); 
		AppData ap = (AppData) getApplication();
		ap.addActivity(this);
		
		loginInfo= ap.getLoginInfo();
		
		ImageButton backtohome = (ImageButton) findViewById(R.id.backtohome);
    	backtohome.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),TableActivity.class);
				startActivity(i);
			}
		});
		new AsyncTask<Void, Void, List>(){
			@Override
			protected void onPreExecute() {
				showProgress(rl);
				super.onPreExecute();
			}
			@Override
			protected void onPostExecute(List result) {
				hideProgress(rl);
		    if(!(result==null)){
			hideProgress(rl);
			MyAdapter adapter = new MyAdapter(SearchProgramActivity.this,result);
			listview.setAdapter(adapter);
			super.onPostExecute(result);
			}else{
				Toast.makeText(SearchProgramActivity.this, "没有找到相应的影片", 1).show();
				Intent i = new Intent(SearchProgramActivity.this,MySearchActivity.class);
				startActivity(i);
				finish();
			}
			}
			@Override
			protected List doInBackground(Void... params) {
				Intent i  =getIntent();
				String keyWord = i.getStringExtra("compath");
				 jsonpro="{\"key\":\""+keyWord+"\"}"; 
				 String path="http://tvsrv.webhop.net:8080/api/search/programs";
				SourceFromResponse sr = new SourceFromResponse();
				String usersource = sr.getSource(jsonpro,path );
				if(!"[]".equals(usersource)){
					
					try {
						js = new JSONArray(usersource);
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
						for (int j = 0; j < js.length(); j++) {
							try {
								JSONObject  programjs=js.getJSONObject(j);
								
								
								if(!programjs.isNull("image")){
									 imagepath = programjs.getString("image");
								}else{
									imagepath ="http://u.sgamer.com/uc_server/data/avatar/000/33/90/92_avatar_middle.jpg";
								}
								if(!programjs.isNull("title")){
								 title = programjs.getString("title");
								}else{
									title ="未知的";
								}
								if(!programjs.isNull("actor")){
									 actor = programjs.getString("actor");
								}else{
									actor ="未知的";
								}
								if(!programjs.isNull("description")){
									 desc = programjs.getString("description");
								}else{
									desc ="未知的";
								}
								if(!programjs.isNull("director")){
									 director= programjs.getString("director");
								}else{
									director ="未知的";
								}
								if(!programjs.isNull("keyword")){
									 keyword = programjs.getString("keyword");
								}else{
									keyword ="未知的";
								}
									 id = programjs.getInt("id");
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							 Program pr = new Program();
							pr.setImagePath(imagepath);
							pr.setActor(actor);
							pr.setDescription(desc);
							pr.setTitle(title);
							pr.setKey(keyword);
							pr.setId(id);
							mData.add(pr);
			}
				return mData;
				}else{
					return null;
				}
			}
		}.execute();
		 listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Post  myHomeLineDiscu = new Post();
				Program movie = mData.get(position);
				Topic tp = new Topic();
				User u = null;
				try {
					JSONObject jsuser =new JSONObject(loginInfo);
					u = new User();
					u.setName(jsuser.getString("name"));
					u.setId(jsuser.getInt("id"));
					u.setImage(jsuser.getString("image"));
					u.setCreated_at(jsuser.getLong("created-at"));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				myHomeLineDiscu.setUser(u);
				tp.setProgram(movie);
				tp.setProgramid(movie.getId());
				myHomeLineDiscu.setTopic(tp);
				Intent intent = new Intent(SearchProgramActivity.this,NewFileActivity.class);
				intent.putExtra("saydetial", myHomeLineDiscu);
				startActivity(intent);
			
			}
		});
		 ImageButton  tv_reprogram_button = (ImageButton) findViewById(R.id.tv_reprogram_button);
		 tv_reprogram_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
                      Intent i  = new Intent(SearchProgramActivity.this,MySearchActivity.class);
                     startActivity(i);
			}
		});
	}
//		protected void onItemClick(ListView l, View v, int position, long id) {		
//		}	
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
			public ImageView img;
			public TextView title;
			public TextView actor;
			public TextView keyword;
		}
		class MyAdapter extends BaseAdapter{

			private LayoutInflater mInflater;
			List<Program> mData;
			
			
			public MyAdapter(Context context, List result){
				this.mInflater = LayoutInflater.from(context);
				this.mData=result;
			}
			@Override
			public int getCount() {
				return mData.size();
			}
			public Object getItem(int arg0) {
				return null;
			}

			@Override
			public long getItemId(int arg0) {
				return 0;
			}
			public View getView(final int position, View convertView, ViewGroup parent) {		
				holder=new ViewHolder();  
				if (convertView == null) {
					convertView = mInflater.inflate(R.layout.subject_replay, null);
					holder.img = (ImageView)convertView.findViewById(R.id.image);
					holder.title = (TextView)convertView.findViewById(R.id.title);
					holder.actor = (TextView)convertView.findViewById(R.id.actor);
					holder.keyword=(TextView) convertView.findViewById(R.id.keyword);
					convertView.setTag(holder);
				}else {
					holder = (ViewHolder)convertView.getTag();
				}
			
					new AsyncTask<Void, Void, Bitmap>(){
						@Override
						protected void onPostExecute(Bitmap result) {
							holder.img.setImageBitmap(result);
							super.onPostExecute(result);
						}

						@Override
						protected Bitmap doInBackground(Void... params) {
							Bitmap bitmap;
							try {
								ImageDownloder imageDownloder = new ImageDownloder();
								bitmap = imageDownloder.imageDownloder(mData.get(position).getImagePath());
								return bitmap;
							} catch (Exception e) {
								e.printStackTrace();
								return null;
							}	
							
						}
					}.execute();
				holder.title.setText(getApplication().getResources().getString(R.string.see_filename)+":"+(String)mData.get(position).getTitle());
				holder.actor.setText(getApplication().getResources().getString(R.string.see_actor)+":"+(String)mData.get(position).getActor());
				holder.keyword.setText(mData.get(position).getKey());
//				holder.viewBtn.setOnClickListener(new View.OnClickListener() {
//					@Override
//					public void onClick(View v) {
//						showInfo();					
//					}
//				});
				return convertView;
			}
		}
	 
}
