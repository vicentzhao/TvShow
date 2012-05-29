package cn.rushmedia.jay.tvshow;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.rushmedia.jay.tvshow.domain.AppData;
import cn.rushmedia.jay.tvshow.domain.Tv;
import cn.rushmedia.jay.tvshow.util.ImageDownloder;
import cn.rushmedia.jay.tvshow.util.SourceFromResponse;

public class SearchChannelActivity extends BaseActivity {
	
	/**
	 * search channel
curl -X POST -H "Content-Type: application/json" \
-d '{"key":"cc"}' \
http://tvsrv.webhop.net:8080/api/search/channels
	 */
	
	private GridView gdView;
	private String path="http://tvsrv.webhop.net:8080/api/search/channels";
	private JSONArray js;
	private List<Tv> tvList;
	private ViewHolder holderView;
	private LayoutInflater inflater;
	private RelativeLayout rl;
	MyAdapter myAdapter;
	String keyword;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.channels);
		AppData ap= (AppData) getApplication();
		ap.addActivity(this);
		gdView =(GridView) findViewById(R.id.tv_tvshow_tvchannelshow);
		tvList = new ArrayList<Tv>();
		myAdapter= new MyAdapter();
		gdView.setAdapter(myAdapter);
		Intent it =getIntent();
		rl=(RelativeLayout)findViewById(R.id.loading);
		keyword= it.getStringExtra("compath");
		String json= "{\"key\":\""+keyword+"\"}";
		SourceFromResponse sr = new SourceFromResponse();
		String source = sr.getSource(json, path);
		if(!"".equals(source)){
				
				try {
					js =new JSONArray(source);
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
			    for (int i = 0; i < js.length(); i++) {
					try {
						JSONObject jsonObject =js.getJSONObject(i);
						String name =jsonObject.getString("name");
						String imagePath = jsonObject.getString("image");
						int id= jsonObject.getInt("id");
						Tv tv = new Tv();
						tv.setName(name);
						tv.setImagePath(imagePath);
						tv.setId(id);
						tvList.add(tv);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
		}else{
			Toast.makeText(SearchChannelActivity.this, "没有相关的节目", 1).show();
			finish();
		}
	
//		ImageButton backtohome = (ImageButton) findViewById(R.id.backtohome);
//    	backtohome.setOnClickListener(new OnClickListener() {
//			public void onClick(View v) {
//				Intent i = new Intent(getApplicationContext(),TableActivity.class);
//				startActivity(i);
//			}
//		});
	    gdView.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
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
		public ImageView tv_tvshow_tvchannelimage;
		public TextView tv_tvshow_tvchannelname;
	}
	 private class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return tvList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			holderView = new ViewHolder();
			if(convertView==null){
				inflater =LayoutInflater.from(SearchChannelActivity.this);
				convertView=inflater.inflate(R.layout.channels_item, null);
				holderView.tv_tvshow_tvchannelimage=(ImageView)convertView.findViewById(R.id.tv_tvshow_tvchannelimage);
				holderView.tv_tvshow_tvchannelname =(TextView) convertView.findViewById(R.id.tv_tvshow_tvchannelname);
				
				convertView.setTag(holderView);
			}else{
				holderView =(ViewHolder) convertView.getTag();
			}
			 holderView.tv_tvshow_tvchannelname.setText(tvList.get(position).getName());
			 ImageDownloder idDownloder= new ImageDownloder();
			 try {
				holderView.tv_tvshow_tvchannelimage.setImageBitmap(idDownloder.imageDownloder(tvList.get(position).getImagePath()));
			} catch (Exception e) {
				e.printStackTrace();
			}
			return convertView;
		}
	 }

}
