package cn.rushmedia.jay.tvshow.util;
import java.util.ArrayList;
import java.util.List;

import cn.rushmedia.jay.tvshow.R;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

public class ListViewAdapter extends BaseAdapter {
   //listview要显示的数据,需要从服务器中取，这里先直接有
	private ArrayList arrayList;
	private ListView listView;
	private LayoutInflater mInflater;
	private  LayoutInflater  inflater;
	public ListViewAdapter(Context context, ListView listView, ArrayList arrayList) {
		this.listView = listView;
		this.arrayList=arrayList;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arrayList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return arrayList.get(position);
	}
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		// 默认的图片的值
		//ViewCache viewCache;
		if (view == null) {
			view = mInflater.inflate(R.layout.subject_replay, null);
			
			

		} else {
			
		}
		TextView txtreply = (TextView) view.findViewById(R.id.replay);
		txtreply.setText(arrayList.get(0).toString());
		TextView txttime = (TextView) view
				.findViewById(R.id.curtime);
		txttime.setText(arrayList.get(1).toString());
		//现在需要把用户的头像下载下来
		//得到相应的图像的uri
		//imageUri=JsonParse(url).getimg();
		//in=down(imageUri) 得到相应的流，然后用bitmapfactory的方法得到相应的图片
		//bm=bitmapParse(in);
		//为防止图片过大，内存溢出，需要对图片进行设置，下面伪代码
		/**
		 *  
           BitmapFactory.Options options = new BitmapFactory.Options();   
           options.inSampleSize=2;//图片高宽度都为原来的二分之一，即图片大小为原来的大小的四分之一   
           options.inTempStorage = new byte[5*1024]; //设置16MB的临时存储空间（不过作用还没看出来，待验证）   
           Bitmap bitMap = BitmapFactory.decodeFile(文件路径, options);    
i          mageView.setImageBitmap(bitMap);
		 */
		//ImageView userview=(ImageView) view.findViewById(R.id.user_img);  
		//userview.setImageBitmap(bm);
		//这个图像可以缓存到sd卡中，以便于节省流量
        return view;
	}

}
