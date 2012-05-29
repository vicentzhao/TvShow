package cn.rushmedia.jay.tvshow;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.rushmedia.jay.tvshow.domain.AppData;
import cn.rushmedia.jay.tvshow.domain.MyHomeLineDiscu;
import cn.rushmedia.jay.tvshow.domain.Program;
import cn.rushmedia.jay.tvshow.domain.Topic;
import cn.rushmedia.jay.tvshow.util.ImageDownloder;
import cn.rushmedia.jay.tvshow.util.JsonUtil;

public class AllProgramActivity extends BaseActivity {

	List<Program> mData;
	ListView listview;
	ViewHolder holder ;
	private AppData appData;
	private String logininfo;
	private RelativeLayout rl;
	MyAdapter adapter;
	 int page=1  ;
	 int count=20;
	
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.subject_2_program);
		appData =(AppData) getApplication();
		rl =(RelativeLayout) findViewById(R.id.loading);
		 logininfo = appData.getLoginInfo();
		appData.addActivity(this);
		 listview=(ListView) findViewById(R.id.subjectlist);
		
		 intiData(page,count);
		
		
   listview.setOnItemClickListener(new OnItemClickListener() {
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent(getApplicationContext(),NewFileActivity.class);
		Program movie = mData.get(position);
		MyHomeLineDiscu home = new MyHomeLineDiscu();
		Topic t = new Topic();
		t.setProgramid(movie.getId());
		t.setProgram(movie);
		home.setTopic(t);
		intent.putExtra("saydetial", home);
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
	 ImageButton tv_reprogram_button =(ImageButton) findViewById(R.id.tv_reprogram_button);
	 tv_reprogram_button.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent i = new Intent(getApplicationContext(),TableActivity.class);
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
				intiData(page, count);
				adapter.notifyDataSetChanged();
			
			
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
			adapter.notifyDataSetChanged();
		}
	});
	}
	private void intiData(final int page2, final int count2) {
		new AsyncTask<Void, Void, List>(){

			@Override
			protected void onPreExecute() {
				showProgress(rl);
				super.onPreExecute();
			}
			@Override
			protected void onPostExecute(List result) {
				hideProgress(rl);
				super.onPostExecute(result);
				 adapter = new MyAdapter(getApplicationContext(),result);
				 listview.setAdapter(adapter);
				//adapter.notifyDataSetChanged();
			}
			@Override
			protected List doInBackground(Void... params) {
				JsonUtil jsut = new JsonUtil();
				try {
					 
					 String path ="http://tvsrv.webhop.net:8080/api/programs?page="+page2+"&count="+count2+"";
					 mData =jsut.getMovie(path);
					for (int i = 0; i < mData.size(); i++) {
						Program p = mData.get(i);
						if((p.getId())==0){
							mData.remove(i);
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return mData;
			}
			
		}.execute();
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
//		protected void onItemClick(ListView l, View v, int position, long id) {		
//			Intent intent = new Intent(MyFavProgramActivity.this,NewFileActivity.class);
//			Program movie = mData.get(position);
//			intent.putExtra("movieinfo", movie);
//			startActivity(intent);
//		}	
		public final class ViewHolder{
			public ImageView img;
			public TextView title;
			public TextView actor;
			public TextView keyword;
		}
		class MyAdapter extends BaseAdapter{

			private LayoutInflater mInflater;
			private List<Program> mData;
			
			
			public MyAdapter(Context context,List result){
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
				ImageDownloder imageDownloder = new ImageDownloder();
				Bitmap bitmap = null;
				try {
					bitmap = imageDownloder.imageDownloder(mData.get(position).getImagePath());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(bitmap!=null){
				holder.img.setImageBitmap(bitmap);
				}else{
					holder.img.setImageResource(R.drawable.icon);
				}
				holder.title.setText(getApplication().getResources().getString(R.string.see_filename)+":"+(String)mData.get(position).getTitle());
				holder.actor.setText(getApplication().getResources().getString(R.string.see_actor)+":"+(String)mData.get(position).getActor());
				holder.keyword.setText(mData.get(position).getKey());

				return convertView;
			}
		}
}
