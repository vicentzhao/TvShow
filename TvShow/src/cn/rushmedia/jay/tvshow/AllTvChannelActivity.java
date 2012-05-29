package cn.rushmedia.jay.tvshow;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.TextView;
import cn.rushmedia.jay.tvshow.domain.Tv;
import cn.rushmedia.jay.tvshow.util.ImageDownloder;
import cn.rushmedia.jay.tvshow.util.JsonUtil;

public class AllTvChannelActivity extends Activity {
	private GridView gdView;
	private String path="http://tvsrv.webhop.net:8080/api/channels";
	private JSONArray js;
	private List<Tv> tvList;
	private ViewHolder holderView;
	private LayoutInflater inflater;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.channels);
		gdView =(GridView) findViewById(R.id.tv_tvshow_tvchannelshow);
		tvList = new ArrayList<Tv>();
		ChannelAdapter myAdapter= new ChannelAdapter();
		gdView.setAdapter(myAdapter);
		JsonUtil ju = new JsonUtil();
	    try {
			js = ju.getSource(path);
		} catch (Exception e1) {
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
	    gdView.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
			}
		});

}
	public final class ViewHolder{
		public ImageView tv_tvshow_tvchannelimage;
		public TextView tv_tvshow_tvchannelname;
	}
	 private class ChannelAdapter extends BaseAdapter{
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
				
				inflater =LayoutInflater.from(AllTvChannelActivity.this);
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
			return convertView;
		}
	 }
}