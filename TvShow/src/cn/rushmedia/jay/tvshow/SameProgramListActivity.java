package cn.rushmedia.jay.tvshow;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import cn.rushmedia.jay.tvshow.domain.AppData;
import cn.rushmedia.jay.tvshow.domain.Program;
import cn.rushmedia.jay.tvshow.util.ImageDownloder;
import cn.rushmedia.jay.tvshow.util.JsonUtil;

public class SameProgramListActivity extends BaseActivity {
	private   int programeid ;
	ViewHolder holder ;
	private JSONArray mData;	
	
	
	ListView listview;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.subject);
		AppData ap = (AppData) getApplication();
		ap.addActivity(this);
		try {
		 Intent it =getIntent();
		 programeid = it.getIntExtra("programid", 0);
		JsonUtil jsut = new JsonUtil();
			String path = "http://tvsrv.webhop.net:8080/api/programs/"+programeid+"/topics";
			mData =jsut.getSource(path);
			for (int i = 0; i < mData.length(); i++) {
				JSONObject jsprogram = mData.getJSONObject(i);
				//jsprogram.
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MyAdapter adapter = new MyAdapter(this);
		ListView listView =(ListView) findViewById(R.id.androidlist);
		listView.setAdapter(adapter);
	}
	
	// ListView 中某项被选中后的逻辑

	/**
	 * listview中点击按键弹出对话框
	 */
	public void showInfo(){
		new AlertDialog.Builder(this)
		.setTitle("我的listview")
		.setMessage("介绍...")
		.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		})
		.show();
		ImageButton backtohome = (ImageButton) findViewById(R.id.backtohome);
    	backtohome.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),TableActivity.class);
				startActivity(i);
			}
		});
		
	}
	
	
	
	public final class ViewHolder{
		public ImageView img;
		public TextView title;
		public TextView actor;
		public TextView keyword;
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

	
	
	public class MyAdapter extends BaseAdapter{

		private LayoutInflater mInflater;
		
		
		public MyAdapter(Context context){
			this.mInflater = LayoutInflater.from(context);
		}
		@Override
		public int getCount() {
			return mData.length();
		}
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			return null;
		}

//		@Override
//		public View getView(final int position, View convertView, ViewGroup parent) {
//			
//			holder=new ViewHolder();  
//			if (convertView == null) {
//			
//				convertView = mInflater.inflate(R.layout.subject_replay, null);
//				holder.img = (ImageView)convertView.findViewById(R.id.image);
//				holder.title = (TextView)convertView.findViewById(R.id.title);
//				holder.actor = (TextView)convertView.findViewById(R.id.actor);
//				holder.keyword=(TextView) convertView.findViewById(R.id.keyword);
//				convertView.setTag(holder);
//			}else {
//				holder = (ViewHolder)convertView.getTag();
//			}
//		
//				new AsyncTask<Void, Void, Bitmap>(){
//					@Override
//					protected void onPostExecute(Bitmap result) {
//						holder.img.setImageBitmap(result);
//						super.onPostExecute(result);
//					}
//					@Override
//					protected Bitmap doInBackground(Void... params) {
//						Bitmap bitmap;
//						try {
//							ImageDownloder imageDownloder = new ImageDownloder();
//							bitmap = imageDownloder.imageDownloder(mData.get(position).getImagePath());
//							return bitmap;
//						} catch (Exception e) {
//							e.printStackTrace();
//							return null;
//						}	
//					}
//				}.execute();
//			holder.title.setText(getApplication().getResources().getString(R.string.see_filename)+":"+(String)mData.get(position).getTitle());
//			holder.actor.setText(getApplication().getResources().getString(R.string.see_actor)+":"+(String)mData.get(position).getActor());
//			holder.keyword.setText(mData.get(position).getKey());
//			return convertView;
//		}
	}

}
